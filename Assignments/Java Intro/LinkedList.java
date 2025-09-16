class Node {
    public Node next;
    public String data;

    public Node (String data){
        this.data = data;
        this.next = null;
    }
}




class LinkedList {
    public Node head;
    public Node tail;

    public LinkedList(){
        head = tail = null;
    }

    public void append(String data){
        Node temp = new Node(data);

        if(head==null){
            head = tail = temp;
        }
        else{
            tail.next = temp;
            tail = temp;
        }
    }

    public int search(String data){
        Node current = head;
        int position = 0;

        while(current != null && !(current.data.equals(data))){
            current = current.next;
            position++;
        }
        if(current != null){
            return position;
        }
        else{
            return -1;
        }
    }

    public void print(){
        Node current = head;

        while(current != null){
            System.out.println(current.data + " ");
            current = current.next;
        }
    }

    public int size(){
        Node current = head;
        int count = 0;

        while(current.next != null){
            count++;
            current = current.next;
        }
        return count;
    }

    public void prepend(String data){
        Node temp = new Node(data);

        if(head==null){
            head = tail = temp;
        }
        else{
            temp.next = head;
            head = temp;
        }
    }
    
    public static void main(String[] args) {
        LinkedList bailie = new LinkedList();

        bailie.append("apple");
        bailie.append("banana");
        bailie.append("orange");

        bailie.print();

        System.out.println(bailie.search(("banana")));
    }
}
