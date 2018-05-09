package testThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用匿名内部类方式，测试带返回值线程的创建和返回值获取
 */
public class CallableTest2 {
    public static void main(String[] args) {
        final CallableTest2 test2 = new CallableTest2();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        String userId = "11111111111111111";
        List<String> courseCodes = new ArrayList<>();

        Future<List<String>> listFuture = executorService.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "...run..");
                return test2.getCourseIds(courseCodes);
            }
        });

        Future<List<String>> listFuture2 = executorService.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "...run..");
                return test2.getCourseIds2(userId);
            }
        });

        List<String> result = new ArrayList<>();
        try {
            result.addAll(listFuture.get());
            result.addAll(listFuture2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(result);
    }

    private List<String> getCourseIds(List<String> courseCodes){
        List<String> courseIds = new ArrayList<>();
        courseIds.add("1111");
        courseIds.add("2222");
        courseIds.add("3333");
        return courseIds;
    }

    private List<String> getCourseIds2(String userId){
        List<String> courseIds = new ArrayList<>();
        courseIds.add("1111");
        courseIds.add("2222");
        courseIds.add("3333");
        return courseIds;
    }
}

