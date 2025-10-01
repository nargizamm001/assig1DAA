

public class DeterministicSelect {

    // Public API: k is 0-based (0 => minimum)
    public static int selectKth(int[] a, int k) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        Counters.reset();
        return select(a, 0, a.length - 1, k);
    }

    private static int select(int[] a, int l, int r, int k) {
        while (true) {
            if (l == r) return a[l];
            int pivot = medianOfMedians(a, l, r);
            int pivotPos = partitionAroundValue(a, l, r, pivot);
            if (k == pivotPos) return a[k];
            else if (k < pivotPos) r = pivotPos - 1;
            else l = pivotPos + 1;
        }
    }

    // Partition around a given pivot value; returns final index of pivot
    private static int partitionAroundValue(int[] a, int l, int r, int pivotValue) {
        int pivotIndex = l;
        while (pivotIndex <= r && a[pivotIndex] != pivotValue) pivotIndex++;
        if (pivotIndex > r) pivotIndex = r;
        swap(a, pivotIndex, r);
        int store = l;
        for (int i = l; i < r; i++) {
            Counters.comparisons++;
            if (a[i] < pivotValue) {
                swap(a, store, i);
                store++;
            }
        }
        swap(a, store, r);
        return store;
    }

    private static int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            java.util.Arrays.sort(a, l, r + 1);
            return a[l + n / 2];
        }
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            int gl = l + i * 5;
            int gr = Math.min(gl + 4, r);
            java.util.Arrays.sort(a, gl, gr + 1);
            medians[i] = a[gl + (gr - gl) / 2];
        }
        // recurse to find median of medians
        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
