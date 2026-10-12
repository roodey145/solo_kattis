import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.util.stream.Stream;

public class RadioCommercials {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Integer[] commercialData = getNextIntegers(reader);

        // int commercialBreaks = commercialData[0];
        // int commercialPrice = commercialData[1];
        int commercialPrice = Integer.parseInt(reader.readLine().split(" ")[1]);

        // Integer[] listener = getNextIntegers(reader);
        String[] listener = reader.readLine().split(" ");
        int best = 0;
        int current = 0;
        int curListener;
        for(int i = 0; i < listener.length; i++) {
            curListener = Integer.valueOf(listener[i]);
            // Check if the current commercial break will be profitable
            if(curListener + current >= commercialPrice) {
                current += curListener - commercialPrice;
                // Update the best profiting window
                best = current > best ? current : best;
            } else {
                // Reset the current sum to start a new window
                current = 0; 
            }
        }
        System.out.println(best);
    }


    // public static Integer[] getNextIntegers(BufferedReader reader) throws IOException {
    //     return Stream.of(reader.readLine().split(" ")).map(Integer::valueOf)
    //             .toArray(Integer[]::new);
    // }
}