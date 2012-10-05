import java.util.*;
import java.text.*;

public class B {
	public static void main(String[] args) {
		new B().solve();
	}
	public void solve() {
		Scanner in = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("0.0");

		//This builds a new string of all the input
		StringBuilder input = new StringBuilder();

		//This loop replaces all instances of "(" and ")" with " ( " and " ) " making it easier to parse
		while (in.hasNextLine()) {
			String line = in.nextLine();
			line = line.replace("(", " ( ");
			line = line.replace(")", " ) ");
			input.append(line);
			input.append(" ");
		}

		//Build a new Scanner with the input
		in = new Scanner(input.toString());
		
		//Loop through the input, using default delimiter
		while (in.hasNext()) {

			in.next(); //(

			String type = in.next();
			if (type.equals(")"))
				break;
			
			//Build a set of all bars to iterate over later
			TreeSet<Bar> bars = new TreeSet<Bar>();

			//Build the root - recursively builds its children
			Bar root = new Bar(in, bars);

			//Balance the root, recursively balances the children
			root.balance();

			//Print the length for each bar
			for (Bar b : bars) {
				System.out.println("Bar " + b.num + " must be tied " + df.format(b.L1) + " from one end.");
			}
		}
	}

	/**
	 * Interface that Bars and Decorations will inherit from
	 */
	private interface Item {
		public double getWeight();
		public void balance();
	}

	/**
	 * Bar. Has Length and two items hanging from it.
	 */
	private class Bar implements Item, Comparable<Bar> {
		int num;
		double L, L1;
		Item left, right;

		//Returns the weight of the items attached to it
		public double getWeight() { return left.getWeight() + right.getWeight(); }

		//Recursively balances the left and right children, then balances itself based on the weight
		public void balance() { 
			left.balance();
			right.balance();
			double w1 = left.getWeight();
			double w2 = right.getWeight();

			//Determine the length that it should be hung
			double temp = (this.L*w2)/(w1+w2);

			//Only set L1 to be the minimum of temp and L-temp
			this.L1 = Math.min(temp, this.L - temp);
		}
		public int compareTo(Bar that) { return this.num - that.num; }

		//Constructor that takes in a Scanner and adds itself to a treeset
		public Bar(Scanner in, TreeSet<Bar> bars) {
			this.num = Integer.parseInt(in.next());
			this.L = Double.parseDouble(in.next());
			
			in.next(); //(

			//Process the left item
			String le = in.next();
			if ("B".equals(le))
				this.left = new Bar(in, bars);
			else
				this.left = new Decoration(in);
			
			//Process the right item
			in.next(); //(
			String ri = in.next();
			if ("B".equals(ri))
				this.right = new Bar(in, bars);
			else
				this.right = new Decoration(in);

			in.next(); //)

			bars.add(this);
		}
	}

	/**
	 * This class is a leaf in the mobile and has a weight
	 */
	private class Decoration implements Item {
		double w;
		public double getWeight() { return w; }
		public void balance() {
			return;
		}
		public Decoration(Scanner in) {
			this.w = Double.parseDouble(in.next());
			in.next(); //)
		}
	}

}
