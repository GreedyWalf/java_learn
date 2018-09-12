package testThread2.test2.demo.test;

/**
 * wait()和notify()通信方法实现生产者和消费者
 */
public class PublicBox {
    private int apple = 0;

    public synchronized void increase() throws Exception {
        System.out.println("当前苹果数：" + apple);
        while (apple >= 5) {
            wait();
        }

        apple++;
        System.out.println("生成苹果成功！");
        notify();
    }

    public synchronized void decrease() throws Exception {
        System.out.println("当前苹果数：" + apple);
        while (apple <= 0) {
            wait();
        }

        apple--;
        System.out.println("消费苹果成功！");
        notify();
    }

    public static void main(String[] args) {
        PublicBox box = new PublicBox();
        Consumer consumer = new Consumer(box);
        Producer producer = new Producer(box);

        Thread thread = new Thread(consumer);
        Thread thread2 = new Thread(producer);
        thread.start();
        thread2.start();
    }
}


class Producer implements Runnable {
    private PublicBox box;

    public Producer(PublicBox box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("pro i:" + i);
            try {
//                SleepUtils.second(1);
                box.increase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private PublicBox box;

    public Consumer(PublicBox box) {
        this.box = box;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("con i: " + i);
            try {
//                SleepUtils.second(1);
                box.decrease();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
