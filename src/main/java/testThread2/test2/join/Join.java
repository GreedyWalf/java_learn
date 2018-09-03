package testThread2.test2.join;

/**
 * 测试7：
 * 测试thread.join()，如果一个线程执行了thread.join()，表示当前线程A等待thread线程终止之后，
 * 才从thread.join()返回。
 */
public class Join {

    static class Domino implements Runnable {

        private Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                //等待thread线程任务执行完成后，再执行当前线程
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " terminate.");
        }


        public static void main(String[] args) {
            //主线程
            Thread previous = Thread.currentThread();
            for (int i = 0; i < 10; i++) {
                //每一个线程终止的前提都是前驱线程的终止，每个线程等待当前前驱线程终止后，才从join()方法返回，这里涉及了等待/通知机制
                Thread thread = new Thread(new Domino(previous), String.valueOf(i));
                thread.start();
                previous = thread;
            }

            //最开始的线程为main线程，所以main.join()，第一个线程会等待main线程执行完，第二个线程会等待第一个线程执行完。。。，所以执行顺序 就是 main->thread1->thread2..
            System.out.println("thread：" + Thread.currentThread().getName() + " ending..");
        }
    }
}
