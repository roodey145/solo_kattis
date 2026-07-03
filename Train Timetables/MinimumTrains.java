import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MinimumTrains {
    private static MinimumTrains _instance = new MinimumTrains();
    private static BufferedReader _reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        int cases = Integer.parseInt(_reader.readLine());
        for(int i = 0; i < cases; i++) {
            handleCase(i + 1);
        }
    }

    public static void handleCase(int caseNr) throws IOException {
        int t = Integer.parseInt(_reader.readLine()); // Turnaround delay time
        String[] tripsMetaData = _reader.readLine().split(" ");
        int abTrips = Integer.parseInt(tripsMetaData[0]);
        int baTrips = Integer.parseInt(tripsMetaData[1]);
        TrainLocation[] schedule = new TrainLocation[(abTrips + baTrips) * 2];
        String[] tripInfo;
        int depTime;
        int arrTime;
        // Read trips from a to b
        for (int i = 0; i < abTrips * 2; i += 2) {
            tripInfo = _reader.readLine().split(" ");
            depTime = convertTimeStringToMinutes(tripInfo[0]);
            schedule[i] = _instance.new TrainLocation(depTime, Location.A, true);
            arrTime = convertTimeStringToMinutes(tripInfo[1]) + t; // Add the delay to the arrival time since the train is only ready after it
            schedule[i+1] = _instance.new TrainLocation(arrTime, Location.B, false);
        }

        for (int i = 0; i < baTrips * 2; i += 2) {
            tripInfo = _reader.readLine().split(" ");
            depTime = convertTimeStringToMinutes(tripInfo[0]);
            schedule[abTrips*2 + i] = _instance.new TrainLocation(depTime, Location.B, true);
            arrTime = convertTimeStringToMinutes(tripInfo[1]) + t; // Add the delay to the arrival time since the train is only ready after it
            schedule[abTrips*2 + i+1] = _instance.new TrainLocation(arrTime, Location.A, false);
        }

        // Now sort the array so we know when a train is needed and when it is available
        Arrays.sort(schedule);

        int avalA = 0;
        int avalB = 0;
        int neededA = 0;
        int neededB = 0;
        boolean neededTrain;
        for (int i = 0; i < schedule.length; i++) {
            neededTrain = schedule[i].departure; // If true then this is a needed train
            if(schedule[i].location == Location.A) {
                if(neededTrain && avalA <= 0) {
                    neededA++; // A train must leave location A, but there is no available train
                } else if(neededTrain) {
                    avalA--; // There is avialble train, just allow it to leave
                } else {
                    avalA++;
                }

            } else {
                if(neededTrain && avalB <= 0) {
                    neededB++; // A train must leave location A, but there is no available train
                } else if(neededTrain) {
                    avalB--; // There is avialble train, just allow it to leave
                } else {
                    avalB++;
                }
            }
        }

        System.out.println("Case #" + caseNr + ": " + neededA + " " + neededB);
    }

    private static int convertTimeStringToMinutes(String timeString) {
        String[] timeData = timeString.split(":");
        int hours = Integer.parseInt(timeData[0]);
        int minutes = Integer.parseInt(timeData[1]);
        return hours * 60 + minutes;
    }

    class TrainLocation implements Comparable<TrainLocation> {
        int timeMinutes;
        Location location;
        boolean departure;


        TrainLocation(int timeInMinutes, Location location, boolean departure) {
            this.timeMinutes = timeInMinutes;
            this.location = location;
            this.departure = departure;
        }

        @Override
        public int compareTo(TrainLocation o) {
            if (timeMinutes != o.timeMinutes)
                return Integer.compare(timeMinutes, o.timeMinutes);

            // arrivals before departures
            if (departure != o.departure)
                return departure ? 1 : -1;

            return 0;
        }

        
    }

    enum Location {
        A,
        B
    }


}