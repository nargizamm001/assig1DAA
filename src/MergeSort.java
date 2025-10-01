

public class MergeSort {
    public static void sort(int[] arr) {
        Counters.maxDepth = 0;
        sort(arr, 0, arr.length - 1, 1);
    }

    private static void sort(int[] arr, int left, int right, int depth) {
        Counters.maxDepth = Math.max(Counters.maxDepth, depth);
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid, depth + 1);
            sort(arr, mid + 1, right, depth + 1);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];
        Counters.allocations += 2;

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (Counters.cmp(L[i], R[j]) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
}
