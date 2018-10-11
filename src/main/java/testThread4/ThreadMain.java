package testThread4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试：以卖票业务，使用synchronized、lock保证多线程并发执行安全性
 */
public class ThreadMain {
    public static void main(String[] args) throws InterruptedException {
        TicketService ticketService = new TicketService();
        List<Ticket> ticketList = ticketService.getAllTickets();

//        ticketList.forEach(ticket -> System.out.println(ticket.getTicketNo()));

        TicketTask task = new TicketTask(ticketList);

        //开启5个窗口同事买票
        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(task, "窗口-" + i);
            thread.start();
        }

    }
}

/**
 * 服务类：模拟数据库中获取所有剩余的票
 */
class TicketService {
    public List<Ticket> getAllTickets() {
        List<Ticket> ticketList = new ArrayList<>();

        for (int i = 1000; i <= 1010; i++) {
            ticketList.add(new Ticket(i));
        }

        return ticketList;
    }
}

/**
 * 票实体
 */
class Ticket {
    private Integer ticketNo;

    public void setTicketNo(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Integer getTicketNo() {
        return ticketNo;
    }

    public Ticket() {
    }

    public Ticket(Integer ticketNo) {
        this.ticketNo = ticketNo;
    }
}


///**
// * 模拟卖票业务
// */
//class TicketTask implements Runnable {
//    private List<Ticket> ticketList;
//
//
//    public TicketTask(List<Ticket> ticketList) {
//        this.ticketList = ticketList;
//    }
//
//    //使用同步方法，保证多线程对tickList成员变量之间的竞争（当然也可以使用同步代码块咯）
//    private synchronized void saleTicket() {
//        if (ticketList == null || ticketList.size() <= 0) {
//            System.out.println(Thread.currentThread().getName() + ": 不好意思，票已经卖完啦~~");
//            return;
//        }
//
//        Ticket ticket = ticketList.remove(0);
//        System.out.println(Thread.currentThread().getName() + "卖了一张票，票号为：" + ticket.getTicketNo());
//    }
//
//
//    @Override
//    public void run() {
//        while (ticketList.size() > 0) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            saleTicket();
//        }
//    }
//}


/**
 * 模拟卖票业务
 */
class TicketTask implements Runnable {
    //定义重入锁
    private final Lock lock = new ReentrantLock();

    //多线程共享变量
    private List<Ticket> ticketList;


    public TicketTask(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    //使用重入锁，保证多线程对tickList成员变量之间的竞争
    private void saleTicket() {
        try {
            lock.lock();
            if (ticketList == null || ticketList.size() <= 0) {
                System.out.println(Thread.currentThread().getName() + ": 不好意思，票已经卖完啦~~");
                return;
            }

            Ticket ticket = ticketList.remove(0);
            System.out.println(Thread.currentThread().getName() + "卖了一张票，票号为：" + ticket.getTicketNo());
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void run() {
        while (ticketList.size() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            saleTicket();
        }
    }
}






