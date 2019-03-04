package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Subject proxySubject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler());
        //使用代理对象 调用目标对象中定义的方法
        // （代理类实现了InvocationHandler接口，实现invoke方法，而invoke方法中调用的就是目标类中实现的接口方法）
        proxySubject.doSomething();
    }
}

interface Subject {
    void doSomething();
}

class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("doSomething...");
    }
}

class ProxyHandler implements InvocationHandler {

    private Object target;

    public ProxyHandler(Object tartget) {
        this.target = tartget;
    }

    public ProxyHandler(){

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在转调具体对象之前 可以执行一些功能处理
//        System.out.println("--->>> could do something before invoke target object method...");
//        Object obj = method.invoke(target, args);
        //在转调具体对象之后，可以执行一些功能处理
//        System.out.println("--->>> could do something after invoke target object method...");

        System.out.println(method.getName());
        return null;
    }
}

