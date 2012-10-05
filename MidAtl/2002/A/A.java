import java.util.*;
import java.text.*;

public class A {
  public static void main(String[] args) {
    new A().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("0.00");
    double start = Double.parseDouble(in.next());
    while (true) {
      double curr = Double.parseDouble(in.next());
      if (curr == 999.0) break;
      System.out.println(df.format(curr-start));
      start = curr;
    }
    System.out.println("End of Output");
  }
}
