import java.io.BufferedInputStream;
import java.io.IOException;

public class RadioCommercialsV2 {
    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();

        int n = fs.nextInt();
        int price = fs.nextInt();

        int best = 0;
        int current = 0;

        for (int i = 0; i < n; i++) {
            int profit = fs.nextInt() - price;

            current = Math.max(0, current + profit);
            best = Math.max(best, current);
        }

        System.out.println(best);
    }

    static class FastScanner {
        private final BufferedInputStream in =
                new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = in.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = in.read();
            }

            while (c > ' ') {
                value = value * 10 + c - '0';
                c = in.read();
            }

            return value * sign;
        }
    }
}