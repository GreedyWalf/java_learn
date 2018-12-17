package thread.testThread3.ch4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * AtomicIntegerArray：不加锁的array
 */
public class AtomicIntegerArrayDemo {

    //定义一个包含10个元素的数组
    static AtomicIntegerArray array = new AtomicIntegerArray(10);

    //定义10个线程，倒计数
    final static CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 10000; k++) {
                    //每个线程都会将数组中10个元素自增1，在线程安全的情况下，最后数组输出应该为每个元素值都是10000
                    array.getAndIncrement(k % array.length());
                }

                countDownLatch.countDown();
            });
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

        //等待10个线程倒计数（等待线程执行完）
        countDownLatch.await();
        System.out.println(array);
    }
}
