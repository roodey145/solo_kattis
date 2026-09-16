
class Fenwick {

    public static void main(String[] args) {
        Fenwick fenwick = new Fenwick(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        System.out.println(fenwick.prefixSum(1));
        System.out.println(fenwick.prefixSum(2));
        System.out.println(fenwick.prefixSum(5));
    }

    private final int[] tree;
    public Fenwick(int[] values) {
        tree = new int[values.length + 1];

        for(int i = 0; i < values.length; i++) {
            add(i+1, values[i]);
        }
    }

    public void add(int i, int v){
        while(i < tree.length) {
            tree[i] += v;
            i += lsb(i);
        }
    }

    public int prefixSum(int i) {
        System.out.println("Get PreFixSum of " + i);
        int sum = 0;
        while(i > 0) {
            sum += tree[i];
            System.out.println(i);
            i -= lsb(i);
        }
        System.out.print("The PrefixSum is: ");

        return sum;
    }

    private int lsb(int n) {
        return n & (-n);
    }
}