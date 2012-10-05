import java.util.*;
import java.math.*;
import java.text.*;

public class C {
  private static DecimalFormat df = new DecimalFormat("$#,###,##0.00");
  private static BigInteger thousand = new BigInteger("1000");
  public static void main(String[] args) {
    new C().go();
  }
  public void go(){
    Scanner in = new Scanner(System.in);
    int N = Integer.parseInt(in.next());
    for (int i = 0 ; i < N ; i++) {
      BigInteger wager = new BigInteger(in.next());
      wager = wager.multiply(thousand);
      int rounds = Integer.parseInt(in.next());
      for (int j = 0 ; j < rounds ; j++) {
        BigInteger moneyLine = new BigInteger(in.next());
        moneyLine = moneyLine.multiply(thousand);
        String outcome = in.next();
        System.err.println(moneyLine + "    " + outcome + "    " + wager);
        if ("loss".equalsIgnoreCase(outcome)) {
          System.err.println("Lost    " + moneyLine + "    " + outcome + "    " + wager);
          wager = new BigInteger("0");
          continue;
        }
        if ("tie".equalsIgnoreCase(outcome)) {
          continue;
        }
        if (moneyLine.compareTo(new BigInteger("0")) > 0) {
          moneyLine = moneyLine.divide(new BigInteger("100"));
        }
        else {
          moneyLine = new BigInteger("100000000").divide(moneyLine.multiply(new BigInteger("-1")));
        }
        System.err.println(moneyLine);
        wager = wager.add(truncate(wager, moneyLine));
      }
      System.err.println(wager);
      int intWager = wager.intValue();
      double d = (double)intWager/1000.0;
      System.out.println(df.format(d));
    }
  }

  public BigInteger truncate (BigInteger i, BigInteger j) {
    return i.multiply(j).divide(new BigInteger("10000")).multiply(new BigInteger("10"));
  }
}
