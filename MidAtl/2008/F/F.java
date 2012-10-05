import java.util.*;

public class F {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while(true) {
			int n = Integer.parseInt(in.next());
			int t1 = Integer.parseInt(in.next());
			int t2 = Integer.parseInt(in.next());
			int t3 = Integer.parseInt(in.next());
			if (n == 0 && t1 == 0 && t2 == 0 && t3 == 0)
				break;
			int ticks = 0;
			
			//Two full revolutions
			ticks += 2*n;

			//Turn to t1
			ticks += n-1;

			//Turn one full revolution
			ticks += n;

			//Turn to t2
			if (t2 > t1)
				ticks += (t2-t1);
			else
				ticks += (n - (t1-t2));

			//Turn to t3
			if (t3 > t2)
				ticks += (n - (t3-t2));
			else
				ticks += (t2-t3);

			System.out.println(ticks);

		}
	}
}
