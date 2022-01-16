package Multithread;

// 奇偶数交替打印
public class CountDemo1 {
    static class Wrapper {
        private Integer count;
        public Wrapper(Integer count) {
            this.count = count;
        }
        public Integer getCount() {
            return count;
        }
        public void setCount(Integer count) {
            this.count = count;
        }
    }

    static class PrintOdd implements Runnable {
        private final Wrapper wrapper;
        public PrintOdd(Wrapper wrapper) {
            this.wrapper = wrapper;
        }
        @Override
        public void run() {
            try {
                synchronized (wrapper) {
                    while (wrapper.getCount() <= 100) {
                        if (wrapper.getCount() % 2 == 0) {
                            wrapper.wait();
                        } else {
                            System.out.println("PrintOdd thread print..." + wrapper.getCount());
                            wrapper.setCount(wrapper.getCount() + 1);
                            wrapper.notify();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    static class PrintEven implements Runnable {
        private final Wrapper wrapper;
        public PrintEven(Wrapper wrapper) {
            this.wrapper = wrapper;
        }
        @Override
        public void run() {
            try {
                synchronized (wrapper) {
                    while (wrapper.getCount() <= 100) {
                        if (wrapper.getCount() % 2 == 1) {
                            wrapper.wait();
                        } else {
                            System.out.println("PrintEven thread print..." + wrapper.getCount());
                            wrapper.setCount(wrapper.getCount() + 1);
                            wrapper.notify();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Wrapper wrapper = new Wrapper(1);
        PrintOdd printOdd = new PrintOdd(wrapper);
        PrintEven printEven = new PrintEven(wrapper);
        new Thread(printOdd).start();
        new Thread(printEven).start();

        try{
            Thread.sleep(1000000);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("end!");
    }
}