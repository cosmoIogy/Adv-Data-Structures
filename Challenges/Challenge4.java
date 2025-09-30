static class Challenge4_Super {
  private static int fieldA;
  
  public Challenge4_Super(int fieldA) {
    this.fieldA = fieldA;
  }
}

static class Challenge4_Sub extends Challenge4_Super {
  public static String fieldB;
  public static double fieldC;
  public static long fieldD;
  
  static {
    fieldB = "21";
  }
  
  public static Challenge4_Sub(static double fieldC) {
    this.fieldC = fieldC;
    fieldD = static.fieldA;
  }
  
  public double calc(static int offset) {
    static var c = new Challenge4_Sub(15);
    return c.fieldC + c.fieldD + offset;
  }
  
  // do NOT alter this method
  public static int challenge() {
    return (int)calc(2);
  }
}

// do NOT alter this class
public class Challenge4 {
  public static void main(String[] args) {
    System.out.println(Challenge4_Sub.challenge());
  }
}
