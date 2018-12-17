package thread.testThread2.test2.waitNotify;

import thread.testThread2.test2.utils.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试5：测试wait()、notifyAll()
 * 注意细节：
 *  1）是用户wait、notify、notifyAll时，需要对调用对象加锁；
 *  2）调用wait方法后，线程状态由RUNNING变为WAITING，并将当前线程防止到对象的等待队列；
 *  3) notify或notifyAll方法调用后，等待线程依旧不会从wait处返回，需要等待notify或notifyAl的线程释放锁之后，等待线程才有机会从wait返回，继续执行；
 *  4）notify方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而notifyAll方法则是将等待队列中所有线程全部移到同步队列，被移动的线程状态由WATTING变为BLOCKED；
 *  5）从wait方法返回的前提是获得了调用对象的锁；
 */
public class WaitNotify {
    private static boolean flag = true;

    //定义一个锁对象
    private static Object lock = new Object();


    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + " flag is true @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        //调用wait方法，放弃锁，进入对象的等待队列（WaitQueue）中，进去等待状态，等到notidy线程唤醒
                        lock.wait();
                        System.out.println("测试下，是不是唤醒后，在等待处之后接着执行代码呢。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread() + " flag is false @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock. notify@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                //wait线程释放锁后，notify线程获取到锁，调用对象的notifyAll()方法，将wait线程从WaitQueue中移到SynchronizedQueue中，此时wait线程处于阻塞状态；
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            //再次获得锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " hold lock again. sleep@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
//        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
    }

}
