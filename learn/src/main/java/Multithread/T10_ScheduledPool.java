package Multithread;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;




public class T10_ScheduledPool {
    public static void main(String[] args) {

        // 线程池推荐创建方式：ThreadPoolExecutor

        // 本质调用的是 ThreadPoolExecutor。
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        String tmp = "anc" + new String("ann");

        // 第一个参数(Delay)第一个任务执行之 前需要往后面推多长时间；第二个(period)间隔多长时间；第三个参数是时间单位。
        service.scheduleAtFixedRate(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}
