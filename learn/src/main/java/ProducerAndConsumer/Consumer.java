package ProducerAndConsumer;

public class Consumer extends Thread {

    private int num;

    public AbstractStorage abstractStorage;

    public Consumer(AbstractStorage abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        consume(num);
    }


    private void consume(int num) {
        abstractStorage.consume(num);
    }
}
