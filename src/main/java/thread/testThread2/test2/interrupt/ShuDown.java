package thread.testThread2.test2.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 测试4_2：如何安全、优雅的结束一个线程（两种方式停止一个线程，都比较优雅哈，相比用stop()来终止一个线程，线程可以安全的停止）
 */
public class ShuDown {

    private static class Runner implements Runnable {
        private long i;

        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }

            System.out.println("currentThread=" + Thread.currentThread().getName()
                    + ",Count i= " + i + ",isInterrupted=" + Thread.currentThread().isInterrupted());
        }

        public void cancel() {
            on = false;
        }
    }

    /**
     * 使用终端和标志位两种方式去停止线程
     */
    public static void main(String[] args) throws InterruptedException {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread1");
        countThread.start();
        //睡眠1s，main线程对CountThread进行中断，使CountThread能够感知到中断而结束
        TimeUnit.SECONDS.sleep(1);
        //主线程中通知该线程要中断了，该线程在自己准备好了，就响应中断请求
        countThread.interrupt();

        //使用一个标识位控制线程终止（实际就是满足条件让线程执行完，线程有机会去清理资源，这种方式更优雅）
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread2");
        countThread.start();
        //睡眠1s，main线程对Runner two进行取消，是CountThread能够感知到on为false而结束
        TimeUnit.SECONDS.sleep(1);
        two.cancel();

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("isInterrupted=" + Thread.currentThread().isInterrupted());
            }
        },"thread3");
        thread3.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("正常执行完成的线程：" + thread3.isInterrupted()); //false
    }
}
