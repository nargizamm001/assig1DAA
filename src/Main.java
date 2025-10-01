
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Sizes - adjust if too slow on your machine
        int sortN = 200_000;
        int selectN = 200_000;
        int closestN = 20_000; // keep this smaller (ClosestPair is heavier)

        // ---------- MergeSort ----------
        int[] arr1 = randomIntArray(sortN, 42);
        int[] copy1 = arr1.clone();
        System.out.println("=== MergeSort ===");
        long t0 = System.nanoTime();
        MergeSort.sort(arr1);
        long t1 = System.nanoTime();
        System.out.printf("time(ms)=%.3f%n", (t1 - t0) / 1e6);
        Arrays.sort(copy1);
        System.out.println("correct: " + Arrays.equals(arr1, copy1));
        printMetrics();

        // ---------- QuickSort ----------
        int[] arr2 = randomIntArray(sortN, 123);
        int[] copy2 = arr2.clone();
        System.out.println("\n=== QuickSort ===");
        t0 = System.nanoTime();
        QuickSort.sort(arr2);
        t1 = System.nanoTime();
        System.out.printf("time(ms)=%.3f%n", (t1 - t0) / 1e6);
        Arrays.sort(copy2);
        System.out.println("correct: " + Arrays.equals(arr2, copy2));
        printMetrics();

        // ---------- Deterministic Select ----------
        int[] arr3 = randomIntArray(selectN, 555);
        int k = selectN / 2; // median index
        System.out.println("\n=== Deterministic Select (k = " + k + ") ===");
        t0 = System.nanoTime();
        int val = DeterministicSelect.selectKth(arr3.clone(), k);
        t1 = System.nanoTime();
        System.out.printf("time(ms)=%.3f value=%d%n", (t1 - t0) / 1e6, val);
        // verify
        int[] sorted = arr3.clone();
        Arrays.sort(sorted);
        System.out.println("correct: " + (val == sorted[k]));
        printMetrics();

        // ---------- Closest Pair ----------
        ClosestPair.Point[] pts = randomPoints(closestN, 999, 98765L);
        System.out.println("\n=== Closest Pair ===");
        t0 = System.nanoTime();
        double best = ClosestPair.closest(pts);
        t1 = System.nanoTime();
        System.out.printf("time(ms)=%.3f distance=%.6f%n", (t1 - t0) / 1e6, best);
        printMetrics();
    }

    private static int[] randomIntArray(int n, long seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(2_000_001) - 1_000_000;
        return a;
    }

    private static ClosestPair.Point[] randomPoints(int n, int coordRange, long seed) {
        Random rnd = new Random(seed);
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            double x = rnd.nextDouble() * coordRange;
            double y = rnd.nextDouble() * coordRange;
            pts[i] = new ClosestPair.Point(x, y);
        }
        return pts;
    }

    private static void printMetrics() {
        System.out.printf("metrics: comps=%d allocs=%d maxDepth=%d%n",
                Counters.comparisons, Counters.allocations, Counters.maxDepth);
    }
}
