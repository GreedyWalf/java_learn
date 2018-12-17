package thread.testThread2.test2.waitNotify;

import thread.testThread2.test2.utils.SleepUtils;

/**
 * 测试：线程等待/唤醒/等待超时
 */
public class WaitTimeOut {
    private static boolean flag = true;

    //定义一个锁对象
    private static Object lock = new Object();

    //一个线程处于等待状态，设置等待超时时间，另一个线程在超时之前没有唤醒，等待线程会自动唤醒并继续执行业务逻辑；
    private static void test(long timeOut) {
        System.out.println("-->>" + Thread.currentThread().getName() + ": 设置的过时时间为：" + timeOut);

        Thread waitThread = new Thread(() -> {
            long future = System.currentTimeMillis() + timeOut;
            long remaining = timeOut;
            synchronized (lock) {
                while (flag && remaining > 0) {
                    try {
                        System.out.println("-->>等待中。。");
                        lock.wait(timeOut);
                        remaining = future - System.currentTimeMillis();
                        System.out.println("--->> remaining=" + remaining);
                        System.out.println("-->>已经被唤醒啦。。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "WaitThread");
        waitThread.start();

        //中间main线程处理了一些业务，耗时5s，那么waitThread在2s后，等待就超时了，就会继续执行。
        SleepUtils.second(5);

        Thread notifyThread = new Thread(() -> {
            synchronized (lock) {
                System.out.println("线程:" + Thread.currentThread().getName() + "开始通知了。。");
                lock.notifyAll();
                flag = false;
            }

        }, "NotifyThread");
        notifyThread.start();
    }


    public static void main(String[] args) {
        test(2000);
    }

}
