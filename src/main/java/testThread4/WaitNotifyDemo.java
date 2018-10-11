package testThread4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试：一个盒子中最多可以装5个苹果，定义两个生产者生产苹果，两个消费者消费苹果，总共生产消费100个苹果；
 * 使用线程经典wait、notify模式：生产者、消费者模式；
 * 注意：wait()、notify()方法需要结合synchronized来使用，否则会出现illegalMonitorException异常；
 */
public class WaitNotifyDemo {

    public static void main(String[] args) {
        Box box = new Box();
        Thread produce = new Thread(new Producer(box), "produce-thread1");
//        Thread produce2 = new Thread(new Producer(box), "produce-thread2");

        Thread consumer = new Thread(new Consumer(box), "consumer-thread1");
//        Thread consumer2 = new Thread(new Consumer(box), "consumer-thread2");

        produce.start();
//        produce2.start();

        consumer.start();
//        consumer2.start();
    }
}

/**
 * 保证盒子中最多只能有5个苹果：使用synchronized+wait+notify实现
 * 即生产者调用increase()方法判断盒子中苹果数大于5个，则等待消费者消费；
 * 消费者调用decrease()方法，盒子中苹果数为0，则等待生产者生产；
 */
//class Box {
//    //盒子里的苹果数
//    private Integer apple = 0;
//
//    private Integer allCount = 0;
//
//    public Box() {
//
//    }
//
//    public Box(Integer apple) {
//        this.apple = apple;
//    }
//
//    public void setApple(Integer apple) {
//        this.apple = apple;
//    }
//
//    public Integer getApple() {
//        return apple;
//    }
//
//    public synchronized void increase() throws Exception {
//        System.out.println(Thread.currentThread().getName() + "==>>increase当前盒子中苹果数：" + apple);
//        if (apple >= 5) {
//            System.out.println("==>>increase开始等待消费者消费。。。");
//            wait();
//        }
//
//        apple++;
//        allCount++;
//        System.out.println(Thread.currentThread().getName() + " 生产苹果成功！");
//        System.out.println("======>>>总共生产苹果：" + allCount);
//        //苹果生产成功，唤醒消费者来消费
//        notify();
//    }
//
//    public synchronized void decrease() throws Exception {
//        System.out.println(Thread.currentThread().getName() + "==>>decrease当前盒子中苹果数：" + apple);
//        if (apple <= 0) {
//            wait();
//        }
//
//        apple--;
//        System.out.println(Thread.currentThread().getName() + " 消费苹果成功！");
//        //苹果消费成功，唤醒生产者来生产
//        notify();
//    }
//}


/**
 * 保证盒子中最多只能有5个苹果：使用ReentrantLock+condition实现
 * 即生产者调用increase()方法判断盒子中苹果数大于5个，则等待消费者消费；
 * 消费者调用decrease()方法，盒子中苹果数为0，则等待生产者生产；
 */
class Box {

    //定义重入锁
    private static Lock lock = new ReentrantLock();

    //定义condition
    private static Condition condition = lock.newCondition();

    //盒子里的苹果数
    private Integer apple = 0;

    //生产的苹果总数
    private Integer allCount = 0;

    public Box() {

    }

    public Box(Integer apple) {
        this.apple = apple;
    }

    public void setApple(Integer apple) {
        this.apple = apple;
    }

    public Integer getApple() {
        return apple;
    }

    public void increase() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "==>>increase当前盒子中苹果数：" + apple);
            if (apple >= 5) {
                System.out.println("==>>increase开始等待消费者消费。。。");
                condition.await();
            }

            apple++;
            allCount++;
            System.out.println(Thread.currentThread().getName() + " 生产苹果成功！");
            System.out.println("======>>>总共生产苹果：" + allCount);
            //苹果生产成功，唤醒消费者来消费
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrease() throws Exception {
        try {
            //加锁
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "==>>decrease当前盒子中苹果数：" + apple);
            if (apple <= 0) {
                //盒子中没有能消费的水果了，消费线程等待
                condition.await();
            }

            apple--;
            System.out.println(Thread.currentThread().getName() + " 消费苹果成功！");
            //苹果消费成功，唤醒生产者来生产
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}


/**
 * 消费者
 */
class Consumer implements Runnable {

    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                box.decrease();
//                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


/**
 * 生产者
 */
class Producer implements Runnable {

    private Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            try {
                box.increase();
//                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
