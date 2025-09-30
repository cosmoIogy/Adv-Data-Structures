import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


// Challenge 0 goes here
interface IntOperation {
    int invoke(int a, int b);
}

enum OperationType {
    OPERATION_1(1),
    OPERATION_2(2),
    OPERATION_3(3);

    public final int opNum;
     
    OperationType(int opNum) {
        this.opNum = opNum;
    }

    public static OperationType get(int num) {
      switch (num) {
        case 1:
          return OperationType.OPERATION_1;
        case 2:
          return OperationType.OPERATION_2;
        case 3:
          return OperationType.OPERATION_3;
      }
      return null;
  }
}


public class JavaAdv_Challenges {
  // main function used to test
  public static void main(String[] args) {

  }
  
  // Challenge 1 goes here
  public static <K, V> HashMap<K, V> makeJumpTable(K[] keys, List<V> vals) {
    if (keys.length != vals.size()) {
      HashMap<K, V> empty = new HashMap<>();
      return empty;
    }
    HashMap<K, V> keyVals = new HashMap<>();
    for (int i = 0; i < keys.length; i++) {
      keyVals.put(keys[i], vals.get(i));
    }
    return keyVals;
  }


  
  // Challenge 2 goes here
  public static int testJumpTable(int arg1, int arg2) {
    OperationType types[] = {OperationType.OPERATION_1,
                             OperationType.OPERATION_2,
                             OperationType.OPERATION_3};


    ArrayList<IntOperation> items = new ArrayList<IntOperation>();
    items.add((int a, int b) -> a + b);
    items.add((int a, int b) -> Math.abs(a - b));
    items.add((int a, int b) -> a * b);

    HashMap<OperationType, IntOperation> jumpTable;
    jumpTable = makeJumpTable(types, items);

    int val1 = jumpTable.get(OperationType.OPERATION_1).invoke(arg1, arg2);
    int val2 = jumpTable.get(OperationType.OPERATION_2).invoke(arg1, arg2);
    int val3 = jumpTable.get(OperationType.OPERATION_3).invoke(arg1, arg2);


    return val1 + val2 + val3;
  }
  
  
  // Challenge 3 goes here
  @SafeVarargs
  @Override
  public static <K, V> HashMap<K, V> makeJumpTable(K[] keys, <V>... vals) {
    if (keys.length != vals.size()) {
      HashMap<K, V> empty = new HashMap<>();
      return empty;
    }
    HashMap<K, V> keyVals = new HashMap<>();
    for (int i = 0; i < keys.length; i++) {
      keyVals.put(keys[i], vals.get(i));
    }
    return keyVals;
  }

  
  
}

// Challenge 4 goes here




// Challenge 5 goes here
