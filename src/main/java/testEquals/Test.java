package testEquals;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        testEquals();
//        testEquals2();
//        testEqual3();
//        testEqual4();
//        testBreak();
        testEquals5();
    }

    private static void testEquals(){
        String s1 = "123";
        String s2 = "123";
        String s3 = new String("123");
        System.out.println(s1 == s2);  //true
        System.out.println(s1 == s3); //false
        System.out.println(s1.endsWith(s2)); //true
//        System.out.println(s1.hashCode());
//        System.out.println(s2.hashCode());
//        System.out.println(s3.hashCode());

        System.out.println("------------------");
        List<String> list = new ArrayList<String>();
        list.add(s1);
        List<String> list2 = new ArrayList<String>();
        list2.add(s1);
        List<String> list3 = new ArrayList<String>();
        list3.add("123");
        System.out.println(list == list2);  //false
        System.out.println(list.equals(list2));  //true
        System.out.println(list.equals(list3));  //true  比较个数和每个元素内容
    }

    private static void testEquals2(){
        String s1 = new String("123");
        s1 = s1.intern();
        String s2 = "123";

        System.out.println(s1 == s2);  //true
        System.out.println(s1.endsWith(s2));  //true
    }

    /**
     * 比较基本类型和包装类，装箱和拆箱
     */
    private static void testEqual3(){
        Integer a = new Integer(3);
        Integer b = 3;  //赋值基本类型int，赋值按照从右向左方向，基本类型编程包装类型，为装箱，会自动调用Integer.valueOf()方法
        int c = 3;
        System.out.println(a==b);  //false
        System.out.println(a==c);  //true   //可以理解为和基本类型比较，会自动拆箱为基本类型数据，比较的是数值大小；
        System.out.println(b==c);  //true
    }


    /**
     * 测试装箱，以及Integer对象初始化
     *
     *  字面量值在-128~127之间，不会new新的Integer对象，而是直接引用常量池中的Integer对象；
     */
    private static void testEqual4() {
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
        System.out.println(f1 == f2);  //true
        System.out.println(f3 == f4);  //false
    }


    /**
     * 可以在循环开始时候，添加标志，在break后面添加标识，跳出循环；（一般很少用）
     */
    private static void testBreak() {
        A:
        for (int i = 0; i < 9; i++) {
            B:
            for (int j = 0; j < 9; j++) {
                if(i==2 && j==2){
                    break ;
//                    return;
                }
            }

            System.out.println("i=" + i);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    private static void testEquals5(){
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2); //false
        System.out.println(s1 == s3);  //true
        System.out.println(s1 == s1.intern()); //true
        System.out.println(s1 == s2.intern()); //true
    }
}
