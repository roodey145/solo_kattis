
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/***
 * This class is supposed to find the minimum possible weight
 * of a complete graph given a minimmum spanning tree. The
 * class is constraint to give a min weight that ensure the
 * there would be no possibility for the existance of another 
 * minimum spinning tree.
 */
public class MSTMinWeight {
    private static boolean _debug = false;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine()); // Number of test cases in total
        for(int i = 0; i < testCases; i++) {
            handleCase(reader);
        }
    }


    private static void handleCase(BufferedReader reader) throws IOException {
        // I'll start implementing the first and simpliest algorithm that came to mind
        // It will probably have a running time of O(T*N^2*log(N)) where N is the number of nodes
        // and T is the number of casses
        MSTMinWeight instance = new MSTMinWeight();
        Set<String> MSTEdges = new HashSet<>();
        Map<Integer, Set<Integer>> connectedEdgesMap = new HashMap<>(); // Explains what node (by id) is connected to which nodes (by ids)
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();
        int minWeight = 10_000; // 10k is the max possible weight
        int maxWeight = 1; // 1 is the min possible weight
        reader.readLine(); // Ignore empty line
        int n = Integer.parseInt(reader.readLine());
        Exercise1DisjointSetA unions = instance.new Exercise1DisjointSetA(n + 1);
        String[] edgeMetaData;
        int from;
        int to;
        int weight;
        Edge edge;
        // long MSTWeightSum = 0;
        Map<Integer, Integer> weightsMap = new HashMap<>();
        Integer[] weightsArray;
        // read the MST edges
        for (int i = 0; i < n - 1; i++) {
            edgeMetaData = reader.readLine().split(" ");
            from = Integer.parseInt(edgeMetaData[0]);
            to = Integer.parseInt(edgeMetaData[1]);
            weight = Integer.parseInt(edgeMetaData[2]);
            MSTEdges.add(to + "-" + from);
            MSTEdges.add(from + "-" + to);
            edge = instance.new Edge(from, to, weight, true);
            edgesPQ.add(edge);
            // MSTWeightSum += weight;
            weightsMap.put(weight, weightsMap.getOrDefault(weight, 0) + 1);

            // Indicate the existence of those edges.
            addConnection(from, to, connectedEdgesMap);
            addConnection(to, from, connectedEdgesMap);

            // Keep track of min and max weight
            if(weight < minWeight) minWeight = weight;
            if (weight > maxWeight) maxWeight = weight;
        }
        weightsArray = weightsMap.keySet().toArray(Integer[]::new);
        Arrays.sort(weightsArray);

        if(_debug) {
            System.out.println("Min: " + minWeight);
            System.out.println("Max: " + maxWeight);
        }

        // Create missing edges, all must start with weight min + 1
        Set<Integer> edgeConnectionsSet;
        int unknownEdgesStartWeight = minWeight + 1;
        for (int i = 1; i <= n; i++) {
            edgeConnectionsSet = connectedEdgesMap.get(i);
            for(int j = i + 1; j <= n; j++) {
                if(edgeConnectionsSet.contains(j)) continue; // Connection already exists
                edgesPQ.add(instance.new Edge(i, j, unknownEdgesStartWeight, false));
            }
        }
        // Calculate the initial min weigths
        // MSTWeightSum += ((n*(n-1) / 2) * (long)unknownEdgesStartWeight);

        // Do a normal MST algorithm
        int curMinWeight = minWeight;
        int fixedWeightEdges = n-1;// The edges that was found but did not connect new edges (no need to have higher weight) or the MST edges
        int visitedMSTEdges = 0; 
        int curMinWeightIndex = 0;
        int edgesWithSimilarWeight = 0;
        // Set<Integer> connectedNodesSet = new HashSet<>();
        Set<String> connectedNodesSet = new HashSet<>();
        long totalWeights = 0;
        Edge curEdge;
        while(!edgesPQ.isEmpty() && curMinWeight < maxWeight+1) {
            curEdge = edgesPQ.remove();
            if(curEdge.MSTEdge) {
                edgesWithSimilarWeight++;
                if (edgesWithSimilarWeight >= weightsMap.get(curEdge.weight)) {
                    curMinWeightIndex++;
                    edgesWithSimilarWeight = 0;
                }
                // Indicate the connected edges
                // connectedNodesSet.add(curEdge.from);
                // connectedNodesSet.add(curEdge.to);
                // connectedNodesSet.add(curEdge.to + "-" + curEdge.from);
                // connectedNodesSet.add(curEdge.from + "-" + curEdge.to);
                unions.union(curEdge.from, curEdge.to);
                totalWeights += curEdge.weight;
                visitedMSTEdges++;
                if(visitedMSTEdges >= n-1) break; // Visited all MST edges
            } else { // This edge is not part of the MST
                // Check if this edge is connected to already connected edges
                if(unions.query(curEdge.from, curEdge.to)/*connectedNodesSet.contains(curEdge.from + "'" + curEdge.to) && connectedNodesSet.contains(curEdge.to) */) { // It is fine to keep this weight as is
                    // This edge weight does not need to change anymore since it has been reached after
                    // the nodes it connects has already been connected.
                    totalWeights += curEdge.weight;
                    fixedWeightEdges++;
                } else { // This edge connects a new node, thus, it results in a MST that is smaller than the desired one
                    // Increase the weight of this node and add it again.
                    // The new weight must be larger than the next minimum weight
                    curEdge.weight = weightsArray[curMinWeightIndex] + 1;
                    edgesPQ.add(curEdge);
                }
            }
        }

        if(_debug) {
            System.out.println("CurMinWeight: " + weightsArray[curMinWeightIndex-1]);
            System.out.println("fixedWeightEdges: " + fixedWeightEdges);
            System.out.println("visitedMSTEdges: " + visitedMSTEdges);
        }

        totalWeights += ((n*(n-1) / 2) - fixedWeightEdges) * (maxWeight+1);

        System.out.println(totalWeights);
    }

    private static void addConnection(int from, int to, Map<Integer, Set<Integer>> map) {
        Set<Integer> set;
        // Check if the from node has already been seen before
        if(map.containsKey(from)) { // indicate that from node is connected to "to" node
            set = map.get(from);
        } else {
            set = new HashSet<>();
        }
        set.add(to);
        map.put(from, set);
    }

    // The graph is undirected.
    class Edge implements Comparable<Edge> {
        final boolean MSTEdge;
        final int from;
        final int to;
        int weight; // Can be made volatile to make the class thread-safe

        

        public Edge(int from, int to, int weight, boolean partOfMST) {
            this.MSTEdge = partOfMST;
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            if(weight != o.weight) {
                // Since the logic is to increase the weights of the non-MST edges
                // then it is important to see if the is a non-MST edge that is 
                // equal to a MST edge, therefore, MST edges must be prioritized
                return Integer.compare(weight, o.weight);
            }

            // weights are equal at this point, prioritize MST edges
            return Boolean.compare(MSTEdge, o.MSTEdge);
        }

    }



    class Exercise1DisjointSetA {
        private final int[] sets;
        private final int[] size;

        Exercise1DisjointSetA(int size) {
            this.sets = new int[size];
            this.size = new int[size];
            for (int i = 0; i < size; i++) {
                this.sets[i] = i;
                this.size[i] = 1;
            }
        }

        public boolean  query(int s, int t) {
            int sTreeRoot = getTreeRoot(s);
            int tTreeRoot = getTreeRoot(t);
            return sTreeRoot == tTreeRoot;
        }


        private int getTreeRoot(int p) {
            while (p != sets[p]) {
                p = sets[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = getTreeRoot(p);
            int rootQ = getTreeRoot(q);
            if (rootP == rootQ)
                return;
            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                sets[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                sets[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
        }
    }
}