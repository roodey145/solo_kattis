import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SpidermanWorkout {
    private static SpidermanWorkout instance = new SpidermanWorkout();
    private static long[][] cache;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cases; i++) {
            handleCase(reader);
        }
    }

    private static void handleCase(BufferedReader reader) throws IOException {
        int jumps = Integer.parseInt(reader.readLine());
        String[] jumpsStrArr = reader.readLine().split(" ");
        int[] jumpsArr = new int[jumps];
        int maxPossibleHeight = 0;
        for(int i = 0; i < jumps; i++) {
            jumpsArr[i] = Integer.parseInt(jumpsStrArr[i]);
            maxPossibleHeight += jumpsArr[i];
        }

        cache = new long[jumps + 1][maxPossibleHeight + 1];

        for (long[] row : cache) {
            Arrays.fill(row, -1);
        }

        Result result = lowestHeight(jumpsArr, 0, instance.new Result());
        if(!result.completed) {
            System.out.println("IMPOSSIBLE");
            return;
        }
        // System.out.println(result.highestJump + 2);
        for(int i = jumps - 1; i >= 0; i--) {
            if((result.sequence & (1L << i)) == 0) {
                System.out.print("U");
            } else {
                System.out.print("D");
            }
        }
        System.out.println();
    }

    private static Result lowestHeight(int[] jumps, int i, Result res) {
        if (i == jumps.length) {
            res.completed = res.curHeight == 0;
            return res;
        }

        // Check if this i with that height has been cached/explored befroe
        if(cache[i][res.curHeight] != -1) {
            return res;
        }
        

        int curJump = jumps[i];
        // Jump up
        Result jumpUpResult = res.copy();
        jumpUpResult.curHeight += curJump;
        jumpUpResult.highestJump = Integer.max(jumpUpResult.highestJump, jumpUpResult.curHeight);
        jumpUpResult.sequence += 1L << i;
        jumpUpResult = lowestHeight(jumps, i + 1, jumpUpResult);
        // Cache current height
        cache[i][res.curHeight] = jumpUpResult.sequence;


        // Jump down
        Result jumpDownResult = jumpUpResult;
        if (res.curHeight - curJump >= 0) {
            jumpDownResult = res.copy();
            jumpDownResult.curHeight -= curJump;
            jumpDownResult.highestJump = Integer.max(jumpDownResult.highestJump, jumpDownResult.curHeight);
            jumpDownResult = lowestHeight(jumps, i + 1, jumpDownResult);
            // Cache current height
            cache[i][res.curHeight] = jumpUpResult.sequence;
            
        }

        if(!jumpDownResult.completed) return jumpUpResult;
        if(!jumpUpResult.completed) return  jumpDownResult;

        return jumpDownResult.completed && jumpDownResult.highestJump <= jumpUpResult.highestJump ? jumpDownResult
                : jumpUpResult;
    }

    class Result {
        int highestJump = 0;
        int curHeight = 0;
        long sequence = 0;
        boolean completed = false;


        protected Result copy() {
            Result res = new Result();
            res.highestJump = highestJump;
            res.curHeight = curHeight;
            res.sequence = sequence;
            res.completed = false;

            return res;
        }
    }
}
