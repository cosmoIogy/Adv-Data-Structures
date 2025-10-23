// node class for each letter in the Trie
class Node {
    private Node[] children;   // array for all possible next letters
    private boolean isTerminal;     // true if this node is a terminal node

    public Node(int alphabetSize) {
        children = new Node[alphabetSize];  // create space for letters
        isTerminal = false;
    }

    public Node getChild(char c) {
        return children[c];
    }

    public void setChild(char c, Node node) {
        children[c] = node;
    }

    public boolean getIsTerminal() {
        return isTerminal;
    }

    public void setIsTerminal(boolean value) {
        isTerminal = value;
    }
}
