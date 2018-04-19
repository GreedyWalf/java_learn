package proxy;


import jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.plugin.javascript.navig.LinkArray;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理模式
 *  代理模式是对象的结构模式，它给某一个对象提供一个代理对象，并由代理对象控制原对象的引用。
 *
 *  下面例子，可以使用代理对象完成对原对象功能
 */
public class TestProxy {
    public static void main(String[] args) {
        testCgProxyMethod();
        testCgProxy();
    }



    /**
     * 测试cglib动态代理
     */
    private static void testCgProxyMethod(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ArrayList.class);
        enhancer.setCallback(new ListCgProxy<String>(new ArrayList<>()));
        List<String> proxyList = (ArrayList<String>) enhancer.create();
        proxyList.add("123");
    }

    /**
     * 使用匿名内部类，实现cglib动态代理
     */
    private static void testCgProxy(){
//        ArrayList<Object> proxy = new ListCgProxy<>(new ArrayList<>()).getProxyInstance();
//        proxy.add("1234");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ArrayList.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("-->>before " + method.getName());
                Object value = methodProxy.invokeSuper(o, objects);
                System.out.println(value);
                System.out.println("-->>after " + method.getName());
                return value;
            }
        });

        ArrayList<String> proxyArrayList = (ArrayList<String>) enhancer.create();
        proxyArrayList.add("qinyupeng");
    }

}

/**
 * cglib代理需要实现MethodInterceptor接口，实现接口中定义的intercept方法
 */
class ListCgProxy<T> implements MethodInterceptor{

    private ArrayList<T> target;

    public ListCgProxy(ArrayList<T> target){
        this.target = target;
    }

    public ArrayList<T> getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setSuperclass(ArrayList.class);

        //回调，this表示当前代理对象
        enhancer.setCallback(this);
        return (ArrayList<T>) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("-->before " + method.getName());
        Object value = methodProxy.invokeSuper(o, objects);
        System.out.println(value);
        System.out.println("-->after " + method.getName());
        return value;
    }
}

