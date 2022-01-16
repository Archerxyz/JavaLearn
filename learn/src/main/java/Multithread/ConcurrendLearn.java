package Multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrendLearn {
    static List<String> list = new CopyOnWriteArrayList<>();

    static class addRun implements Runnable{
        @Override
        public void run(){
            for (int i = 0; i <30; i++) {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合获取内容
                System.out.println(Thread.currentThread().getName() + " : " + list.toString());
            }
        }
    }

    public static class LSaleTicket {
        public static void main(String[] args) {
            new Thread(new addRun(), "A").start();
            new Thread(new addRun(), "B").start();
        }
    }

}
