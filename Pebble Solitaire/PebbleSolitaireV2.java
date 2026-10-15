import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The idea is to reduce the redundent work which is a core
 * concept in dynamic programming. This implementation is supposed
 * to be faster than V1 as it will be developed using bit operations.
 */
public class PebbleSolitaireV2 {
    // Integer: 32 bits -> enough for 23 cavities. 
    // Byte: 8bits -> enough for to represent number of pebbles left
    private static Map<Integer, Byte> cacheMap = new HashMap<>();
    private static int boardLength;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int boards = Integer.parseInt(reader.readLine());
        
        String boardStr;
        int boardInt;
        for(int i = 0; i < boards; i++) {
            boardInt = 0;
            boardStr = reader.readLine();
            boardLength = boardStr.length();
            for(int c = 0; c < boardStr.length(); c++) {
                boardInt += boardStr.charAt(c) == 'o' ? (1 << c) : 0;
            }
            System.out.println(getMinPossiblePebbles(boardInt));
        }
    }

    private static byte getMinPossiblePebbles(int board) {
        if(cacheMap.containsKey(board)) {
            return cacheMap.get(board);
        }

        byte min = (byte) Integer.bitCount(board);
        int newBoard;

        for(int i = 0; i < boardLength - 3; i++) {
            if((board & 1 << i) > 0 && (board & 1 << (i+1)) > 0 && (board & 1 << (i+2)) == 0) {
                newBoard = board - (1 << i) - (1 << (i+1)) + (1 << (i+2));
                // Can move a pebble
                min = (byte) Integer.min(min, getMinPossiblePebbles(newBoard));
                cacheMap.put(newBoard, min);
            }
        }

        for(int i = boardLength; i > 1; i--) {
            if((board & 1 << i) > 0 && (board & 1 << (i-1)) > 0 && (board & 1 << (i-2)) == 0) {
                newBoard = board - (1 << i) - (1 << (i-1)) + (1 << (i-2));
                // Can move a pebble
                min = (byte) Integer.min(min, getMinPossiblePebbles(newBoard));
                cacheMap.put(newBoard, min);
            }
        }

        return min;
    }
    
}
