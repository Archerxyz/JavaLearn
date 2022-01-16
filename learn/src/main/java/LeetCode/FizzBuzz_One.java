package LeetCode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

// 这种写法感觉对，但是效率其实不太高。因为所有线程一直在访问num。
// 由于对排序还有要求，所以必须有东西控制其打印顺序。
public class FizzBuzz_One {
    private int n;
    private AtomicInteger num = new AtomicInteger(1);
    final Object printLock = new Object();

    public FizzBuzz_One(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (num.get() <= n) {
            synchronized (printLock) {
                if (num.get() % 3 == 0 && num.get() % 5 != 0) {
                    printFizz.run();
                    num.incrementAndGet();
                    printLock.notifyAll();
                } else {
                    printLock.wait();
                }
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (num.get() <= n) {
            synchronized (printLock) {
                if (num.get() % 3 != 0 && num.get() % 5 == 0) {
                    printBuzz.run();
                    num.incrementAndGet();
                    printLock.notifyAll();
                } else {
                    printLock.wait();
                }
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (num.get() <= n) {
            synchronized (printLock) {
                if (num.get() % 3 == 0 && num.get() % 5 == 0) {
                    printFizzBuzz.run();
                    num.incrementAndGet();
                    printLock.notifyAll();
                } else {
                    printLock.wait();
                }
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (num.get() <= n) {
            synchronized (printLock) {
                if (num.get() % 3 != 0 && num.get() % 5 != 0) {

                    printNumber.accept(num.get());
                    num.incrementAndGet();
                    printLock.notifyAll();
                } else {
                    printLock.wait();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        FizzBuzz_One test = new FizzBuzz_One(15);

        Runnable r1 = () -> System.out.println("fizz");
        Runnable r2 = () -> System.out.println("buzz");
        Runnable r3 = () -> System.out.println("fizzbuzz");
        Runnable r4 = () -> System.out.println("");

        Runnable runnable1 = () -> {
            try {
                test.fizz(r1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable2 = () -> {
            try {
                test.buzz(r2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable3 = () -> {
            try {
                test.fizzbuzz(r3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable4 = () -> {
            try {
                test.number(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        Thread t4 = new Thread(runnable4);


        t3.start();
        t1.start();
        t2.start();
        t4.start();
    }

}
