package testThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLockTest2 {
    private Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        ThreadLockTest2 test2 = new ThreadLockTest2();
        MyThread thread = new MyThread(test2);
        MyThread thread2 = new MyThread(test2);
        thread.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.interrupt();
    }


    public void insert(Thread thread) throws InterruptedException {
        //当第一个线程获取到了锁，当第二个线程等待获取锁时，可以响应第二个线程的interrupt动作，终端第二个线程等待锁过程
        lock.lockInterruptibly();
        try {
            System.out.println(thread.getName() + "得到了锁");
            long startTime = System.currentTimeMillis();
            //这里第一个线程拿到了锁，一直处于等待状态
            for(; ;){
                if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName() + "执行finally");
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁");
        }
    }

}

class MyThread extends Thread{
    private ThreadLockTest2 test2 = null;

    public MyThread(ThreadLockTest2 test2){
        this.test2 = test2;
    }

    @Override
    public void run() {
        try {
            test2.insert(Thread.currentThread());
        } catch (Exception e) {
            //第二个线程调用interrupt()方法，发生线程执行中断异常（）
            System.out.println(e);
            System.out.println(Thread.currentThread().getName() + "被中断");
        }
    }
}
