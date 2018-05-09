package testString;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;

public class TestString {
    public static void main(String[] args) {
        System.out.println(reverse("12345"));

        Calendar.getInstance().get(Calendar.MONTH);

        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,1,1,0,0,0);
        long timeInMillis = calendar.getTimeInMillis();
        System.out.println(timeInMillis);

        System.out.println(convertCode("你好"));
    }


    /**
     * 字符串反转方法
     */
    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        return reverse(str.substring(1)) + str.charAt(0);
    }

    /**
     * 怎样将GB232编码装换为UTF-8编码
     */
    public static String convertCode(String str){
        try {
            return new String(str.getBytes("GB2312"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
