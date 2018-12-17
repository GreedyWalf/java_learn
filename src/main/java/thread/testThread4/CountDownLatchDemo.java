package thread.testThread4;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试：倒计时器（CountDownLatch）
 * 模拟业务：10个线程进行火箭发射前校验工作，10个线程都校验成功后，火箭开始发射。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //定义countDownLatch实例，初始化线程计数为10
        final CountDownLatch countDownLatch = new CountDownLatch(10);

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "：check completed");
                    //线程计数，每完成一个线程，计数器减1
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        //主线程在这里开始等待，当线程计数达到设定的线程数后唤醒
        countDownLatch.await();
        System.out.println("====>>>火箭发射啦！");
        service.shutdown();
    }
}