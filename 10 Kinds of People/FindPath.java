
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class FindPath {
    private static FindPath _instance = new FindPath();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] mapMetaData = reader.readLine().split(" ");
        int rows = Integer.parseInt(mapMetaData[0]);
        int cols = Integer.parseInt(mapMetaData[1]);
        Exercise1DisjointSetA unions = _instance.new Exercise1DisjointSetA(rows * cols);
        boolean[][] map = new boolean[rows][cols];
        String line;
        for (int r = 0; r < rows; r++) {
            line = reader.readLine();
            for (int c = 0; c < cols; c++) {
                map[r][c] = line.charAt(c) == '1'; // Ones = true and zeros = false
            }
        }

        int queries = Integer.parseInt(reader.readLine());
        for (int i = 0; i < queries; i++) {
            handleQuery(map, reader, unions);
        }
    }

    // This method assumes that start location is not the same as the end location
    private static void handleQuery(boolean[][] map, BufferedReader reader, Exercise1DisjointSetA unions) throws Exception {
        Set<String> visitedAreas = new HashSet<>();
        // Breadth firsth search
        Queue<Point> queue = new ArrayDeque<>();
        String[] tripMetaData = reader.readLine().split(" ");
        // Subtract 1 because the input gives location from 1 to r and 1 to c
        int sr = Integer.parseInt(tripMetaData[0]) - 1;
        int sc = Integer.parseInt(tripMetaData[1]) - 1;
        int er = Integer.parseInt(tripMetaData[2]) - 1;
        int ec = Integer.parseInt(tripMetaData[3]) - 1;
        Integer startCityID = getCityID(map, new Point(sr, sc));
        Integer endCityID = getCityID(map, new Point(er, ec));
        // Check if the start and end has previously been connected
        visitedAreas.add(sr + "-" + sc);
        
        queue.add(new Point(sr, sc));
        boolean startType = map[sr][sc]; // If ones, then only allow ones, otherwise zeros
        if (startType != map[er][ec]) { // Start position is of different type than end position
            System.out.println("neither");
            return;
        } else if(unions.query(startCityID, endCityID) || startCityID == endCityID) {
            print(startType);
            return;
        }
        Point nextCity;
        Point surCity;
        while (!queue.isEmpty()) {
            nextCity = queue.poll(); // Next element to be visited
            // Get surrounding elements
            // Top city
            surCity = new Point(nextCity.x - 1, nextCity.y);
            if (!visitedAreas.contains(surCity.x + "-" + surCity.y) && withinBoundaries(surCity, map) && map[surCity.x][surCity.y] == startType) {
                visitedAreas.add(surCity.x + "-" + surCity.y);
                unions.union(getCityID(map, surCity), startCityID);
                queue.add(surCity);
            }
            if (surCity.x == er && surCity.y == ec) {
                print(startType);
                return;
            }
            // Bottom city
            surCity = new Point(nextCity.x + 1, nextCity.y);
            if (!visitedAreas.contains(surCity.x + "-" + surCity.y) && withinBoundaries(surCity, map) && map[surCity.x][surCity.y] == startType) {
                visitedAreas.add(surCity.x + "-" + surCity.y);
                unions.union(getCityID(map, surCity), startCityID);
                queue.add(surCity);
            }
            if (surCity.x == er && surCity.y == ec) {
                visitedAreas.add(surCity.x + "-" + surCity.y);
                print(startType);
                return;
            }
            // Right city
            surCity = new Point(nextCity.x, nextCity.y + 1);
            if (!visitedAreas.contains(surCity.x + "-" + surCity.y) && withinBoundaries(surCity, map) && map[surCity.x][surCity.y] == startType) {
                unions.union(getCityID(map, surCity), startCityID);
                visitedAreas.add(surCity.x + "-" + surCity.y);
                queue.add(surCity);
            }
            if (surCity.x == er && surCity.y == ec) {
                print(startType);
                return;
            }
            // Left city
            surCity = new Point(nextCity.x, nextCity.y - 1);
            if (!visitedAreas.contains(surCity.x + "-" + surCity.y) && withinBoundaries(surCity, map) && map[surCity.x][surCity.y] == startType) {
                visitedAreas.add(surCity.x + "-" + surCity.y);
                unions.union(getCityID(map, surCity), startCityID);
                queue.add(surCity);
            }
            if (surCity.x == er && surCity.y == ec) {
                print(startType);
                return;
            }
        }

        System.out.println("neither");
    }


    private static Integer getCityID(boolean[][] map, Point cityLocation) {
        return cityLocation.x * map[0].length + cityLocation.y; 
    }

    private static boolean withinBoundaries(Point cityLocation, boolean[][] map) {
        return cityLocation.x < map.length && cityLocation.x >= 0 && cityLocation.y < map[0].length && cityLocation.y >= 0;
    }

    private static void print(boolean type) {
        System.out.println(!type ? "binary" : "decimal");
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

        public void union(int p, int q) throws Exception {
            int rootP = getTreeRoot(p);
            int rootQ = getTreeRoot(q);
            if (rootP == rootQ)
                return;
                // throw new Exception("Should never happens");
                // return;
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
        }
    }

}
