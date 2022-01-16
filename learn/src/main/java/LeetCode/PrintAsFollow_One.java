package LeetCode;

import java.util.concurrent.atomic.AtomicInteger;

class PrintAsFollow_One {

    private AtomicInteger firstJobDone = new AtomicInteger(0);
    private AtomicInteger secondJobDone = new AtomicInteger(0);

    public PrintAsFollow_One() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        firstJobDone.incrementAndGet();
    }


    public void second(Runnable printSecond) throws InterruptedException {
        while (firstJobDone.get() != 1) {
            // waiting for the first job to be done.
        }
        // printSecond.run() outputs "second".
        printSecond.run();
        // mark the second as done, by increasing its count.
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (secondJobDone.get() != 1) {
            // waiting for the second job to be done.
        }
        // printThird.run() outputs "third".
        printThird.run();
    }

    public static void main(String[] args) throws InterruptedException {

        PrintAsFollow_One test = new PrintAsFollow_One();

        Runnable r1 = () -> System.out.println("one");
        Runnable r2 = () -> System.out.println("two");
        Runnable r3 = () -> System.out.println("three");

        Runnable runnable1 = () -> {
            try {
                test.first(r1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable2 = () -> {
            try {
                Thread.sleep(3000);
                test.second(r2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable3 = () -> {
            try {
                test.third(r3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);


        t3.start();
        t1.start();
        t2.start();
    }
}