import java.util.*;
import java.text.*;

public class B {
  public static void main(String[] args) {
    new B().go();
  }
  public void go() {
    Scanner in = new Scanner(System.in);
    DecimalFormat df = new DecimalFormat("0.0");
    while (true) {
      double L = Double.parseDouble(in.next());
      if (L < 0.0) break;
      TreeSet<Interval> set = new TreeSet<Interval>();
      while (true) {
        double x = Double.parseDouble(in.next());
        double y = Double.parseDouble(in.next());
        if (x > y) break;
        set.add(new Interval(Math.max(0.0,x), Math.min(L,y)));
      }
      ArrayList<Interval> list = new ArrayList<Interval>();
      for (Interval i : set) list.add(i);
      for (int i = 0 ; i < list.size()-1 ; i++) {
        Interval curr = list.get(i);
        Interval next = list.get(i+1);
        if (next.x < curr.y) {
          if (next.y <= curr.y) {
            list.remove(i+1);
            i--;
          }
          else {
            curr.y = next.y;
            list.remove(i+1);
            i--;
          }
        }
      }
      double sum = 0.0;
      for (Interval i : list)
        sum += i.y - i.x;
      System.out.println("The total planting length is " + df.format(L-sum));
    }
  }
  private class Interval implements Comparable<Interval>{
    double x;
    double y;
    public Interval(double x, double y) {this.x = x; this.y = y;}
    public int compareTo(Interval other) {
      if (this.x < other.x) return -1;
      if (this.x > other.x) return 1;
      if (this.y < other.y) return -1;
      if (this.y > other.y) return 1;
      return 0;
    }
  }
}
