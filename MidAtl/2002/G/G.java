import java.util.*;

public class G {
	public static void main(String [] args) {
		new G().solve();
	}
	public void solve() {
		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			int o = Integer.parseInt(in.next());
			int sz = (int)Math.pow(3, o);
			char [] line = new char[sz];
			for (int i = 0 ; i < sz ; i++)
				line[i] = '-';
			cantor(line, 0, sz);
			System.out.println(new String(line));
		}
	}
	public void cantor(char [] line, int start, int end) {
		if ((end - start) == 1) return;
		int interval = (end - start)/3;
		clear(line, start+interval, start+2*interval);
		cantor(line, start, start+interval);
		cantor(line, start+2*interval, end);
	}
	public void clear(char [] line, int start, int end) {
		for (int i = start; i < end ; i++) {
			line[i] = ' ';
		}
	}
}
