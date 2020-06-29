package Multithread;

public class YieldThread extends Thread {

    YieldThread(String s) {
        super(s);
    }

    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(getName() + ":" + i);
            if (i == 100)
                yield();
        }
    }

}
