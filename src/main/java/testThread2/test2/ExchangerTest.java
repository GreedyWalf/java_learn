package testThread2.test2;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试：Exchanger（线程间交换数据）
 * <p>
 * 设置最大等待时长：exchange(V x,longtimeout,TimeUnit unit)
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            String A = "银行流水A";
            try {
                String B = exgr.exchange(A);
                System.out.println("B=" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        threadPool.execute(() -> {
            String B = "银行流水B";
            try {
                String A = exgr.exchange(B);
                System.out.println("A=" + A);
                System.out.println("A和B数据是否一致："
                        + A.endsWith(B));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();
    }

}
