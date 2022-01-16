package Multithread;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * volatile 关键字，使一个变量在多个线程间可见
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 *
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 *
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 *
 * @author mashibing
 */

public class Volatile {

    /*volatile*/ boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别

    void m() {
        System.out.println("m start");
        while (running) {
        }
        System.out.println("m end!");
    }

    public static void main(String[] args) {
        Volatile t = new Volatile();

        // 用这种方法可以转换为Runnable，为线程使用它实际上是一个方法参数。
        Runnable temp = t::m;
        Consumer<String> methodParam = t::printValur;

        // 言归正传：

        new Thread(t::m, "t1").start();

        try {
            // 主线程睡眠一秒
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("I want change the runnning!" + t.running);

        t.running = false;

        System.out.println("but I cant. because my running is a copy");

    }

    private void printValur(String s) {
        System.out.println(s);
    }



    // 禁止重排序
    static class Example {

        int x = 0;

        volatile boolean v = false;

        public void writer() {
            x = 42;
            v = true;
        }

        public void reader() {

            if (v) {
                System.out.println(x);
            }

        }

        public static void main(String[] args) {
            Example e = new Example();

            Thread t1 = new Thread(e::writer);
            Thread t2 = new Thread(e::reader);

            t1.start();
            t2.start();

        }
    }
}
