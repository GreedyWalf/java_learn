package base.thread;

/**
 * 1、通过实现Runnable接口创建线程
 *
 *  定义一个类实现Runnable接口，实现接口中定义的run()方法；
 *
 *  Thread(Runnable threadOb,String threadName);  //还有这个构造方法。。
 */
public class ThreadTest {

    public static void main(String[] args) {
        RunnableDemo r1 = new RunnableDemo("Thread-1");
        r1.start();

        RunnableDemo r2 = new RunnableDemo("Thread-2");
        r2.start();
    }
}


class RunnableDemo implements Runnable {
    private Thread thread;
    private String threadName;

    RunnableDemo(String threadName) {
        this.threadName = threadName;
        System.out.println("Creating " + threadName);
    }

    @Override
    public void run() {
        System.out.println("Running " + thread);

        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                //让线程睡一会吧
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }

        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}