package testJdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Test {
    public static void test() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
//        numbers.forEach((Integer value) -> {
//            System.out.println(value);
//        });

        numbers.forEach(System.out::println);
    }

    /**
     * lambda表达式中使用的局部变量默认为final修饰（final可以省略）
     */
    public static void test2() {
        //lambda表达式中使用局部变量，使用final关键字修饰，不允许修改final修饰变量的值
        final int num = 2;

        //参数为from，返回值为from*num
        Function<Integer, Integer> stringConverter = (from) -> from * num;
        System.out.println(stringConverter.apply(3));
    }

    /**
     * 测试：集合遍历，jdk8简化
     */
    public static void test3() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        for (int i : arr) {
            System.out.println(i);
        }

        System.out.println("====1");
        Arrays.stream(arr).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });

        System.out.println("====2");
        //IntConsumer接口使用@FunctionalInterface注解标记该接口为符合函数式编程规范接口
        Arrays.stream(arr).forEach((final int x) -> {
            System.out.println(x);
        });

        System.out.println("====3");
        Arrays.stream(arr).forEach((x) -> {
            System.out.println(x);
        });

        System.out.println("====4");
        //使用方法引用方式，省略参数声明和传递
        Arrays.stream(arr).forEach(System.out::println);
    }

    /**
     * 测试：将两个IntConsumer结合起来使用
     */
    public static void test4() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        //使用函数引用方式定义了两个IntConsumer接口实例，一个指向标准输出，另一个指向标准错误
        IntConsumer outPrintln = System.out::println;
        IntConsumer errPrintln = System.err::println;
        //使用接口默认函数IntConsumer.addThen()将两个IntConsumer进行组合，得到新的IntConsumer
        Arrays.stream(arr).forEach(outPrintln.andThen(errPrintln));
    }


    /**
     * 测试：使用jdk8提供的串行流、并行流计算1~10000000内的质数（对比运行效率：数据量大，当然是并行效率高啦）
     */
    public static void test5() {
        //使用jdk8串行流方式
        long time = System.currentTimeMillis();
        long count = IntStream.range(1, 10000000).filter(PrimeUtil::isPrime).count();
        System.out.println("1~1000000内质数个数：" + count);
        System.out.println("串行耗时：" + (System.currentTimeMillis() - time));

        //自定义实现
        long time2 = System.currentTimeMillis();
        int cnt = 0;
        for (int i = 1; i < 10000000; i++) {
            if (PrimeUtil.isPrime(i)) {
                cnt++;
            }
        }
        System.out.println("1~1000000内质数个数：" + cnt);
        System.out.println("自定义实现耗时：" + (System.currentTimeMillis() - time2));

        //jdk8提供的并行流方式（parallel()）
        long time3 = System.currentTimeMillis();
        long count2 = IntStream.range(1, 10000000).parallel().filter(PrimeUtil::isPrime).count();
        System.out.println("1~1000000内质数个数：" + count2);
        System.out.println("耗时：" + (System.currentTimeMillis() - time3));
    }


    /**
     * 测试：jdk8从集合中可以得到一个流或并行流
     */
    public static void test6() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("111", "zhangsan", 90));
        userList.add(new User("222", "lisi", 80));
        userList.add(new User("333", "wangwu", 70));

        double ave = userList.stream().mapToDouble(User::getScore).average().getAsDouble();
        System.out.println("平均值：" + ave);

        double ave2 = userList.parallelStream().mapToDouble(User::getScore).average().getAsDouble();
        System.out.println("平均值：" + ave2);
    }


    /**
     * 测试：jdk8中提供对数据批量赋值（并行方式批量赋值、批量排序），测试结果：就批量赋值而言，
     * 并行效率在数据量比较大的情况下没有串行效率高。。
     */
    public static void test7() {
        //
        long time = System.currentTimeMillis();
        int[] arr = new int[10000000];
        Random random = new Random();
        Arrays.setAll(arr, (int i) -> random.nextInt());
        //不能写成下面的简化方式，下面的简化方式实际为：(int i)->{random.nextInt(i)}
//        Arrays.setAll(arr, random::nextInt);
//        Arrays.stream(arr).forEach(System.out::println);
//        Arrays.sort(arr);
        System.out.println("===>>串行数组赋随机值：" + (System.currentTimeMillis() - time));


        long time2 = System.currentTimeMillis();
        int[] arr2 = new int[10000000];
        Random random2 = new Random();
        Arrays.parallelSetAll(arr2, i -> random2.nextInt());
//        Arrays.stream(arr).forEach(System.out::println);
        //并行排序
        //Arrays.parallelSort(arr2);
        System.out.println("===>>并行数组赋随机值：" + (System.currentTimeMillis() - time2));
    }


}

class PrimeUtil {

    /**
     * 判断一个数是不是质数（素数）
     */
    public static boolean isPrime(int number) {
        if (number <= 3) {
            return number > 1;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}


class User {
    private String userId;
    private String userName;
    private double score;

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User() {

    }

    public User(String userId, String userName, double score) {
        this.userId = userId;
        this.userName = userName;
        this.score = score;
    }
}
