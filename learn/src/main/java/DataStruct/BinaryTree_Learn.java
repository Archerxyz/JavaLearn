package DataStruct;

import java.util.Deque;
import java.util.LinkedList;

// 这个还挺重要的。不然咋搜索
public class BinaryTree_Learn<T extends Comparable<? super T>> {

    private class Node<T> {
        T data;
        Node left;
        Node right;
        int val;

        private Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private int size;
    private Node root;

    public boolean contains(T item, Node<T> root) {

        if (root == null) {
            return false;
        }

        int compareResult = item.compareTo(root.data);

        if (compareResult < 0) {
            contains(item, root.left);
        } else if (compareResult > 0) {
            contains(item, root.right);
        } else {
            return true;
        }

        return false;
    }

    public T findmin() {
        Node<T> temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }

        return temp.data;
    }

    public Node insert(T item, Node<T> curRoot) {

        if (this.root == null) {
            this.root = new Node<T>(item, null, null);
            return this.root;
        }

        if (curRoot == null) {
            return new Node<T>(item, null, null);
        }

        int compareResult = item.compareTo(curRoot.data);

        if (compareResult > 0){
            curRoot.left = insert(item,curRoot.left);
        }
        else if (compareResult < 0){
            curRoot.right =  insert(item,curRoot.right);
        }
        else{

        }

        // 这一步很重要，就是原来遍历的路径还是原来的。
        // 虽然我感觉这样写很恶心？还是写带parent的while好
        return curRoot;

    }

    // 二叉树删除节点，就比较复杂了
    // 实际上就是要找比删除节点关键值大的节点集合中最小的一个节点，只有这样代替删除节点后才能满足二叉搜索树的特性。
    // 转换问题为，1.求被删除节点右子树的最小值 2.替换被删除的这个节点 3.再在这个子树中删除这个节点。（最小值必为叶子节点）
    public Node remove(T item, Node<T> curNode) {

        int compareResult = item.compareTo(curNode.data);

        if (compareResult > 0) {
            curNode.left = remove(item, curNode.left);
        } else if (compareResult < 0) {
            curNode.right = remove(item, curNode.right);
        } else {

            if (curNode.left != null && curNode.right != null) {
                return null;
            } else {
                curNode = (curNode.left != null) ? curNode.left : curNode.right;
            }
        }

        return curNode;
    }

    // 深度优先
    public int minDeep(Node root) {
        if (root == null)
            return 0;

        if (root.left == null && root.right == null)
            return 1;

        int min = Integer.MAX_VALUE;

        if (root.left != null) {
            min = Math.min(min, minDeep(root.left));
        }

        if (root.right != null) {
            min = Math.min(min, minDeep(root.right));
        }

        return min + 1;
    }

    // 广度优先

    public int minDeep2(Node root) {
        if (root == null)
            return 0;
        root.val = 1;

        Deque<Node> deque = new LinkedList<Node>();
        deque.offer(root);

        while (!deque.isEmpty())
        {
            Node cur = deque.poll();
            if (cur.left == null && cur.right == null)
            {
                return cur.val;
            }

            if (cur.left != null)
            {
                cur.left.val = cur.val + 1;
                deque.offer(cur.left);
            }

            if (cur.right != null)
            {
                cur.right.val = cur.val + 1;
                deque.offer(cur.right);
            }
        }
        return 0;
    }


}
