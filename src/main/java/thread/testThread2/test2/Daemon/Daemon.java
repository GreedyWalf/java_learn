package thread.testThread2.test2.Daemon;

import thread.testThread2.test2.utils.SleepUtils;

/**
 * 测试2：测试守护线程执行
 * 当main方法执行完成后，虚拟机要退出了，所有的守护线程也会终止，终止也可能造成Daemon线程任务没执行完；
 */
public class Daemon {

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                //在构建Daemon线程时，不能依靠finally块中来确保执行关闭活清除资源的逻辑
                System.out.println("DaemonThread finally run.");
            }
        }
    }


    /**
     * 程序没有任何输出：
     * main线程启动了DaemonRunner线程之后，main方法执行完毕后终止，此时java虚拟器退出，java中所有Daemon线程都需要终止，造成Daemon线程立即终止了，没有执行finally代码块；
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        //设置线程为守护线程
        thread.setDaemon(true);
        thread.start();
    }

}
