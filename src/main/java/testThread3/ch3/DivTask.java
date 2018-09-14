package testThread3.ch3;


import testThread3.util.TraceThreadPoolExecutor;

import java.util.concurrent.*;

public class DivTask implements Runnable {
    int a, b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double result = a / b;
        System.out.println(result);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < 5; i++) {
            //这里100/0会报错的。
            pool.submit(new DivTask(100, i));
//            pool.execute(new DivTask(100, i));
        }
    }
}



