import java.util.*;
import java.math.*;

public class G {
  private static BigInteger ZERO = new BigInteger("0");
  public static void main(String [] args) {
    new G().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while (true) {
      BigInteger N = new BigInteger(in.next());
      BigInteger K = new BigInteger(in.next());
      if (N.equals(ZERO) && K.equals(ZERO)) break;

      //The numbers of each letter as an int array
      int [] letters = new int[N.intValue()];

      //The total number of letters - this will be the size of the string
      int total = 0;

      //Read in the numbers and increment the total
      for (int i = 0 ; i < N.intValue() ; i++) {
        letters[i] = Integer.parseInt(in.next());	
        total += letters[i];
      }

      //This stores the answer string as a char array
      char [] ans = new char[total];

      //Make the call to permute with inital parameters
      permute(letters, 0, K, ans, total);

      //Print string
      System.out.println(new String(ans));
    }
  }

  //This method takes in the number of letters still available to use, the current position, the Kth
  //permutation to find, a char array that represents the answer, and the total number of letters left
  //to find. It picks the letter to put at the current position, then recurses to find the next letter
  public void permute(int [] letters, int pos, BigInteger K, char [] ans, int total) {
    //System.err.println("Pos: " + pos + "   K: " + K + "    total: " + total);

    //Break if the position is equal to the length
    if (pos == ans.length) return;

    //Keep a current index of what letter to use and a count of how many permutations we've seen
    int curr = 0;
    BigInteger count = new BigInteger("0");

    //Loop until we find our index
    while (true) {
      //If we've gone to far, go back and quit out
      if (curr == letters.length) { curr--; break; }

      //If there are no letters to select, keep going
      if (letters[curr] == 0) {
        curr++;
        continue;
      }

      //Compute the denominator
      BigInteger denom = new BigInteger("1");
      for (int i = 0 ; i < letters.length ; i++) {
        if (curr == i) denom = denom.multiply(fac(new BigInteger(""+(letters[i]-1))));
        else denom = denom.multiply(fac(new BigInteger(""+letters[i])));
      }

      //System.err.println(fac(total-1)+ "/" + denom);

      //Computer (n-1)!/denom
      BigInteger num = fac(new BigInteger(""+(total-1))).divide(denom);
      //System.err.println(num);

      //Compare our count to the number we've seen so far
      BigInteger tempCount = count.add(num);

      //If we've gotten to a value greater than K, break out
      if (K.compareTo(tempCount) < 0) break;

      //Update count and curr
      count = tempCount;
      curr++;
    }
    //System.err.println("Count: " + count);

    //Determine the letter to append
    ans[pos] = (char)('a' + curr);

    //Decrement the letter that we've used
    letters[curr]--;

    //Recurse
    permute(letters, pos+1, K.subtract(count), ans, total-1);
  }

  //Factorial method for BigInteger
  public BigInteger fac(BigInteger N) {
    if (N.equals(ZERO)) return BigInteger.ONE;
    return N.multiply(fac(N.subtract(BigInteger.ONE)));
  }
}
