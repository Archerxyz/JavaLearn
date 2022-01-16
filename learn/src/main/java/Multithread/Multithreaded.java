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
                    if (plus) {
                        int nTemp = new Random(1).nextInt(2000);
                        number1 = number1 + nTemp;
                        number2 = number2 - nTemp;
                        System.out.println(number1 + number2);
                        System.out.println("-------------");
                    } else {
                        int nTemp = new Random(2).nextInt(2000);
                        number1 = number1 - nTemp;
                        number2 = number2 + nTemp;
                    }

                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
//                number = 777;
            } finally {
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

    private static void simulationCriticalSection() throws InterruptedException {
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
                    if (deadlockNum < 0) {
                        // 注意，是锁在await，实质上是线程等待?锁控制了当前执行它的线程？
                        // 死锁某个线程，不会导致主线程无法退出。
                        oLessThanZeroCondition.await();
                    }
                }
            } catch (InterruptedException e) {

            } finally {
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


    private static void simulationDeadlock() throws InterruptedException {
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
        int i = 10000;
        i = i >> 1;

        Thread t = new Thread(r);
        t.start();
        return t;
    }

    // 使用同步方法 同等与模拟1
    private static void simulationSynchronized() throws InterruptedException {
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

    public void test1() {
        String s1 = "a" + "b" + "c";//编译期优化：等同于"abc"
        String s2 = "abc"; //"abc"一定是放在字符串常量池中，将此地址赋给s2
        /*
         * 最终.java编译成.class,再执行.class
         * String s1 = "abc";
         * String s2 = "abc"
         */
        System.out.println(s1 == s2); //true
        System.out.println(s1.equals(s2)); //true
    }

    public void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";

        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop";//编译期优化
        //如果拼接符号的前后出现了变量，则相当于在堆空间中new String()，具体的内容为拼接的结果：javaEEhadoop
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4);//true
        System.out.println(s3 == s5);//false
        System.out.println(s3 == s6);//false
        System.out.println(s3 == s7);//false
        System.out.println(s5 == s6);//false
        System.out.println(s5 == s7);//false
        System.out.println(s6 == s7);//false
        //intern():判断字符串常量池中是否存在javaEEhadoop值，如果存在，则返回常量池中javaEEhadoop的地址；
        //如果字符串常量池中不存在javaEEhadoop，则在常量池中加载一份javaEEhadoop，并返回次对象的地址。
        String s8 = s6.intern();
        System.out.println(s3 == s8);//true
    }

    /**
     * 思考：
     * new String("a") + new String("b")呢？
     * 对象1：new StringBuilder()
     * 对象2： new String("a")
     * 对象3： 常量池中的"a"
     * 对象4： new String("b")
     * 对象5： 常量池中的"b"
     * <p>
     * 深入剖析： StringBuilder的toString():
     * 对象6 ：new String("ab")
     * 强调一下，toString()的调用，在字符串常量池中，没有生成"ab"
     */

    public void StringNewTest() {
        String str = new String("a") + new String("b");

    }


    /**
     * StringIntern.java中练习的拓展：
     */
    public void StringNewTest2() {
        //执行完下一行代码以后，字符串常量池中，是否存在"11"呢？答案：不存在！！
        String s3 = new String("1") + new String("1");//new String("11")
        //在字符串常量池中生成对象"11"，代码顺序换一下，实打实的在字符串常量池里有一个"11"对象
        String s4 = "11";
        String s5 = s3.intern();

        // s3 是堆中的 "ab" ，s4 是字符串常量池中的 "ab"
        System.out.println(s3 == s4);//false

        // s5 是从字符串常量池中取回来的引用，当然和 s4 相等
        System.out.println(s5 == s4);//true

    }
}
