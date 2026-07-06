import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/***
 * This class is supposed to find the minimum possible weight
 * of a complete graph given a minimmum spanning tree. The
 * class is constraint to give a min weight that ensure the
 * there would be no possibility for the existance of another 
 * minimum spinning tree.
 */
public class MSTMinWeightFast {
    private static boolean _debug = false;
    public static void main(String[] args) throws IOException, Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(reader.readLine()); // Number of test cases in total
        for(int i = 0; i < testCases; i++) {
            handleCase(reader);
        }
    }


    private static void handleCase(BufferedReader reader) throws IOException, Exception {
        MSTMinWeightFast instance = new MSTMinWeightFast();
        PriorityQueue<Edge> edgesPQ = new PriorityQueue<>();
        reader.readLine(); // Ignore empty line
        int n = Integer.parseInt(reader.readLine());
        Exercise1DisjointSetA unions = instance.new Exercise1DisjointSetA(n + 1);
        String[] edgeMetaData;
        int from;
        int to;
        int weight;
        Edge edge;

        // read the MST edges
        for (int i = 0; i < n - 1; i++) {
            edgeMetaData = reader.readLine().split(" ");
            from = Integer.parseInt(edgeMetaData[0]);
            to = Integer.parseInt(edgeMetaData[1]);
            weight = Integer.parseInt(edgeMetaData[2]);
            edge = instance.new Edge(from, to, weight, true);
            edgesPQ.add(edge);
        }

        // Do a normal MST algorithm
        int fixedWeightEdges = n-1;// The edges that was found but did not connect new edges (no need to have higher weight) or the MST edges
        int visitedMSTEdges = 0; 
        long totalWeights = 0;
        Edge curEdge;
        UnionResponse response;
        while(!edgesPQ.isEmpty()) {
            curEdge = edgesPQ.remove();
            response = unions.union(curEdge.from, curEdge.to);
            // We assume the sets are complete graphs. So now we need 
            // to calculate the size of the new edges that can be added
            // to make the newly joint set a complete graph.
            // All the newly added edges to make the graph complete must
            // have a weight is larger by one than the current edge that was explore
            int set1Edges = calcCompleteGraphEdges(response.set1Size);
            int set2Edges = calcCompleteGraphEdges(response.set2Size);
            // Since max nodes is 15k, then it is safe to do this int addition
            int completeGraphEdges = calcCompleteGraphEdges(response.set1Size + response.set2Size);
            // The number of new edges, is the number of the complete graph
            // that combines the nodes of both set 1 and minues the already
            // considered edges in the complete graph 1 and 2 and minues 1.
            // the -1 is due to the current edge that caused the union of 
            // set 1 and 2.
            int newEdges = completeGraphEdges - set1Edges - set2Edges - 1;
            // The newly added edges can have weight that is 1 larger than
            // the recently added edge. This will ensure the currently explored
            // path will always be part of the MST.
            totalWeights += curEdge.weight + (long)newEdges * (curEdge.weight + 1);
        }

        if(_debug) {
            System.out.println("fixedWeightEdges: " + fixedWeightEdges);
            System.out.println("visitedMSTEdges: " + visitedMSTEdges);
        }


        System.out.println(totalWeights);
    }


    private static int calcCompleteGraphEdges(int nodes) {
        return (nodes*(nodes-1) / 2);
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

        public UnionResponse union(int p, int q) throws Exception {
            int rootP = getTreeRoot(p);
            int rootQ = getTreeRoot(q);
            if (rootP == rootQ)
                throw new Exception("Should never happens");
                // return;
            UnionResponse response = new UnionResponse(size[rootQ], size[rootP]);
            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                sets[rootP] = rootQ;
                size[rootQ] += size[rootP];
                // return size[rootQ]; 
            } else {
                sets[rootQ] = rootP;
                size[rootP] += size[rootQ];
                // return size[rootP];
            }

            return response; // Returns the size of elements in the joint sets
        }
    }

    class UnionResponse {
        int set1Size;
        int set2Size;

        public UnionResponse(int set1Size, int set2Size) {
            this.set1Size = set1Size;
            this.set2Size = set2Size;
        }
    }
}