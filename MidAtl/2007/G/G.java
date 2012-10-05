import java.util.*;

public class G {
  public static void main(String[] args) {
    new G().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while(true) {
      int K = Integer.parseInt(in.nextLine());
      if (K <= 0) break;

      ArrayList<int[]> guesses = new ArrayList<int[]>();
      ArrayList<Triplet> tripList = new ArrayList<Triplet>();
      while (true) {
        Scanner scan = new Scanner(in.nextLine());
        int [] guess = new int[K];
        for (int i = 0 ; i < K ; i++) {
          guess[i] = scan.nextInt();
        }
        if (guess[0] == -1) break;

        guesses.add(guess);
        int B = scan.nextInt();
        int C = scan.nextInt();
        tripList.add(new Triplet(B, C, K - B - C));
      }

      Triplet[] trips = new Triplet[tripList.size()];
      for (int i = 0 ; i < tripList.size() ; i++) 
        trips[i] = tripList.get(i);

      int [] emptyGuess = new int[K];
      for (int i = 0 ; i < K ; i++)
        emptyGuess[i] = -1;

      /**
       * This caches based on:
       * a - The index of the initial guess in the list of guess-responses
       * i - The number at that position (0-9)
       * pos - The position within the array
       */
      Response[][][] cache = genCache(guesses, K);
      Guess initial = new Guess(emptyGuess, 0, trips);

      Deque<Guess> Q = new ArrayDeque<Guess>();
      Q.add(initial);
      String smallest = "";
      int numSolutions = 0;
      while (!Q.isEmpty()) {
        Guess curr = Q.removeFirst();
        if (curr.isSolution()) {
          numSolutions++;
          if (smallest.length() == 0 || curr.toString().compareTo(smallest) < 0) smallest = curr.toString();
          continue;
        }
        for (Guess g : curr.getNextGuesses(guesses, cache))
          Q.addLast(g);
      }

      System.out.println(smallest + " is one of " + numSolutions + " possible solutions.");
    }
  }
  private Response[][][] genCache(ArrayList<int[]> guesses, int K) {
    Response[][][] cache = new Response[guesses.size()][10][K];
    for (int a = 0 ; a < guesses.size() ; a++)
      for (int i = 0 ; i < 10 ; i++)
        for (int pos = 0 ; pos < K ; pos++)
          cache[a][i][pos] = genResponse(guesses.get(a), i, pos); 
    return cache;
  }
  private Response genResponse(int[] arr, int i, int pos) {
    if (arr[pos] == i) return Response.BULL;
    for (int a : arr) if (a == i) return Response.COW;
    return Response.UNUSED;
  }
  private enum Response { BULL, COW, UNUSED };
  private class Triplet {
    int B, C, U;
    public Triplet(int B, int C, int U) { this.B = B; this.C = C; this.U = U; }
    @Override public Object clone() { return new Triplet(B, C, U); }
  }
  private class Guess implements Comparable<Guess> {
    int guess[];
    int step;
    Triplet [] trips;
    public Guess(int [] guess, int step, Triplet[] trips) {
      this.guess = guess;
      this.step = step;
      this.trips = trips;
    }
    public Guess(Guess parent) {
      this.guess = (int[])parent.guess.clone();
      this.step = parent.step + 1;
      this.trips = new Triplet[parent.trips.length];
      for (int i = 0 ; i < this.trips.length ; i++)
        this.trips[i] = (Triplet)parent.trips[i].clone();
    }
    public int hashCode() {
      return guess.hashCode();
    }
    public boolean equals(Object that) {
      return Arrays.equals(guess, ((Guess)that).guess);
    }
    public boolean isSolution() {
      return this.step == this.guess.length;
    }
    public int compareTo(Guess that) {
      return this.toString().compareTo(that.toString());
    }
    public String toString() {
      String ans = "";
      for (int i = 0 ; i < guess.length ; i++)
        ans += guess[i];
      return ans;
    }
    public Set<Guess> getNextGuesses(List<int[]> _guesses, Response[][][] _cache) {
      Set<Guess> ans = new HashSet<Guess>();
outer_loop:
      for (int i = 0 ; i < 10 ; i++) {
        //Check that it hasn't already been used
        for (int j = 0 ; j < this.step ; j++) 
          if (this.guess[j] == i) 
            continue outer_loop;

        //Generate a new candidate
        Guess cand = new Guess(this);

        //Make sure that choosing i is allowed for all
        for (int j = 0 ; j < _guesses.size() ; j++) {
          switch(_cache[j][i][this.step]) {
            case BULL:
              if (cand.trips[j].B <= 0)
                continue outer_loop;
              cand.trips[j].B--;
              break;
            case COW:
              if (cand.trips[j].C <= 0) 
                continue outer_loop;
              cand.trips[j].C--;
              break;
            case UNUSED:
              if (cand.trips[j].U <= 0) 
                continue outer_loop;
              cand.trips[j].U--;
          }
        }

        //Candidate has survived, update and add to answer
        cand.guess[this.step] = i;
        ans.add(cand);
      }

      return ans;
    }
  }
}
