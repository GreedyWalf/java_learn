package testThread2.test2.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库连接池
 *
 * @author IluckySi
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<Connection>();

    /**
     * 初始化连接池
     *
     * @param initialSize
     */
    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 如果mills时间内无法获取到连接，返回null
     *
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 完全超时, 一直等待空闲链接.
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }

                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }

                Connection connection = null;
                if (!pool.isEmpty()) {
                    connection = pool.removeFirst();
                }

                return connection;
            }
        }
    }

    /**
     * 释放连接
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.add(connection);
                // 释放连接后需要进行通知, 这样其他消费者能够感知到连接池中已经归还了一个链接。
                pool.notifyAll();
            }
        }
    }

}

/**
 * 模拟数据库连接驱动
 * 即通过代理的方式，模拟向数据库提交数据需要100毫秒。
 *
 * @author IluckySi
 */
class ConnectionDriver {

    /**
     * 内部类
     * 代理类关联的InvocationHandler。
     *
     * @author IluckySi
     */
    static class ConnectionHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                TimeUnit.MICROSECONDS.sleep(100);
            }

            return null;
        }
    }

    /**
     * 创建一个Connection的代理, 在commit时休眠100毫秒.
     *
     * @return
     */
    public static final Connection createConnection() {
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[]{Connection.class}, new ConnectionHandler());
    }
}

/**
 * 等待/通知机制.
 * 等待超时模式的应用场景数据库连接池
 *
 * @author IluckySi
 */

class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(5);

    // 保证所有ConnectionRunner同时运行.
    static CountDownLatch prepare;
    static CountDownLatch finish;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量, 可以通过修改线程数量进行观察.
        int threadCount = 200;
        prepare = new CountDownLatch(1);
        finish = new CountDownLatch(threadCount);

        // 每个线程发起20次获取数据库连接的请求.
        int count = 20;
        AtomicInteger got = new AtomicInteger(0);
        AtomicInteger notGot = new AtomicInteger(0);

        // 开始测试...
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        prepare.countDown();
        finish.await();
        System.out.println("Total invoker:" + (threadCount * count));
        System.out.println("Got connection: " + got);
        System.out.println("NotGot connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            try {
                prepare.await();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            while (count > 0) {
                try {
                    // 从线程池中获取连接, 如果1000ms内无法获取到, 将会返回null,
                    // 分别统计链接获取的数量got和未获取连接的数量notGot.
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }

            finish.countDown();
        }
    }
}
