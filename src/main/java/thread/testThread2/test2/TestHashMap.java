package thread.testThread2.test2;

import java.util.HashMap;
import java.util.UUID;

/**
 * 测试：多线程并发情况下，使用HashMap集合的put方法操作时会引起死循环，因为多线程会
 * 导致HashMap的Entry链表形成环形数据结构，一旦形成环形数据结构，Entry的next节点永远
 * 不为空，就会产生死循环获取Entry；
 */
public class TestHashMap {

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, String> map = new HashMap<>(2);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                new Thread(() -> map.put(UUID.randomUUID().toString(), ""), "ftf" + i).start();
            }
        });

        t.start();
        t.join();
    }
}
