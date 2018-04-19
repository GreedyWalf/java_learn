package decorator;

/**
 * 装饰器模式（Decorator Pattern）允许向一个现有对象添加新的功能，同时又不改变其结构。
 *
 * 这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性前提下，提供了额外的功能；
 */
public class TestDecorator {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.draw();


        Shape redCircle = new RedShapeDecorator(new Circle());
        redCircle.draw();
    }
}


/**
 * 定义形状接口
 */
interface Shape{
    void draw();
}

/**
 * 实体实现形状的接口中的draw()
 */
class Circle implements Shape{

    @Override
    public void draw() {
        System.out.println("draw circle~~");
    }
}

/**
 * 构建形状装饰类
 */
abstract class ShapeDecorator implements Shape{

    protected Shape decorator;

    public ShapeDecorator(Shape decorator){
        this.decorator = decorator;
    }

    @Override
    public void draw() {
        decorator.draw();
    }
}

/**
 * 使用装饰类构建形状颜色实体类，重新draw()，在构建形状时，并附加上颜色
 */
class RedShapeDecorator extends ShapeDecorator{

    public RedShapeDecorator(Shape decorator) {
        super(decorator);
    }

    @Override
    public void draw() {
        super.draw();
        setColor(decorator);
    }

    public void setColor(Shape decorator){
        System.out.println("set color red~~");
    }
}