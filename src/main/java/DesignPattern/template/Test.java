package DesignPattern.template;

/**
 * 设计模式-模版方法模式：
 * 1、定义用户账户登录、计算利息、展示利息通用逻辑处理流程；
 * 2、子类实现父类的抽象方法，活期存储和定期存储利息计算方式具体细节由子类实现；
 * 3、实际使用时，使用父类Account引用子类实例，多态完成模版处理逻辑；
 */
public class Test {

    public static void main(String[] args) {
        //计算获取存储利息
        Account account = new CurrentAccount();
        account.handler("张无忌", "123456");

        //计算定期存储利息
        Account account2 = new SavingAccount();
        account2.handler("张无忌", "123456");
    }
}

/**
 * 抽象账户类
 */
abstract class Account {

    public boolean validateAccount(String account, String password) {
        System.out.println("账号：" + account);
        System.out.println("密码：" + password);

        //模拟登陆
        if (account.equals("张无忌") && password.equals("123456")) {
            return true;
        } else {
            return false;
        }
    }

    //计算利息
    public abstract void calculateInterest();


    //展示利息
    public void display() {
        System.out.println("显示利息。。");
    }

    //模板方法
    public void handler(String account, String password) {
        if (!validateAccount(account, password)) {
            System.out.println("账户密码错误！");
            return;
        }

        calculateInterest();
        display();
    }
}


//获取账户类：按照活期开始计算利息
class CurrentAccount extends Account {

    @Override
    public void calculateInterest() {
        System.out.println("按照活期开始计算利息。。");
    }
}


//定期账户类：按照定期开始计算利息
class SavingAccount extends Account {

    @Override
    public void calculateInterest() {
        System.out.println("按照定期开始计算利息。。");
    }
}