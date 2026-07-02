import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MinimumTrains {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(reader.readLine());
        int t = Integer.parseInt(reader.readLine()); // Turnaround delay time
        String[] tripsMetaData = reader.readLine().split(" ");
        int abTrips = Integer.parseInt(tripsMetaData[0]);
        int baTrips = Integer.parseInt(tripsMetaData[1]);
    }
}