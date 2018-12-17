package thread.testThread3.ch3;

import thread.testThread2.test2.utils.SleepUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * 分而治之：Fork/Join框架
 * <p>
 * 自定义任务类，继承RecursiveTask，带返回值；
 * <p>
 * 注意：如果获取不到返回结果，则可能原因是：
 * 1、系统内的线程数量越积越多，导致性能严重下降；
 * 2、函数的调用层次变得很深，最终导致栈溢出；
 */
public class CountTask extends RecursiveTask<Long> {

    //定义任务分解的规模
    private static final int THRESHOULD = 100;

    private long start;

    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        boolean canCompute = (end - start) <= THRESHOULD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //分成100个小任务
            long step = (start + end) / 2;
            ArrayList<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 2; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }

                CountTask subTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }

            for (CountTask t : subTasks) {
                sum += t.join();
            }
        }

        return sum;
    }


    public static void main(String[] args) {
        //初始化forkJoinPool线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //将一个大任务，加入线程池
        CountTask task = new CountTask(0, 1000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long res = result.get();
            System.out.println("-->>sum=" + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


class SumTask extends RecursiveTask<Long> {

    static final int THRESHOLD = 2;

    long[] array;

    int start;

    int end;


    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        //任务足够小，就直接执行
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }

            SleepUtils.second(1);

            System.out.println(String.format("compute %d~%d=%d", start, end, sum));
            return sum;
        }


        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==>> %d~%d,%d~%d", start, end, start, middle, middle, end));
        SumTask sumTask1 = new SumTask(this.array, start, middle);
        SumTask sumTask2 = new SumTask(this.array, middle, end);
        invokeAll(sumTask1, sumTask2);

        Long subResult1 = sumTask1.join();
        Long subResult2 = sumTask2.join();
        Long result = subResult1 + subResult2;
        return result;
    }


    public static void main(String[] args) {
        long[] array = new long[]{1, 2, 3, 4, 5};
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = forkJoinPool.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("===>>>result=" + result + ",花费时间：" + (endTime - startTime));
    }
}
