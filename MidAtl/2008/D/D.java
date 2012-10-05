import java.util.*;

public class D {
  public static void main(String[] args) {
    new D().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while (true) {
      int n = Integer.parseInt(in.next());
      int m = Integer.parseInt(in.next());
      if (n==0 && m==0) break;

      //Read in all n values
      int[] vals = new int[n];	
      for (int i = 0 ; i < n ; i++)
        vals[i] = Integer.parseInt(in.next());

      //This stores the strategic value for all (from, to) pairs
      int[][] cache = new int[n][n];

      //Populate the cace
      populateCache(vals, cache);

      //This stores the least strategic value. The first row represents 0 attacks
      //I make an important assumption that all int arrays are defaulted to 0 in Java
      int[][] lsv = new int[m+1][n];
      for (int i = 0 ; i < n ; i++)
        lsv[0][i] = cache[0][i];


      attack(lsv, vals, cache);

      System.out.println(lsv[m][n-1]);
    }
  }
  public void attack(int[][] lsv, int[] vals, int[][] cache) {
    //Loop through all attacks
    for (int a = 1 ; a < lsv.length ; a++) {
      //Loop through all stations above the attack index
      for (int s = a ; s < vals.length ; s++) {
        int min = Integer.MAX_VALUE;
        for (int i = s-1 ; i >= (a-1) ; i--) {
          int val = lsv[a-1][i] + cache[i+1][s]; //Calculate based on the cached value
          if (val < min) min = val;
        }
        lsv[a][s] = min;
      }
    }
  }
  public void populateCache(int[] vals, int[][] cache) {
    for (int start = 0 ; start < vals.length ; start++) {
      //Keep a cumulative sum of the values we've seen so far
      int sum = vals[start];

      //Store the previously computed value
      int val = 0;

      //Loop through all end values and store the (from, to) in the cache
      for (int end = start+1 ; end < vals.length ; end++) {
        val += sum*vals[end];
        cache[start][end] = val;
        sum += vals[end];
      }
    }
  }
}
