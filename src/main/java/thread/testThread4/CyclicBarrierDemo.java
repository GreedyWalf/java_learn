package thread.testThread4;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试：并发控制工具-循环栅栏（CyclicBarrier）使用
 * 模拟业务：10个厨师对应10个线程开始做菜，10个菜做好了，顾客开始吃饭；
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        //实例化cyclicBarrier，第一个参数：指定等待线程数
        // 第二参数：定义barrierAction，当等待的线程达到指定线程数时，等待的线程会被唤醒，然后执行这里定义的线程任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> System.out.println("所有厨师将饭菜做好了，顾客开始吃饭了。。"));

        Thread[] threads = new Thread[10];
        CookingTask task = new CookingTask(cyclicBarrier);
        //开启10个线程
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task, "cookier-thread-" + i);
            threads[i].start();

            //10个线程，如果有一个线程中断退出了，则运行结果会出现1个线程出现InterruptedException异常，9个线程一直处于等待状态；
           /* if (i == 5) {
                threads[i].interrupt();
            }*/
        }
    }
}

/**
 * 定义厨师做菜线程任务
 */
class CookingTask implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public CookingTask(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "：开始做饭了。。。");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() + "：饭做好了。。。");

            //System.out.println("线程计数：" + cyclicBarrier.getNumberWaiting());
            //线程计数，每个线程执行线程任务后，在这里等待所有线程都执行完成
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


