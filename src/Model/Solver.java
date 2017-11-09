package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Solver {

	private ArrayList<ArrayList<Integer>> graph;
	private ArrayList<ArrayList<String>> loopsComb;
	private double[] deltaGains;
	private boolean[] visited;
	private double overAllGain;
	private String[] forwardPaths, loops;
	private String fpString, lpString, n_combLoops;
	private double[] forGains, loopGains;

	public Solver() {
		initialize();
	}

	private void initialize() {
		overAllGain = 0;
		fpString = lpString = n_combLoops = "";
		graph = new ArrayList<ArrayList<Integer>>();
		loopsComb = new ArrayList<ArrayList<String>>();
	}

	public String[] getforwardPaths() {

		return forwardPaths;
	}

	public String[] getLoops() {

		return loops;
	}

	public String[] getFpGains() {
		String[] s = new String[forGains.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = "M" + (i + 1) + "= " + forGains[i];
		}
		return s;
	}

	public String[] getlpGains() {
		String[] s = new String[loopGains.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = "L" + (i + 1) + "= " + loopGains[i];
		}
		return s;
	}

	public String[] getlpCombinations() {
		return n_combLoops.split("\n");
	}

	public String getOverAllGain() {
		return overAllGain + "";
	}

	public String[] getDeltaGains() {
		String[] s = new String[deltaGains.length];
		for (int i = 0; i < s.length; i++) {
			s[i] = "" + deltaGains[i];
		}
		return s;
	}

	public boolean solve(int numOfnodes, String[][] edgesInfo) {
		initialize();
		// construct graph
		build(edgesInfo);
		// get forward paths and individual loops
		visited = new boolean[graph.size()];
		forwardPathAndLoops(0, numOfnodes - 1, "", numOfnodes);
		if (!fpString.isEmpty()) {
			forwardPaths = fpString.split("\n");
			loops = lpString.split("\n");
			// get gains
			forGains = getGains(forwardPaths);
			loopGains = getGains(loops);
			// initialize delta gains as the number of forward paths
			deltaGains = new double[forGains.length + 1];
			// get combination of non touching loops with gains
			loopsComb();
			deltaGains[deltaGains.length - 1] = calcDelta(loopsComb, -1) - getSum(loopGains);
			calcMinorDeltas();
			calcOverAllGain();
			return true;
		}
		return false;
	}

	private void calcOverAllGain() {
		int i = 0;
		double nominator = 0;
		for (i = 0; i < forGains.length; i++) {
			nominator += forGains[i] * deltaGains[i];
		}
		overAllGain = nominator / deltaGains[i];

	}

	private void calcMinorDeltas() {
		for (int i = 0; i < deltaGains.length - 1; i++) {
			deltaGains[i] = calcDelta(combOfIthForwardpath(i), 1);
		}

	}

	private ArrayList<ArrayList<String>> combOfIthForwardpath(int n) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		list.add(new ArrayList<String>());
		int row = 0;
		for (int i = 0; i < loops.length; i++) {
			if (!isIntersected(loops[i], forwardPaths[n])) {
				String s = "L" + (i + 1);
				list.get(row).add(s);
			}

		}
		for (int i = 0; i < loopsComb.size(); i++) {
			list.add(new ArrayList<String>());
			row++;
			for (int j = 0; j < loopsComb.get(i).size(); j++) {
				String combine = getLoopsNodes(loopsComb.get(i).get(j));
				if (!isIntersected(combine, forwardPaths[n])) {
					list.get(row).add(loopsComb.get(i).get(j));
				}
			}
		}
		return list;

	}

	private double calcDelta(ArrayList<ArrayList<String>> loopsComb, int startFactor) {
		double delta = 1;
		int factor = startFactor;
		for (int i = 0; i < loopsComb.size(); i++) {
			factor *= -1;
			for (int j = 0; j < loopsComb.get(i).size(); j++) {
				delta += factor * getLoopsGains(loopsComb.get(i).get(j));
			}
		}

		return delta;
	}

	private double getLoopsGains(String s) {
		double gain = 1;
		int len = s.length();
		for (int i = 1; i < len; i += 2) {
			gain *= loopGains[s.charAt(i) - 1 - 48];
		}
		return gain;
	}

	private double getSum(double[] arr) {
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		return sum;
	}

	private void loopsComb() {
		loopsComb.add(new ArrayList<String>());
		int row = 0;
		for (int i = 0; i < loops.length; i++) {
			for (int j = i + 1; j < loops.length; j++) {
				if (!isIntersected(loops[i], loops[j])) {
					String s = "L" + (i + 1) + "L" + (j + 1);
					n_combLoops += s + " ";
					loopsComb.get(row).add(s);
				}

			}
		}
		n_combLoops += "\n";
		boolean found = true;
		while (found) {
			found = false;
			loopsComb.add(new ArrayList<String>());
			int len = loopsComb.get(row).size();
			for (int i = 0; i < len; i++) {
				String combine = getLoopsNodes(loopsComb.get(row).get(i));
				for (int j = 0; j < loops.length; j++) {
					if (!isIntersected(combine, loops[j])) {
						found = true;
						String s = "L" + (i + 1) + "L" + (j + 1);
						n_combLoops += s + " ";
						loopsComb.get(row + 1).add(s);
					}
				}
			}
			row++;
			n_combLoops += "\n";
		}

	}

	private String getLoopsNodes(String s) {
		int len = s.length();
		String result = "";
		for (int i = 1; i < len; i += 2) {
			result += loops[s.charAt(i) - 1 - 48];
		}
		return result;
	}

	private boolean isIntersected(String a, String b) {
		HashMap<Character, Boolean> map = new HashMap<Character, Boolean>();
		int len = a.length();
		for (int i = 1; i < len; i += 2) {
			map.put(a.charAt(i), true);
		}
		len = b.length();
		for (int i = 1; i < len; i += 2) {
			if (map.containsKey(b.charAt(i)))
				return true;
		}
		return false;
	}

	private void forwardPathAndLoops(int from, int to, String path, int numOfnodes) {
		if (!visited[from]) {
			visited[from] = true;
			String s = path + "Y" + (from + 1);
			if (from == numOfnodes - 1)
				fpString += s + "\n";
			for (int i = 0; i < graph.get(from).size(); i++) {
				forwardPathAndLoops(graph.get(from).get(i), to, s, numOfnodes);
			}
			visited[from] = false;
		} else {
			int k = 1;
			int len = path.length();
			while (k < len && path.charAt(k) - 48 != (from + 1)) {
				k += 2;
			}
			if (k == len - 1 || checkForwardLoop(path.substring(k)))
				if (!lpString.contains(path.substring(k - 1) + "Y" + (from + 1)))
					lpString += path.substring(k - 1) + "Y" + (from + 1) + "\n";
		}
	}

	private boolean checkForwardLoop(String path) {
		boolean valid = true;
		for (int i = 2; i < path.length(); i += 2) {
			if (path.charAt(i) <= path.charAt(0)) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	private double[] getGains(String[] s) {
		double[] gains = new double[s.length];
		for (int i = 0; i < s.length; i++) {
			gains[i] = 1;
			for (int j = 0; j < s[i].length() - 2; j += 2) {
				gains[i] *= GraphBulider.getGain(s[i].substring(j, j + 4));
			}
		}
		return gains;
	}

	private void build(String[][] edgesInfo) {
		for (int i = 0; i < edgesInfo.length; i++) {
			int from = Integer.parseInt(edgesInfo[i][0]);
			int to = Integer.parseInt(edgesInfo[i][1]);
			graph.add(new ArrayList<Integer>());
			graph.get(from - 1).add(to - 1);
		}

	}
}
