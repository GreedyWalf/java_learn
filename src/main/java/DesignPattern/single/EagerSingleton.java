package DesignPattern.single;

/**
 * 饿汉式单例模式
 * 当类被加载时，自动实例化对象，不存在多线程并发访问安全问题，但是也没有延迟加载，在系统初始化
 * 时就立即实例化，会一直占用系统资源。
 */
public class EagerSingleton {

    //当类被加载时，静态变量instance会被初始化，此时类的私有构造函数会被调用，单例类的唯一实例将被创建
    private static final EagerSingleton instance = new EagerSingleton();

    //私有化构造方法
    private EagerSingleton() {
    }

    //提供静态方法，返回唯一实例
    public static EagerSingleton getInstance() {
        return instance;
    }
}

/**
 * 懒汉式单例模式
 * 类加载的时候，不实例化对象，在需要时进行实例化，存在多线程并发访问安全问题，虽然可以使用同步去解决，
 * 较饿汉式单例模式来说，访问性能不高。
 */
class LazySingleton {
    //类加载的时候，不实例化对象，在需要实例化时再加载（延时加载）
    private static LazySingleton instance;

    //私有化构造方法
    private LazySingleton() {
    }

    //synchronized同步方法，解决多线程访问并发安全问题
    public synchronized static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}

/**
 * 懒汉式单例模式--双重检查锁定
 * 相比于上面使用同步方法懒汉式单例，这种双重检查锁定方式性能会高点，只有当多个线程通过第一个if判断后，才会进入同步代码块。同时也支持延时加载；
 * 但是，使用volatile关键字，为了维持内存的可见性会防止指令重排序，在一定程度上也会影响性能，并发性能还是不如饿汉式单例模式；
 */
class LazySingleton2 {

    //使用volatile关键字修饰，保证多线程运行下，变量可见性。
    private volatile static LazySingleton2 instance;

    //私有化构造方法
    private LazySingleton2() {
    }

    //提供静态方法，返回唯一实例
    public static LazySingleton2 getInstance() {
        if (instance == null) {
            //使用同步代码块，再次判断instance是否实例化，在多线程并发访问时，volatile关键字保证instance变量可见性。
            synchronized (LazySingleton2.class) {
                if (instance == null) {
                    instance = new LazySingleton2();
                }
            }
        }

        return instance;
    }
}

/**
 * 一种更好的单例实现方法，使用java特性--静态内部类实现
 * 在满足延迟加载同时，也保证高性能。
 */
class Singleton {

    //私有构造方法
    private Singleton() {
    }

    //私有的静态内部类，在类加载时候会自动初始化instance静态变量；
    private static class HolderClass {
        private static final Singleton instance = new Singleton();
    }

    //提供公有静态方法，获取唯一实例（在调用该方法时，会加载HolderClass静态内部类，从而初始化instance实例）
    public static Singleton getInstance() {
        return HolderClass.instance;
    }
}



