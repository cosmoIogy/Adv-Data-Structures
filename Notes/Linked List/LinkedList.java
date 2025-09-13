class Node {
        public int data;
        public Node next; //link to next node in list

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
}

class LinkedList {
        public Node head; //head of list 
        public Node tail; //tail of list
        
        public LinkedList() {
                head = tail = null;
        }
    
        public void append(int data) {
            Node temp = new Node(data);
            if (head == null) {
                head = tail = temp;
            }
            else {
                tail.next = temp;
                tail = temp;
            }
        }

        public int search (int data) {
                int position = 0;
                Node current = head;
                while (current != null && current.data != data) {
                    current = current.next;
                    position++;
                }
                if (current != null) {
                    return position;
                }
                else {
                    return -1;
                }
        }

        public void print() {
            Node current = head;
            while (current != null){
                    System.out.print(current.data + " ");
                    current = current.next;
            }
        }

        public static void main(String[] args) {
                LinkedList linkedlist = new LinkedList();
                linkedlist.append(42);
                linkedlist.append(24);
                linkedlist.append(14);
                System.out.println(linkedlist.search(42));
                System.out.println(linkedlist.search(20));
                linkedlist.print();
            }
    }
