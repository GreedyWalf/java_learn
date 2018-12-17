package thread.testThread3.ch4;

import thread.testThread2.test2.utils.SleepUtils;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class PCData {

    private final int intData;

    public PCData(int intData) {
        this.intData = intData;
    }

    public PCData(String d) {
        this.intData = Integer.valueOf(d);
    }

    public int getIntData() {
        return intData;
    }

    @Override
    public String toString() {
        return "intData:" + intData;
    }
}


/**
 * 生产者
 */
class Producer implements Runnable {

    private volatile boolean isRunning = true;

    //内存缓冲区
    private BlockingQueue<PCData> queue;

    //总数
    private static AtomicInteger count = new AtomicInteger();

    private static final int SLEEPTIME = 1000;


    public Producer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        PCData data = null;
        Random r = new Random();
        System.out.println("start producer name=" + Thread.currentThread().getName());


        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(SLEEPTIME));
                data = new PCData(count.incrementAndGet());
                System.out.println(data + " is put into queue");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("failed to put data: " + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        isRunning = false;
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {

    private BlockingQueue<PCData> queue;

    private static final int SLEEPTIME = 1000;


    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer name=" + Thread.currentThread().getName());
        Random random = new Random();

        try {
            while (true) {
                PCData data = queue.take();
                int result = data.getIntData() * data.getIntData();
                System.out.println(MessageFormat.format("{0}*{1}={2}",
                        data.getIntData(), data.getIntData(), result));
                Thread.sleep(random.nextInt(SLEEPTIME));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}


class TestDemo {
    public static void main(String[] args) {
        BlockingQueue<PCData> queue = new LinkedBlockingDeque<>(10);

        Producer[] producers = new Producer[3];
        Consumer[] consumers = new Consumer[3];

        for (int i = 0; i < 3; i++) {
            producers[i] = new Producer(queue);
            consumers[i] = new Consumer(queue);
        }

        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            service.execute(producers[i]);
            service.execute(consumers[i]);
        }

        SleepUtils.second(3);
        producers[0].stop();
        producers[1].stop();
        producers[2].stop();
        SleepUtils.second(3);
        service.shutdown();

    }
}
