

import java.util.Random;

public class QuickSort {
    private static final int CUTOFF = 32;
    private static final Random RNG = new Random(42);

    // Public entry
    public static void sort(int[] a) {
        Counters.reset();
        quickSort(a, 0, a.length - 1, 1);
    }

    private static void quickSort(int[] a, int lo, int hi, int depth) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                insertionSort(a, lo, hi);
                return;
            }
            // update max depth
            Counters.maxDepth = Math.max(Counters.maxDepth, depth);

            int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
            swap(a, pivotIndex, hi); // move pivot to end
            int p = partition(a, lo, hi);
            int leftSize = p - lo;
            int rightSize = hi - p;
            // recurse on smaller side first
            if (leftSize < rightSize) {
                quickSort(a, lo, p - 1, depth + 1);
                lo = p + 1; // tail on larger
            } else {
                quickSort(a, p + 1, hi, depth + 1);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            Counters.comparisons++;
            if (a[j] < pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l) {
                Counters.comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
