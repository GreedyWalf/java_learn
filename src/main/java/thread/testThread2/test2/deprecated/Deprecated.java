package thread.testThread2.test2.deprecated;

import thread.testThread2.test2.utils.SleepUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 测试3：控制线程运行的三个过期方法
 * suspend(): 暂停；
 * resume(): 恢复；
 * stop(): 停止；
 * <p>
 * 过期不建议使用：
 * 线程suspend()不会释放已经占用的资源（比如锁），而是占着资源进行睡眠状态，容易引发死锁问题。
 * 线程stop()不会保证线程资源正常释放，通常没有给与线程完成资源释放工作的机会，会导致程序可能处于不确定状态下；
 *
 * @since 2018年08月28日11:08:54
 */
public class Deprecated {

    static class Runner implements Runnable {
        @Override
        public void run() {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                System.out.println(Thread.currentThread().getName() + " Run as " + format.format(new Date()));
                SleepUtils.second(1);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintRunner");
        printThread.setDaemon(true);
        printThread.start();

        //主线程睡眠3s，让printThread线程打印输出3秒
        TimeUnit.SECONDS.sleep(3);

        //将PrintThread进行暂停，输出内容工作停止
        printThread.suspend();
        System.out.println("main线程暂停了PrintThread线程，时间：" + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        printThread.resume();
        System.out.println("main线程恢复了PrintThread线程，时间：" + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        printThread.stop();
        System.out.println("main线程停止了PrintThread线程，时间：" + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
    }
}
