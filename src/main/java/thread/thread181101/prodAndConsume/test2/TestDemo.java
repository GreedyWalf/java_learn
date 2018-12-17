package thread.thread181101.prodAndConsume.test2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestDemo {

    public static void main(String[] args) {
        Depot depot = new Depot(100, 0);
        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        producer.produce(60);
        producer.produce(60);
        consumer.consume(120);
    }
}


class Depot {

    //定义仓库容量
    private int capacity;
    //仓库中实际产品数（多线程共享，属于竞争资源）
    private int size;

    //定义重入锁
    private static Lock lock = new ReentrantLock();
    //定义condition对象，绑定在该重入锁上
    private static Condition condition = lock.newCondition();

    public Depot(int capacity, int size) {
        this.capacity = capacity;
        this.size = size;
    }


    public void produce(int val) {
        //定义需要生成的产品数
        lock.lock();
        int left = val;
        try {
            while (left > 0) {
                while (size >= capacity) {
                    System.out.println(Thread.currentThread().getName() + "--仓库满了，开始等待了。。。");
                    condition.await();
                }

                int inc = capacity - size <= left ? capacity - size : left;
                left -= inc;
                size += inc;
                System.out.println(Thread.currentThread().getName() + "--成功生产了" + inc + "个产品。。" + "，size=" + size);
                //生产完成后，唤醒操作
                condition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int val) {
        lock.lock();
        int left = val;
        try {
            while (left > 0) {
                //仓库里没有产品，消费者开始等待
                while (size <= 0) {
                    System.out.println(Thread.currentThread().getName() + "--仓库里没有库存了，开始等待了。。。");
                    condition.await();
                }

                //开始消费动作
                int dec = size - left >= 0 ? left : size;
                size -= dec;
                left -= dec;
                System.out.println(Thread.currentThread().getName() + "--成功消费了" + dec + "个产品。。" + "，size=" + size);

                //消费后，唤醒操作
                condition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Producer {

    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }

    public void produce(int var) {
        new Thread(() -> {
            depot.produce(var);
        }).start();
    }
}


class Consumer {

    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    public void consume(int val) {
        new Thread(() -> {
            depot.consume(val);
        }).start();
    }

}
