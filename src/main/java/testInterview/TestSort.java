package testInterview;

/**
 * 可以完成排序，可是冒泡好像不是这样的，冒泡排序总是相邻的两个约束进行比较
 */
public class TestSort {

    public static void main(String[] args) {
//        testSort();
        testSort2();
    }

    public static void testSort() {
        int[] arr = new int[]{2, 1, 3, 5, 4};
        //第一次排序，可以找到最大值
        for (int i = 1; i < arr.length; i++) {
            if (arr[0] < arr[i]) {
                int temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;
            }
        }

        show(arr);
        //第二次排序时，数组中第一个元素已经为最大值，无需参与排序
        for (int i = 2; i < arr.length; i++) {
            if (arr[1] < arr[i]) {
                int temp = arr[1];
                arr[1] = arr[i];
                arr[i] = temp;
            }
        }

        show(arr);
    }

    public static void testSort2() {
        int[] arr = new int[]{2, 1, 3, 5, 4};
        for (int i = 0; i < arr.length - 1; i++) {  //最外层决定循环的次数（5个数排序，需要进行4趟排序）
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        show(arr);
    }


    public static void show(int[] arr) {
        for (int a : arr) {
            System.out.print(a);
        }

        System.out.println();
    }
}
