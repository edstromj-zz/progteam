import java.util.*;
import java.awt.geom.*;
import java.awt.*;


public class C {
  public static void main(String[] args) {
    new C().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    int gabeIsAnIdiot = 1;
    while (true) {
      int D = in.nextInt();
      int P = in.nextInt();
      if (D==0 && P==0) break;
      if (gabeIsAnIdiot > 1) System.out.println();			
      TreeSet<Point> dSet = new TreeSet<Point>();
      for (int i = 0 ; i < D ; i++) {
        int x1 = in.nextInt(); int y1 = in.nextInt();
        int x2 = in.nextInt(); int y2 = in.nextInt();
        dSet.add(new Point(x1, y1)); dSet.add(new Point(x1, y2));
        dSet.add(new Point(x2, y1)); dSet.add(new Point(x2, y2));
      }
      TreeSet<Point> pSet = new TreeSet<Point>();
      for (int i = 0 ; i < P ; i++) {
        int x1 = in.nextInt(); int y1 = in.nextInt();
        int x2 = in.nextInt(); int y2 = in.nextInt();
        pSet.add(new Point(x1, y1)); pSet.add(new Point(x1, y2));
        pSet.add(new Point(x2, y1)); pSet.add(new Point(x2, y2));
      }
      Point[] doors = new Point[dSet.size()];
      Point[] penguins = new Point[pSet.size()];
      int count = 0;
      for (Point p : dSet) doors[count++] = p;
      count = 0;
      for (Point p : pSet) penguins[count++] = p;

      Polygon dHull = grahamsScan(doors);
      Polygon pHull = grahamsScan(penguins);
      boolean ans = intersects(dHull, pHull);
      System.out.println("Case " + (gabeIsAnIdiot++) + ": It is" + (ans ? " not " : " ") + "possible to separate the two groups of vendors.");
    }
  }
  private boolean intersects(Polygon poly1, Polygon poly2) {
    for (int i = 0, j = poly1.npoints-1 ; i < poly1.npoints ; j=i++) {
      Line2D.Double line1 = new Line2D.Double(poly1.xpoints[i], poly1.ypoints[i], poly1.xpoints[j], poly1.ypoints[j]);
      //System.err.print("Line 1: ");
      //printLine(line1);
      if (poly2.contains(poly1.xpoints[i], poly1.ypoints[i])) return true;
      for (int x = 0, y = poly2.npoints-1 ; x < poly2.npoints ; y=x++) {
        Line2D.Double line2 = new Line2D.Double(poly2.xpoints[x], poly2.ypoints[x], poly2.xpoints[y], poly2.ypoints[y]);
        //System.err.print("Line 2: "); printLine(line2);
        if (poly1.contains(poly2.xpoints[x], poly2.ypoints[x]) || line1.intersectsLine(line2) || line1.ptSegDist(poly2.xpoints[x], poly2.ypoints[x]) == 0.0)
          return true;
      }
    }
    return false;
  }
  private void printLine(Line2D.Double line) {
    System.err.println(line.x1 + " " + line.y1 + " " + line.x2 + " " + line.y2);
  }
  private class Point implements Comparable<Point> {
    final int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
    public String toString() { return x + " " + y; }
    public int compareTo(Point that) { if (this.x != that.x) return this.x - that.x; return this.y - that.y; }
  }
  private class pData implements Comparable<pData>
  {
    Point point;
    double angle;
    long distance;

    pData(Point p, double a, long d)
    {
      point = p;
      angle = a;
      distance = d;
    }

    public int compareTo(pData that)
    {
      int angleSmaller = Double.valueOf(this.angle).compareTo(that.angle);
      if (angleSmaller != 0)
        return angleSmaller;
      return Long.valueOf(this.distance).compareTo(that.distance);
    }
  }
  public double angle(Point o, Point a)
  {
    return Math.atan2(a.y - o.y, a.x - o.x);
  }

  public long distance2(Point a, Point b)
  {
    return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
  }

  public int ccw(Point p1, Point p2, Point p3)
  {
    return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
  }
  // convex hull routine
  public Polygon grahamsScan(Point[] points)
  {
    int pNum = points.length;

    // (0) find the lowest point
    Point min = Collections.min(Arrays.asList(points), new Comparator<Point>() {
      public int compare(Point p1, Point p2) {
        int y = Integer.valueOf(p1.y).compareTo(p2.y);
        return y != 0 ? y : Integer.valueOf(p1.x).compareTo(p2.x);
      }
    });

    ArrayList<pData> al = new ArrayList<pData>();

    // (1) calculate angle and distance from base
    for (Point p : points) {
      if ( p ==  min )
        continue;

      double ang = angle(min, p);
      if ( ang < 0 )
        ang += Math.PI;

      al.add(new pData(p, ang, distance2(min, p)));
    }

    // (2) sort by angle and distance
    Collections.sort(al);

    // (3) create stack
    Point stack[] = new Point[pNum+1];
    int j = 2;
    for ( int i = 0; i < pNum; i++ ) {
      if ( points[i] == min )
        continue;
      stack[j] = al.get(j-2).point;
      j++;
    }
    stack[0] = stack[pNum];
    stack[1] = min;

    // (4)
    int M = 2;
    for ( int i = 3; i <= pNum; i++ ) {
      while ( ccw(stack[M-1], stack[M], stack[i]) <= 0 )
        M--;
      M++;

      // swap i and M
      Point tmp = stack[i];
      stack[i] = stack[M];
      stack[M] = tmp;
    }

    // assign border points
    int [] xPoints2 = new int[M];
    int [] yPoints2 = new int[M];
    for ( int i = 0; i < M; i++ ) {
      xPoints2[i] = stack[i+1].x;
      yPoints2[i] = stack[i+1].y;
    }
    return new Polygon(xPoints2, yPoints2, M);
  }
}
