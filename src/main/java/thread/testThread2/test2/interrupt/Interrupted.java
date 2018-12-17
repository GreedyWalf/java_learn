package thread.testThread2.test2.interrupt;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.concurrent.TimeUnit;

/**
 * 测试4：线程中断
 * <p>
 * 睡眠中的线程中断会抛出异常，并将中断标志位清除，isInterrupted()返回false；(可以理解为线程睡眠时被中断，根据isInterrupted()无法判断线程当前状态，因为一直为false)
 * 运行中的线程中断不会抛出异常，中断标志位不会清除，isInterrupted()返回true；
 *
 * @since 2018年08月28日10:49:22
 */
public class Interrupted {

    static class SleepRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }


    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // sleepThread不同的尝试睡眠
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);

        //busyThread不停的运行
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");

        sleepThread.start();
        busyThread.start();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted()); //false
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted()); //false

        //休眠5秒后，让sleepThread和BusyThread充分运行
        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());  //false
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted()); //true

        //防止sleepThread和busyThreadlike退出
        SleepUtils.second(2);
    }
}
