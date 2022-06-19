import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.*;

public class Graph {

	int arr[][];
	ArrayList<String> nodes = new ArrayList<>();
	// Collection to map Document to index of vertex
	// You can change it

	public ArrayList<String> findNeighbours(String x) {
		int nodeIndex = -1;

		ArrayList<String> neighbours = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).equals(x)) {
				nodeIndex = i;
				break;
			}
		}

		if (nodeIndex != -1) {
			for (int j = 0; j < arr[nodeIndex].length; j++) {
				if (arr[nodeIndex][j] == 1)
					neighbours.add(nodes.get(j));
			}
		}
		return neighbours;
	}

	// HashMap<String,Integer> name2Int;
	private Map<String, Integer> strToInt = new HashMap<>();
    private Map<Integer, String> intToStr = new HashMap<>();

	private int getIndex(String str) {
		return strToInt.get(str);
	}

	// @SuppressWarnings("unchecked")
	// Collection to map index of vertex to Document
	// You can change it
	// Entry<String, Document>[] arrDoc=(Map.Entry<String, Document>[])new
	// Map.Entry[0];

	// The argument type depend on a selected collection in the Main class
	public Graph(SortedMap<String, Document> internet) {
		int size = internet.size();
		arr = new int[size][size];
		int index = 0;
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			strToInt.put(entry.getKey(), index);
            intToStr.put(index, entry.getKey());	// added
            index++;
		}
		for (Map.Entry<String, Document> entry : internet.entrySet()) {
			Document document = entry.getValue();
			for (Map.Entry<String, Link> link : document.link.entrySet()) {
				int x = getIndex(entry.getKey());
				int y = getIndex(link.getKey());
				arr[x][y] = link.getValue().weight; // 1; changed
			}
			nodes.add(entry.getKey());
		}
	}

	public String bfs(String start) {
		if (!strToInt.containsKey(start))
			return null;

		StringBuilder result = new StringBuilder();
		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		queue.add(start);
		visited.add(start);

		while (!queue.isEmpty()) {
			String element = queue.remove();
			result.append(element).append(", ");
			ArrayList<String> neighbours = findNeighbours(element);
			for (int i = 0; i < neighbours.size(); i++) {
				String n = neighbours.get(i);
				if (n != null && !visited.contains(n)) {
					queue.add(n);
					visited.add(n);
				}
			}

		}
		if (result.length() > 0) {
			return result.substring(0, result.length() - 2).trim();
		}
		return null;
	}

	private void dfs(StringBuilder builder, Set<String> visited, String node) {
		builder.append(node).append(", ");
		ArrayList<String> neighbours = findNeighbours(node);
		visited.add(node);
		for (int i = 0; i < neighbours.size(); i++) {
			String n = neighbours.get(i);
			if (n != null && !visited.contains(n))
				dfs(builder, visited, n);
		}
	}

	public String dfs(String start) {
		if (!strToInt.containsKey(start))
			return null;
		StringBuilder result = new StringBuilder();
		dfs(result, new HashSet<>(), start);
		if (result.length() > 0)
			return result.substring(0, result.length() - 2).trim();
		return null;
	}

	public int connectedComponents() {
		DisjointSetForest deepForest = new DisjointSetForest(arr.length);

		for (int i = 0; i < arr.length; i++) {
			deepForest.makeSet(i);
		}

		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr.length; y++) {
				if (arr[x][y] == 1)
					if (deepForest.findSet(x) != deepForest.findSet(y))
						deepForest.union(x, y);
			}
		}
		return deepForest.countSets();
	}
	
	public String DijkstraSSSP(String startVertexStr) {
        int size = arr.length;

        if (!strToInt.containsKey(startVertexStr))
            return null;
        int startVertex = strToInt.get(startVertexStr);

        int[] shortest = new int[size];

        boolean[] added = new boolean[size];

        for (int vertexIndex = 0; vertexIndex < size; vertexIndex++){
            shortest[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortest[startVertex] = 0;

        int[] parents = new int[size];

        parents[startVertex] = -1;

        for (int i = 1; i < size; i++){
            int nearest = -1;
            int shortestDist = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < size; vertexIndex++){
                if (!added[vertexIndex] && shortest[vertexIndex] < shortestDist){
                    nearest = vertexIndex;
                    shortestDist = shortest[vertexIndex];
                }
            }
            if (nearest == -1)
                continue;

            added[nearest] = true;

            for (int vertexIndex = 0; vertexIndex < size; vertexIndex++){
                int edgeDist = arr[nearest][vertexIndex];

                if (edgeDist > 0 && ((shortestDist + edgeDist) < shortest[vertexIndex])){
                    parents[vertexIndex] = nearest;
                    shortest[vertexIndex] = shortestDist + edgeDist;
                }
            }
        }

        return printResult(shortest, parents);
	}
	
	private String printResult(int[] distances, int[] parents){
        int nVertices = distances.length;
        StringBuilder result = new StringBuilder();

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++){
            int dist = distances[vertexIndex];
            String vertexValue = intToStr.get(vertexIndex);
            if (dist == Integer.MAX_VALUE)
                result.append("no path to ").append(vertexValue).append('\n');
            else{
                printVertexPath(vertexIndex, parents, result);
                result.setLength(result.length() - 2);
                result.append("=").append(dist);
                result.append('\n');
            }
        }
        return result.toString();
    }


    private void printVertexPath(int currentVertex, int[] parents, StringBuilder result){
        if (currentVertex == -1)
            return;
        printVertexPath(parents[currentVertex], parents, result);
        result.append(intToStr.get(currentVertex)).append("->");
    }
}