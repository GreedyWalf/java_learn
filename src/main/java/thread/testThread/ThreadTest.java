package thread.testThread;

/**
 * 线程安全问题实例（多个线程同时访问实例变量，出现线程安全问题）
 */
public class ThreadTest {
    public static void main(String[] args) {
        ThreadSafe threadSafe = new ThreadSafe();
//        for(int i=0; i<100; i++){
//            threadSafe.addSafe();
//        }
//
//        for(int i=0; i<100; i++){
//            threadSafe.addUnsafe();
//        }

        UnsafeT unsafeT = new UnsafeT(threadSafe);
        for(int i=0; i<10; i++){
            Thread unSafeThread = new Thread(unsafeT);
            unSafeThread.start();
        }

        SafeT safeT = new SafeT(threadSafe);
        for(int i=0; i<10; i++){
            Thread safeThread = new Thread(safeT);
            safeThread.start();
        }
    }
}

class ThreadSafe{
    //定义实例变量（实例变量在线程间是共享的，在多个线程同时访问时可能出现线程安全问题）
    private int countUnsafe = 0;
    private int countSafe = 0;

    //线程不安全的方法
    public void addUnsafe(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countUnsafe++;
        System.out.println("countUnsafe=" + countUnsafe);
    }

    //线程安全的方法（使用同步方法）
    public synchronized void addSafe(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countSafe++;
        System.out.println("countSafe=" + countSafe);
    }
}

//不安全线程测试类
class UnsafeT implements Runnable{

    private ThreadSafe threadSafe;

    public UnsafeT(ThreadSafe threadSafe){
        this.threadSafe = threadSafe;
    }

    @Override
    public void run() {
        threadSafe.addUnsafe();
    }
}

//安全线程测试类
class SafeT  implements Runnable{

    private ThreadSafe threadSafe;

    public SafeT(ThreadSafe threadSafe) {
        this.threadSafe = threadSafe;
    }

    @Override
    public void run() {
        threadSafe.addSafe();
    }
}