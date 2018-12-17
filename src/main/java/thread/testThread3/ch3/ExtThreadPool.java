package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.concurrent.*;

/**
 * 可以拓展线程池（ThreadPoolExecutor），线程池提供了执行前、执行完成等方法，可以重写该方法
 */
public class ExtThreadPool {

    static class MyTask implements Runnable {

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
//                System.out.println("正在执行：" + Thread.currentThread().getName());
                SleepUtils.second(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = new ExtThreadPoolExecutor(5, 5,
                0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Thread(new MyTask("线程" + i)));
        }

        //发出关闭信号，不在接受新线程任务
        executorService.shutdown();
    }


}


class ExtThreadPoolExecutor extends ThreadPoolExecutor {

    public ExtThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ExtThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ExtThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ExtThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println(t.getName() + "线程开始执行了。。");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println(Thread.currentThread().getName() + "线程执行完成了。。");
    }

    @Override
    protected void terminated() {
        System.out.println("--->>>termminated");
    }
}





