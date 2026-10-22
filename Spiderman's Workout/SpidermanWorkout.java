import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpidermanWorkout {
    private static SpidermanWorkout instance = new SpidermanWorkout();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cases; i++) {
            handleCase(reader);
        }
    }

    private static void handleCase(BufferedReader reader) throws IOException {
        int jumps = Integer.parseInt(reader.readLine());
        Result result = lowestHeight(reader.readLine().split(" "), 0, instance.new Result());
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

    private static Result lowestHeight(String[] jumps, int i, Result res) {
        if (i == jumps.length) {
            res.completed = res.curHeight == 0;
            return res;
        }
        int curJump = Integer.parseInt(jumps[i]);
        // Jump up
        Result jumpUpResult = res.copy();
        jumpUpResult.curHeight += curJump;
        jumpUpResult.highestJump = Integer.max(jumpUpResult.highestJump, jumpUpResult.curHeight);
        jumpUpResult.sequence += 1L << i;
        jumpUpResult = lowestHeight(jumps, i + 1, jumpUpResult);

        // Jump down
        Result jumpDownResult = jumpUpResult;
        if (res.curHeight - curJump >= 0) {
            jumpDownResult = res.copy();
            jumpDownResult.curHeight -= curJump;
            jumpDownResult.highestJump = Integer.max(jumpDownResult.highestJump, jumpDownResult.curHeight);
            jumpDownResult = lowestHeight(jumps, i + 1, jumpDownResult);
        }

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
