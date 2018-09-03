package testCommon;

public class TestSort {
    public static void main(String[] args) {
//        sort();
//        System.out.println("aaaaa");

        int[] arr = new int[]{2, 1, 5, 4, 3};


        bubble(arr);
    }


    public static void sort() {
        int[] arr = new int[]{2, 1, 5, 4, 3};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] < arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

//        print(arr);
    }


    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i] + ",");
        }
    }

    public static void bubble(int[] data){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - i - 1; j++) {
                if(data[j]<data[j+1]){
                    int temp = data[j];
                    data[j]= data[j+1];
                    data[j + 1] = temp;
                }
            }
        }

        print(data);
    }
}
