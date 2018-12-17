package thread.testThread4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试：<T> Future<T> submit(Callable<T> task);
 * 线程任务类实现callable接口，线程任务带返回值，线程池的submit()方法接收Callable实例作为参数，执行线程任务，将返回值封装在Future中，
 * 最后通过Future.get()获取线程任务的返回值数据
 */
public class ThreadPoolDemo2 {
    public static void main(String[] args) {
        //初始化只有1个线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //定义线程任务（实现Callable接口，返回值为List<User>）
        GetUserDataTask task = new GetUserDataTask();
        //submit(Callable T)：执行线程任务，返回future，使用future.get()获取线程任务的返回值
        Future<List<User>> future = executorService.submit(task);
        List<User> userList = null;

        //to do something
        try {
            Thread.sleep(1000);
            userList = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userList != null) {
            //输出获取到的全部用户集合
            userList.forEach((user) -> System.out.println("userId=" + user.getUserId()
                    + ",userName=" + user.getUserName()));
        }

        executorService.shutdown();
    }
}

class User {
    private String userId;

    private String userName;

    public User() {

    }

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

class GetUserDataTask implements Callable<List<User>> {
    @Override
    public List<User> call() throws Exception {
        List<User> userList = getAllUsers();
        if (userList == null) {
            userList = new ArrayList<>();
        }

        return userList;
    }

    private List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("111", "zhangsan"));
        userList.add(new User("112", "lisi"));
        userList.add(new User("113", "wangwu"));
        return userList;
    }
}

/**
 * 测试：<T> Future<T> submit(Runnable task, T result);
 * 注意：这里的线程任务类实现了Runnable接口；
 * 通过测试知道：future.get()获取到的result和参数中的result对象为同一个对象
 */
class ThreadPoolDemo3 {
    public static void main(String[] args) {
        User user = new User();
        GetUserTask task = new GetUserTask(user);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<User> future = executorService.submit(task, user);
        try {
            User user2 = future.get();
            System.out.println("获取用户信息：userId=" + user.getUserId() + ",userId=" + user.getUserName());
            System.out.println("获取用户信息：userId=" + user2.getUserId() + ",userId=" + user2.getUserName());
            System.out.println(user == user2); //true
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}

class GetUserTask implements Runnable {
    private static Lock lock = new ReentrantLock();

    private User user;

    public GetUserTask(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        lock.lock();
        user.setUserId("22222");
        user.setUserName("张三丰");
        lock.unlock();
    }
}
