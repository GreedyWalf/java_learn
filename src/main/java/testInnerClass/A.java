package testInnerClass;


/**
 * 测试静态内部类、非静态内部类；
 *
 * 无论是静态内部类或非静态内部类，类中定义的属性，对于外部类来说都是可以直接访问的；对于其他类来说，按照访问修饰符限制访问；
 *
 */
public class A {
    public static void main(String[] args) {

        /**
         * 静态内部类，在外部类中使用，可以直接使用静态内部类按照一般类，实例化；在其他类中需要使用A.B b = new A.B()进行实例化；
         */
        B b = new B("zhangsan");
        System.out.println(b.getUserName());
        System.out.println(b.name);
        System.out.println(b.userName);

        A.B b2 = new A.B("lisi");
        System.out.println(b2.getUserName());


        /**
         * 非静态内部类，需要通过外部类的实例 a.new C()，也就是说先实例化外部类，再实例化非静态内部类;
         */
        A a = new A();
        A.C c = a.new C("qinypeng");
        System.out.println(c.getUserName());
        System.out.println(c.userName);
        System.out.println(c.name);

        C c2 = a.new C("wangwu");
        System.out.println(c2.getUserName());
        System.out.println(c2.userName);
        System.out.println(c2.name);
    }

    //静态方法中没有this，如果一定要实例化的话，可以使用new A()
    public static void test(){
        //不能直接实例化内部类
//        new C();
//        this.new C();

        new A().new C(); //没有this对象，可以new Outer().new Inner();

        new B();  //可以直接实例化静态内部类
    }

    //非静态方法中this省略，可以直接实例化内部类
    public void testA(){
        new C();
//        this.new C();
    }


    public static class B {
        public String name;
        private String userName;
        //静态内部类中可以定义静态属性
        public static String uName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public B() {
        }

        public B(String userName) {
            this.userName = userName;
        }
    }


    class C {
        private String userName;
        public String name;
        //内部类中不能定义静态属性
//        public static String UName;


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public C(){}


        public C(String userName) {
            this.userName = userName;
        }
    }
}
