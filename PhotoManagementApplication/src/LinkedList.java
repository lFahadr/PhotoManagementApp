public class LinkedList<T> {
    public Node head;
    class Node {
        T data;
        Node next;
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }



    public LinkedList() {
        head = null;
    }

    public void insert(T value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public boolean contains(T value) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(value)) return true;
            current = current.next;
        }
        return false;
    }

    public void remove(T value) {
        if (head == null) return;

        if (head.data.equals(value)) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(value)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }


    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public Node iterator() {
        return head;
    }
}
