public class IntegerLearn {

    public static void main(String[] args) {

        // 深入理解Integer
//        Integer a1 = 100;
//        int a2 = 100;
//        System.out.println(a1 == a2);
//
//        Integer a3 = 100;
//        System.out.println(a1 == a3);
//
//
//        Integer b1 = 1000;
//        int b2 = 1000;
//        System.out.println(b1 == b2);
//
//        Integer c1 = 1000;
//        System.out.println(b1 == c1);
//
//        Integer d1 = new Integer(1000);
//
//        System.out.println(b1 == d1);
//        System.out.println(c1 == d1);


        int[] input = {1, 2, 3, 4, 5, 6};

        Node root = new Node(input[0]);
        Node cur = root;

        for(int i = 1; i < input.length; i++){
            cur.next = new Node(input[i]);
            cur = cur.next;
        }

        Node newHead = reverse(root);

        while (newHead != null){
            System.out.println(newHead.val);
            newHead = newHead.next;
        }

    }

    static class Node {
        private Node next;
        private int val;

        public Node(int val){
            this.val = val;
            this.next = null;
        }
    }

    public static Node reverse(Node head){

        Node prev = head;
        Node cur = head.next;

        while (cur != null){
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        head.next = null;

        return prev;
    }






}