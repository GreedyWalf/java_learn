package thread.thread181101.prodAndConsume.test;

/**
 * 经典的生产者消费者模式（wait。。 notify。。）
 */
public class TestDemo {
    public static void main(String[] args) {
        Depot depot = new Depot(100);

        Producer producer = new Producer(depot);
        Consumer consumer = new Consumer(depot);

        //实际会开启两个生产线程
        producer.produce(60);

        //开启一个消费线程
        consumer.consume(120);

        producer.produce(60);
    }
}

/**
 * 定义仓库类
 */
class Depot {

    //仓库的容量
    private int capacity;
    //仓库的时间数量
    private int size;

    public Depot(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }


    //定义产品生产的方法
    public synchronized void produce(int val) {
        //生产者"想要生产的数量"（如果总的生产量太多，则需要多次生产）
        try {
            int left = val;
            while (left > 0) {
                //当前库存已满，等待"消费者"消费产品
                while (size >= capacity) {
                    System.out.println(Thread.currentThread().getName() + "--库存满了,等待中。。");
                    wait();
                }

                System.out.println(Thread.currentThread().getName() + "--开始生产。。。");
                //等待被唤醒后，说明库存被消费了，生产者又可以生产了
                //计算实际生产的产品增量（考虑到可能库存满了，不能生产多了）
                int inc = (size + left) > capacity ? capacity - size : left;
                //一次生产后，仓库中实际产品数
                size += inc;
                System.out.println(Thread.currentThread().getName() + "--生产了" + inc + "产品");
                //多出来的，因为仓库满了，还没生产的产品数
                left -= inc;
                System.out.printf("%s produce(%d) ---> left=%d,inc=%d,size=%d\n", Thread.currentThread().getName(), left, left, inc, size);

                //通知"消费者"可以消费了
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //定义产品消费的方法
    public synchronized void consume(int val) {
        try {
            //消费者"想要消费的数量"（如果一次性消费很多，库存不够时，则需要多次消费）
            int left = val;
            while (left > 0) {
                while (size <= 0) {
                    wait();
                }

                //当被唤醒时，表名生产者已经生产了产品，消费者可以来消费了
                //计算实际消费的产品数
                int dec = size - left > 0 ? left : size;
                //还需要消费的数量
                left -= dec;
                size -= dec;
                System.out.println(Thread.currentThread().getName() + "--成功消费了" + dec + "产品。。");
                System.out.printf("%s consume(%d) ---> left=%d,dec=%d,size=%d\n", Thread.currentThread().getName(), dec, left, dec, size);

                //唤醒生产者
                notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 定义生产者
 */
class Producer {

    private Depot depot;

    public Producer(Depot depot) {
        this.depot = depot;
    }


    //生产产品：新建一个线程向仓库中生产产品
    public void produce(int val) {
        Thread prodThread = new Thread(() -> {
            depot.produce(val);
        });

        prodThread.start();
    }
}

/**
 * 定义消费者
 */
class Consumer {

    private Depot depot;

    public Consumer(Depot depot) {
        this.depot = depot;
    }

    //消费产品：新建一个线程，用来消费仓库中的产品
    public void consume(int val) {
        Thread consumeThread = new Thread(() -> {
            depot.consume(val);
        });

        consumeThread.start();
    }

}