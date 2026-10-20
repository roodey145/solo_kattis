
package Knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KnapsackV1 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try{
                handleCase(reader);
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void handleCase(BufferedReader reader) throws IOException {
        String[] info = reader.readLine().trim().split("\\s+");

        int capacity = Integer.parseInt(info[0]);
        int numberOfObjects = Integer.parseInt(info[1]);

        int[] values = new int[numberOfObjects];
        int[] weights = new int[numberOfObjects];

        for (int i = 0; i < numberOfObjects; i++) {
            info = reader.readLine().trim().split("\\s+");
            values[i] = Integer.parseInt(info[0]);
            weights[i] = Integer.parseInt(info[1]);
        }

        int[] even = new int[capacity + 1];
        int[] odd = new int[capacity + 1];

        // included[i][w] is true if item i was selected
        // when calculating the optimal value for capacity w.
        boolean[][] included = new boolean[numberOfObjects][capacity + 1];

        int[] previous = even;
        int[] target = odd;

        for (int i = 0; i < numberOfObjects; i++) {
            int value = values[i];
            int weight = weights[i];

            for (int w = 0; w <= capacity; w++) {
                int excludeValue = previous[w];

                if (weight <= w) {
                    int includeValue = previous[w - weight] + value;

                    if (includeValue > excludeValue) {
                        target[w] = includeValue;
                        included[i][w] = true;
                    } else {
                        target[w] = excludeValue;
                    }
                } else {
                    target[w] = excludeValue;
                }
            }

            // Swap arrays for the next item.
            int[] temp = previous;
            previous = target;
            target = temp;
        }

        int bestWeight = 0;
        int greatestValue = 0;

        for (int w = 0; w <= capacity; w++) {
            if (previous[w] > greatestValue) {
                greatestValue = previous[w];
                bestWeight = w;
            }
        }

        // Reconstruct the selected items in reverse order.
        int[] selectedItems = new int[numberOfObjects];
        int selectedCount = 0;
        int remainingWeight = bestWeight;

        for (int i = numberOfObjects - 1; i >= 0; i--) {
            if (included[i][remainingWeight]) {
                selectedItems[selectedCount++] = i;
                remainingWeight -= weights[i];
            }
        }

        // // Print the maximum value.
        // System.out.println(greatestValue);

        // Print the number of selected items.
        System.out.println(selectedCount);

        // Print selected item indices (zero-based).
        for (int i = 0; i < selectedCount; i++) {
            System.out.print(selectedItems[i] + ((i == selectedCount - 1) ? "\n" : " "));
        }
    }
}
