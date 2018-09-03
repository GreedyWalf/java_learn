package testReg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegExp {
    public static void main(String[] args) {
//        testEquals();
        test2();
    }

    public static void test() {
        String str = "(中文问号？123???英文)问号?我是华丽[的制表符\t]我是华丽{的空格符 我是华丽}的换行符\n";
        String rex = "\\b";

        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(str);

        String[] result = pattern.split(str);
        for (String string : result) {
            System.out.println("分割的字符串:" + string);

        }
    }

    public static void test2() {
        String str = "Java aa bb";
        String reg = "\\bJava";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }

        String[] result = pattern.split(str);
        for (String s : result) {
            System.out.println(s);
        }
    }
}
