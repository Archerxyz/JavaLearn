package DataStruct;

public class LinkList_Learn<T> {

    private int size;
    private int modCount;

//    private static int Capacity = 10;

    private Node pHead = null;
    private Node pLast = null;

    private static class Node<T> {
        public T data;
        public Node next;
        public Node prev;

        public Node(T data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkList_Learn() {
        pHead = new Node<T>(null, null, null);
        pLast = new Node<T>(null, null, pHead);
        pHead.next = pLast;

        //初始化链表理论上来说什么都不用做 但是建议这么弄一下。
    }

    // 双向链表的添加：头尾不能是一个
    public void add(T newItem) {
        Node temp = new Node<T>(newItem, this.pLast, this.pLast.prev);
        this.pLast.prev.next = temp;
        this.pLast.prev = temp;
        this.size++;
        this.modCount++;
    }

    public Node at(int index) {
        if (index + 1 > this.size) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;

        Node result = this.pHead.next;
        while (result != this.pLast && i != index) {
            result = result.next;
            i++;
        }

        return result;

        // 当然可以用2分正反查
    }

    // 插入都在指定元素钱插入。
    public void add(int index, T newItem) {
        Node dstNode = at(index);
        Node temp = new Node<T>(newItem, dstNode, dstNode.prev);
        dstNode.prev.next = temp;
        dstNode.prev = temp;
        this.size++;
        this.modCount++;
    }


    public void set(int index, T newItem) {
        at(index).data = newItem;
    }

    public void remove(int index, T newItem) {
        Node dstNode = at(index);
        dstNode.prev.next = dstNode.next;
        dstNode.next.prev = dstNode.prev;
        dstNode.data = null;
    }

    // 单项列表求倒数第6个
    public Node getLastSix(LinkList_Learn temp) {
        if (temp.size < 6) {
            return null;
        }

        Node fitst = temp.pHead;

        for (int i = 6; i > 0; i--) {
            fitst = fitst.next;
        }

        Node second = temp.pHead;

        while (fitst != temp.pLast) {
            fitst = fitst.next;
            second = second.next;
        }

        return second;
    }
}
