package thread.testThread3.ch5.jdk;

import java.util.concurrent.*;

public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1、构造FutureTask对象实例，使用Callable接口实现类作为参数传递，告诉FutureTask需要的数据如何产生的
        FutureTask<String> futureTask = new FutureTask<>(new RealData("hello"));

        //2、将FutureTask提交给线程池，作为一个简单任务提交
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(futureTask);
        System.out.println("请求完毕！");

        //模拟业务处理逻辑
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("如何还没有获取到数据，这里会等待future.get()获取数据。。");
        //3、再讲任务提交后，不用等待着任务返回数据，继续执行其他业务操作，在需要数据的时候，执行future.get()获取即可；
        System.out.println("数据=" + futureTask.get());
    }
}

/**
 * 自定义一个类，实现Callable方法，在call()方法中返回需要的数据
 */
class RealData implements Callable<String> {
    private String para;

    public RealData(String para) {
        this.para = para;
    }


    //这里模拟处理业务逻辑
    @Override
    public String call() {
        StringBuffer sb = new StringBuffer();
        sb.append(para);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
