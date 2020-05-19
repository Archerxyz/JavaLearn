import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Multithreaded {
    static int number1 = 5000;
    static int number2 = 5000;

    private static Lock oNumLock = new ReentrantLock();
    private Condition oLessThan8000;

    public static void main(String[] args) {
        Thread newt1 = newThread(true);
        Thread newt2 = newThread(false);
        Thread deamon = newDeamonThread();

        try {
            Thread.sleep(3000);
//            newt.interrupt();
//            Thread.sleep(1000);

            newt1.interrupt();
            newt2.interrupt();

        } catch (InterruptedException e) {

        }

        System.out.println(newt1.getState());
        System.out.println(newt2.getState());
        System.out.println(deamon.getState());
        System.out.println(number1 + number2);
    }


    // 如果不使用锁，会有冲突。

    private static Thread newThread(boolean plus) {
        Runnable r = () -> {
            oNumLock.lock();
            // 临界区 就是try这一块
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    if (plus)
                    {
                        int nTemp = new Random(1).nextInt(2000);
                        number1 = number1 + nTemp;
                        number2 = number2 - nTemp;
                        System.out.println(number1 + number2);
                        System.out.println("-------------");
                    }
                    else {
                        int nTemp = new Random(2).nextInt(2000);
                        number1 = number1 - nTemp;
                        number2 = number2 + nTemp;
                    }

                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
//                number = 777;
            }
            finally {
                // 为保证一定能够解锁
                oNumLock.unlock();
            }
        };

        Thread t = new Thread(r);
        t.start();
        // 线程状态
        System.out.println(t.getState());
        // 线程优先级
        t.setPriority(5);

        return t;
    }


    // 守护线程，即使还在运行，其他线程终止了以后，进程终止。
    private static Thread newDeamonThread() {
        Runnable r = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
//                    System.out.println(System.currentTimeMillis());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {

            }
        };


        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        return t;
    }
}
