
class Trie {
    private Node root;
    private final int ALPHABET_SIZE = 128; // ASCII letters

    public Trie() {
        root = new Node(ALPHABET_SIZE);
    }

    // add a word to the trie
    public void insert(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            if (current.getChild(c) == null) {
                current.setChild(c, new Node(ALPHABET_SIZE));
            }
            current = current.getChild(c);
        }
        current.setEndOfWord(true);
    }

    // check if a word exists in the trie
    public boolean search(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            if (current.getChild(c) == null) {
                return false;
            }
            current = current.getChild(c);
        }
        return current.isEndOfWord();
    }

    // print all words in the trie
    public void printAllWords() {
        printWordsFrom(root, "");
        System.out.println();
    }

    // print all words that start with a given prefix
    public void printAllWords(String prefix) {
        Node current = root;
        for (char c : prefix.toCharArray()) {
            if (current.getChild(c) == null) {
                System.out.println("No words found with prefix '" + prefix + "'");
                return;
            }
            current = current.getChild(c);
        }
        printWordsFrom(current, prefix);
        System.out.println();
    }

    // helper function to print words from a given node
    private void printWordsFrom(Node node, String word) {
        if (node.isEndOfWord()) {
            System.out.print("'" + word + "', ");
        }
        for (char c = 0; c < ALPHABET_SIZE; c++) {
            Node next = node.getChild(c);
            if (next != null) {
                printWordsFrom(next, word + c);
            }
        }
    }
}
