import java.util.*;

public class E {
  public static void main(String[] args) {
    new E().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while (true) {
      int C = in.nextInt();
      int B = in.nextInt();
      if(C==0 && B==0) break;
      in.nextLine();
      HashSet<String> cands = new HashSet<String>();
      ArrayList<Ballot> ballots = new ArrayList<Ballot>();

      for (int i = 0 ; i < B ; i++) {
        Scanner scan = new Scanner(in.nextLine());
        Ballot b = new Ballot();
        while (scan.hasNext()) {
          String cand = scan.next();
          cands.add(cand);
          b.add(cand);
        }
        ballots.add(b);
      }

      int count = 0;
      while(true) {
        HashMap<String, Integer> votes = new HashMap<String, Integer>();
        for (String cand : cands)
          votes.put(cand, 0);
        for (Ballot b : ballots) {
          String vote = b.getVote();
          votes.put(vote, votes.get(vote)+1);
        }
        //System.err.println("Round " + (count++));
        //for (String s : votes.keySet())
        //  System.err.println(s + " => " + votes.get(s));

        Set<String> highest = new HashSet<String>();
        int nHighest = 0;
        Set<String> lowest = new HashSet<String>();
        int nLowest = ballots.size() + 1;
        for (String c : votes.keySet()) {
          int v = votes.get(c);
          if (v > nHighest) {
            nHighest = v;
            highest = new HashSet<String>();
          }
          if (v == nHighest) {
            highest.add(c);
          }
          if (v < nLowest) {
            nLowest = v;
            lowest = new HashSet<String>();
          }
          if (v == nLowest) {
            lowest.add(c);
          }
        }

        if (nHighest >= (ballots.size()+1)/2) {
          System.out.println(highest.iterator().next() + " won");
          break;
        }
        if (highest.size() == cands.size()) {
          String tie = "";
          int i = 0;
          for (String s : highest) {
            tie = tie + ((i++) == 0 ? "" : " and ") + s;
          }
          System.out.println("it is a tie between " + tie);
          break;
        }
        for (Ballot b : ballots)
          b.remove(lowest);
        for (String s : lowest)
          cands.remove(s);
        ArrayList<Ballot> newBallots = new ArrayList<Ballot>();
        for (Ballot b : ballots)
          if (b.size() > 0)
            newBallots.add(b);
        ballots = newBallots;
      }
    }
  }
  private class Ballot {
    ArrayList<String> names;
    public Ballot() {
      this.names = new ArrayList<String>();
    }
    public void add(String name) {
      this.names.add(name);
    }
    public void remove(Set<String> toRemove) {
      ArrayList<String> newNames = new ArrayList<String>();
      for (String s : names)
        if (!toRemove.contains(s))
          newNames.add(s);
      this.names = newNames;
    }
    public int size() {
      return names.size();
    }
    public String getVote() {
      return names.get(0);
    }
  }
}
