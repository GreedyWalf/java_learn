package testThread4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试：executorService.execute(Runnable T)
 * 初始化线程池，使用execute()方法执行线程任务
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //实例化包含10个线程的线程池，corePoolSize=10
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        PrintTask task = new PrintTask(1);
        for (int i = 0; i < 100; i++) {
            //execute方法参数为线程任务类实例，没有返回值；
            executorService.execute(task);
            //submit方法参数为Runnable的实现类实例，没有返回值，这里和execute方法相同
            //executorService.submit(task);
        }

        executorService.shutdown();
    }
}

/**
 * 定义线程任务
 */
class PrintTask implements Runnable {
    private static Lock lock = new ReentrantLock();

    private int count;

    public PrintTask(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + ": " + (count++));
        lock.unlock();
    }
}
