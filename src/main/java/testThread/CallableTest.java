package testThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Java5之后提供创建线程的另一种方式，实现Callable接口，实现接口定义的call方法，带返回值；
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<Integer>> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            list.add(service.submit(new MyTask((int) (Math.random() * 100))));
        }

        int sum = 0;
        for (Future<Integer> future : list) {
            sum += future.get();
        }

        System.out.println(sum);
    }
}

class MyTask implements Callable<Integer> {
    private int upperBounds;

    public MyTask(int upperBounds) {
        this.upperBounds = upperBounds;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= upperBounds; i++) {
            sum += i;
        }

        return sum;
    }
}

