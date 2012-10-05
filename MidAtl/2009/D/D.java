import java.util.*;

public class D {
  public static void main(String[] args) {
    new D().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while(true) {
      int N = in.nextInt();
      int D = in.nextInt();
      if (N==0 && D==0) break;

      TreeSet<Tree> sorted = new TreeSet<Tree>();
      for (int i = 0 ; i < N ; i++) {
        sorted.add(new Tree(in.nextInt(), i));
      }
      ArrayList<Tree> list = new ArrayList<Tree>();
      ArrayList<Interval> intervals = new ArrayList<Interval>();
      list.addAll(sorted);
      boolean possible = true;
      for (int i = 0 ; i < list.size()-1 ; i++) {
        Tree curr = list.get(i);
        Tree next = list.get(i+1);
        int start = Math.min(curr.pos, next.pos);
        int end = Math.max(curr.pos, next.pos);
        if (Math.abs(end-start) > D) {
          possible = false;
          break;
        }
        Interval inter = new Interval(start, end-1, D - (end - start));
        //System.err.println("Adding interval " + start + " " + end + " " + inter.constraint);
        intervals.add(inter);
      }
      if (!possible) {
        System.out.println("-1");
        continue;
      }
      Tree smallest = list.get(0);
      Tree largest = list.get(list.size()-1);
      int start = Math.min(smallest.pos, largest.pos);
      int end = Math.max(smallest.pos, largest.pos);
      int totalDist = 0;
      for (int i = start ; i < end ; i++) {
        int min = D;
        for (Interval inter : intervals) {
          if (inter.contains(i)) {
            //System.err.println("At " + inter + " with constraint " + inter.constraint);
            if (inter.constraint < min) {
              min = inter.constraint;
            }
          }
        }
        //System.err.println("At " + i + " => " + min); 
        totalDist++; //Always going to be at least one jump
        totalDist += min;
        for (Interval inter : intervals) //Update the intervals
          if (inter.contains(i))
            inter.constraint -= min;
      }
      System.out.println(totalDist);
    }
  }
  private class Tree implements Comparable<Tree> {
    int height, pos;
    public Tree(int h, int p) { this.height = h; this.pos = p; }
    public int compareTo(Tree that) { return this.height - that.height; }
  }
  private class Interval {
    int start, end;
    int constraint;
    public Interval(int s, int e, int c) { this.start = s; this.end = e; this.constraint = c; }
    public boolean contains(int j) { return j >= start && j <= end; }
    public String toString() { return "(" + start + "," + end + ")"; }
  }
}
