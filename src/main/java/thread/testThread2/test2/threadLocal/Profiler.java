package thread.testThread2.test2.threadLocal;

import java.util.concurrent.TimeUnit;

/**
 * 测试8：ThreadLocal 线程变量
 *
 * 定义Profiler类，可以用来计算程序执行的时间
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    protected Long initialValue(){
        return System.currentTimeMillis();
    }

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    //获取程序执行的时间
    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }

}
