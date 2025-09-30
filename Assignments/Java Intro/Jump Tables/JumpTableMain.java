import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class JumpTableMain {
    @FunctionalInterface
    interface StateEnterExitMeth {
        void invoke();
    }

    @FunctionalInterface
    interface StateStayMeth {
        boolean invoke();
    }

    enum State {
        IDLE,
        STACK,
        QUEUE,
        LIST
    }

    static class Screen {
        private final HashMap<State, StateEnterExitMeth> stateEnterMeths = new HashMap<>();
        private final HashMap<State, StateStayMeth> stateStayMeths = new HashMap<>();
        private final HashMap<State, StateEnterExitMeth> stateExitMeths = new HashMap<>();

        private State currentState = State.IDLE;

        private final Stack<Character> stack = new Stack<>();
        private final Queue<Character> queue = new LinkedList<>();
        private final ArrayList<Character> list = new ArrayList<>();

        private static final String STACK_FILE = "stack.txt";
        private static final String QUEUE_FILE = "queue.txt";
        private static final String LIST_FILE  = "list.txt";

        private final Scanner scanner = new Scanner(System.in);

        public Screen() {
            stateEnterMeths.put(State.IDLE, this::StateEnterIdle);
            stateEnterMeths.put(State.STACK, this::StateEnterStack);
            stateEnterMeths.put(State.QUEUE, this::StateEnterQueue);
            stateEnterMeths.put(State.LIST,  this::StateEnterList);

            stateStayMeths.put(State.IDLE, this::StateStayIdle);
            stateStayMeths.put(State.STACK, this::StateStayStack);
            stateStayMeths.put(State.QUEUE, this::StateStayQueue);
            stateStayMeths.put(State.LIST,  this::StateStayList);

            stateExitMeths.put(State.IDLE, this::StateExitIdle);
            stateExitMeths.put(State.STACK, this::StateExitStack);
            stateExitMeths.put(State.QUEUE, this::StateExitQueue);
            stateExitMeths.put(State.LIST,  this::StateExitList);
        }

        public boolean doState() {
            StateStayMeth stay = stateStayMeths.get(currentState);
            if (stay == null) return false;
            return stay.invoke();
        }

        private void changeState(State newState) {
            StateEnterExitMeth exit = stateExitMeths.get(currentState);
            if (exit != null) exit.invoke();
            currentState = newState;
            StateEnterExitMeth enter = stateEnterMeths.get(currentState);
            if (enter != null) enter.invoke();
        }

        // ENTER
        private void StateEnterIdle() { }
        private void StateEnterStack() { loadStackFromFile(); }
        private void StateEnterQueue() { loadQueueFromFile(); }
        private void StateEnterList()  { loadListFromFile(); }

        // STAY
        private boolean StateStayIdle() {
            clearScreen();
            System.out.println("1. Stack");
            System.out.println("2. Queue");
            System.out.println("3. List");
            System.out.println("4. Quit");
            System.out.print("? ");
            String line = scanner.nextLine().trim();
            switch (line) {
                case "1": changeState(State.STACK); return true;
                case "2": changeState(State.QUEUE); return true;
                case "3": changeState(State.LIST);  return true;
                case "4": return false;
                default:
                    System.out.println("Invalid option. Press Enter to continue.");
                    scanner.nextLine();
                    return true;
            }
        }

        private boolean StateStayStack() {
            clearScreen();
            drawStack();
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Save & Move to Queue");
            System.out.println("4. Save & Move to List");
            System.out.println("5. Quit");
            System.out.print("? ");
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) return true;
            String[] parts = line.trim().split("\\s+", 2);
            String opt = parts[0];
            switch (opt) {
                case "1":
                    if (parts.length >= 2) stack.push(parts[1].charAt(0));
                    return true;
                case "2":
                    if (!stack.isEmpty()) stack.pop();
                    return true;
                case "3": changeState(State.QUEUE); return true;
                case "4": changeState(State.LIST);  return true;
                case "5": return false;
                default:
                    System.out.println("Invalid option. Press Enter.");
                    scanner.nextLine();
                    return true;
            }
        }

        private boolean StateStayQueue() {
            clearScreen();
            drawQueue();
            System.out.println("1. Enqueue");
            System.out.println("2. Dequeue");
            System.out.println("3. Save & Move to Stack");
            System.out.println("4. Save & Move to List");
            System.out.println("5. Quit");
            System.out.print("? ");
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) return true;
            String[] parts = line.trim().split("\\s+", 2);
            String opt = parts[0];
            switch (opt) {
                case "1":
                    if (parts.length >= 2) queue.add(parts[1].charAt(0));
                    return true;
                case "2":
                    queue.poll();
                    return true;
                case "3": changeState(State.STACK); return true;
                case "4": changeState(State.LIST);  return true;
                case "5": return false;
                default:
                    System.out.println("Invalid option. Press Enter.");
                    scanner.nextLine();
                    return true;
            }
        }

        private boolean StateStayList() {
            clearScreen();
            drawList();
            System.out.println("1. Append");
            System.out.println("2. Remove");
            System.out.println("3. Save & Move to Stack");
            System.out.println("4. Save & Move to Queue");
            System.out.println("5. Quit");
            System.out.print("? ");
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) return true;
            String[] parts = line.trim().split("\\s+", 2);
            String opt = parts[0];
            switch (opt) { 
                case "1": 
                    if (parts.length >= 2) list.add(parts[1].charAt(0)); 
                    return true; 
                case "2": 
                    if (!list.isEmpty()) list.remove(list.size() - 1); 
                    return true; 
                case "3": changeState(State.STACK); return true; 
                case "4": changeState(State.QUEUE); return true; 
                case "5": return false; 
                default: 
                    System.out.println("Invalid option. Press Enter."); 
                    scanner.nextLine(); 
                    return true; 
            } 
        }

        // EXIT
        private void StateExitIdle() { }
        private void StateExitStack() { saveStackToFile(); }
        private void StateExitQueue() { saveQueueToFile(); }
        private void StateExitList()  { saveListToFile(); }

        private void drawStack() {
            if (stack.isEmpty()) {
                System.out.println("|   |");
                System.out.println("|---|");
                System.out.println("Empty stack");
                return;
            }
            System.out.println("|   |");
            System.out.println("|---|");
            for (int i = stack.size() - 1; i >= 0; i--) {
                System.out.println("| " + stack.get(i) + " |");
                System.out.println("|---|");
            }
        }

        private void drawQueue() {
            if (queue.isEmpty()) {
                System.out.println("Empty queue");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Character c : queue) sb.append("| ").append(c).append(" ");
            sb.append("|");
            System.out.println(sb.toString());
        }

        private void drawList() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            for (Character c : list) sb.append(c).append(", ");
            sb.append("}");
            System.out.println(sb.toString());
        }

        private void loadStackFromFile() {
            stack.clear();
            try {
                Path p = Paths.get(STACK_FILE);
                if (!Files.exists(p)) { Files.writeString(p, ""); return; }
                String s = Files.readString(p);
                for (String t : s.split(",")) if (!t.trim().isEmpty()) stack.push(t.trim().charAt(0));
            } catch (IOException e) { }
        }

        private void saveStackToFile() {
            try {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < stack.size(); i++) sb.append(stack.get(i)).append(",");
                Files.writeString(Paths.get(STACK_FILE), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) { }
        }

        private void loadQueueFromFile() {
            queue.clear();
            try {
                Path p = Paths.get(QUEUE_FILE);
                if (!Files.exists(p)) { Files.writeString(p, ""); return; }
                String s = Files.readString(p);
                for (String t : s.split(",")) if (!t.trim().isEmpty()) queue.add(t.trim().charAt(0));
            } catch (IOException e) { }
        }

        private void saveQueueToFile() {
            try {
                StringBuilder sb = new StringBuilder();
                for (Character c : queue) sb.append(c).append(",");
                Files.writeString(Paths.get(QUEUE_FILE), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) { }
        }

        private void loadListFromFile() {
            list.clear();
            try {
                Path p = Paths.get(LIST_FILE);
                if (!Files.exists(p)) { Files.writeString(p, ""); return; }
                String s = Files.readString(p);
                for (String t : s.split(",")) if (!t.trim().isEmpty()) list.add(t.trim().charAt(0));
            } catch (IOException e) { }
        }

        private void saveListToFile() {
            try {
                StringBuilder sb = new StringBuilder();
                for (Character c : list) sb.append(c).append(",");
                Files.writeString(Paths.get(LIST_FILE), sb.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) { }
        }

        private void clearScreen() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            for (int i = 0; i < 3; i++) System.out.println();
        }
    }

    public static void main(String[] args) {
        Screen screen = new Screen();
        boolean keepRunning = true;
        while (keepRunning) {
            keepRunning = screen.doState();
        }
    }
}