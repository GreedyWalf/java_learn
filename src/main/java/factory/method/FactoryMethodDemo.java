package factory.method;

/**
 * 测试类
 */
public class FactoryMethodDemo {
    public static void main(String[] args) {
        CircleFactory circleFactory = new CircleFactory();
        circleFactory.getShape().draw();

        RectangleFactory rectangleFactory = new RectangleFactory();
        rectangleFactory.getShape().draw();

        ShapeFactory.getCircle().draw();
        ShapeFactory.getRectangle().draw();
    }
}


/**
 * 1、抽象工厂角色
 */
interface IShapeFactory {
    IShape getShape();
}


/**
 * 2、具体工厂角色
 */
class CircleFactory implements IShapeFactory {

    @Override
    public IShape getShape() {
        return new Circle();
    }
}

class RectangleFactory implements IShapeFactory {

    @Override
    public IShape getShape() {
        return new Rectangle();
    }
}

/**
 * 3、抽象产品角色
 */
interface IShape {
    void draw();
}

/**
 * 4、具体产品角色
 */
class Circle implements IShape {

    @Override
    public void draw() {
        System.out.println("circle draw method~~");
    }
}

class Rectangle implements IShape {

    @Override
    public void draw() {
        System.out.println("rectangle draw method~~");
    }
}


/**
 * 定义工厂类，类中分别定义获取具体产品实例的静态方法，拓展其他具体产品时，只需要添加对应产品类和创建该产品类的静态方法即可；
 */
class ShapeFactory{

    public static IShape getCircle(){
        return new Circle();
    }


    public static IShape getRectangle(){
        return new Rectangle();
    }
}


