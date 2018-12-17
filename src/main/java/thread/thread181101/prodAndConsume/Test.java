package thread.thread181101.prodAndConsume;

public class Test {
    public static void main(String[] args) {
        Task task = new Task();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}

class Task implements Runnable {

    private int n = 0;

    @Override
    public synchronized void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int a = n;
        n = n + 1;
        System.out.println(Thread.currentThread().getName() + "--n=" + n);
    }
}
