import java.util.*;

public class F {
  public static void main(String[] args) {
    new F().solve();
  }
  public void solve() {
    Scanner in = new Scanner(System.in);
    while(true) {
      String line = in.nextLine();
      if ("".equals(line)) break;

      Scanner scan = new Scanner(line);
      Map<String,Integer> mapSys1 = new HashMap<String,Integer>();
      ArrayList<String> listSys1 = new ArrayList<String>();
      int count = 0;
      while (scan.hasNext()) {
        String unit = scan.next();
        listSys1.add(unit);
        mapSys1.put(unit, count++);
      }

      double [][] convSys1 = new double[listSys1.size()][listSys1.size()];
      for (double[] arr : convSys1) Arrays.fill(arr, -1.0);
      for (int i = 0 ; i < listSys1.size()-1 ; i++) {
        scan = new Scanner(in.nextLine());
        double d1 = scan.nextDouble();
        String u1 = scan.next();
        scan.next();
        double d2 = scan.nextDouble();
        String u2 = scan.next();
        convSys1[mapSys1.get(u1)][mapSys1.get(u2)] = d2/d1;
        convSys1[mapSys1.get(u2)][mapSys1.get(u1)] = d1/d2;
        convSys1[i][i] = 1;
      }
      convSys1[listSys1.size()-1][listSys1.size()-1] = 1;

      scan = new Scanner(in.nextLine());
      Map<String,Integer> mapSys2 = new HashMap<String,Integer>();
      ArrayList<String> listSys2 = new ArrayList<String>();
      count = 0;
      while (scan.hasNext()) {
        String unit = scan.next();
        listSys2.add(unit);
        mapSys2.put(unit, count++);
      }

      double [][] convSys2 = new double[listSys2.size()][listSys2.size()];
      for (double[] arr : convSys2) Arrays.fill(arr, -1.0);
      for (int i = 0 ; i < listSys2.size()-1 ; i++) {
        scan = new Scanner(in.nextLine());
        double d1 = scan.nextDouble();
        String u1 = scan.next();
        scan.next();
        double d2 = scan.nextDouble();
        String u2 = scan.next();
        convSys2[mapSys2.get(u1)][mapSys2.get(u2)] = d2/d1;
        convSys2[mapSys2.get(u2)][mapSys2.get(u1)] = d1/d2;
        convSys2[i][i] = 1;
      }
      convSys2[listSys2.size()-1][listSys2.size()-1] = 1;

      scan = new Scanner(in.nextLine());
      double dLink1 = scan.nextDouble();
      int posLink1 = mapSys1.get(scan.next());
      scan.next();
      double dLink2 = scan.nextDouble();
      int posLink2 = mapSys2.get(scan.next());

      double [][] G = new double[listSys1.size()+listSys2.size()][listSys1.size()+listSys2.size()];
      for (double[] arr : G) Arrays.fill(arr, Double.MAX_VALUE);
      for (int i = 0 ; i < listSys1.size() ; i++) 
        for (int j = 0 ; j < listSys1.size() ; j++)
          if (convSys1[i][j] > 0.0)
            G[i][j] = convSys1[i][j];
      for (int i = 0 ; i < listSys2.size() ; i++)
        for (int j = 0 ; j < listSys2.size() ; j++)
          if (convSys2[i][j] > 0.0)
            G[i+listSys1.size()][j+listSys1.size()] = convSys2[i][j];
      G[posLink1][posLink2+listSys1.size()] = dLink2/dLink1;
      G[posLink2+listSys1.size()][posLink1] = dLink1/dLink2;

      for (int k = 0 ; k < G.length ; k++)
        for (int i = 0 ; i < G.length ; i++)
          if (G[i][k] != Double.MAX_VALUE)
            for (int j = 0 ; j < G.length ; j++)
              if (G[k][j] != Double.MAX_VALUE)
                if (G[i][k] * G[k][j] < G[i][j])
                  G[i][j] = G[i][k] * G[k][j];

      //printGraph(G);

      while(true) {
        String prob = in.nextLine();
        if ("".equals(prob)) break;
        scan = new Scanner(prob);

        double dTotal = 0.0;
        while (scan.hasNext()) {
          double d = scan.nextDouble();
          String u = scan.next();
          dTotal += G[mapSys1.get(u)][listSys1.size()-1]*d;
        }

        //System.err.println("dTotal: " + dTotal);

        long[] ans = new long[listSys2.size()];
        for (int i = listSys1.size(), j = 0 ; i < listSys1.size() + listSys2.size() - 1; i++, j++) {
          ans[j] = (long)Math.floor(dTotal*G[listSys1.size()-1][i]);
          dTotal -= ans[j]*G[i][listSys1.size()-1]; 
        }
        ans[listSys2.size()-1] = Math.round(dTotal*G[listSys1.size()-1][listSys1.size()+listSys2.size()-1]);

        for (int i = 0 ; i < ans.length ; i++)
          System.out.print((i==0 ? "" : " ") + ans[i] + " " + listSys2.get(i));
        System.out.println();
      }
    }
  }

  public void printGraph(double[][] G) {
    for (int i = 0 ; i < G.length ; i++) {
      for (int j = 0 ; j < G[i].length ; j++) {
        System.err.printf("%10.3f ", G[i][j]);
      }
      System.err.println();
    }
  }
}
