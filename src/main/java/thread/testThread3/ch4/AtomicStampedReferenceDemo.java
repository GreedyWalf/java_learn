package thread.testThread3.ch4;

import java.util.concurrent.atomic.AtomicStampedReference;


/**
 * 使用AtomicStampedReference，在每次更新操作同时更新stamp值，作为已经更新过的标志，当再次
 * 更新时，发现stamp值已经更新了，则不会再次更新；（解决CAS操作，无法识别更新多次的操作）
 */
public class AtomicStampedReferenceDemo {

    //实例化一个带时间戳的原子类整数对象
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19, 0);

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int timestamp = money.getStamp();

            //优惠充值线程
            new Thread(() -> {
                while (true) {
                    while (true) {
                        Integer m = money.getReference();
                        if (m < 20) {
                            if (money.compareAndSet(m, m + 20, timestamp, timestamp + 1)) {
                                System.out.println("余额小于20元，充值成功，余额：" + money.getReference() + "元！");
                                break;
                            }
                        } else {
                            System.out.println("余额大于20元，不享受充值优惠！");
                            break;
                        }
                    }
                }
            }).start();
        }

        //用户消费线程，模拟消费行为
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                while (true) {
                    int timestamp = money.getStamp();
                    Integer m = money.getReference();
                    if (m > 10) {
                        System.out.println("大于10元");
                        if (money.compareAndSet(m, m - 10, timestamp, timestamp + 1)) {
                            System.out.println("成功消费10元，余额：" + money.getReference());
                            break;
                        }
                    } else {
                        System.out.println("余额不足！");
                        break;
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
        }).start();
    }
}
