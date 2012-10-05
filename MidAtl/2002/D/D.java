import java.util.*;

public class D {
  static int _pos = 0;
  public static void main(String[] args) {
    new D().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while(in.hasNext()) {
      _pos = 0;
      String line = in.next();
      Node root = buildNode(line);
      //System.out.println(root);
      root.compute();
      //System.out.println(root);
      //System.out.println("w: " + root.w + "      h: " + root.h);
      char [][] grid = new char[root.h][root.w];
      for (int i = 0 ; i < root.h ; i++)
        for (int j = 0 ; j < root.w ; j++)
          grid[i][j] = ' ';
      root.fillGrid(grid, 0, 0);

      for (int i = 0 ; i < root.w+2 ; i++)
        System.out.print("-");
      System.out.println("");
      for (int i = 0 ; i < root.h ; i++) {
        System.out.print("|");
        for (int j = 0 ; j < root.w ; j++) {
          System.out.print(""+grid[i][j]);
        }
        System.out.println("|");
      }
      for (int i = 0 ; i < root.w+2 ; i++)
        System.out.print("-");
      System.out.println("");
    }
  }
  public Node buildNode(String line) {
    int currPos = _pos;
    _pos += 2;
    String exp = line.substring(currPos, currPos+2);
    if ("00".equals(exp) || "11".equals(exp)) return new Node(exp, null, null);
    return new Node(exp, buildNode(line), buildNode(line));
  }
  public void merge(Node n1, Node n2, boolean isVertical) {
    if (isVertical) {
      //Make widths the same
      int common = lcm(n1.w, n2.w);
      n1.scale(common/n1.w);
      n2.scale(common/n2.w);
    }
    else {
      //Make heights the same
      int common = lcm(n1.h, n2.h);
      n1.scale(common/n1.h);
      n2.scale(common/n2.h);
    }
  }
  private int lcm(int one, int two) {
    for (int i = Math.max(one, two) ;; i++) {
      if (i%one == 0 && i%two == 0) return i;
    }
  }
  private class Node {
    String exp;
    Node left, right;
    int w, h;
    public Node(String exp, Node l, Node r) { 
      this.exp = exp; 
      this.left = l; 
      this.right = r; 
      this.w = 1;
      this.h = 1;
    }
    public String toString() {
      return exp + (left == null ? "" : left.toString()) + (right == null ? "" : right.toString());
    }
    public void scale(int mult) {
      if ("10".equals(exp)) {
        left.scale(mult);
        right.scale(mult);
        w = left.w + right.w;
        h = left.h;
      }
      else if ("01".equals(exp)) {
        left.scale(mult);
        right.scale(mult);
        w = left.w;
        h = left.h + right.h;
      }
      else {
        w*=mult;
        h*=mult;
      }
    }
    public void compute() {
      if ("10".equals(exp)) {
        left.compute();
        right.compute();
        merge(left, right, false);
        w = left.w + right.w;
        h = left.h;
      }
      else if ("01".equals(exp)){
        left.compute();
        right.compute();
        merge(left, right, true);
        w = left.w;
        h = left.h + right.h;
      }

    }
    public void fillGrid(char [][] grid, int r, int c) {
      if ("00".equals(exp)) {
        for (int i = r; i < (r+h) ; i++)
          for (int j = c ; j < (c+w) ; j++)
            grid[i][j] = 'X';
      }
      else if ("10".equals(exp)) {
        left.fillGrid(grid, r, c);
        right.fillGrid(grid, r, c+left.w);
      }
      else if ("01".equals(exp)) {
        left.fillGrid(grid, r, c);
        right.fillGrid(grid, r+left.h, c);
      }
    }

  }
}
