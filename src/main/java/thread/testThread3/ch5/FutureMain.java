package thread.testThread3.ch5;

import thread.testThread2.test2.utils.SleepUtils;

public class FutureMain {

    public static void main(String[] args) {
        Client client = new Client();
        //这里会立即返回，因为得到的是FutureData而不是RealData
        Data data = client.request("name");
//        System.out.println("等待着数据获取，数据：：" + data.getResult());
        System.out.println("请求完毕！");

        //等待数据获取，模拟处理其他业务
        SleepUtils.second(2);
        System.out.println("数据=" + data.getResult());
    }
}

/**
 * 定义一个返回数据接口
 */
interface Data {
    String getResult();
}

/**
 * 真正处理数据的类，作为FutureResult类的引用
 */
class RealData implements Data {
    protected final String result;

    public RealData(String para) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}

/**
 * Future数据构造很快，但是是一个虚拟的数据，需要装配RealData
 * <p>
 * FutureData是Future模式的关键，它实际上是真实数据RealData的代理，封装了获取RealData的等待过程
 */
class FutureData implements Data {
    protected RealData realData = null;
    protected boolean isReady = false;

    //当线程任务执行完成后，获取到真实数据，执行该方法会唤醒FutureData.getResult()阻塞的方法
    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }

        this.realData = realData;
        isReady = true;
        notifyAll();
    }


    @Override
    public synchronized String getResult() {
        while (!isReady) {
            try {
                System.out.println("如果数据没有完全获取，我是在这里等着的。。");
                wait();
                System.out.println("我被唤醒了，说明数据也准备好了。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return realData.result;
    }
}


class Client {
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        //开启一个线程去获取真实的数据
        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            //当获取到需要的数据后，将数据包装到future中，该操作会执行唤醒操作，结束future获取真实数据的等待
            future.setRealData(realData);
        }).start();

        //立即返回一个虚拟数据
        return future;
    }
}

