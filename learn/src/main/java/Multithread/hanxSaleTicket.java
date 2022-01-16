package Multithread;

import java.util.concurrent.locks.ReentrantLock;

public class hanxSaleTicket {

    //第一步  创建资源类，定义属性和和操作方法
    static class Ticket {
        //票数
        private int number = 30;
        //操作方法：卖票
        public synchronized void sale() {
            //判断：是否有票
            if(number > 0) {
                System.out.println(Thread.currentThread().getName()+" : 卖出："+(number--)+" 剩下："+number);
            }
        }
    }

    static class SaleTicketThread extends Thread{
        Ticket ticket;

        public SaleTicketThread(Ticket ticket){
            this.ticket = ticket;
        }

        @Override
        public void run() {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }

    }

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        //创建三个线程
        SaleTicketThread a = new SaleTicketThread(ticket);
        a.setName("AA");
        a.start();

        SaleTicketThread b = new SaleTicketThread(ticket);
        a.setName("BB");
        b.start();

        SaleTicketThread c = new SaleTicketThread(ticket);
        a.setName("CC");
        c.start();
    }


    static class LTicket {
        //票数量
        private int number = 30;
        //创建可重入锁
        private final ReentrantLock lock = new ReentrantLock(false);
        //卖票方法
        public void sale() {
            //上锁
            lock.lock();
            try {
                //判断是否有票
                if(number > 0) {
                    System.out.println(Thread.currentThread().getName()+" ：卖出"+(number--)+" 剩余："+number);
                }
            } finally {
                //解锁
                lock.unlock();
            }
        }
    }

    static class LockSaleTicketThread implements Runnable{
        LTicket ticket;

        public LockSaleTicketThread(LTicket ticket){
            this.ticket = ticket;
        }

        @Override
        public void run() {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }

    }

    public static class LSaleTicket {
        //第二步 创建多个线程，调用资源类的操作方法
        //创建三个线程
        public static void main(String[] args) {

            LTicket ticket = new LTicket();

            new Thread(new LockSaleTicketThread(ticket),"AA").start();
            new Thread(new LockSaleTicketThread(ticket),"BB").start();
//            LockSaleTicketThread a = new LockSaleTicketThread(ticket);
//            a.setName("AA");
//            a.start();

//            LockSaleTicketThread b = new LockSaleTicketThread(ticket);
//            a.setName("BB");
//            b.start();

//            new Thread(()-> {
//                for (int i = 0; i < 40; i++) {
//                    ticket.sale();
//                }
//            },"AA").start();
//
//            new Thread(()-> {
//                for (int i = 0; i < 40; i++) {
//                    ticket.sale();
//                }
//            },"BB").start();
//
//            new Thread(()-> {
//                for (int i = 0; i < 40; i++) {
//                    ticket.sale();
//                }
//            },"CC").start();


        }
    }




//    public class SaleTicketMain {
//        //第二步 创建多个线程，调用资源类的操作方法
//        public void main(String[] args) {
//            //创建Ticket对象
//            Ticket ticket = new Ticket();
//            //创建三个线程
//            SaleTicketThread a = new SaleTicketThread(ticket);
//            a.setName("AA");
//            a.start();
//
//            SaleTicketThread b = new SaleTicketThread(ticket);
//            a.setName("BB");
//            a.start();
//
//            SaleTicketThread c = new SaleTicketThread(ticket);
//            a.setName("CC");
//            a.start();
//        }
}


