
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class FindPath {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] mapMetaData = reader.readLine().split(" ");
        int rows = Integer.parseInt(mapMetaData[0]);
        int cols = Integer.parseInt(mapMetaData[1]);
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
            handleQuery(map, reader);
        }
    }

    // This method assumes that start location is not the same as the end location
    private static void handleQuery(boolean[][] map, BufferedReader reader) throws IOException {
        Set<String> visitedAreas = new HashSet<>();
        // Breadth firsth search
        Queue<Point> queue = new ArrayDeque<>();
        String[] tripMetaData = reader.readLine().split(" ");
        // Subtract 1 because the input gives location from 1 to r and 1 to c
        int sr = Integer.parseInt(tripMetaData[0]) - 1;
        int sc = Integer.parseInt(tripMetaData[1]) - 1;
        int er = Integer.parseInt(tripMetaData[2]) - 1;
        int ec = Integer.parseInt(tripMetaData[3]) - 1;
        visitedAreas.add(sr + "-" + sc);

        queue.add(new Point(sr, sc));
        boolean startType = map[sr][sc]; // If ones, then only allow ones, otherwise zeros
        if (startType != map[er][ec]) { // Start position is of different type than end position
            System.out.println("neither");
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
                queue.add(surCity);
            }
            if (surCity.x == er && surCity.y == ec) {
                print(startType);
                return;
            }
        }

        System.out.println("neither");
    }

    private static boolean withinBoundaries(Point cityLocation, boolean[][] map) {
        return cityLocation.x < map.length && cityLocation.x >= 0 && cityLocation.y < map[0].length && cityLocation.y >= 0;
    }

    private static void print(boolean type) {
        System.out.println(!type ? "binary" : "decimal");
    }

}
