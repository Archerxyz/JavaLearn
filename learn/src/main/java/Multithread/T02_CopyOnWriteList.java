package Multithread;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class T02_CopyOnWriteList {

    public static void main(String[] args) {
        List<String> lists =
                // 这个会出并发问题！ArrayIndexOutOfBoundsException
//                new ArrayList<>();

                // Vector 很快！
                 new Vector();

                //  CopyOnWriteArrayList 有点慢？
//                new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];

        Thread[] thsForRead = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            Runnable task = () -> {
                for (int i1 = 0; i1 < 1000; i1++)
                    lists.add("a" + r.nextInt(10000));
            };
            ths[i] = new Thread(task);
        }

        for (int i = 0; i < thsForRead.length; i++) {
            Runnable task = () -> {
                for (int i1 = 0; i1 < 1000; i1++)

                    System.out.println(lists.get(r.nextInt(lists.size())));
            };
            thsForRead[i] = new Thread(task);
        }

        runAndComputeTime(ths, thsForRead);

        System.out.println(lists.size());
    }

    static void runAndComputeTime(Thread[] ths, Thread[] thsForRead) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(Thread::start);
        Arrays.asList(thsForRead).forEach(Thread::start);

        Arrays.asList(ths).forEach(t -> {
            try {
                // 在执行主线程前，先执行他们
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Arrays.asList(thsForRead).forEach(t -> {
            try {
                // 在执行主线程前，先执行他们
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long s2 = System.currentTimeMillis();
        System.out.println("Costing Time:" + (s2 - s1));
    }
}
