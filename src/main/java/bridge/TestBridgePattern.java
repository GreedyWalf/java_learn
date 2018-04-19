package bridge;

/**
 * 桥接模式（Bridge Pattern）是用于把抽象化与实现化解耦，使得二者可以独立变化。 属于结构型模式，
 * 它通过提供抽象化和实现化之间的桥接结构，来实现两者的解耦；
 *
 * 测试使用Shape和DrawAPI类画出不同颜色的图
 */
public class TestBridgePattern {
    public static void main(String[] args) {
        Shape redCircle = new Circle(100, 100, 10, new RedCircle());
        Shape greenCircle = new Circle(100, 100, 100, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }
}

/**
 * 使用桥接模式，需要抽象出来一个接口，接口可以代表一个维度的拓展，接口会被实体类引用。
 */
interface DrawAPI {
    void drawCircle(int radius, int x, int y);
}


/**
 * 具体的实现类不用继承Circle类，实现对应的功能接口就可以，防止因为继承造成的类爆炸；
 */
class RedCircle implements DrawAPI {

    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: " +
                radius + ", x:" + x + ", " + y + "]");
    }
}


class GreenCircle implements DrawAPI {

    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: " + radius
                + ", x: " + x + "," + y + "]");
    }
}

/**
 * 使用DrawAPI接口创建抽象类Shape
 */
abstract class Shape {

    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    abstract void draw();
}


/**
 * 创建继承了Shape抽象类的实体类
 */
class Circle extends Shape {

    private int x, y, radius;

    protected Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    void draw() {
        drawAPI.drawCircle(radius, x, y);
    }
}