

public class Counters {
    public static long comparisons = 0;
    public static long allocations = 0;
    public static long maxDepth = 0;

    public static int cmp(int a, int b) {
        comparisons++;
        return Integer.compare(a, b);
    }

    public static void reset() {
        comparisons = 0;
        allocations = 0;
        maxDepth = 0;
    }
}
