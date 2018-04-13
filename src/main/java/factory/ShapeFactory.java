package factory;

/**
 * 1、工厂类角色：创建工厂类
 */
public class ShapeFactory {
    public static final int TYPE_CIRCLE = 1; //圆形
    public static final int TYPE_RECTANGLE = 2; //矩形
    public static final int TYPE_SQUARE = 3; //椭圆

    public Shape getShape(int shapeType) {
        switch (shapeType) {
            case TYPE_CIRCLE:
                return new Circle();
            case TYPE_RECTANGLE:
                return new Rectangle();
            case TYPE_SQUARE:
                return new Square();
            default:
                return null;
        }
    }
}

/**
 * 2、抽象产品角色：定义一个所有具体产品类都需要继承或实现的接口
 */
interface Shape {
    void draw();
}


/**
 * 3、具体产品角色：工厂类创建并返回的具体产品
 */
class Circle implements Shape {

    public void draw() {
        System.out.println("circle draw method..");
    }
}

class Rectangle implements Shape {

    public void draw() {
        System.out.println("rectangle draw method..");
    }
}

class Square implements Shape {

    public void draw() {
        System.out.println("square draw method..");
    }
}


/**
 * 使用反射机制可以解决每次增加一个产品时。都需要增加一个对象实现工厂的特点
 */
class ShapeFactory2 {
    public static Object getClass(Class<? extends Shape> clazz) {
        Object obj = null;

        try {
            //这里使用反射，使用无参的构造方法创建实例，实际情况下不常使用，一般实例化会初始化属性值；
            obj = Class.forName(clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }
}