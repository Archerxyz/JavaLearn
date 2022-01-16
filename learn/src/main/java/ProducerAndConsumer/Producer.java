package ProducerAndConsumer;

public class Producer extends Thread {

    private int num;

    public AbstractStorage abstractStorage;

    public Producer(AbstractStorage abstractStorage) {
        this.abstractStorage = abstractStorage;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        produce(num);
    }


    private void produce(int num) {
        abstractStorage.produce(num);
    }
}
