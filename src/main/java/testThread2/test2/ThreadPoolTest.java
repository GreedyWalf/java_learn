package testThread2.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {
    //核心线程数=当前系统核数*2
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 100,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(2000));

//    private List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());

    public void test() {
        Future<List<String>> submit = threadPoolExecutor.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> list = new ArrayList<>();
                list.add("aaaa_" + Thread.currentThread().getName());
                return list;
            }
        });

        Future<List<String>> submit2 = threadPoolExecutor.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> list = new ArrayList<>();
                list.add("bbbb_" + Thread.currentThread().getName());
                return list;
            }
        });

        try {
            List<String> result = submit.get();
            List<String> result2 = submit2.get();
            System.out.println(result);
            System.out.println(result2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭线程池
        threadPoolExecutor.shutdown();
    }

    public static void main(String[] args) {
        ThreadPoolTest test = new ThreadPoolTest();
        test.test();
    }
}
