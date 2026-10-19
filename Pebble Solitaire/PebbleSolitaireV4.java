/**
 * The idea is to reduce the redundent work which is a core
 * concept in dynamic programming. This implementation seems to be slower than
 * V3 by a tiny bit. The difference is that we mesmorize both the board and
 * its mirror as that would lead to the same min number of pebbles.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PebbleSolitaireV4 {

    private static byte[] cache;
    private static int boardLength;

    private static byte getMinPossiblePebbles(int board) {
        // Return the cached result if this state has been solved.
        if (cache[board] != -1) {
            return cache[board];
        }

        byte min = (byte) Integer.bitCount(board);

        // Check every group of three consecutive positions.
        for (int i = 0; i < boardLength - 2; i++) {
            int triple = (board >>> i) & 7;

            // 011 -> 100 or 110 -> 001
            if (triple == 3 || triple == 6) {
                int next = board ^ (7 << i);

                byte result = getMinPossiblePebbles(next);

                if (result < min) {
                    min = result;
                }
            }
        }

        // Cache the result for both the board and its mirror.
        int mirrored = mirrorBoard(board);

        cache[board] = min;
        cache[mirrored] = min;

        return min;
    }

    private static int mirrorBoard(int board) {
        int mirrored = 0;

        for (int i = 0; i < boardLength; i++) {
            if ((board & (1 << i)) != 0) {
                mirrored |= 1 << (boardLength - 1 - i);
            }
        }

        return mirrored;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        int tests = Integer.parseInt(reader.readLine().trim());

        for (int t = 0; t < tests; t++) {
            String boardStr = reader.readLine().trim();
            boardLength = boardStr.length();

            // One cache entry for every possible board configuration.
            cache = new byte[1 << boardLength];
            Arrays.fill(cache, (byte) -1);

            int board = 0;

            for (int i = 0; i < boardLength; i++) {
                if (boardStr.charAt(i) == 'o') {
                    board |= 1 << i;
                }
            }

            System.out.println(getMinPossiblePebbles(board));
        }
    }
}