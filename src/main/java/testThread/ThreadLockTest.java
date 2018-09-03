package testThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLockTest {
    //重入锁
    Lock lock = new ReentrantLock();

    private List<Integer> arrayList = new ArrayList<>();

    public static void main(String[] args) {
        final ThreadLockTest test = new ThreadLockTest();

        new Thread(){
            @Override
            public void run() {
//                testEquals.insert(Thread.currentThread());
                test.insert2(Thread.currentThread());
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                testEquals.insert(Thread.currentThread());
                test.insert2(Thread.currentThread());
            }
        }).start();
    }


    private void insert(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName() + "得到了锁");
            for(int i=0; i<5; i++){
                arrayList.add(i);
            }

        } catch (Exception e) {
            //todo handle exception

        } finally {
            System.out.println(thread.getName() + "释放了锁");
            lock.unlock();
        }
    }


    private void insert2(Thread thread) {
        if(lock.tryLock()){
            try {
                System.out.println(thread.getName() + "获得了锁");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
//                lock.unlock();
                System.out.println(thread.getName() + "释放了锁");
            }
        }else{
            System.out.println(thread.getName() + "获得锁失败");
        }
    }
}

