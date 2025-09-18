class Node {
    public String data;
    public Node next; 

    public Node(String data) {
        this.data = data;
        this.next = null;
    }
}


class LinkedList {
    public Node head;
    public Node tail;

    public LinkedList() {
        head = tail = null;
    }

    public void append(String data) {
        Node temp = new Node(data);

        if (head == null) {
            head = tail = temp;
        }
        else {
            tail.next = temp;
            tail = temp;
        }
    }

    public boolean search(String data) {
        Node current = head;

        while (current != null && !(current.data.equals(data))){
            current = current.next;
        }
        if (current != null) {
            return true;
        }
        else{
            return false;
        }
    }

    public void print() {
        Node current = head; 
        while (current != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public void delete(String data) {
        if (head == null) {
            return;
        }
        if (head.data.equals(data)) {
            head = head.next;
            if (head == tail) {
                tail = null;
            }
        }
        else {
            Node current = head; 
            while (current.next != null && !(current.next.data.equals(data))) {
                current = current.next;
            }
            if (current.next != null) {
                current.next = current.next.next;
                if (current.next == null) {
                    tail = current;
                }
            }
        }
    }
    public static void main(String[] args) {
        LinkedList poopybailie = new LinkedList();
        poopybailie.append("apple");
        poopybailie.append("banana");

        System.out.println(poopybailie.search("apple"));

        poopybailie.delete("banana");

        poopybailie.print();


    }
}