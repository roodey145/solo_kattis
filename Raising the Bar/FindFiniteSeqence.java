import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FindFiniteSeqence {
    public static void main(String[] args) throws IOException {
        // // Read info
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] metaData = reader.readLine().split(" ");
        int numerator = Integer.parseInt(metaData[0]);
        int denominator = Integer.parseInt(metaData[1]);

        Map<Integer, Integer> remainderIndexMap = new HashMap<>();

        // Figure out the reminder
        int remainder = numerator % denominator;
        int position = 0; // Keep track of the number of decimals

        while (remainder != 0 && !remainderIndexMap.containsKey(remainder)) {
            // Add the reminder to the map with its position index.
            // Notice that we also consider the reminder of the first decimals!
            // as the first decimal could be part of a bigger repeating sequence
            remainderIndexMap.put(remainder, position++);


            remainder *= 10;
            remainder %= denominator;
        }

        if (remainder == 0) {
            // terminating decimal
            System.out.println(position + " 0");
        } else {
            int nonRepeating = remainderIndexMap.get(remainder);
            int repeating = position - nonRepeating;
            System.out.println(nonRepeating + " " + repeating);
        }
    }
}