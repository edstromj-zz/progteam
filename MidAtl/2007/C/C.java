import java.util.*;

public class C {
	public static void main(String[] args) {
		new C().solve();
	}
	public void solve() {
		Scanner in = new Scanner(System.in);
		while (true) {
			int W = Integer.parseInt(in.next());
			int H = Integer.parseInt(in.next());
			if (W < 3 || H < 3) break;

			in.nextLine();
			char [][] grid = new char[H][W];
			Map<Integer, Integer> r_x = new HashMap<Integer, Integer>();
			Map<Integer, Integer> r_y = new HashMap<Integer, Integer>();
			int m_x = 0;
			int m_y = 0;
			for (int i = 0 ; i < H ; i++) {
				String line = in.nextLine();
				for (int j = 0 ; j < W ; j++) {
					char c = line.charAt(j);
					if (c == 'Y') {
						m_x = j;
						m_y = i;
					}
					else if (c >= '0' && c <= '9') {
						int pos = (int)(c-'0');
						r_x.put(pos, j);
						r_y.put(pos, i);
					}
					grid[i][j] = c;
				}
			}

			String[] r_dir = new String[r_x.size()];
			for (int i = 0 ; i < r_dir.length ; i++) {
				r_dir[i] = in.next();
			}
			int S = r_dir[0].length();

			Step first = new Step(W, H, r_dir.length);
			first.grid = grid;
			first.r_dir = r_dir;
			first.m_x = m_x;
			first.m_y = m_y;
			for (int pos : r_x.keySet()) first.r_x[pos] = r_x.get(pos);
			for (int pos : r_y.keySet()) first.r_y[pos] = r_y.get(pos);

			int step = 0;
			Set<Step> curr = new HashSet<Step>();
			curr.add(first);
			for ( ; step < S ; step++) {
				Set<Step> newSteps = new HashSet<Step>();
				for (Step s : curr) {
					for (Step newS : s.getNextSteps(step)) {
						newSteps.add(newS);
					}
				}
				if (newSteps.isEmpty()) break;

				curr = newSteps;
			}
			System.out.println("You can hide for " + step + " turns.");
		}
	}
	private class Step {
		char [][] grid;
		String[] r_dir;
		int[] r_x;
		int[] r_y;
		int m_x;
		int m_y;
		public Step(int W, int H, int R) {
			this.grid = new char[H][W];
			this.r_dir = new String[R];
			this.r_x = new int[R];
			this.r_y = new int[R];
			this.m_x = 0;
			this.m_y = 0;
		}
		public Step(Step parent) {
			this.grid = new char[parent.grid.length][parent.grid[0].length];
			for (int i = 0 ; i < grid.length ; i++)
				grid[i] = (char[])parent.grid[i].clone();
			this.r_dir = parent.r_dir;
			this.r_x = (int[])parent.r_x.clone();
			this.r_y = (int[])parent.r_y.clone();
			this.m_x = parent.m_x;
			this.m_y = parent.m_y;
		}
		public int hashCode() { 
			int hash = 0;
			for (int i = 0 ; i < grid.length ; i++)
				hash += Arrays.hashCode(grid[i]);
			return hash;
		}
		public boolean equals(Object that) { 
			for (int i = 0 ; i < grid.length ; i++) {
				if (!Arrays.equals(this.grid[i], ((Step)that).grid[i]))
					return false;
			}
			return true;
		}
		public Set<Step> getNextSteps(int s) {
			Step temp = new Step(this);
			temp.stepRobots(s);
			return temp.validMoves();
		}
		private void stepRobots(int s) {
			for (int i = 0 ; i < r_dir.length ; i++) {
				switch(r_dir[i].charAt(s)) {
					case 'N': moveRobot(i,r_x[i],r_y[i]-1); break;
					case 'S': moveRobot(i,r_x[i],r_y[i]+1); break;
					case 'E': moveRobot(i,r_x[i]+1,r_y[i]); break;
					case 'W': moveRobot(i,r_x[i]-1,r_y[i]); break;
				}
			}
		}
		private void moveRobot(int i, int x, int y) {
			grid[r_y[i]][r_x[i]] = ' ';
			grid[y][x] = (""+i).charAt(0);
			r_x[i] = x;
			r_y[i] = y;
		}
		private Set<Step> validMoves() {
			Set<Step> ans = new HashSet<Step>();
			Step stay = new Step(this);
			if (!stay.foundMe()) ans.add(stay);
			if (m_x-1 >= 0 && isValidMove(grid[m_y][m_x-1])) {
				Step left = new Step(this);
				left.moveMe(m_x-1,m_y);
				if (!left.foundMe()) ans.add(left);
			}
			if (m_x+1 < grid[0].length && isValidMove(grid[m_y][m_x+1])) {
				Step right = new Step(this);
				right.moveMe(m_x+1, m_y);
				if (!right.foundMe()) ans.add(right);
			}
			if (m_y-1 >= 0 && isValidMove(grid[m_y-1][m_x])) {
				Step up = new Step(this);
				up.moveMe(m_x, m_y-1);
				if (!up.foundMe()) ans.add(up);
			}
			if (m_y+1 < grid.length && isValidMove(grid[m_y+1][m_x])) {
				Step down = new Step(this);
				down.moveMe(m_x, m_y+1);
				if (!down.foundMe()) ans.add(down);
			}
			return ans;
		}
		public void moveMe(int x, int y) {
			grid[m_y][m_x] = ' ';
			grid[y][x] = 'Y';
			m_y = y;
			m_x = x;
		}
		private boolean isValidMove(char c) {
			return c == ' ';
		}
		public boolean foundMe() {
			for (int i = 0 ; i < r_dir.length ; i++) {
				if (youCanSeeMe(i)) {
					return true;
				}
			}
			return false;
		}
		private boolean youCanSeeMe(int i) {
			int x = r_x[i];
			int y = r_y[i];
			for (int l = x-1 ; l >= 0 ; l--) {
				if(grid[y][l] == 'Y') return true;
				if(grid[y][l] != ' ') break;
			}
			for (int r = x+1 ; r < grid[0].length ; r++) {
				if(grid[y][r] == 'Y') return true;
				if(grid[y][r] != ' ') break;
			}
			for (int u = y-1 ; u >= 0 ; u--) {
				if(grid[u][x] == 'Y') return true;
				if(grid[u][x] != ' ') break;
			}
			for (int d = y+1 ; d < grid.length ; d++) {
				if(grid[d][x] == 'Y') return true;
				if(grid[d][x] != ' ') break;
			}
			return false;
		}
		public String toString() {
			String ans = "m_x: " + m_x + "     m_y: " + m_y + "\n";
			for (int i = 0 ; i < r_dir.length ; i++)
				ans = ans + "Robot " + i + ": (" + r_x[i] + "," + r_y[i] + ")\n";
			for (int i = 0 ; i < grid.length ; i++) {
				for(int j = 0 ; j < grid[i].length ; j++) {
					ans = ans + grid[i][j];
				}
				ans += "\n";
			}
			return ans;
		}
	}
}
