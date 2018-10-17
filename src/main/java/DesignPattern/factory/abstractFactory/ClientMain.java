package DesignPattern.factory.abstractFactory;

/**
 * 测试：抽象工厂模式实例
 *  计划生产两种风格的组件：Spring风格、Summer风格（具体包括具体产品：Button、TextField、ComboBox）；
 *  在这里：
 *      Button、SpringButton、SummerButton为一个产品等级；
 *      TextField、SpringTextField、SummerField为一个产品等级；
 *      ComboBox、SpringComboBox、SummerComboBox为一个产品等级；
 *
 *      Button、TextField、ComboBox为一个产品族（对应SkinFactory抽象工厂）
 *      SpringButton、SpringTextField、SpringComboBox为一个产品族 （对应SpringSkinFactory具体工厂）
 *      SummerButton、SummerTextField、SummerComboBox为一个产品族 （对应SummerSkinFactory具体工厂）
 *
 *     确定一个产品所处于的产品等级和产品族，就能唯一确定一个产品；
 *     抽象工厂模式是所有形式的工厂模式中最为抽象和最具一般性的一种形式；
 */
public class ClientMain {
    public static void main(String[] args) {
        SkinFactory factory;
        //使用具体的工厂，完成特定产品族实例化
        //factory = new SpringSkinFactory();
        factory = new SummerSkinFactory();
        Button button = factory.createButton();
        button.display();

        TextField textField = factory.createTextField();
        textField.display();

        ComboBox comboBox = factory.createComboBox();
        comboBox.display();
    }
}


//按钮接口：抽象产品
interface Button {
    void display();
}

//Spring按钮类：具体产品
class SpringButton implements Button {

    @Override
    public void display() {
        System.out.println("显示为浅绿色按钮。。");
    }
}

//Summer按钮类：具体产品
class SummerButton implements Button {

    @Override
    public void display() {
        System.out.println("显示为浅蓝色按钮。。");
    }
}


//文本框接口：抽象产品
interface TextField {
    void display();
}


//Spring文本框类：具体产品
class SpringTextField implements TextField {

    @Override
    public void display() {
        System.out.println("显示为浅绿色文本框。。");
    }
}


//Summer文本框类：具体产品
class SummerTextField implements TextField {

    @Override
    public void display() {
        System.out.println("显示为浅蓝色文本框。。");
    }
}

//组合框接口：抽象产品
interface ComboBox {
    void display();
}

//Spring组合框类：具体产品
class SpringComboBox implements ComboBox {

    @Override
    public void display() {
        System.out.println("显示为浅绿色组合框。。");
    }
}


//Summer组合框类：具体产品
class SummerComboBox implements ComboBox {

    @Override
    public void display() {
        System.out.println("显示为浅蓝色组合框。。");
    }
}


/**
 * 界面皮肤工厂接口：抽象工厂
 * 本例中：统一类型的按钮、文本框、组合框一起组成一个产品族（比如Spring风格、Summer风格），
 * 抽象工厂中抽象出一个产品族通用类型创建实例方法，再由特定产品族具体工厂类分别实现；
 */
interface SkinFactory {
    Button createButton();

    TextField createTextField();

    ComboBox createComboBox();
}


//Spring皮肤工厂：具体工厂（Spring风格的组件作为一个产品族，统一由该具体工厂生产）
class SpringSkinFactory implements SkinFactory {

    @Override
    public Button createButton() {
        return new SpringButton();
    }

    @Override
    public TextField createTextField() {
        return new SpringTextField();
    }

    @Override
    public ComboBox createComboBox() {
        return new SpringComboBox();
    }
}

//Summer皮肤工厂：具体工厂
class SummerSkinFactory implements SkinFactory {

    @Override
    public Button createButton() {
        return new SummerButton();
    }

    @Override
    public TextField createTextField() {
        return new SummerTextField();
    }

    @Override
    public ComboBox createComboBox() {
        return new SummerComboBox();
    }
}




