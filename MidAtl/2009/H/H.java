import java.util.*;

/**
 * Problem H - MidAtlantic Contest 2009
 */
public class H {
  public static void main(String [] args) {
    /* 
     * This pattern allows me to take methods/classes defined elsewhere in
     * the program out of the static context.
     */
    new H().solve();
  }

  public void solve() {

    Scanner in = new Scanner(System.in);
    while (true) {
      //Read in the number of words
      int N = Integer.parseInt(in.next());

      //If N is non-positive, break out of the loop
      if (N <= 0) break;

      //This is our adjacency matrix
      int [][] G = new int[N][N];

      //Read in the words to an array
      String[] words = new String[N];
      for (int i = 0 ; i < N ; i++) {
        words[i] = in.next();
      }

      /*
       * This code populates our adjacency matrix. If the edit distance between two words i, j is
       * 1, then a 1 is put at G[i][j]. If the edit distance is greater than 1, G[i][j] is
       * set to Integer.MAX_VALUE. Along diagonals (where i==j), -1 is put in.
       */
      for (int i = 0 ; i < N ; i++) {
        for (int j = 0 ; j < N ; j++) {
          if (i == j) {
            G[i][j] = -1;
          }
          else if (match(words[i], words[j])) { 
            G[i][j] = 1; 
          }
          else {
            G[i][j] = Integer.MAX_VALUE;
          }
        }
      }

      //printGraph(G);

      /*
       * Floyd's Algorithm. O(n^3)
       * This finds the shortest paths between all-pairs in the graph. From a high level, it works
       * by determining if the word chain starting with i and ending with j can be formed by going
       * through word k. If so, it determines whether or not the current chain between i and j can
       * be shortened by going through k. If so, it changes the value in G[i][j] to reflect the 
       * current shortest path between i and j. Note that no path reconstruction is needed, so we
       * don't need to remember the path. After this algorithm, each value at G[i][j] is the shortest
       * path between i and j. Those with Integer.MAX_VALUE means that there is no word chain possible.
       */
      for (int k = 0 ; k < N ; k++) {
        for (int i = 0 ; i < N ; i++) {
          if (G[i][k] != -1 && G[i][k] != Integer.MAX_VALUE) {
            for (int j = 0 ; j < N ; j++) {
              if (G[k][j] != -1 && G[k][j] != Integer.MAX_VALUE) {
                if (G[i][j] != -1 && G[i][k] + G[k][j] < G[i][j]) {
                  G[i][j] = G[i][k] + G[k][j];
                }
              }
            }
          }
        }
      }

      //printGraph(G);

      /*
       * Now we need to determine the maximum of the shortest paths. We first assume that no word
       * chain is possible, then update the max. When we encounter Integer.MAX_VALUE and -1, we
       * skip it.
       */
      int max = 0;
      for (int i = 0 ; i < N ; i++) {
        for (int j = 0 ; j < N ; j++) {
          if (G[i][j] != -1 && G[i][j] < Integer.MAX_VALUE && G[i][j] > max)
            max = G[i][j];
        }
      }

      //Variable max only contains the number of edges. Add 1 to get number of words in chain.
      System.out.println(max+1);
    }
  }
  private boolean match(String s, String t) {
    //Short circuit the computation if the lengths differ by greater than 1
    if (Math.abs(s.length() - t.length()) > 1) return false;

    //If s is longer, assume that a letter is added to t
    if (s.length() > t.length()) return matchAdd(t, s);

    //If t is longer, assume that a letter is added to s
    if (t.length() > s.length()) return matchAdd(s, t);

    //This means the lengths are the same, so just do a character by character check
    return matchChange(s, t);
  }

  /**
   * This method assumes that shorter is greater than longer by exactly one.
   * It loops through and, if two characters are different, it assumes an insertion
   * and "uses" its insertion. From then on, if two characters are different, the Strings
   * have been determined to have edit distance greater than 1.
   **/
  private boolean matchAdd(String shorter, String longer) {
    boolean used = false;
    for (int i = 0 , j = 0 ; j < longer.length() ; i++, j++) {
      if (i == shorter.length()) return true;
      if (shorter.charAt(i) != longer.charAt(j)) {
        if (!used) {
          used = true;
          i--;
        }
        else
          return false;
      }
    }
    return true;
  }

  /**
   * This method assumes that the two strings are the same length. It loops through the
   * characters and, when a difference is found, it "uses" its replacement. After that,
   * any additional differences mean the Strings have edit distance greater than 1.
   **/
  private boolean matchChange(String s, String t) {
    boolean used = false;
    for (int i = 0 ; i < s.length() ; i++) {
      if (s.charAt(i) != t.charAt(i)) {
        if (!used) used = true;
        else return false;
      }
    }
    return true;
  }

  /**
   * Debug function to print our adjancency matrix
   */
  private void printGraph(int[][] G) {
    for (int i = 0 ; i < G.length ; i++) {
      System.err.print("[");
      for (int j = 0 ; j < G[i].length ; j++) {
        System.err.print("    " + G[i][j]);
      }
      System.err.println("]");
    }
  }

}
