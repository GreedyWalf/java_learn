package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static class Soldier implements Runnable {

        private String soldierName;

        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic, String soldierName) {
            this.cyclic = cyclic;
            this.soldierName = soldierName;
        }


        @Override
        public void run() {
            try {
                //等待所有士兵到齐
                System.out.println("--->>cyclic.count=" + cyclic.getNumberWaiting());
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            SleepUtils.second(new Random().nextInt(10));
            System.out.println(soldierName + ":任务完成");
        }

    }


    public static class BarrierRun implements Runnable {
        private boolean flag;
        private int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令:[士兵" + N + "个，任务完成！");
            } else {
                System.out.println("司令:[士兵" + N + "个，集合完毕！");
                flag = true;
            }
        }
    }


    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        //设定10个线程完成后，执行BarrierRun中定义的线程任务；
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(false, N));
        System.out.println("集合队伍！");

        for (int i = 0; i < N; i++) {
            System.out.println("士兵 " + i + " 报道！");
            allSoldier[i] = new Thread(new Soldier(cyclic, "士兵 " + i));
            allSoldier[i].start();
        }
    }
}


/**
 * 业务场景：去饭店吃饭，10个厨师同时开始做菜，10个菜做好了，顾客在吃饭之前，🙏感谢上帝给我饭吃。。。
 */
class DoCooking implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private String cookerName;

    public DoCooking(CyclicBarrier cyclicBarrier, String cookerName) {
        this.cyclicBarrier = cyclicBarrier;
        this.cookerName = cookerName;
    }

    @Override
    public void run() {
        try {
            System.out.println(cookerName + ": 开始做菜了。。");
            SleepUtils.second(1);
            System.out.println(cookerName + ": 做好了。。");

            //每个线程执行完之后，在开始等待，知道最后一个线程执行完之后，唤醒cyclicBarrier，然后执行构造方法中定义的线程任务
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("顾客开始祈祷：感谢上帝赐予我事物，我要吃饭了。。。");
        });

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new DoCooking(cyclicBarrier, "厨师" + i));
            threads[i].start();
//            if (i == 3) {
//                threads[3].interrupt();
//            }
        }
    }
}
