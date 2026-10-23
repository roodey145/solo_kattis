import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * We have the following bills {1, 5, 15, 30, 150} and we
 * want to get as few of them as possible when paying.
 * The main problem: moneyDist_n = min(moneyDist_[n-money1], ...,
 * moneyDist_[n-moneyn]) + 1
 */

public class ExactChange {
    private static int[] bills = new int[] { 1, 5, 15, 30, 150 };
    private static MinBills[] cache;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int bill = Integer.parseInt(reader.readLine());
        cache = new MinBills[bill + 1];
        cache[0] = new MinBills(new int[bills.length]); 
        MinBills result = solve(bill);
        for (int i = 0; i < result.billsInfo.length; i++) {
            System.out.print(result.billsInfo[i] + (i < result.billsInfo.length - 1 ? " " : ""));
        }
    }

    private static MinBills solve(int desiredMoney) {
        if (cache[desiredMoney] != null) {
            return cache[desiredMoney];
        }

        int bestBillToUseI = 0;
        int minBills = Integer.MAX_VALUE;
        int[] billsUsed = new int[ExactChange.bills.length];
        MinBills curMinBill;
        // Get the minimum billsCount from previous sums, starting from the biggest bill
        for (int i = billsUsed.length - 1; i >= 0; i--) {
            // Make sure even after subtracting the bill, it is still above -1
            if (desiredMoney - ExactChange.bills[i] < 0) {
                continue;
            } else if (desiredMoney - ExactChange.bills[i] == 0) {
                bestBillToUseI = i;
                break;
            } else {
                curMinBill = solve(desiredMoney - ExactChange.bills[i]);
                if (curMinBill.getBillsCount() < minBills) {
                    minBills = curMinBill.getBillsCount();
                    billsUsed = curMinBill.billsInfo.clone();
                    bestBillToUseI = i;
                }
            }
        }

        billsUsed[bestBillToUseI]++;
        cache[desiredMoney] = new MinBills(billsUsed);
        return cache[desiredMoney];
    }

    static class MinBills {
        final int[] billsInfo;
        final int billsCount;

        MinBills(int[] billsInfo) {
            this.billsInfo = billsInfo;
            int count = 0;
            for (int i = 0; i < billsInfo.length; i++) {
                count += billsInfo[i];
            }
            billsCount = count;
        }

        int getBillsCount() {
            return billsCount;
        }
    }
}