package Multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T08_SynchronusQueue { //容量为0
    public static void main(String[] args) throws InterruptedException, IOException {
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String strInput = bufferedReader.readLine();

        //阻塞等待消费者消费。我不输入，就一直没有输出。
        strs.put(strInput);

        System.out.println(strs.size());
    }
}
