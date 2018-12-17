package thread.testThread2.test2.status;

import java.util.concurrent.TimeUnit;

/**
 * 测试1：线程状态
 * New: ﻿初始状态，线程被构建，但是还没有调用start()方法；
 * RUNNABLE: ﻿运行状态，java线程将操作系统中的就绪和运行两种状态称为“运行中”；
 * BLOCKED: 阻塞状态，表示线程阻塞于锁；
 * WAITING: ﻿等待状态，表示线程进入等待状态，进入该状态表示当前线程需要等待其他线程做出一些特定动作（通知或中断）；
 * TIME_WAITING: ﻿超时等待状态，该状态不同于WATING，它可以在指定时间自行返回；
 * TERMINATED: ﻿终止状态，表示当前线程已经执行完毕；
 */
public class ThreadStatus {

    //该线程不断的进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    //该线程在Wating.class实例上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //该线程在Blocked.class实例上加锁后，不会释放锁
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();

        //使用两个Block线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }
}


class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
