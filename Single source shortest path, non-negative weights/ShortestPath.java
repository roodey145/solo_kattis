import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPath {    
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(handleOneCase(reader)) {}
    }

    private static boolean handleOneCase(BufferedReader reader)  throws IOException {
        // We will assume the meta data contains: n, m, q, and s
        String line = reader.readLine();
        if (line == null)
            return false; // In case there is no more cases

        String[] metaData = line.split(" ");
        int n = Integer.parseInt(metaData[0]); // Nodes
        int e = Integer.parseInt(metaData[1]); // The number of edges
        int q = Integer.parseInt(metaData[2]); // Number of queries
        int s = Integer.parseInt(metaData[3]); // The index of the starting node
        // Create the empty graph
        ShortestPath graph = new ShortestPath(n);//, s);

        String[] curEdgeData;
        int from;
        int to;
        int weight;
        for(int i = 0; i < e; i++) {
            curEdgeData = reader.readLine().split(" ");
            from = Integer.parseInt(curEdgeData[0]);
            to = Integer.parseInt(curEdgeData[1]);
            weight = Integer.parseInt(curEdgeData[2]);
            graph.addDirectedEdge(from, to, weight);
        }

        graph.initializeMST(s);

        for(int i = 0; i < q; i++) {
            to = Integer.parseInt(reader.readLine());
            graph.printShortestPath(to);
        }

        return true;
    }

    private final Node[] nodes;
    private final Map<Integer, Integer> shortestPathTreeMap;
    ShortestPath(int n)//, int startPoint) 
    {
        nodes = new Node[n];
        shortestPathTreeMap = new HashMap<>();
        initialiseNodes();
        // initializeMST(startPoint);
    }

    private void initialiseNodes(){
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }
    }

    private void addDirectedEdge(int from, int to, int weight) {
        // We assume the edge will connect two existing nodes!
        nodes[from].addEdge(to, weight);
    }

    /**
     * This method assumes positive weights only.
     * @param startPoint The starting place (node) for which a minimum spinning tree would be found.
     */
    private void initializeMST(int startPoint) {
        if(nodes.length == 0) return; // Empty graph
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        Edge next;
        Node curNode;

        // Inidcate that the start point is visited
        shortestPathTreeMap.put(startPoint, 0);
        nodes[startPoint].visited = true;

        addNodeEdges(nodes[startPoint], pq, 0);

        while(!pq.isEmpty()) {
            next = pq.remove();
            curNode = nodes[next.to];
            if(/*visited.contains(to)*/ curNode.visited){
                continue; // Already explored
            }

            addNodeEdges(curNode, pq, next.getWeight());

            shortestPathTreeMap.put(next.to, next.getWeight());

            curNode.visited = true;
        }
    }

    private void addNodeEdges(Node node, PriorityQueue<Edge> pq, int cost) {
        for (Edge e : node.edges) {
            e.addCost(cost);
            pq.add(e);
        }
    }

    private void printShortestPath(int to) {
        if(shortestPathTreeMap.containsKey(to)) {
            System.out.println(shortestPathTreeMap.get(to));
        } else {
            System.out.println("Impossible");
        }
    }


    class Node{
        int index;
        List<Edge> edges = new LinkedList<>();
        Boolean visited = false;
        Node() {
        }

        void addEdge(int to, int weight) {
            edges.add(new Edge(index, to, weight));
        }
    }

    class Edge implements Comparable<Edge>{
        int from;
        int to;
        int weight;
        int cost;
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        void addCost(int cost) {
            this.cost = cost;
        }

        int getWeight() {
            return cost + weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(getWeight(), o.getWeight());
        }
    }
}