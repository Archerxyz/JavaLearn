package LeetCode;

class PrintAsFollow_Two {

//    private AtomicInteger firstJobDone = new AtomicInteger(0);
//    private AtomicInteger secondJobDone = new AtomicInteger(0);

    // 之前使用一个Integer既做锁，又做触发条件，报错IllegalMonitorStateException，是什么原因？

    /*
    在并发编程时，对象锁是无法回避的问题，什么样的对象可以用了做锁呢？就Java语法而言，只要是对象就能作为锁来使用，然而，仍有几点必须遵守：
    锁不能为空，即用作锁的对象不能为空，这种错误很容易暴露，一般都能避免；
    锁应该是final的，此处并非要求用作锁的对象的引用一定要声明为final，而是指一个对象要用作锁的话，其引用不应该存在被修改指向的可能，否则引用指向变了，对象锁也就变了，锁可能会失效。
    针对第2点，我们可以将对象锁的引用声明为final以避开问题。除此之外，需要小心的便是，如果使用基本数据类型的封装类型，如Integer、Long等对象做锁时，一定要非常小心，对此类引用的赋值操作，在一些情况下（常量池的因素）其实是一次引用重指向的操作，会引起锁失效，此时：
    Integer i = 7;
    等价于
    Integer i = new Integer(7);
    引用i所指向的对象是一个新的对象。
    Integer i = 7;
    Integer j = 7;
    i与j是否为同一个对象呢？按照上文解释，不是，而实际上，i和j是同一个对象，因此上文的描述是不准确的。
    基于JVM的一些优化，如同String字符串池一样，为了节省内存，JVM会将小范围内Integer值放到一个对象池里，这个范围可能是-127~127（待验证），如果使用直接赋值的方式来创建Integer对象，而值也在对象池范围之内，JVM将直接从对象池中返回对象，而不会在堆内存去创建新的对象，从而达到节省内存的目的。
    */

    private Object lock = new Object();
    private boolean firstFinished = false;
    private boolean secondFinished = false;

    public PrintAsFollow_Two() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        synchronized (lock) {
            printFirst.run();
            firstFinished = true;
            lock.notifyAll();
        }
    }


    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
            while (!firstFinished) {
                // wait是会释放锁的
                lock.wait();
            }

            printSecond.run();
            secondFinished = true;
            lock.notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
            while (!secondFinished) {
                lock.wait();
            }

            printThird.run();
            lock.notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        PrintAsFollow_Two test = new PrintAsFollow_Two();

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


        // 非顺序
        t3.start();
        t1.start();
        t2.start();
    }
}