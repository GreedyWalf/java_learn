package factory;

public class FactoryPatternDemo {

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        //根据传参形状类型，分别获取对应形状的实例；
        Shape circle = shapeFactory.getShape(ShapeFactory.TYPE_CIRCLE);
        circle.draw();

        Shape rectangle = shapeFactory.getShape(ShapeFactory.TYPE_RECTANGLE);
        rectangle.draw();

        Shape square = shapeFactory.getShape(ShapeFactory.TYPE_SQUARE);
        square.draw();
    }
}
