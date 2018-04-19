package prototype;

import java.util.Hashtable;

/**
 * 原型模式（Prototype Pattern）用于创建重复的对象，同时又能保证性能；这种类型的设计模式属于创建型模式，提供了一种创建对象的最佳方式；
 *
 *
 *使用场景：当一个对象需要在一个高代价的数据库操作时候被创建，我们可以在第一次获取的时候缓存该对象，
 * 在下一个请求时返回它的克隆，以减少数据库的使用，提高性能；
 *
 * 这种模式是实现了一个原型接口，该接口用于创建当前对象的克隆。当直接创建对象的代价比较大时，则采用这种模式；
 */
public class TestPrototype {
    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape clonedShaped2 = ShapeCache.getShape("1");
        System.out.println("Shape: " + clonedShaped2.getType());


        Shape cloneShape3 = ShapeCache.getShape("2");
        System.out.println("Shape: " + cloneShape3.getType());
    }
}

/**
 * 注意事项：这里使用的是浅拷贝，定义类实现Cloneable接口，重写clone方法；
 * 如果使用深拷贝则可以通过实现Serializable读取二进制流；
 */
abstract class Shape implements Cloneable{
    private String id;
    protected String type;

    abstract void draw();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }
}


class Rectangle extends Shape{

    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    void draw() {
        System.out.println("Rectangle draw()");
    }
}


class Square extends Shape{

    public Square(){
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Square draw()");
    }
}


class ShapeCache{

    //将数据库中获取到的对象缓存起来，当直接创建对象的代价比较大时，在下一个请求时返回对象的克隆
    private static Hashtable<String, Shape> shapeMap = new Hashtable<>();


    /**
     * ShapeCache类中，根据工厂模式中获取到对应的子类形状对象，然后调用子类重写的clone方法，创建子类复制后的对象
     * @param shapeId
     * @return
     */
    public static Shape getShape(String shapeId){
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    //模拟数据库中获取对象
    public static void loadCache(){

        Square square = new Square();
        square.setId("1");
        shapeMap.put(square.getId(), square);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("2");
        shapeMap.put(rectangle.getId(), rectangle);
    }
}