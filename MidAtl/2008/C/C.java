import java.util.*;
import java.text.*;

public class C {
	public static void main(String[] args) {
		new C().solve();
	}
	public void solve() {
		Scanner in = new Scanner(System.in);
		while (true) {
			String line = in.nextLine();
			if ("END".equals(line))
				break;
			Trie trie = new Trie(line);
			System.out.println(trie.findLargestTree());
		}
	}
	private class Trie {
		private Node root;
		private char[] array;
		private ArrayList<Node> nodes;
		private int counter = 0;
		public Trie(String line) {
			this.array = line.toCharArray();
			this.root = buildNode();
			this.nodes = new ArrayList<Node>();
			buildNodes(this.root);
		}
		public String findLargestTree() {
			String [] strings = new String[array.length];
			TreeMap<String, ArrayList<Node>> nodeMap = new TreeMap<String, ArrayList<Node>>();
			for (int i = 0 ; i < strings.length ; i++) {
				Node n = nodes.get(i);
				strings[i] = "";
				if (n.c != '#') {
					strings[i] = n.str;
					if (!nodeMap.containsKey(n.str)) 
						nodeMap.put(n.str, new ArrayList<Node>());
					nodeMap.get(n.str).add(n);
				}
			}

			String maxTrie = "";
			int maxNodes = 0;
			for (String s : nodeMap.keySet()) {
				ArrayList<Node> nodeList = nodeMap.get(s);
				int saved = (nodeList.size()-1)*(getTrieSize(s));
				if (maxTrie.length() == 0) { maxTrie = s; maxNodes = saved; }
				if (saved > maxNodes) {
					maxTrie = s;
					maxNodes = saved;
				}
				else if (saved == maxNodes) {
					if (getTrieSize(s) < getTrieSize(maxTrie)) {
						maxTrie = s;
						maxNodes = saved;
					}
					else if (getTrieSize(s) == getTrieSize(maxTrie)) {
						for (int i = 0 ; i < strings.length ; i++) {
							if (strings[i].equals(s)) {
								maxTrie = s;
								maxNodes = saved;
								break;
							}
							else if (strings[i].equals(maxTrie)) {
								break;
							}
						}						
					}
				}
			}

			return maxTrie + " " + maxNodes;
		}
		private Node buildNode() {
			int curr = counter++;
			if (this.array[curr] == '#') return new Node(this.array[curr], curr, null, null);
			return new Node(this.array[curr], curr, buildNode(), buildNode());
		}
		private void buildNodes(Node n) {
			if (n == null) return;
			nodes.add(n);
			buildNodes(n.left);
			buildNodes(n.right);
		}
		private int getTrieSize(String str) {
			int count = 0;
			for (int i = 0 ; i < str.length() ; i++)
				if (str.charAt(i) != '#')
					count++;
			return count;
		}
		private class Node {
			char c;
			int pos;
			Node left;
			Node right;
			String str;
			public Node(char c, int pos, Node left, Node right) {
				this.c = c;
				this.pos = pos;
				this.left = left;
				this.right = right;
				char [] ans = new char[getTotalChildren()];
				toStringHelper(ans, this, 0);
				this.str = new String(ans);
			}
			public int getTotalChildren() {
				return 1 + (left == null ? 0 : left.getTotalChildren()) + (right == null ? 0 : right.getTotalChildren());
			}
			public String toString() {
				return str;
			}
			private int toStringHelper(char [] ans, Node n, int idx) {
				if (n == null) return idx;
				ans[idx] = n.c;
				int newIdx = toStringHelper(ans, n.left, idx+1);
				newIdx = toStringHelper(ans, n.right, newIdx);
				return newIdx;
			}
		}
	}
}
