import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MinimizeCans {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] metaData = reader.readLine().split(" "); // Contains n and m
        int n = Integer.parseInt(metaData[0]); // Nr of different paint cans sizes
        int m = Integer.parseInt(metaData[1]); // Nr of colors

        int[] canSizes = new int[n];
        for (int i = 0; i < n; i++) {
            canSizes[i] = Integer.parseInt(reader.readLine());
        }

        // Sort the array to retrieve the closest size to the needed amount
        Arrays.sort(canSizes);
        int desiredSize;
        long wastedAmount = 0; // The type "Long" avoids stack overflow
        int targetIndex;
        int bestSize;

        for (int i = 0; i < m; i++) {
            desiredSize = Integer.parseInt(reader.readLine());
            targetIndex = Arrays.binarySearch(canSizes, desiredSize);
            if (targetIndex < 0) { // The size is not found but should be in place (-targetIndex) and has index (-targetIndex - 1)
                bestSize = canSizes[-targetIndex - 1];
            } else {
                bestSize = canSizes[targetIndex]; // The exact size exists!
            }

            wastedAmount += (long) bestSize - desiredSize;
        }

        System.out.println(wastedAmount);
        
    }
}