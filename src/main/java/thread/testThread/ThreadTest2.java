package thread.testThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest2 {
    public static void main(String[] args) {
        //模拟100个人同时给一个人转账
        Account account = new Account();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 1; i <= 100; i++) {
            executorService.execute(new AddMoneyThread(account, 1));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }

//        for (int i = 1; i <= 100; i++) {
//            new Thread(new AddMoneyThread(account, 1)).start();
//        }

        System.out.println("账户余额：" + account.getBalance());
    }
}

/**
 * 银行账户
 */
class Account {
    //定义锁
    private Lock accountLock = new ReentrantLock();

    //账户余额
    private double balance;

    public /*synchronized*/ void deposit(double money) {
        accountLock.lock();
        double newBalance = balance + money;

        //模拟此业务需要一段处理时间
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            accountLock.unlock();
        }

        balance = newBalance;
//        balance = balance + money;
    }

    public double getBalance() {
        return balance;
    }
}

/**
 * 存钱线程
 */
class AddMoneyThread implements Runnable {

    //存入账户
    private Account account;
    //存入金额
    private double money;

    public AddMoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {
        account.deposit(money);
    }
}
