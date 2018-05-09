public class Test {
    public static void main(String[] args) {
//        System.out.println(getValidParameter("123"));
//        System.out.println(getValidParameter("123-3-3"));
//        System.out.println(getValidParameter("as12-as12"));
//
//
//        System.out.println("abc".substring(1));
//        System.out.println("abc".substring(2));


        String reverse = reverse("123");
        System.out.println(reverse);

    }


    public void test(){

    }

    public static void test2(){
        new Test().test();
    }


    public static String reverse(String str){
        if(str == null || str.length()<=1){
            return str;
        }

        return reverse(str.substring(1)) + str.charAt(0);
    }


    /**
     * 获取去除指定特殊字符后返回字符串
     *
     * @param value 原字符串
     * @return 获取指定特殊字符后的字符串
     */
    public static String getValidParameter(String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return "";
        }

        String reg = "[<>()\\-;='\"]";
        return value.replaceAll(reg, "").trim();
    }
}
