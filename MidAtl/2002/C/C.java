import java.util.*;

public class C {
  public static void main(String[] args) {
    new C().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    int W = Integer.parseInt(in.next());
    TreeSet<String> dictionary = new TreeSet<String>();
    for (int i = 0 ; i < W ; i++) {
      dictionary.add(in.next());
    }
    int E = Integer.parseInt(in.next());
    for (int i = 1 ; i <= E ; i++) {
      ArrayList<String> mistakes = new ArrayList<String>();
      while (true) {
        String word = in.next();
        if ("-1".equals(word)) break;
        if (!dictionary.contains(word))
          mistakes.add(word);
      }
      if (mistakes.isEmpty()) {
        System.out.println("Email " + i + " is spelled correctly.");
      }
      else {
        System.out.println("Email " + i + " is not spelled correctly.");
        for (String s : mistakes)
          System.out.println(s);
      }
    }
    System.out.println("End of Output");
  }
}
