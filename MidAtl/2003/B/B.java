import java.util.*;

public class B {
	public static void main(String[] args) {
		new B().go();
	}
	public void go() {
		Scanner in = new Scanner(System.in);
		int c = Integer.parseInt(in.nextLine());
		Map<String, City> cities = new HashMap<String, City>();
		for (int i = 0 ; i < c ; i++) {
			String city = in.nextLine();
			cities.put(city, new City(city));
		}
		int r = Integer.parseInt(in.nextLine());
		for (int i = 0 ; i < r ; i++) {
			Scanner scan = new Scanner(in.nextLine());
			String road = scan.next();
			City from = cities.get(scan.next());
			while (scan.hasNext()) {
				double dist = scan.nextDouble();
				double time = scan.nextDouble();
				City to = cities.get(scan.next());
				from.addRoad(new Road(road, to, dist, time));
				to.addRoad(new Road(road, from, dist, time));
				from = to;
			}
		}

		while (in.hasNextLine()) {
			Scanner scan = new Scanner(in.nextLine());
			QueryType type = QueryType.getByName(scan.next());
			City from = cities.get(scan.next());
			City to = cities.get(scan.next());
			State initial = new State(from);
			
			PriorityQueue<State> Q = new PriorityQueue<State>();
			Q.add(initial);
			//Set<State> visited = new HashSet<State>();
			State goal = null;
			while (!Q.isEmpty()) {
				State curr = Q.poll();
				//System.err.println("City: " + curr.curr.name + " with cost " + curr.cost);
				//if (!visited.contains(curr)) {
					if (curr.isGoal(to)) {
						goal = curr;
						break;
					}

					for (State s : curr.nextStates(type)) {
						//if (!visited.contains(s))
							Q.add(s);
					}
				//}
			}

			System.out.println("from " + from.name);
			System.out.print(goal);

		}


			
	}
	private enum QueryType {
		TIME("time"),
		DISTANCE("distance"),
		TURNS("turns");

		private String m_type;
		QueryType(String type) {
			m_type = type;
		}
		public static QueryType getByName(String name) {
			for (QueryType q : QueryType.values())
				if (q.getType().equals(name))
					return q;
			return TIME;
		}
		public String getType() {
			return m_type;
		}
	}
	private class State implements Comparable<State>{
		double cost;
		City curr;
		ArrayDeque<Road> path;
		Set<City> visited;
		public State(City city) {
			this.cost = 0.0;
			this.curr = city;
			this.path = new ArrayDeque<Road>();
			this.visited = new HashSet<City>();
			this.visited.add(city);
		}
		public State(State parent) {
			this.cost = parent.cost;
			this.curr = null;
			this.path = new ArrayDeque<Road>(parent.path);
			this.visited = new HashSet<City>(parent.visited);
		}
		/*public int hashCode() {
			return path.hashCode();
		}
		public boolean equals(Object that) {
			return 
		}*/
		public int compareTo(State that) {
			if (this.cost <= that.cost) return -1;
			return 1;
		}
		public String toString() {
			StringBuilder msg = new StringBuilder();
			for (Road r : path) {
				msg.append(r.toString() + "\n");
			}
			return msg.toString();
		}
		public boolean isGoal(City dest) {
			return this.curr == dest;
		}
		public List<State> nextStates(QueryType type) {
			List<State> ans = new LinkedList<State>();
			for (Road r : curr.roads) {
				if (!visited.contains(r.dest)) {
					State next = new State(this);
					next.curr = r.dest;
					next.visited.add(r.dest);
					if (!next.path.isEmpty()) {
						if (next.path.getLast().name.equals(r.name))
							next.path.removeLast();
					}
					next.path.addLast(r);
					switch (type) {
						case TIME:
							next.cost += r.time;
							break;
						case DISTANCE:
							next.cost += r.length;
							break;
						case TURNS:
							next.cost = (double)(next.path.size()-1);
							break;
					}
					ans.add(next);
				}
			}
			return ans;
		}
	}
	private class City {
		List<Road> roads;
		String name;
		public City(String name) {
			this.name = name;
			this.roads = new ArrayList<Road>();
		}
		public int hashCode() {
			return name.hashCode();
		}
		public boolean equals(Object that) {
			return this.name.equals(((City)that).name);
		}
		public void addRoad(Road r) {
			roads.add(r);
		}
	}
	private class Road {
		String name;
		City dest;
		double length;
		double time;
		public Road(String name, City dest, double length, double time) {
			this.name = name;
			this.dest = dest;
			this.length = length;
			this.time = time;
		}
		public String toString() {
			return this.name + " to " + dest.name;
		}
	}
}
