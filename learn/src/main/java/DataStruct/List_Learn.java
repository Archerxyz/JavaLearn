package DataStruct;

import java.util.Iterator;

public class List_Learn<T> implements Iterable<T> {

    private static final int DEFAULT_SIZE = 10;
    private T[] items;

    private int size;

    public List_Learn() {
    }

    public void clear() {

    }

    // size返回的是装了多少东西，而不是占多大空间。
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T get(int index) {
        // 超出范围的警告？
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return items[index];
    }

    public void set(int index, T newVal) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }

        items[index] = newVal;
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < this.size) {
            return;
        }

        T[] oldItems = this.items;
        this.items = (T[]) new Object[newCapacity];


        for (int i = 0; i < this.size; i++) {
            this.items[i] = oldItems[i];
        }
    }

    // 末尾加元素
    public void add(T newItem) {
        if (this.items.length == this.size) {
            // 扩张容量，但是为何要加一？ 是为了保证 size不为0
            ensureCapacity(2 * this.size + 1);
        }

        items[size] = newItem;
    }

    public void add(int index, T newItem) {
        if (this.items.length == this.size) {
            // 扩张容量，但是为何要加一？ 是为了保证 size不为0
            ensureCapacity(2 * this.size + 1);
        }

        for (int i = this.size; i > index; i--) {
            items[i] = items[i - 1];
        }

        // 更新size
        this.size++;
    }

    public void remove(int index) {
        for (int i = index; i < size; i++) {
            items[i] = items[i + 1];
        }

        this.size--;
    }



    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
