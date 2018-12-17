package thread.testThread4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.ms");
        Date date = new Date();
        String dateStr = sdf.format(date);

        ParseDateTask task = new ParseDateTask(sdf, dateStr);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
    }
}


class ParseDateTask implements Runnable {

    private static Lock lock = new ReentrantLock();

    private SimpleDateFormat sdf;

    private String dateStr;

    public ParseDateTask(SimpleDateFormat sdf, String dateStr) {
        this.sdf = sdf;
        this.dateStr = dateStr;
    }

    @Override
    public void run() {
        try {
//            lock.lock();
            //sdf.parse()方法不是线程安全的，可以使用加锁解决这个问题
            System.out.println(sdf.parse("2018-01-01 12:12:" + (int) (Math.random() * 9 + 10) + "." + (int) (Math.random() * 900) + 100));
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
//            lock.unlock();
        }
    }
}


class TestSafeDemo {
    public static void main(String[] args) {
        SafeDateParserTask task = new SafeDateParserTask();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(task);
        }
    }
}

/**
 * 线程安全的日期解析任务类
 */
class SafeDateParserTask implements Runnable {
    //使用ThreadLocal，确保每个线程实例化一个SimpleDateFormat对象，解决多线程并发，SimpleDateFormat安全性问题
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.mss"));

    @Override
    public void run() {
        try {
            Thread thread = new Thread();
            System.out.println(threadLocal.get().parse("2018-01-01 12:12:" + (int) (Math.random() * 9 + 10) + "." + (int) (Math.random() * 900) + 100));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


class TT {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

        longThreadLocal.set(Thread.currentThread().getId());
        stringThreadLocal.set(Thread.currentThread().getName());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                longThreadLocal.set(Thread.currentThread().getId());
                stringThreadLocal.set(Thread.currentThread().getName());

                System.out.println(longThreadLocal.get());
                System.out.println(stringThreadLocal.get());
            }
        });

        thread.start();
        thread.join();

        System.out.println(longThreadLocal.get());
        System.out.println(stringThreadLocal.get());
    }
}


