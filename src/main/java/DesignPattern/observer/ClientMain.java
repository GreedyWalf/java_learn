package DesignPattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试：观察者模式类
 *
 *  核心：
 *  目标类中维护观察者类的集合，一般定义增加和删除观察者的方法，控制着监听观察者的数量；
 *  当观察者观察到某个状态或者条件改变，需要触发通知其他观察者时，由目标类统一通知即可；
 *  观察者之间解耦合，无需知道其他观察者所处的状态，由目标类统一通知；
 *
 */
public class ClientMain {
    public static void main(String[] args) {
        AllyControlCenter edgController = new ConcreteAllyControlCenter("EDG");
        AllyControlCenter uziController = new ConcreteAllyControlCenter("UZI");


        Player player = new Player("张三");
        Player player2 = new Player("李四");
        Player player3 = new Player("王五");
        Player player4 = new Player("张飞");
        Player player5 = new Player("关羽");
        Player player6 = new Player("赵云");

        edgController.join(player);
        edgController.join(player3);
        edgController.join(player4);
        edgController.quit(player4);
        edgController.join(player2);

        uziController.join(player4);
        uziController.join(player5);
        uziController.join(player6);

        player6.beAttacked(uziController);

        player.beAttacked(edgController);
    }
}

/**
 * 抽象观察者类
 */
interface Observer {
    void setPlayerName(String playerName);

    String getPlayerName();

    //声明支援盟军的方法
    void help();

    //声明被攻击的方法
    void beAttacked(AllyControlCenter acc);
}

class Player implements Observer {

    private String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void help() {
        System.out.println("盟友：" + this.playerName + "正在赶往支援。。");
    }

    @Override
    public void beAttacked(AllyControlCenter acc) {
        acc.notifyObserver(this.playerName);
    }
}

/**
 * 战队控制中心：目标类
 */
abstract class AllyControlCenter {
    protected String allyName;

    //定义一个集合用于存储所有盟友（存储所有的观察者）
    protected List<Observer> observerList = new ArrayList<>();

    public void setAllyName(String allyName) {
        this.allyName = allyName;
    }

    public String getAllyName() {
        return allyName;
    }

    //加入战队方法
    protected void join(Observer observer) {
        observerList.add(observer);
        System.out.println("队员" + observer.getPlayerName() + "加入了" + this.allyName + "军队！");
    }

    //退出战队方法
    protected void quit(Observer observer) {
        observerList.remove(observer);
        System.out.println("队员" + observer.getPlayerName() + "退出了" + this.allyName + "军队！");
    }

    //声明唤醒方法
    public abstract void notifyObserver(String playerName);
}


/**
 * 具体目标类：重新抽象目标类定义的通知所有观察者的方法notifyObserver()
 */
class ConcreteAllyControlCenter extends AllyControlCenter {

    public ConcreteAllyControlCenter(String allyName) {
        this.allyName = allyName;
        System.out.println(allyName + "--战队组成成功！");
        System.out.println("---------------");
    }

    @Override
    public void notifyObserver(String playerName) {
        System.out.println(allyName + "战队紧急通知，盟友" + playerName + "正在被攻击，请求协助。。");
        for (Observer observer : observerList) {
            if (!playerName.equalsIgnoreCase(observer.getPlayerName())) {
                observer.help();
            }
        }
    }
}