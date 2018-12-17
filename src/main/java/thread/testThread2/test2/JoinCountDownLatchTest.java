package thread.testThread2.test2;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 测试：使用多个线程解析excel，一个线程解析一个sheet，当excel中的sheet都
 * 解析完成后，给出提示；
 * <p>
 * join让当前线程等待join线程执行结束；实现原理：不停的检查join线程是否存活，如果join线程存活，
 * 则让当前线程永远等待（wait(0)），直到join线程终止后，jvm中会执行this.notify()唤醒等待中的
 * 线程，并且结束掉该线程；
 */
public class JoinCountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " running..");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " running..");
        });

        thread.start();
        thread2.start();

        //join用于让当前线程等待join线程执行结束；
        thread.join();
        thread2.join();
        System.out.println("all parser finish");
    }
}


/************  Java中的并发工具类 ***************/

/**
 * 测试：CountDownLatch（等待多线程完成）
 */
class CountDownLatchTest {
    //CountDownLatch的构造函数接收一个int类型的参数作为计数器，如果你想等待N个点完成，这里就传入N
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("1");
            //每次调用countDown()方法，N就会减1；（countDown方法可以在任何地方执行，可以是N个线程，也可以是一个线程的N个执行步骤）
            countDownLatch.countDown();
            System.out.println("2");
            countDownLatch.countDown();
        }).start();

        //滴啊用await()方法会阻塞当前线程，直到N变成0（N=0是不会阻塞当前线程）；
        countDownLatch.await();

        //设置等待阻塞的时间为3s，3s后返回，不会再阻塞当前线程；
        //countDownLatch.await(3, TimeUnit.SECONDS);
        System.out.println(3);
    }
}


/**
 * 测试：CyclicBarrier（同步屏障）
 */
class CyclicBarrierTest {
    //默认的构造方法，参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier，当前线程
    //已经到达了屏障，然后当前线程被阻塞，等待最后一个线程调用await方法；
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(1);
        });

        thread.start();
//        thread.interrupt();

        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            //true
            System.out.println("是否中断：" + cyclicBarrier.isBroken());
        }

        System.out.println(2);
    }
}


/**
 * 测试：CyclicBarrier的另一个高级的构造方法
 */
class CyclicBarrierTest2 {

    //设置拦截线程数量为2，指定等待完成的线程A，所以主线程会等待第一个线程和A线程执行完成后，再继续执行主线程，输出2；
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new A());

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(1);
        }).start();

        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(2);
    }


    static class A implements Runnable {

        @Override
        public void run() {
            System.out.println("3");
        }
    }
}

/**
 * 测试：CyclicBarrier应用场景
 * 假设excel中存在4个sheet需要解析银行流水，最后合并计算出结果；
 */
class BankWaterService implements Runnable {

    //创建4个屏障，处理完之后执行当前类的run方法
    private CyclicBarrier c = new CyclicBarrier(4, this);

    //假设只有4个sheet
    private Executor executor = Executors.newFixedThreadPool(4);

    //保存每个sheet计算出的银流结果
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();

    private void count() {
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            executor.execute(() -> {
                //计算当前sheet的银行流水数据，计算代码省略
                sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                System.out.println("i=" + finalI);
                try {
                    //银行流水计算完成，插入一个屏障
                    c.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
            result += sheet.getValue();
        }

        sheetBankWaterCount.put("result", result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}


//CyclicBarrier和CountDownLatch区别：CountCownLatch的计数器只能使用一次，而Cyclicbarier计数器
//可以使用reset()方法重置；所以CyclicBarrier能处理更为复杂的业务场景；（比如：如果计算发生错误，可以
//重置计数器。并让线程重新执行一次 ）