package testInterview;

public class TestSxh {
    public static void main(String[] args) {
        getSxhNew(5);
        getSxhNew2(5);
    }

    private static void getNum() {
        int a = 0, b = 0, c = 0;
        for (int i = 100; i < 1000; i++) {
            a = i / 100;
            b = i % 100 / 10;
            c = i % 100 % 10;

            a = a * a * a;
            b = b * b * b;
            c = c * c * c;
            if ((a + b + c) == i) {
                System.out.println("水仙花数：" + i);
            }
        }
    }

    private static void getNum2() {
        for (int i = 1000; i < 10000; i++) {
            int a = i / 1000 % 10;
            int b = i / 100 % 10;
            int c = i / 10 % 10;
            int d = i / 1 % 10;

            System.out.println("a=" + a + ",b=" + b + ",c=" + c + ",d=" + d);
        }
    }

    /**
     * n>=3
     */
    private static void getSxh(int n) {
        int maxNum = (int) Math.pow(10.0, n);
        for (int i = 100; i < maxNum; i++) {
            String str = String.valueOf(i);
            int sum = 0;
            char[] chars = str.toCharArray();
            for (char aChar : chars) {
                int elem = Integer.valueOf(aChar + "");
                sum += (Math.pow(elem, chars.length));
            }

            if (i == sum) {
                System.out.println("水仙花：" + i);
            }
        }
    }


    private static void getSxhByNum(int n) {
        int maxNum = pow(10, n);
        int minNum = pow(10, n - 1);
        for (int i = minNum; i < maxNum; i++) {
            char[] chars = String.valueOf(i).toCharArray();
            int sum = 0;
            int len = chars.length;
            for (char ch : chars) {
                sum += (pow(Integer.valueOf(ch + ""), len));
            }

            if (sum == i) {
                System.out.println("当n=" + n + "时，水仙花数：" + i);
            }
        }
    }


    /**
     * 来自百度百科
     */
    private static void test(int N) {
        for (int i = 3; i <= N; i++) {
            int a[] = new int[i];
            int num = (int) Math.pow(10, i - 1) + 1;
            System.out.print(i + "位的水仙花数有：\t");
            while (num <= Math.pow(10, i)) {
                int sum = 0;
                for (int j = 0; j < i; j++)
                    a[j] = (int) (num / Math.pow(10, j) % 10);
                for (int j = 0; j < i; j++)
                    sum = sum + (int) Math.pow(a[j], i);
                if (num == sum)
                    System.out.print(num + "\t");
                num++;
            }
            System.out.println();
        }

    }


    /**
     * 计算次幂
     */
    private static int pow(int elem, int num) {
        int sum = 1;
        while (num-- > 0) {
            sum *= elem;
        }

        return sum;
    }


    private static void getSxhNew(int N) {
        int maxNum = (int) Math.pow(10, N);
        for (int i = 100; i < maxNum; i++) {
            int len = String.valueOf(i).length();
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) {
                arr[j] = i / (int) (Math.pow(10, j)) % 10;
            }

            int sum = 0;
            for (int a : arr) {
                sum += Math.pow(a, len);
            }

            if (sum == i) {
                System.out.println(i);
            }
        }
    }

    private static void getSxhNew2(int N) {
        for (int n = 3; n <= N; n++) {
            System.out.print(n + "位：");
            int maxNum = (int) Math.pow(10, n);
            int[] arr = new int[n];
            for (int i = 100; i < maxNum; i++) {
                //获取i中每个数位的数字
                for (int j = 0; j < n; j++) {
                    arr[j] = i / (int) Math.pow(10, j) % 10;
                }

                int sum = 0;
                for (int a : arr) {
                    sum += (int) Math.pow(a, n);
                }

                if (i == sum) {
                    System.out.print(i + " ");
                }
            }

            System.out.println();
        }
    }
}
