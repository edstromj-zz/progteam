import java.util.*;
import java.util.regex.*;

public class G
{
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    while (true)
    {
      String line = in.nextLine();
      List<Rule> rules = new ArrayList<Rule>();
      while (!line.equals(""))
      {
        Scanner l = new Scanner(line);
        String p = l.next();
        l.next();
        String r = l.next();
        rules.add(new Rule(p, r));
        line = in.nextLine();
      }
      line = in.nextLine();
      while (!(line.equals("") || line.equals("***")))
      {
        Scanner l = new Scanner(line);
        l.useDelimiter("((?<=[^a-zA-Z])|(?=[^a-zA-Z]))");
        while (l.hasNext())
        {
          String word = l.next();
          String newWord = null;
          for (Rule rule: rules)
          {
            newWord = rule.apply(word);
            if (newWord != null)
              break;
          }
          if (newWord != null)
            System.out.print(newWord);
          else
            System.out.print(word);
        }
        System.out.println();
        line = in.nextLine();
      }
      System.out.println("***");
      if (line.equals("***"))
        break;
    }
  }

  private static class Rule
  {
    private Pattern p;
    private String r;

    public Rule(String p, String r)
    {
      this.p = stringToPattern(p);
      this.r = stringToReplaceString(r);
    }

    public String apply(String s)
    {
      Matcher m = p.matcher(s);
      if (!m.matches())
        return null;
      return m.replaceFirst(r);
    }

    private Pattern stringToPattern(String s)
    {
      String p = "";
      for (int i = 0; i < s.length(); i++)
      {
        char c = s.charAt(i);
        if (c == '*')
          p += "([a-zA-Z]+)";
        else if (c == 'V')
          p += "([aeiou])";
        else if (c == 'C')
          p += "([a-z&&[^aeiou]])";
        else if (Character.isDigit(c))
          p += "(\\" + c + ")";
        else
          p += "(" + Character.toLowerCase(c) + "|" + Character.toUpperCase(c) + ")";
      }
      return Pattern.compile(p);
    }

    private String stringToReplaceString(String s)
    {
      String r = "";
      for (int i = 0; i < s.length(); i++)
      {
        char c = s.charAt(i);
        if (Character.isDigit(c))
          r += "$" + c;
        else
          r += "" + c;
      }
      return r;
    }
  }
}
