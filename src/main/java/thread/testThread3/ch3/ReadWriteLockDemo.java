package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 使用读写锁，读锁之间是并行，读与写之间是互斥的，写与写之间也是互斥的；
 */
public class ReadWriteLockDemo {

    private static Lock lock = new ReentrantLock();

    //实例化读写锁
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //读操作锁
    private static Lock readLock = readWriteLock.readLock();
    //写操作锁
    private static Lock writeLock = readWriteLock.writeLock();

    private int value;


    public Object handleRead(Lock lock) {
        try {
            lock.lock();  //模拟读操作
            System.out.println("-->>" + Thread.currentThread().getName());
            SleepUtils.second(1);
            return value;
        } finally {
            lock.unlock();
        }
    }


    public void handleWrite(Lock lock, int index) {
        try {
            lock.lock();
            System.out.println("-->>" + Thread.currentThread().getName());
            SleepUtils.second(1);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(readLock);
//                    demo.handleRead(lock);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleWrite(writeLock, new Random().nextInt());
//                demo.handleWrite(lock, new Random().nextInt());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }


        for (int i = 18; i < 20; i++) {
            new Thread(writeRunnable).start();
        }
    }
}
