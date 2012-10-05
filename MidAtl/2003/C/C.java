import java.util.*;

public class C
{
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    //in.useDelimiter("(\\p{javaWhitespace}+|,|.|;|\\|`|'|\"|\\(|\\)|\\/|:|-)");
    in.useDelimiter("(\\p{javaWhitespace}|,|;|\\.|\\\\|`|'|\"|\\(|\\)|/|:|-)+");
    //System.err.println(in.delimiter());
    Map<String, Integer> counts = new TreeMap<String, Integer>();
    while (in.hasNext())
    {
      String cur = in.next().toLowerCase();
      //System.err.println(cur);
      if (counts.containsKey(cur))
        counts.put(cur, counts.get(cur)+1);
      else
        counts.put(cur, 1);
    }
    //System.err.println(counts.size());
    int n = 0;
    List<String> words = new ArrayList<String>();
    for (String s: counts.keySet())
    {
      int count = counts.get(s);
      if (count > n)
      {
        n = count;
        words = new ArrayList<String>();
        words.add(s);
      }
      else if (count == n)
        words.add(s);
    }
    System.out.println(n + " occurrences");
    for (String s: words)
      System.out.println(s);
  }
}
