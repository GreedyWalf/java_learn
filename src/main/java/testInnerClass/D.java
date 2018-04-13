package testInnerClass;

public class D {
    public static void main(String[] args) {
        new D().test();
    }

    public void test(){
        A.B b = new A.B("zhangsan");
        System.out.println(b.getUserName());

        A a = new A();
        A.C c = a.new C("lisi");
        System.out.println(c.getUserName());

    }
}
