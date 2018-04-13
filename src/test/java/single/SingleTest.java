package single;

/**
 * 测试单例设计模式
 */
public class SingleTest {

    public static void main(String[] args) {
        SingleObject singleObject = SingleObject.getSingleInstance();
        SingleObject singleObject2 = SingleObject.getSingleInstance();
        System.out.println(singleObject == singleObject2);
    }
}
