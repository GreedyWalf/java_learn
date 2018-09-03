package testInterview;

/**
 * 排序算法：
 *  1、冒泡排序：每两个元素之间进行比较，一轮比较可以确定下来一个最大值；n个数排序需要n-1轮比较；
 *  2、快速排序：指定一个基准值（一般为第一个元素，然后规定最左边和左右边两个标志，右边寻找比基准值小的位置和左边寻找比基准值大的位置，完成交换，一轮下来，基准值左边为小于它的值，右边为大于它的值；然后在以基准值划分为左右两块，在进行上述排序操作，感觉很麻烦，但是速度快点）
 */
public class TestSort2 {
    public static void main(String[] args) {

//        testEquals();
//        testSort();

        int[] arr = {6, 1, 2, 7, 9, 11, 4, 5, 10, 8};
        quickSort(arr, 0, arr.length - 1);
        show(arr);
    }


    public static void test() {
        int[] arr = new int[]{1, 3, 5, 2, 4};
        //第一趟排序，将最大值获取到放在左右边（第0和第1比较，将大的值放在右边，以此循环）
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                int temp = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = temp;
            }
        }

        show(arr);

        //第二趟，最右边的数已经是最大的了，循环可以少一遍比较最右边的数据了。。 以此类推
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i - 1] > arr[i]) {
                int temp = arr[i - 1];
                arr[i - 1] = arr[i];
                arr[i] = temp;
            }
        }

        show(arr);
    }


    /**
     * 冒泡排序
     */
    public static void testSort() {
        int[] arr = new int[]{5, 4, 3, 2, 1};
        //外层循环决定循环的次数
        for (int i = 0; i < arr.length - 1; i++) {
            //内存循环，没循环一次，就能确定一个最大数放在最右端，在下次比较时，可以少比较一次
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }

            show(arr);
        }
    }


    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }

        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }

            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }

            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        show(arr);
        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j + 1, high);
    }


    public static void show(int[] arr) {
        for (int a : arr) {
            System.out.print(a + " ");
        }

        System.out.println();
    }
}
