import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The idea is to reduce the redundent work which is a core
 * concept in dynamic programming.
 */
public class PebbleSolitaireV1 {
    private static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int boards = Integer.parseInt(reader.readLine());
        StringBuilder builder;
        map = new HashMap<>();
        for (int i = 0; i < boards; i++) {
            builder = new StringBuilder(reader.readLine());

            System.out.println(getMinPossiblePebbles(builder));
        }
    }

    private static int getMinPossiblePebbles(StringBuilder board) {
        if (map.containsKey(board.toString())) {
            return map.get(board.toString());
        }
        int min = 0;
        for (int i = 0; i < board.length(); i++)
                if (board.charAt(i) == 'o')
                    min++;

        for (int i = 0; i < board.length() - 3; i++) {
            if (board.charAt(i) == 'o' && board.charAt(i + 1) == 'o' && board.charAt(i + 2) == '-') {
                board.setCharAt(i, '-');
                board.setCharAt(i + 1, '-');
                board.setCharAt(i + 2, 'o');
                min = Integer.min(min, getMinPossiblePebbles(board));
                map.put(board.toString(), min); // Cache
                // Reset to previous state
                board.setCharAt(i, 'o');
                board.setCharAt(i + 1, 'o');
                board.setCharAt(i + 2, '-');
            }
        }

        for (int i = board.length() - 1; i > 1; i--) {
            if (board.charAt(i) == 'o' && board.charAt(i - 1) == 'o' && board.charAt(i - 2) == '-') {
                board.setCharAt(i, '-');
                board.setCharAt(i - 1, '-');
                board.setCharAt(i - 2, 'o');
                min = Integer.min(min, getMinPossiblePebbles(board));
                map.put(board.toString(), min); // Cache
                // Reset to previous state
                board.setCharAt(i, 'o');
                board.setCharAt(i - 1, 'o');
                board.setCharAt(i - 2, '-');
            }
        }

        return min;
    }
}
