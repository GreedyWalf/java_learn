package testThread2.test2.demo.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 采用阻塞队列实现生产者消费者模式
 */
public class PublicBoxQueue {

    public static void main(String[] args) {
        BlockingQueue publicBoxQueue = new LinkedBlockingDeque(5);
        Thread pro = new Thread(new ProducerQueue(publicBoxQueue));
        Thread con = new Thread(new ConsumerQueue(publicBoxQueue));

        pro.start();
        con.start();
    }
}


class ProducerQueue implements Runnable {
    private final BlockingQueue proQueue;

    public ProducerQueue(BlockingQueue proQueue) {
        this.proQueue = proQueue;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("生产者生成的苹果编号为：" + i);
                proQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class ConsumerQueue implements Runnable {

    private final BlockingQueue conQueue;

    public ConsumerQueue(BlockingQueue conQueue) {
        this.conQueue = conQueue;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("消费者消费的苹果编号为：" + conQueue.take());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}