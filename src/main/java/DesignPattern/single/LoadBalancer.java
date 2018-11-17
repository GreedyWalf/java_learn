package DesignPattern.single;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 负载均衡器LoadBalancer：单例类，
 */
public class LoadBalancer {

    //私有静态成员变量，存储唯一实例
    private static LoadBalancer instance;

    //服务器集合
    private List serverList;

    //私有构造函数
    private LoadBalancer() {
        serverList = new ArrayList();
    }

    //公有静态成员方法，返回唯一实例
    public static LoadBalancer getLoadBalancer() {
        if (instance == null) {
            instance = new LoadBalancer();
        }

        return instance;
    }

    //增加服务器
    public void addServer(String server) {
        serverList.add(server);
    }

    //移除服务器
    public void removeServer(String server) {
        serverList.remove(server);
    }

    //使用Random类随机获取服务器
    public String getServer() {
        Random random = new Random();
        return (String) serverList.get(random.nextInt(serverList.size()));
    }

}

/**
 * 测试类：单例的负载均衡（懒汉式，缺点：存在并发问题）
 */
class TestDemo {
    public static void main(String[] args) {
        LoadBalancer loadBalancer = LoadBalancer.getLoadBalancer();
        LoadBalancer loadBalancer2 = LoadBalancer.getLoadBalancer();
        System.out.println(loadBalancer == loadBalancer2);

        loadBalancer.addServer("server1");
        loadBalancer.addServer("server2");
        loadBalancer2.addServer("server3");
        loadBalancer2.addServer("server4");

        for (int i = 0; i < 10; i++) {
            String server = loadBalancer.getServer();
            System.out.println("请求分发至服务器：" + server);
        }
    }
}
