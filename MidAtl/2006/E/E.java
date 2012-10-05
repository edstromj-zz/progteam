import java.util.*;

public class E {
  public static void main(String[] args) {
    new E().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while (true) {
      int one = Integer.parseInt(in.next());
      int two = Integer.parseInt(in.next());
      int three = Integer.parseInt(in.next());
      if (one == 0 && two == 0 && three == 0) {
        System.out.println("   0   0   0");
        System.out.println("============");
        break;
      }

      State start = new State(one, two, three);
      State end = start;
      ArrayDeque<Entry> Q = new ArrayDeque<Entry>();
      HashSet<State> visited = new HashSet<State>();
      Q.add(new Entry(start));
      while (!Q.isEmpty()) {
        State curr = Q.removeFirst().state;
        if (!visited.contains(curr)) {
          visited.add(curr);
          if (curr.isGoal()) {
            end = curr;
            break;
          }
          for (State s : curr.generateCandidates()) {
            Q.addLast(new Entry(s));
          }
        }
      }
      printHelper(end);
      System.out.println("============");
    }
  }
  private void printHelper(State s) {
    if (s == null) return;
    printHelper(s.prev);
    System.out.println(s);
  }
  private class Entry implements Comparable<Entry> {
    State state;
    int cost;
    public Entry(State s) { this.state = s; this.cost = s.hops; }
    public int compareTo(Entry that) { return this.cost - that.cost; }
  }
  private class State implements Comparable<State> {
    int hops;
    State prev;
    int baskets[];
    public State (int one, int two, int three) {
      this.hops = 0;
      this.prev = null;
      this.baskets = new int[3];
      baskets[0] = one;
      baskets[1] = two;
      baskets[2] = three;
    }
    public State(State prev) {
      this.prev = prev;
      this.hops = prev.hops+1;
      baskets = new int[3];
      baskets[0] = prev.baskets[0];
      baskets[1] = prev.baskets[1];
      baskets[2] = prev.baskets[2];
    }
    @Override
      public int hashCode() {
        return Arrays.hashCode(baskets);
      }
    @Override
      public boolean equals(Object that) {
        return Arrays.equals(this.baskets, ((State)(that)).baskets);
      }
    public boolean isGoal() { return baskets[0] == baskets[1] && baskets[1] == baskets[2]; }
    public String toString() {
      return toStringHelper(baskets[0]) + toStringHelper(baskets[1]) + toStringHelper(baskets[2]);
    }
    private String toStringHelper(int i) {
      String s = ""+i;
      while (s.length() < 4) { s = " " + s; }
      return s;
    }
    public int compareTo(State that) { return this.toOrdered().compareTo(that.toOrdered()); }
    private String toOrdered() {
      TreeSet<Integer> set = new TreeSet<Integer>();
      set.add(baskets[0]);
      set.add(baskets[1]);
      set.add(baskets[2]);
      StringBuilder msg = new StringBuilder();
      for (int i : set) msg.append(toStringHelper(i));
      return msg.toString();
    }
    public ArrayList<State> generateCandidates() {
      ArrayList<State> cands = new ArrayList<State>();
      for (int i = 0 ; i < baskets.length ; i++) {
        for (int j = 0 ; j < baskets.length ; j++) {
          if (i == j) continue;
          if (baskets[i] < baskets[j]) continue;
          State cand = new State(this);
          cand.baskets[j] += this.baskets[j];
          cand.baskets[i] -= this.baskets[j];
          cands.add(cand);
        }
      }
      return cands;
    }
  }
}
