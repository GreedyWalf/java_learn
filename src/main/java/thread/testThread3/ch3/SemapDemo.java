package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量（Semaphore）
 * 可以定义许可，一次性允许多个许可范围内的线程获取许可，去执行线程任务；
 * 许可数已满时，其他线程会进行等待获得许可，已经执行完线程任务时，可以释放许可；
 */
public class SemapDemo implements Runnable {

    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {

        try {
            //获取许可
            semp.acquire();
            SleepUtils.second(2);
            System.out.println("-->>" + Thread.currentThread().getName() + ", 执行任务完成！");
            //释放许可
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemapDemo semapDemo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            executorService.execute(semapDemo);
        }
    }
}
