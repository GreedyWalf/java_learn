package thread.testThread2.test;

/**
 * 测试2：死锁
 *
 * 执行第一个线程时，获得锁A，在sleep时候，第二个线程获得锁B，然后等待获得锁A，这时，第一个线程在等待获得锁B，然后就成功死锁了。。
 *
 * 避免死锁：
 *  1、避免一个线程同事获取多个锁；
 *  2、避免一个线程在锁内同事占用多个资源，尽量保证每个锁只占用一个资源；
 *  3、尝试使用定时锁，使用lock.tryLock(timeout)来替代使用内部锁机制；
 *  4、对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况；
 *
 */
public class DeadLockDemo {

    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }

}
