package Multithread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    static Lock lock1 = new ReentrantLock(false);
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread.start();
        thread1.start();

        // 中断是一种解决死锁的方式。
//        thread.interrupt();//是第一个线程中断
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {

                lockInterruptibly();
                tryLock();




            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()+"正常结束!");
            }
        }


        private void lockInterruptibly() throws InterruptedException {
            // 可中断锁
            firstLock.lockInterruptibly();
            TimeUnit.MILLISECONDS.sleep(10);//更好的触发死锁
            secondLock.lockInterruptibly();
        }

        private void tryLock() throws InterruptedException {
            while(!lock1.tryLock()){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            while(!lock2.tryLock()){
                lock1.unlock();
                TimeUnit.MILLISECONDS.sleep(10);
            }
        }
    }
}
