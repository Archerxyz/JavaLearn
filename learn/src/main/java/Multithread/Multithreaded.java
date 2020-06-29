package Multithread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Multithreaded {
    static int number1 = 5000;
    static int number2 = 5000;
    static int deadlockNum = 100;

    // 线程安全的变量，无需用锁，效率高
    static volatile int number3 = 5000;

    // 线程不安全，但是其他线程不会看到它是null的情况。
    final static Map<String, Integer> account = new HashMap<>();


    private static Lock oNumLock = new ReentrantLock();

    private static Lock odeadLock = new ReentrantLock();
    private static Condition oLessThanZeroCondition;

    public static void main(String[] args) {
        try {
//            simulationCriticalSection();
//            simulationDeadlock();
//            simulationSynchronized();
            System.out.println(callableAndFuture());
            theardPool();
        } catch (InterruptedException | ExecutionException e1) {

        }
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
        // 线程优先级 Windows下才有效！
        t.setPriority(5);

        return t;
    }

    private static void simulationCriticalSection() throws InterruptedException{
        Thread newt1 = newThread(true);
        Thread newt2 = newThread(false);
        Thread deamon = newDeamonThread();


        Thread.sleep(3000);
        newt1.interrupt();
        newt2.interrupt();

        System.out.println(newt1.getState());
        System.out.println(newt2.getState());
        System.out.println(deamon.getState());
    }

    // 守护线程，即使还在运行，其他线程终止了以后，进程终止。
    private static Thread newDeamonThread() {
        Runnable r = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
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

    private static Thread newDeadLockThread() {
        oLessThanZeroCondition = odeadLock.newCondition();
        Runnable r = () -> {
            odeadLock.lock();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(10);
                    System.out.println(deadlockNum--);
                    if (deadlockNum < 0)
                    {
                        // 注意，是锁在await，实质上是线程等待?锁控制了当前执行它的线程？
                        // 死锁某个线程，不会导致主线程无法退出。
                        oLessThanZeroCondition.await();
                    }
                }
            } catch (InterruptedException e) {

            }
            finally {
                odeadLock.unlock();
            }
        };

        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        return t;
    }

    private static Thread newSignalAllThread() {
        Runnable r = () -> {
            odeadLock.lock();
            try {
                deadlockNum = 100;
                // 不能在主线程signalAll，因为它不是立即激活其他线程，需要等自己进程结束！
                oLessThanZeroCondition.signalAll();
            } finally {
                odeadLock.unlock();
            }
        };

        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        return t;
    }


    private static void simulationDeadlock() throws InterruptedException{
        Thread deadlockThread = newDeadLockThread();
        Thread.sleep(2000);

        System.out.println(deadlockThread.getState());

        Thread signalAllThread = newSignalAllThread();
        Thread.sleep(2000);
        System.out.println(signalAllThread.getState());
    }

    // 同步方法:实质是调用对象的内部锁
    // 注意是静态方法 表示synchronized(T.class)，这里这个synchronized(T.class)锁的就是T类的对象。
    private synchronized static void synchronizedMethod(boolean plus) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            if (plus) {
                int nTemp = new Random(1).nextInt(3000);
                number1 = number1 + nTemp;
                number2 = number2 - nTemp;
                System.out.println(number1 + number2);
                System.out.println("-------------");
            } else {
                int nTemp = new Random(2).nextInt(3000);
                number1 = number1 - nTemp;
                number2 = number2 + nTemp;
            }
            Thread.sleep(1);
        }
    }

    private static Thread newSynchronizedThread(boolean plus) {
        Runnable r = () -> {
            try {
                synchronizedMethod(plus);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(r);
        t.start();
        return t;
    }

    // 使用同步方法 同等与模拟1
    private static void simulationSynchronized() throws InterruptedException{
        Thread newt1 = newSynchronizedThread(true);
        Thread newt2 = newSynchronizedThread(false);

        Thread.sleep(3000);
        newt1.interrupt();
        newt2.interrupt();

        // 线程临时变量！虽然按理来说线程间的数据是共享的。
//        ThreadLocal<SimpleDateFormat> dataFomat =
//                ThreadLocal.withInitial(()->new SimpleDateFormat("yyyy-MM-dd"));
    }

    //Callalbe 与 Future
    private static int callableAndFuture() throws ExecutionException, InterruptedException {
        Callable<Integer> myComputation = () -> {
            int nTemp = 0;
            for (int i = 0; i < 1000; i++) {
                nTemp += new Random(3).nextInt(10000);
                Thread.sleep(2);
            }
            return nTemp;
        };

        FutureTask<Integer> task = new FutureTask<Integer>(myComputation);
        Thread t = new Thread(task);
        t.start();
        System.out.println("Main thread: begin FutureTask");
        simulationSynchronized();
        return task.get();
    }

    private static void theardPool() throws InterruptedException, ExecutionException {

        ExecutorService pool = Executors.newCachedThreadPool();

        SimpleCallable oTempCallable = new SimpleCallable();

        Future<Integer> result = pool.submit(oTempCallable);

        pool.shutdown();

        System.out.println(result.get());
    }
}
