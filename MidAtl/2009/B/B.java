import java.util.*;

public class B {
  private static long BORDER = 0L;

  static {
    for (int i = 0 ; i < 8 ; i++) {
      for (int j = 0 ; j < 8 ; j++) {
        int pos = i*8 + j;
        if (i==0 || i==7 || j==0 || j==7) {
          BORDER |= (1L << pos);
        }
      }
    }
  }

  public static void main(String[] args) {
    new B().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while (true) {
      char goal = in.next().charAt(0);
      if (goal == '*') break;

      Map<Character, Long> chars = new HashMap<Character, Long>();
      long end = 0L;
      for (int i = 0 ; i < 6 ; i++) {
        String line = in.next();
        for (int j = 0 ; j < 6 ; j++) {
          int pos = (i+1)*8 + (j+1);
          char c = line.charAt(j);
          if (c == '.') continue;
          if (!chars.containsKey(c))
            chars.put(c, 0L);
          chars.put(c, chars.get(c) | (1L << pos));

          if (c == goal) {
            int endPos = (i+1)*8 + 5;
            end |= (1L << endPos);
            endPos++;
            end |= (1L << endPos);
          }
        }
      }
      ///*
      long[] blocks = new long[chars.size()];
      HashMap<Character, Integer> pos = new HashMap<Character, Integer>();
      int count = 0;
      for (Character c : chars.keySet()) {
        blocks[count] = chars.get(c);
        pos.put(c, count++);
      }

      Grid grid = new Grid(blocks);
      int numMoves = -1;
      int endPos = pos.get(goal);
      Deque<Grid> Q = new ArrayDeque<Grid>();
      Map<Grid, Integer> dist = new HashMap<Grid, Integer>();
      Q.addLast(grid);
      while (!Q.isEmpty()) {
        Grid curr = Q.removeFirst();
        if (dist.containsKey(curr)) continue;
        if (curr.isGoal(end, endPos)) {
          numMoves = curr.moves;
          break;
        }
        dist.put(curr, curr.moves);
        for (Grid g : curr.getCandidates()) {
          if (!dist.containsKey(g))
            Q.addLast(g);
        }
      }

      System.out.println(numMoves == 0 ? 1 : numMoves);
      //*/
    }
  }
  private class Grid {
    long[] blocks;
    long freeSpace;
    int moves;
    public Grid(long [] array) { 
      this.blocks = array; 
      this.moves = 0;
      freeSpace = computeFreeSpace();
    }
    public Grid(Grid parent) {
      this.blocks = (long[])(parent.blocks.clone());
      this.moves = parent.moves + 1;
      this.freeSpace = parent.freeSpace;
    }
    public int hashCode() {
      return Arrays.hashCode(blocks);
    }
    public boolean equals(Object that) {
      return Arrays.equals(this.blocks, ((Grid)that).blocks);
    }
    private long computeFreeSpace() {
      long freeSpace = BORDER;
      for (long l : blocks)
        freeSpace |= l;
      return freeSpace;
    }
    public List<Grid> getCandidates() {
      List<Grid> ans = new ArrayList<Grid>();
      for (int b = 0 ; b < blocks.length ; b++) {
        long l = blocks[b];
        long newFreeSpace = freeSpace ^ l;
        if (isHoriz(b)) {
          for (int i = 1 ; i <= 4 ; i++) {
            Grid next = processCandidate(newFreeSpace, (l << i), b);
            if (next == null) break;
            ans.add(next);
          }
          for (int i = 1 ; i <= 4 ; i++) {
            Grid next = processCandidate(newFreeSpace, (l >> i), b);
            if (next == null) break;
            ans.add(next);
          }
        }
        else {
          for (int i = 1 ; i <= 4 ; i++) {
            Grid next = processCandidate(newFreeSpace, (l << (8*i)), b);
            if (next == null) break;
            ans.add(next);
          }
          for (int i = 1 ; i <= 4 ; i++) {
            Grid next = processCandidate(newFreeSpace, (l >> (8*i)), b);
            if (next == null) break;
            ans.add(next);
          }
        }
      }
      return ans;
    }
    public Grid processCandidate(long fs, long nb, int b) {
      if ((fs & nb) != 0L) return null;
      Grid next = new Grid(this);
      next.blocks[b] = nb;
      next.freeSpace = fs | nb;
      return next;
    }
    public boolean isHoriz(int b) {
      return (blocks[b] & (blocks[b] << 1)) != 0L;
    }
    public boolean isGoal(long end, int goal) {
      return blocks[goal] == end;
    }
  }
}
