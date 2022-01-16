package ProducerAndConsumer;

import java.util.LinkedList;
import java.util.List;

public class Storage1 implements AbstractStorage{

    private final int MAX_SIZE = 1000;

    // 可以考虑用BlockQueue来实现！
    private LinkedList list = new LinkedList();

    @Override
    public void produce(int num) {
        // 生产时，先拿到仓库的锁！
        synchronized (list){
            while (list.size() + num > MAX_SIZE){
                System.out.println("【要生产的产品数量】:" + num + "\t【库存量】:" + list.size() + "\t暂时不能执行生产任务!");

                try{
                    // 把锁还回去，自己去休息去了，等有人叫他
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 有人notify的话，produce就会继续干活，从上次睡觉的地方开始。
            // 其他人把produce叫醒了，而produce在临界区，因此实际上其他人会被把 仓库的锁 释放， 然后produce拿到这个锁，继续干活
            for (int i = 0; i < num; i++) {
                list.add(new Object());
            }

            System.out.println("【已经生产产品数】:" + num + "\t【现仓储量为】:" + list.size());

            // 同理，由于干完活了，把可能其他的人叫醒，因此，喊话完一定要释放锁。
            list.notifyAll();
        }

        Integer a = 1;
        a.equals(1);
    }


    @Override
    public void consume(int num) {
        synchronized (list) {
            while (list.size() < num) {

                System.out.println("【要消费的产品数量】:" + num + "\t【库存量】:" + list.size() + "\t暂时不能执行消费任务!");

                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < num; i++) {
                list.remove();
            }

            System.out.println("【已经消费产品数】:" + num + "\t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }
}
