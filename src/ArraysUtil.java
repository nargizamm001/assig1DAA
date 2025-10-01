

import java.util.Random;

public class ArraysUtil {
    public static int[] randomIntArray(int n, int min, int max, long seed) {
        Random rnd = new Random(seed);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rnd.nextInt(max - min + 1) + min;
        }
        Counters.allocations++;
        return arr;
    }

    public static int[] copy(int[] arr) {
        int[] b = arr.clone();
        Counters.allocations++;
        return b;
    }
}
