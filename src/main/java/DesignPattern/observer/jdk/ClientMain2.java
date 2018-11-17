package DesignPattern.observer.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 测试：jdk提供的观察者模式
 * 注意点：Observable类定义类成员变量changed，当该值为true时，再手动调用notifyObservers()方法会通知所有注册到目标类集合（Vector）
 * 的观察者对象的update()方法；，
 *
 */
public class ClientMain2 {
    public static void main(String[] args) {
        MySubject mySubject = new MySubject();
        Operator operator = new Operator();
        mySubject.addObserver(operator);

        List<String> userNames = new ArrayList<>();
        userNames.add("aaa");
        mySubject.setResult(userNames);
    }
}


/**
 * 观察者
 */
class Operator implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        MySubject subject = (MySubject) o;
        System.out.println("数据更新了！size=" + subject.getResult().size());
    }
}

/**
 * 被观察者：目标类
 */
class MySubject extends Observable {

    private List<String> result = new ArrayList<>();

    public void setResult(List<String> result) {
        this.result = result;
        if (result.size() > 0) {
            setChanged();
            notifyObservers();
        }
    }

    public List<String> getResult() {
        return result;
    }
}

