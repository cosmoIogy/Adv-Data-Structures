import java.util.*;
import java.io.*;

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
        current.setIsTerminal(true);
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
        return current.getIsTerminal();
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
        if (node.getIsTerminal()) {
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


public class TrieMain {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        if (args[0].equals("test")) {
            runTestMode();
        } 
        else if (args[0].equals("memuse")) {
            runMemUseMode();
        }
    }

    // testmode
    private static void runTestMode() {
    Scanner in = new Scanner(System.in);
    Trie trie = new Trie();

    // add starting words
    String[] words = {"banana", "bandana", "bandaid", "bandage",
                      "letter", "lettuce", "let", "tool", "toy", "toilet"};
    for (String w : words) {
        trie.insert(w);
    }

    // press enter to start
    in.nextLine();

    while (true) {
        clearScreen();

        // print menu
        System.out.println("{add WORD}: Add a word");
        System.out.println("{printAll}: Print all words");
        System.out.println("{startsWith PREFIX}: Print words that start with");
        System.out.println("{search WORD}: Match a specific word");
        System.out.println("{quit}: Quit");
        System.out.print("? ");

        String input = in.nextLine().trim();
        // only run commands if input is not empty
        if (!input.isEmpty()) {

            String[] parts = input.split("\\s+");
            String command = parts[0];

            // add
            if (command.equals("add") && parts.length > 1) {
                trie.insert(parts[1]);
                in.nextLine();
            }

            // print all
            else if (command.equals("printAll")) {
                System.out.println("All words:");
                trie.printAllWords();
                System.out.println();
                in.nextLine();
            }

            // startsWith
            else if (command.equals("startsWith") && parts.length > 1) {
                System.out.println("All words that start with '" + parts[1] + "':");
                trie.printAllWords(parts[1]);
                System.out.println();
                in.nextLine();
            }

            // search
            else if (command.equals("search") && parts.length > 1) {
                boolean found = trie.search(parts[1]);
                if (found)
                    System.out.println("word '" + parts[1] + "' was found in the trie");
                else
                    System.out.println("word '" + parts[1] + "' not found");
                System.out.println();
                in.nextLine();
            }

            // quit
            else if (command.equals("quit")) {
                break;
            }
        }
    }
}

    // helper to clear the screen
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // memuse mode
    private static void runMemUseMode() {
        Runtime rt = Runtime.getRuntime();
        Scanner in = new Scanner(System.in);
        ArrayList<String> words = new ArrayList<>();
        Trie trie = new Trie();
        Display.setYMax(900);
        Display.setYInc(15);

        // read all words from redirected input
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            if (!line.isEmpty()) {
                words.add(line);
            }
        }

        // show memory for trie
        for (int i = 0; i < words.size(); i++) {
            trie.insert(words.get(i));
            long heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
            Display.show(trie, heapMemoryInBytes, i+1);
        }

        // clear trie and compare with hashSet
        trie = null;
        rt.gc();

        HashSet<String> hashSet = new HashSet<>();
        for (int i = 0; i < words.size(); i++) {
            hashSet.add(words.get(i));
            long heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
            Display.show(hashSet, heapMemoryInBytes, i+1);
        }
    }
}