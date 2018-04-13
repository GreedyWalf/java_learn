package single;


/**
 * 单例设计模式：这种设计模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单一对象被创建；
 *
 * 这个类提供了一个可以访问其唯一对象的方式，可以直接访问，不需要实例化该类的对象；
 *
 * 优点：1、在内存中只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例；
 *      2、避免对资源的多重占用；（比如写文件操作）
 *
 *  缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不是关心外面怎么实例化；
 *
 *  设计思路：判断系统是否已经有这个单例，如果有则返回，如果没有则创建；
 *  关键代码：构造函数是私有的；
 */
public class SingleObject {

    //创建SingleObject的一个对象
    private static SingleObject singleInstance = new SingleObject();

    //让构造函数private，这样该类就不能被实例化
    private SingleObject(){

    }

    //获取唯一可用的对象
    public static SingleObject getSingleInstance(){
        return singleInstance;
    }
}


/**
 * 单例模式的集中实现方式：
 */


/**
 * 1、懒汉式，线程不安全
 *
 *  是否Lazy初始化：是
 *  是否线程安全：否
 *
 *  这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁（synchronized），
 *  所以严格意义上它并不算是单例模式；
 *
 *  这种方式lazy loading很明显，不要求线程安全，在多线程中不能正常工作；
 */
class Singleton{
    private static Singleton instance;

    private Singleton(){}

    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }

        return instance;
    }
}


/**
 * 2、懒汉式，线程安全
 *
 *  是否Lazy初始化：是
 *  是否线程安全：是
 *
 *  这种方式具备很好的lazy loading，能够在多线程中很好的工作，但是效率很低；（基本上很少情况下需要同步）
 *
 *  优点：第一次调用时才初始化，避免内存浪费；
 *  缺点：必须加锁（synchronized）才能保证单例，但加锁会影响效率；
 */

class Singleton2{
    private static Singleton2 instance;

    private Singleton2(){

    }

    /**
     * 使用同步方法，在创建实例时加锁
     */
    public static synchronized Singleton2 getInstance(){
        if(instance == null){
            instance = new Singleton2();
        }

        return instance;
    }
}


/**
 * 3、饿汉式（常用）
 *
 *  是否Lazy初始化：否
 *  是否多线程安全：是
 *
 *  优点：没有加锁，执行效率会提高；
 *  缺点：类加载时就会初始化，浪费内存；
 *
 *  它基于classloader机制避免了多线程的同步问题，不过，instance在类装载时就实例化原因有很多种，
 *  不确定除了调用getIntance方法之外是否存在其他方式导致类装载，这时初始化instance显然没有做到
 *  lazy loading的效果；
 */
class Singleton3{
    private static Singleton3 instance = new Singleton3();

    private Singleton3(){}

    public static Singleton3 getInstance(){
        return instance;
    }
}


/**
 * 4、双检锁/双重校验锁（DCL，即double-checked locking）
 *
 *  是否Lazy初始化：是
 *  是否多线程安全：是
 *
 *  这种方式采用双锁机制，安全且在多线程情况下能保持高性能，getInstance()的性能对应用程序很关键；
 *
 */
class Singleton4{
    private volatile static Singleton4 instance;

    private Singleton4(){}

    public static Singleton4 getInstance(){
        //多线程访问时，受限判断该类是否存在实例，如果存在直接返回，如果不存在，在同步代码块中判断确实不存在实例，则初始化一个；
        if(instance == null){
            synchronized (Singleton4.class){
                if(instance == null){
                    instance = new Singleton4();
                }
            }
        }

        return instance;
    }
}


/**
 * 5、登记式/静态内部类
 *
 *  是否Lazy初始化：是
 *  是否多线程安全：是
 *
 *  这种方式同样利用了classloader机制来保证吃实话instance时只有一个线程，它和第3中不同的是：第3中
 *  方式只要类被撞在了，那么实例就会被实例化（没有lazy loading效果），而这种方式，Singleton类被装
 *  载了，instance也不一定会被初始化，因为SingletonHolder类没有被主动使用，只用通过主动调用getInstance方法时，
 *  才会显示装载SingletonHolder类，从而实例化instance；
 */
class Singleton5{

    //定义静态内部类，内部类中定义常量
    private static  class SingletonHolder{
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private Singleton5(){}


    public static Singleton5 getInstance(){
        return SingletonHolder.INSTANCE;
    }
}


/**
 * 6、枚举
 *
 *  是否Lazy初始化：否
 *  是否线程安全：是
 *
 *  这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。
 *  jdk1.5之后才有枚举特性，除此之外，这种写法让人感觉生疏，实际工作中，也很少使用；
 */
enum Singleton6{
    INSTANCE;
    public void whateverMethod(){

    }
}


/**
 * 总结：
 *  一般情况下，不建议使用第 1 种和第 2 种懒汉方式，建议使用第 3 种饿汉方式。
 *  只有在要明确实现 lazy loading 效果时，才会使用第 5 种登记方式。
 *  如果涉及到反序列化创建对象时，可以尝试使用第 6 种枚举方式。
 *  如果有其他特殊的需求，可以考虑使用第 4 种双检锁方式。
 */



