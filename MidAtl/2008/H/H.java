import java.util.*;
import java.text.*;

public class H {
  public static void main(String [] args) {
    new H().go();
  }
  public void go() {
    Scanner in = new Scanner(System.in);
    int count = 1;
    DecimalFormat df = new DecimalFormat("0.00");
    while(true) {
      int N = Integer.parseInt(in.next());
      if (N <= 0) break;
      Switch[] switches = new Switch[N];
      for (int i = 0 ; i < N ; i++) {
        switches[i] = new Switch(i, Double.parseDouble(in.next()));
        int C = Integer.parseInt(in.next());
        for (int j = 0 ; j < C ; j++) {
          int to = Integer.parseInt(in.next());
          double d = Double.parseDouble(in.next());
          switches[i].addLine(to, d);
        }
      }
      LinkedList<Switch> Q = new LinkedList<Switch>();
      switches[0].signal = 1.0*switches[0].amp;
      Q.add(switches[0]);
      while (Q.peek() != null) {
        Switch curr = Q.poll();
        for (Integer i : curr.lines.keySet()) {
          double sig = curr.signal*curr.lines.get(i)*switches[i].amp;
          if (sig > switches[i].signal)
            switches[i].signal = sig;
          if (!switches[i].processed)
            Q.add(switches[i]);
        }
        curr.processed = true;
      }

      System.out.println("Network " + (count++) + ": " + df.format(switches[N-1].signal));
    }
  }
  private class Switch {
    int num;
    double amp;
    TreeMap<Integer, Double> lines;
    boolean processed;
    double signal;
    public Switch(int num, double amp) {
      this.num = num;
      this.amp = amp;
      lines = new TreeMap<Integer, Double>();
      processed = false;
      signal = 0.0;
    }
    public void addLine(int i, double d) {
      lines.put(i, d);
    }
  }
}
