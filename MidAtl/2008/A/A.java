import java.util.*;

public class A {
  public static void main(String [] args) {
    Scanner in = new Scanner(System.in);
    while (true) {
      double calories = Double.parseDouble(in.next());
      double fat = Double.parseDouble(in.next());
      double carbs = Double.parseDouble(in.next());
      double protein = Double.parseDouble(in.next());
      if (calories == 0.0 && fat == 0.0 && carbs == 0.0 && protein == 0.0)
        break;
      double lower = 0.0;
      lower += Math.max(0,fat-.5)*9.0;
      lower += Math.max(0,carbs-.5)*4.0;
      lower += Math.max(0,protein-.5)*4.0;
      double upper = 0.0;
      upper += (fat+.5)*9.0;
      upper += (carbs+.5)*4.0;
      upper += (protein+.5)*4.0;
      if (calories >= lower && calories <= upper)
        System.out.println("yes");
      else
        System.out.println("no");

    }
  }
}
