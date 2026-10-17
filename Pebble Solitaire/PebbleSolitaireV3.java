/**
 * The idea is to reduce the redundent work which is a core
 * concept in dynamic programming. This implementation is supposed
 * to be a bit faster than V2. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PebbleSolitaireV3 {

    private static byte[] cache;
    private static int boardLength;

    private static byte getMinPossiblePebbles(int board) {
        if (cache[board] != -1) {
            return cache[board];
        }

        byte min = (byte) Integer.bitCount(board);

        for (int i = 0; i < boardLength - 2; i++) {
            int triple = (board >>> i) & 7;

            // 011 -> 100: move one pebble right
            // 110 -> 001: move one pebble left
            if (triple == 3 || triple == 6) {
                int next = board ^ (7 << i);
                byte result = getMinPossiblePebbles(next);

                if (result < min) {
                    min = result;
                }
            }
        }

        cache[board] = min;
        return min;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

        int tests = Integer.parseInt(reader.readLine().trim());

        for (int t = 0; t < tests; t++) {
            String s = reader.readLine().trim();
            boardLength = s.length();

            // One entry for every possible board configuration.
            cache = new byte[1 << boardLength];
            Arrays.fill(cache, (byte) -1);

            int board = 0;

            for (int i = 0; i < boardLength; i++) {
                if (s.charAt(i) == 'o') {
                    board |= 1 << i;
                }
            }

            System.out.println(getMinPossiblePebbles(board));
        }
    }
}
