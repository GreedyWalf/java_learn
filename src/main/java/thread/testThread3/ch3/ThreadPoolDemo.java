package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor框架--线程池使用
 */
public class ThreadPoolDemo {

    static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "： Thread Id："
                    + Thread.currentThread().getId());

            SleepUtils.second(1);
        }
    }


    /**
     * 最大5个线程同时执行，随后线程池中没有空闲线程，等待5个线程中执行完的线程，然后再开启线程，执行任务；
     * <p>
     * 输出结果：5个一组，中间休眠1s，然后，再次启动5个线程；
     */
    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        //创建一个线程池，包含5个线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executorService.submit(myTask);
        }

        executorService.shutdown();
    }
}
