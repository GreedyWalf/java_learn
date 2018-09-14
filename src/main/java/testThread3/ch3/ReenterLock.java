package testThread3.ch3;

import testThread2.test2.utils.SleepUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            lock.lock();
            try {
                i++;
                System.out.println(Thread.currentThread().getName() + ": " + i);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock task = new ReenterLock();
        Thread thread = new Thread(task);
        Thread thread2 = new Thread(task);
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();
    }
}

/**
 * 重入锁和synchronized相比，可以响应中断，好处就是可以避免可能的死锁的发生
 * <p>
 * 下面程序执行顺序：
 * 首先thread-r1获得lock1锁，然后睡眠；然后thread-r2获得lock2锁，然后睡眠；
 * 接下来，thread-r1在等待thread-r2释放lock2锁，thread-r2等待thread-r1释放lock1锁，然后就死锁了；
 * 主线程休眠2s后，对thread-r2发起中断，thread-r2响应中断后，释放了lock2锁，并开始退出线程任务；
 * 此时thread-r1就可以拿到lock2锁，完成线程任务，后退出；
 */
class IntLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    private int lock;


    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                //lockInterruptibly 在等待锁的过程中，可以响应中断
                lock1.lockInterruptibly();
                System.out.println("-->>" + Thread.currentThread().getName() + ":获得lock1锁");
                SleepUtils.second(1);
                lock2.lockInterruptibly();
                System.out.println("-->>" + Thread.currentThread().getName() + ":获得lock2锁");
            } else {
                lock2.lockInterruptibly();
                System.out.println("-->>" + Thread.currentThread().getName() + ":获得lock2锁");
                SleepUtils.second(1);
                lock1.lockInterruptibly();
                System.out.println("-->>" + Thread.currentThread().getName() + ":获得lock1锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
                System.out.println("-->>" + Thread.currentThread().getName() + ":释放lock1锁");
            }

            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
                System.out.println("-->>" + Thread.currentThread().getName() + ":释放lock2锁");
            }

            System.out.println(Thread.currentThread().getName() + ":线程退出");
        }
    }


    public static void main(String[] args) {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);

        Thread thread = new Thread(r1, "thread-r1");
        Thread thread2 = new Thread(r2, "thread-r2");
        thread.start();
        thread2.start();
        SleepUtils.second(2);
        //中断其中一个线程
        thread2.interrupt();
    }
}

/**
 * 锁申请等待超时
 */
class TimeLock implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            //tryLock方法进行一次限时的等待
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println("-->>>" + Thread.currentThread().getName() + ": 获得锁");
                //占用着锁，睡6s
                SleepUtils.second(6);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("--->>" + Thread.currentThread().getName() + ": 已经释放锁了，其他线程可以去拿了。。");
            } else {
                System.out.println("-->>" + Thread.currentThread().getName() + "，一直未获得锁，等带超时了。");
            }
        }
    }


    public static void main(String[] args) {
        TimeLock task = new TimeLock();
        Thread thread = new Thread(task, "thread-task1");
        Thread thread2 = new Thread(task, "thread-task2");

        thread.start();
        thread2.start();
    }
}

/**
 * tryLock()尝试获得锁，不会等待，没有获得锁会返回false，这样可以有效避免死锁发生；
 */
class TryLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    private int lock;


    public TryLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == 1) {
            while (true) {
                try {
                    if (lock1.tryLock()) {
                        SleepUtils.second(1);

                        if (lock2.tryLock()) {
                            SleepUtils.second(1);
                            System.out.println("-->>" + Thread.currentThread().getName() + "：my job done");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }

                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }
            }
        } else {
            while (true) {
                try {
                    if (lock2.tryLock()) {
                        SleepUtils.second(1);

                        if (lock1.tryLock()) {
                            SleepUtils.second(1);
                            System.out.println("-->>" + Thread.currentThread().getName() + "：my job done");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }

                    if (lock1.isHeldByCurrentThread()) {
                        lock1.unlock();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        TryLock r1 = new TryLock(1);
        TryLock r2 = new TryLock(2);

        Thread t1 = new Thread(r1, "thread-r1");
        Thread t2 = new Thread(r2, "thread-r2");

        t1.start();
        t2.start();
    }
}


/**
 * 公平锁
 */
class FairLock implements Runnable {
    //参数true，表示当前锁为公平锁
    private static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println("-->>" + Thread.currentThread().getName() + ": 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    //输出结果：2个线程会交替获得锁，基本上不会出现一个线程多次获得锁情况；
    public static void main(String[] args) {
        FairLock r = new FairLock();

        Thread thread = new Thread(r, "thread-r1");
        Thread thread2 = new Thread(r, "thread-r2");
        thread.start();
        thread2.start();
    }
}






