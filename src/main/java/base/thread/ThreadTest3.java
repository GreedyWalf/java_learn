package base.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过Callable和Future创建线程
 *
 *  1、创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值；
 *  2、创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值；
 *  3、使用FutureTask对象作为Thread对象的target创建并启动新线程；
 *  4、调用FutureTask对象的get()方法类获取子线程执行结束后的返回值；
 */
public class ThreadTest3 {
    public static void main(String[] args) {
        //创建callable接口实现类
        CallableThread ct = new CallableThread();
        //创建futureTask实例，封装callable实现类实例
        FutureTask<Integer> futureTask = new FutureTask<Integer>(ct);
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20) {
                new Thread(futureTask,"🈶返回值的线程").start();
            }
        }

        try {
            //通过futureTask.get()方法获取启用的线程执行完后的返回值
            System.out.println("子线程返回值：" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


class CallableThread implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        int i=0;
        for(; i<50; i++){
            System.out.println(Thread.currentThread().getName() + "--" + i);
        }

        return i;
    }
}


