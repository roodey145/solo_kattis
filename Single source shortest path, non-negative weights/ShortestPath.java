import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    ShortestPath() {

    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // We will assume the meta data contains: n, m, q, and s
        String[] metaData = reader.readLine().split(" ");
        int n = Integer.parseInt(metaData[0]); // Nodes
        int e = Integer.parseInt(metaData[1]); // The number of edges
        int q = Integer.parseInt(metaData[2]); // Number of queries
        int s = Integer.parseInt(metaData[3]); // The index of the starting node

        for(int i = 0; i < e; i++) {

        }
    }

    class Node {
        int index;
        List<Edge> edges = new LinkedList<>();
        boolean visited = false;
        Node() {
        }

        void addEdge(int to) {
            edges.add(new Edge(index, to));
        }
    }

    class Edge {
        int from;
        int to;
        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}