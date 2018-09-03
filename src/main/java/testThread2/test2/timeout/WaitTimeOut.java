package testThread2.test2.timeout;

import testThread2.test2.utils.SleepUtils;

public class WaitTimeOut {

    private static int result = 0;

    private static final Object lock = new Object();


    private static int getResources(long timeout) {
        long future = System.currentTimeMillis() + timeout;
        long remaining = timeout;
        synchronized (lock) {
            while (result <= 0 && remaining > 0) {
                try {
                    lock.wait();
                    remaining = future - System.currentTimeMillis();
                    System.out.println("-->>线程被唤醒了，result=" + result);
                    if (result > 0) {
                        result += 10;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }
    }


    public static void main(String[] args) {
        int resources = WaitTimeOut.getResources(10000);
        System.out.println("resource= " + resources);

        Thread thread = new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " get lock...");
                lock.notifyAll();
                result = 100;
            }
        }, "writeResultThread");

        thread.start();
    }
}
