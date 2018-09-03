package testThread2.test2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 测试：Semaphore（控制并发线程数）
 *
 * 启动30个线程，并发往数据库存储，而数据库可用的链接数只有10个，可以使用Semaphore来做流量控制；
 */
public class SemaphoreTest {
    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    //设置信号量，只允许10个并发执行，只允许10个线程获取许可证
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            //运行30个线程
            threadPool.execute(() -> {
                try {
                    //线程首先使用semaphore.acquire()获取一个许可证
                    s.acquire();
                    System.out.println("save data...");
                    //使用完之后，归还许可证；
                    s.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
    }
}
