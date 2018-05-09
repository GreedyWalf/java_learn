package testSerializable;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Color color = new Color("red", 1);
        Car car = new Car("奔驰", 100, color);
        writeObject(car);
        readObject(car);
        System.out.println(car.getName());

        Double d = 3.14;
    }


    /**
     * 将car对象序列化后存储在文件car.ser中
     */
    private static void writeObject(Car car) throws IOException {
        System.out.println("开始序列化啦。。");
        FileOutputStream fos = new FileOutputStream("D:/car.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(car);
        oos.flush();
        fos.close();
        oos.close();
        System.out.println("序列化成功啦~~");
    }

    private static void readObject(Car car2) throws IOException, ClassNotFoundException {
        System.out.println("解序列化开始。。");
        FileInputStream fis = new FileInputStream("D:/car.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Car car = (Car) ois.readObject();
        if(car==null){
            return;
        }

        System.out.println(car==car2);
        System.out.println("解序列化成功啦~~");
        System.out.println(car);
    }
}


class Car implements Serializable {
    private String name;
    private transient Double price;
    private Color color;

    public Car() {
        System.out.println("car");
    }

    public Car(String name, double price, Color color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", color=" + color +
                '}';
    }
}

class Color implements Serializable {
    private String colorName;
    private int level;

    public Color(String colorName, int level) {
        this.colorName = colorName;
        this.level = level;
    }

    public Color() {
        System.out.println("color");
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Color{" +
                "colorName='" + colorName + '\'' +
                ", level=" + level +
                '}';
    }
}
