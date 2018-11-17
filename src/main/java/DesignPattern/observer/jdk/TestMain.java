package DesignPattern.observer.jdk;


import java.util.Observable;
import java.util.Observer;

/**
 * 测试jdk提供的观察者模式，重写自定义玩家组队打怪例子
 */
public class TestMain {
    public static void main(String[] args) {
        AllyControllerCenter edgController = new AllyControllerCenter("EDG");
        AllyControllerCenter uziController = new AllyControllerCenter("UZI");


        Player player = new Player("张三");
        Player player2 = new Player("李四");
        Player player3 = new Player("王五");
        Player player4 = new Player("张飞");
        Player player5 = new Player("关羽");
        Player player6 = new Player("赵云");

        edgController.addObserver(player);
        edgController.addObserver(player3);
        edgController.addObserver(player4);
        edgController.deleteObserver(player4);
        edgController.addObserver(player2);

        uziController.addObserver(player4);
        uziController.addObserver(player5);
        uziController.addObserver(player6);

        player6.beAttacked(uziController);
        player2.beAttacked(edgController);
    }
}

/**
 * 观察者
 */
class Player implements Observer {

    private String playerName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void update(Observable o, Object arg) {
        //自己已经被攻击了，自己不能协助自己，这里在控制中心通知所有盟友时，排除掉自己；
        if (this == arg) {
            return;
        }

        System.out.println("盟友：" + playerName + "正在协助。。");
    }

    public void beAttacked(Observable o) {
        //当战队中的一个盟友被攻击时，经过控制中心通知战队中其他盟友请求协助
        o.notifyObservers(this);
    }
}

/**
 * 被观察者：目标类 控制中心，统一通知盟友信息
 */
class AllyControllerCenter extends Observable {

    private String allyName;

    public void setAllyName(String allyName) {
        this.allyName = allyName;
    }

    public String getAllyName() {
        return allyName;
    }

    public AllyControllerCenter(String allyName) {
        this.allyName = allyName;
    }


    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        Player player = (Player) o;
        System.out.println("队友：" + player.getPlayerName() + "加入战队" + allyName);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        Player player = (Player) o;
        System.out.println("队友：" + player.getPlayerName() + "退出战队" + allyName);
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers(Object arg) {
        Player player = (Player) arg;
        System.out.println("盟友：" + player.getPlayerName() + "正在被攻击，请求协助。。");

        //将changed标志位设置为true
        super.setChanged();
        //通知所有注册到该战队的所有盟友(调用player实例的update()方法)
        super.notifyObservers(arg);
    }
}


