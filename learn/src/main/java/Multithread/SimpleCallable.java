package Multithread;

import java.util.Random;
import java.util.concurrent.Callable;

public class SimpleCallable implements Callable<Integer> {

    @Override
//    @MyAnnotation(userName = "abb", age = 22)
    public Integer call() {
        int nTemp = 0;
        try {

            for (int i = 0; i < 1000; i++) {
                nTemp += new Random(3).nextInt(10000);
                Thread.sleep(2);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return nTemp;
    }
}
