package base.thread;


/**
 * 2、通过集成Thread类创建新线程
 *
 *  创建一个线程的第二种方法是创建一个新的类，该类继承 Thread 类，然后创建一个该类的实例。
 *  继承类必须重写 run() 方法，该方法是新线程的入口点。它也必须调用 start() 方法才能执行。
 *  该方法尽管被列为一种多线程实现方式，但是本质上也是实现了 Runnable 接口的一个实例。
 *
 */
public class ThreadTest2 {
    public static void main(String[] args) {
        ThreadDemo t1 = new ThreadDemo("thread-1");
        t1.start();

        ThreadDemo t2 = new ThreadDemo("thread-2");
        t2.start();
    }
}

class ThreadDemo extends Thread {
    private Thread thread;
    private String threadName;


    ThreadDemo(String name) {
        threadName = name;
        System.out.println("--Creating " + threadName);
    }

    @Override
    public void run() {
        System.out.println("--Running " + threadName);

        try {
            for(int i=4; i>0; i--){
                System.out.println("Thread: " + threadName + ", " + i);
                //让线程多睡一会哈
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread: " + threadName + " interrupted.. ");
        }

        System.out.println("Thread " + threadName + " exiting..");
    }

    public void start(){
        System.out.println("--Starting " + threadName);
        if(thread == null){
            thread = new Thread(this, threadName);
            thread.start();
        }
    }
}
