package CS146.HW.HW1.HW1_LinkedList;

public class linkedList {

    private Node head;
    private Node tail;
    private int size;
    private Node current;

    public linkedList(){
        head = null;
        tail = null;
        current = null;
        size = 0;
    }
    public int getSize(){
        return this.size;
    }

    public Boolean isEmpty(){
        return this.size == 0;
    }

    public void print(){
        Node node = head;
        int i = 0;
        while(i < getSize()){
            System.out.print(node.getValue());
            System.out.print(" ");
            node = node.next;
            i++;
        }
        System.out.println();
    }

    public void removePerNumber(int num){
        current = head;
        for (int i = 0; i < size; i += num){
            current = current.next;
        }
        removeNode();
        print();
    }

    public void removeNode(){
        // delete the only one node
        if (size == 1){
            return;
        }
        else {
            current = head;
            Node pre = current;
            // to delete the current
            pre.next = current.next;

            // redefine the head to be the next of current,
            // so that next loop will start from the next node
            head = current.next;
            // to delete the pointer of current
            current.next = null;

            // after delete one node, resize the size
            size --;
        }
    }

    public void insert(int val){
        Node nptr = new Node(val, null);

        if (size == 0){
            head = nptr;
            tail = head;
        }
        else{
            tail.next = nptr;
            nptr.next = head;
            tail = nptr;
        }
        size++;

    }

    private class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public int getValue(){
            return this.value;
        }
    }

}
