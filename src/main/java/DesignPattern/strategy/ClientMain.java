package DesignPattern.strategy;

/**
 * 测试：策略模式
 * 策略模式通常把一个系列的算法封装到一系列具体策略类里面，作为抽象类的子类，将算法的职责和算法本省分割开来；
 */
public class ClientMain {
    public static void main(String[] args) {
        MovieTicket movieTicket = new MovieTicket();
        double orgPrice = 100;
        movieTicket.setPrice(orgPrice);
        Discount discount = new HalfDiscount();
        movieTicket.setDiscount(discount);
        System.out.println("优惠后单价：" + movieTicket.getPrice());

    }
}

/**
 * 环境类（Context）
 */
class MovieTicket {

    private double price;

    private Discount discount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        if (discount == null) {
            return price;
        }

        return discount.calculate(price);
    }
}

/**
 * 抽象策略类（Strategy）
 */
interface Discount {
    double calculate(double price);
}

/**
 * 具体策略类（ConcreteStrategy）：半折优惠策略：单价*0.5
 */
class HalfDiscount implements Discount {

    @Override
    public double calculate(double price) {
        return price * 0.5;
    }
}

/**
 * 具体策略类（ConcreteStrategy）：优惠策略：单价-10
 */
class ChipDiscount implements Discount {

    @Override
    public double calculate(double price) {
        return price - 10;
    }
}