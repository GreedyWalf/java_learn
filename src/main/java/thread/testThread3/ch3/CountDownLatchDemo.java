package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器（CountDownLatch）
 *   指定线程数量线程执行线程任务，每个线程执行完成后，调用countDown()方法，
 */
public class CountDownLatchDemo implements Runnable {

    //生成一份CountDownLatch实例，计数数量为10，表示需要有10个线程完成任务
    private static final CountDownLatch end = new CountDownLatch(10);
    private static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            SleepUtils.second(new Random().nextInt(10));
            System.out.println("-->>" + Thread.currentThread().getName() + ",check complete");
            //10个线程，每执行完成一个之后，计数器就减1，当计数器为0时，会进行唤醒操作
            System.out.println(end.getCount());
            end.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(demo);
        }

        //要求主线程等待10个线程检查任务全部完成，在CountDownLatch计数为0之前，一直等待唤醒；
        end.await();
        System.out.println("Fire!");
        executorService.shutdown();
    }
}
