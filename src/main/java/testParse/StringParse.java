package testParse;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StringParse {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        testEquals();
        stringMethod();
    }

    private static void test(){
        Float f1 = 1.2f;
        Float f2 = 1.2F;
        Float f3 = Float.valueOf(1.2f);

        System.out.println(f1==f2);
        System.out.println(f1==f3);

        System.out.println("----");
        int c = 123;
        Integer a = 123;
        Integer d = 123;
        Integer b = Integer.valueOf(123);
        System.out.println(a==b);  //true
        System.out.println(a==c);  //true
        System.out.println(a==d);  //true

        System.out.println("----");
        String s1 = "123";
        String s2 = new String("123");
        String s3 = String.valueOf("123");
        System.out.println(s1==s2);  //false
        System.out.println(s1==s3);  //true
        System.out.println(s2==s3);  //false


        String s = String.valueOf(new char[]{'1', '2'});
        System.out.println(s);
    }


    private static void stringMethod() throws UnsupportedEncodingException {
        String s = new String("123");
        System.out.println(s);

        //字符数组截取为字符串
        s = new String(new char[]{'a', 'b', 'c'}, 1, 2);
        System.out.println(s);

        //将GB2312编码的字符串转换为ISO-8859-1的字符串
        s = new String("你好".getBytes("GB2312"), "ISO-8859-1");
        System.out.println(s);


    }
}
