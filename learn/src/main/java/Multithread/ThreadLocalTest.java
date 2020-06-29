package Multithread;

import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

//    volatile static Person p = new Person();
private static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 没set 会发现是空的
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 没set 会发现拿不着
            tl.set(new Person("asd"));
        }).start();
    }
}

