package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 重入锁结合Condition使用，组成线程等待唤醒机制；
 */
public class ReenterLockCondition implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("--->>" + Thread.currentThread().getName() + ",开始等待。。");
            condition.await();
            System.out.println("--->>" + Thread.currentThread().getName() + ",成功被唤醒了。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    //thread-r1线程先获得锁，然后释放锁进行等待，然后main线程获得锁，并唤醒操作后释放锁，
    //thread-r1线程再次获得锁后，接着等待地方往后执行程序；
    public static void main(String[] args) {
        ReenterLockCondition r = new ReenterLockCondition();

        Thread thread = new Thread(r, "thread-r1");
        thread.start();

        SleepUtils.second(2);

        lock.lock();
        System.out.println("-->>" + Thread.currentThread().getName() + ", 开始唤醒线程。。");
        condition.signal();
        lock.unlock();
    }

}


