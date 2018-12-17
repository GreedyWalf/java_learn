package thread.testThread3.ch4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS：使用AtomicInteger类，实现计数
 */
public class AtomicIntegerDemo {

    //使用原子类，线程安全，共用成员变量，无需加锁
    static AtomicInteger i = new AtomicInteger();

    static CountDownLatch countDownLatch = new CountDownLatch(10);

//    static int i;

    static class AddThread implements Runnable {

        @Override
        public void run() {
            for (int k = 0; k < 10000; k++) {

                //使用CAS操作将自己加1，返回当前值；
                i.incrementAndGet();
//                i++;
            }

            countDownLatch.countDown();
        }
    }


    //线程安全会返回10000，线程不安全输出结果小于10000
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new AddThread());
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }

        //等待开启的10个线程执行完
//        for (int i = 0; i < 10; i++) {
//            threads[i].join();
//        }

        countDownLatch.await();
        System.out.println("i=" + i);
    }
}
