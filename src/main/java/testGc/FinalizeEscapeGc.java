package testGc;

public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("--->>线程：" + Thread.currentThread().getName());
        System.out.println("finalize method executed!");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        SAVE_HOOK = null;

        //第一次执行该方法时，稍后Finalizer线程会调用SAVE_HOOK对象的finalize()方法，
        // 此时可以挽救一下已经面临回收的SAVE_HOOK对象
        System.gc();

        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no i am dead :(");
        }

        SAVE_HOOK = null;

        //再次执行gc()方法时，不会执行被回收对象的finalize()方法，被回收的对象也就无法逃脱被回收的命运了
        System.gc();

        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no i am dead :(");
        }
    }

}
