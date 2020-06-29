package Multithread;

import java.util.concurrent.ExecutionException;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
//        checkSleepThreadState();
//        checkJoinThreadState();
        checkYieldThreadState();
    }

    private static Thread sleepThread() {

        Runnable r = () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        return new Thread(r, "sleepThread");
    }

    private static void checkSleepThreadState() throws InterruptedException {
        Thread sleepThread = sleepThread();
        sleepThread.start();
        //线程还没开始睡呢
        System.out.println(sleepThread.getState());

        Thread.sleep(1000);
        System.out.println(sleepThread.getState());

        // 指定时间的sleep,无法中断。
        sleepThread.interrupt();
        System.out.println(sleepThread.getState());

    }

    private static Thread joinThread() {
        Runnable r = () -> {
            System.out.println("I am Join");
            try {
                Thread.sleep(2000);
                System.out.println("Sleep finish!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        return new Thread(r, "joinThread");
    }

    private static void checkJoinThreadState() throws InterruptedException {
        Thread joinThread = joinThread();
        System.out.println(joinThread.getState());

        // 先 start 再 join
        joinThread.start();
        joinThread.join();
        System.out.println(Thread.currentThread().getState());

        System.out.println("MainThread finish!");
    }

    private static void checkYieldThreadState() throws InterruptedException {
        YieldThread t1 = new YieldThread("t1");
        YieldThread t2 = new YieldThread("t2");
        t1.start();
        t2.start();

        Thread.sleep(1000);

        System.out.println(t1.getState());
        System.out.println(t2.getState());
    }
}
