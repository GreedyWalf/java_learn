package testAbstract;

/**
 * 测试抽象类、继承、静态代码块、代码块、构造方法加载顺序
 */
public abstract class A {

    static {
        System.out.println("A类静态代码块执行了~~");
    }

    {
        System.out.println("A类代码块执行了~~");
    }

    public static String UNAME="ceshi";

    private String name;

    public A(){
        System.out.println("A类无参的构造方法执行了~~");
    }

    public A(String name) {
        System.out.println("A类构造方法执行了~");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void test();
}


class B extends A{

    static {
        System.out.println("B类静态代码块执行了~~");
    }

    {
        System.out.println("B类代码块执行了~~");
    }

    public B(){
        //调用父类构造方法，需要放在第一行 不指定使用父类的构造方法，则调用父类默认无参的构造方法
//        super("zhangsan");
        System.out.println("B类构造方法执行~~");
    }

    @Override
    public void test() {
        System.out.println(A.UNAME);
    }

    public static void main(String[] args) {
        B b = new B();
        b.test();
    }
}
