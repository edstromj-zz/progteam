import java.util.*;

public class A
{
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    int n = Integer.parseInt(in.next());
    String[] teams = new String[n];
    int[][] probs = new int[n][8];
    int[] numSolved = new int[n];
    int[] penaltyScores = new int[n];
    int winner = 0;
    for (int i = 0; i < n; i++)
    {
      teams[i] = in.next();
      for (int j = 0; j < 8; j++)
        probs[i][j] = Integer.parseInt(in.next());
      for (int j = 0; j < 4; j++)
      {
        if (probs[i][j*2+1] != 0)
        {
          numSolved[i]++;
          penaltyScores[i] += (probs[i][j*2]-1)*20 + probs[i][j*2+1];
        }
      }
      if (numSolved[i] > numSolved[winner])
        winner = i;
      else if (numSolved[i] == numSolved[winner] &&
               penaltyScores[i] < penaltyScores[winner])
        winner = i;
    }
    System.out.println(teams[winner] + " " + numSolved[winner] + " " + penaltyScores[winner]);
  }
}
