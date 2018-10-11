package testThread3.ch4;

import testThread2.test2.utils.SleepUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SimpleDateFormat中parse方法不是线程安全的，在不加锁的情况下，多线程并发访问会抛出异常
 * java.lang.NumberFormatException: multiple points
 */
public class ThreadLocalDemo {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static class ParseDate implements Runnable {
        private static Lock lock = new ReentrantLock();
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }


        @Override
        public void run() {
            try {
//                lock.lock();
//                synchronized (ThreadLocalDemo.class) {
                Date date = sdf.parse("2015-03-29 19:29:" + i % 60);
                System.out.println(i + ":" + date);
//                }
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
//                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            //每次new了一个线程任务对象，所以加锁时主要要不要用static关键字
            executorService.execute(new ParseDate(i));
        }

        executorService.shutdown();
    }
}


/**
 * ThreadLocal应用：构建线程安全的SimpleDateFormat
 */
class SafeDateFormatDemo {

    static CountDownLatch countDownLatch = new CountDownLatch(10);

    //使用ThreadLocal，确保每个线程实例化一个SimpleDateFormat对象，解决多线程并发，SimpleDateFormat安全性问题
    static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            System.out.println("-->>实例化");
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };

    static class ParseDate implements Runnable {
        int i = 0;

        ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Date date = threadLocal.get().parse("2015-03-29 19:29:" + i % 60);
                System.out.println(i + ":" + date);
                SleepUtils.second(2);
                System.out.println();
                System.out.println("--->>>" + countDownLatch.getCount());
                countDownLatch.countDown();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            //每次new了一个线程任务对象，所以加锁时主要要不要用static关键字
            executorService.execute(new ParseDate(i));
        }

        countDownLatch.await();
        System.out.println("-->>>10个线程执行完了。。");
        //线程执行完成后，移除threadLocal对象，防止内存泄露
        threadLocal.remove();
        executorService.shutdown();
    }
}




