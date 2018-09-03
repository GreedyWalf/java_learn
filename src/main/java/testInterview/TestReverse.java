package testInterview;

/**
 * 递归实例
 * <p>
 * 递归需要满足两个条件：
 * 1、需要临界条件（也就是代码中程序return的时机）；
 * 2、需要找到递归调用公式；
 * 3、递归方法调用顺序决定输出方式;(递归方法一般放在递归方法体前，表示由最开始向后执行)
 */
public class TestReverse {

    public static void main(String[] args) {
//        System.out.println(inverse("12345"));
//        System.out.println(reverse("12345"));

//        System.out.println(jc(1));
//        System.out.println(jc(2));
//        System.out.println(jc(3));

//        jiu();
        jiu2(9);

//        multiTable(9);
    }


    /**
     * 字符串反转（自创）
     */
    private static String inverse(String str) {
        String result = "";
        if (str.length() <= 1) {
            return str.charAt(0) + "";
        }

        String subStr = str.substring(str.length() - 1, str.length());
        String remainStr = str.substring(0, str.length() - 1);
        result = subStr + inverse(remainStr);
        return result;
    }

    /**
     * 字符串反转（递归）
     */
    private static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        return reverse(str.substring(1)) + str.charAt(0);
    }

    /**
     * 阶乘
     */
    private static int jc(int n) {
        if (n <= 1) {
            return 1;
        }

        return n * jc(n - 1);
    }


    /**
     * 计算fibonacci数
     */
    private static int fibonacci(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        return fibonacci(n - 1) + fibonacci(n - 2);
    }


    /**
     * 9*9乘法表
     */
    private static void jiu() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "*" + j + "=" + (i * j) + "    ");
            }

            System.out.println();
        }
    }


    /**
     * 递归实现99乘法表 （自测，逆序了）
     */
    private static void jiu2(int i) {
        if (i == 1) {
            System.out.println("1*1=1");
            return;
        }

        //递归的调用放的位置，决定输出正序还是逆序
        jiu2(i - 1);
        for (int j = 1; j <= i; j++) {
            if (j > i) {
                continue;
            }

            System.out.print(j + "*" + i + "=" + (j * i) + "   ");
        }

        System.out.println();
    }

    /**
     * 递归实现99乘法表（正解）
     */
    private static void multiTable(int x) {
        if (x == 1) {
            System.out.println(1 + "*" + 1 + "=" + 1);
        } else {
            //递归调用
            multiTable(x - 1);
            for (int i = 1; i <= x; i++) {
                System.out.print(x + "*" + i + "=" + (x * i) + "  ");
            }

            System.out.println();
        }
    }

}
