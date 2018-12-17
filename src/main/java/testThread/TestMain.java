package testThread;

/**
 * 测试：什么是线程共同处理资源？继承Thread类和实现Runnable接口之间的区别。
 */
public class TestMain {

    public static void main(String[] args) {
        TestMain testMain = new TestMain();
//        testMain.test();
        testMain.test2();
//        testMain.test3();
//        testMain.test4();
    }


    /**
     * 测试：开启两个线程，分别执行两个线程任务对象，不存在线程安全问题，因为两个任务对象数据相互独立；
     */
    public void test() {
        Thread thread = new Thread(new ThreadTask());
        Thread thread2 = new Thread(new ThreadTask());
        thread.start();
        thread2.start();
    }

    /**
     * 测试：开启两个线程，执行同一个线程任务，线程任务对象中count属性作为竞争资源，并发读写时会出现实际值和预期值不一致的情况，需要考虑线程安全问题；
     */
    public void test2() {
        ThreadTask task = new ThreadTask();
        Thread thread = new Thread(task);
        Thread thread2 = new Thread(task);

        thread.start();
        thread2.start();
    }


    /**
     * 测试：使用继承Thread类的方式，创建不同的线程，分别执行start()方法，两个线程对象相互独立，不存在线程安全问题；
     */
    public void test3() {
        MyThread thread = new MyThread();
        MyThread thread2 = new MyThread();
        thread.start();
        thread2.start();
    }


    /**
     * 测试：将Thread对象，作为线程任务传递，也可以做到多个线程共享一份资源，属性count为共享成员变量，存在线程安全问题；
     */
    public void test4() {
        MyThread taskThread = new MyThread();

        Thread thread = new Thread(taskThread, "线程1");
        Thread thread2 = new Thread(taskThread, "线程2");
        thread.start();
        thread2.start();
    }
}

/**
 * 实现Runnable接口，定义线程任务；
 */
class ThreadTask implements Runnable {
    //注意static修饰的变量，虽然静态成员在类加载时候只初始化一次，但是静态变量是类和该类所有实例共享的，就算多个线程不共享一个线程任务，也会出现问题（当然这种写法本身就存在问题）
    // private static int count = 0;
    private int count = 0;

    @Override
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-->count=" + count);
            count++;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "==>>result=" + count);
    }
}


/**
 * 继承Thread类，自定义线程类
 */
class MyThread extends Thread {

    // private static int count = 0;
    private int count = 0;

    @Override
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-->count=" + count);
            count++;
        }


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "==>>result=" + count);
    }
}
