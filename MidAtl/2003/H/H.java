import java.util.*;

public class H {
  public static void main(String[] args) {
    new H().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    Stack<Integer> vals = new Stack<Integer>();
    while(in.hasNext()) {
      String tok = in.next();

      if (isOp(tok)) {
        Operation op = Operation.getByStr(tok);
		//System.err.println("Operation: " + op.getStr());
        switch (op) {
          case EQUALS:
            if (vals.empty()) {
              System.out.println("stack underflow");
              break;
            }
            int val = vals.peek();
            if (val <= 0 || val > 4999) {
              System.out.println("out of range exception");
              break;
            }
            System.out.println(toRoman(val));
            break;
          case DIVIDE:
            if (vals.size() < 2) {
              System.out.println("stack underflow");
              break;
            }
            int first = vals.pop();
            int second = vals.pop();
            if (first == 0) {
              System.out.println("division by zero exception");
              vals.push(second);
              break;
            }
            vals.push(op.operate(first, second));
			break;
          case ADD:
          case SUBTRACT:
          case MULTIPLY:
            if (vals.size() < 2) {
              System.out.println("stack underflow");
              break;
            }
            vals.push(op.operate(vals.pop(), vals.pop()));
			break;
        }
      }
      else {
	  	//System.err.println(tok + " => " + fromRoman(tok));
        vals.push(fromRoman(tok));
      }
    }
  }
  private boolean isOp(String s) {
    return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "=".equals(s);
  }
  private int fromRoman(String roman) {
    int sum = 0;
    for (int i = 0 ; i < roman.length() ; i++) {
      String str = "" + roman.charAt(i);
      if ((i+1) < roman.length()) {
        if (RomanNotation.getByStr(str).getVal() < RomanNotation.getByStr(""+roman.charAt(i+1)).getVal()) {
          str += roman.charAt(++i);
        }
      }
      sum += RomanNotation.getByStr(str).getVal();
    }
    return sum;
  }
  private String toRoman(int num) {
    int left = num;
    String ans = "";
outer_loop:
    while (left > 0) {
      for (RomanNotation rn : RomanNotation.values()) {
        if (left - rn.getVal() >= 0) {
          ans += rn.getStr();
          left -= rn.getVal();
          continue outer_loop;
        }
      }
    }
    return ans;
  }
  private enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    EQUALS("=");
    private String op;
    Operation(String oper) { op = oper; }
    String getStr() { return op; }
    static Operation getByStr(String opera) {
      for (Operation oper : Operation.values())
        if (oper.getStr().equals(opera))
          return oper;
      return EQUALS;
    }
	int operate(int first, int second) {
		switch(this) {
			case ADD:
				return second + first;
			case SUBTRACT:
				return second - first;
			case MULTIPLY:
				return second * first;
			case DIVIDE:
				return second / first;
		}
		return second + first;
	}
  }
  private enum RomanNotation {
    M(1000, "M"),
    CM(900, "CM"),
    D(500, "D"),
    CD(400, "CD"),
    C(100,"C"),
    XC(90,"XC"),
    L(50,"L"),
    XL(40,"XL"),
    X(10,"X"),
    IX(9,"IX"),
    V(5,"V"),
    IV(4,"IV"),
    I(1,"I");

    private int val;
    private String str;
    RomanNotation(int v, String s) { val = v; str = s; }
    int getVal() { return val; }
    String getStr() { return str; }
    static RomanNotation getByStr(String s) {
      for (RomanNotation rn : RomanNotation.values()) {
        if (rn.getStr().equals(s))
          return rn;
      }
      return I;
    }
  }
}
