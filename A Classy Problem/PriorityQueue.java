import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PriorityQueue {
    private static BufferedReader _reader;
    public static void main(String[] args) throws IOException {
        _reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(_reader.readLine());
        for(int i = 0; i < cases; i++) {
            handleCase();
        }
    }

    private static void handleCase() throws IOException {
        int people = Integer.parseInt(_reader.readLine());
        for(int i = 0; i < people; i++) {
            
        }
    }
}