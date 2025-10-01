

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    // Public API: returns minimal distance
    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Counters.reset();
        Point[] px = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[px.length];
        return closestRec(px, aux, 0, px.length - 1);
    }

    private static double closestRec(Point[] px, Point[] aux, int l, int r) {
        Counters.maxDepth = Math.max(Counters.maxDepth, (long) (Math.log(r - l + 2) / Math.log(2) + 1));
        int n = r - l + 1;
        if (n <= 64) {
            return brute(px, l, r);
        }
        int m = (l + r) >>> 1;
        double dl = closestRec(px, aux, l, m);
        double dr = closestRec(px, aux, m + 1, r);
        double d = Math.min(dl, dr);

        double midX = px[m].x;
        int t = 0;
        for (int i = l; i <= r; i++) {
            if (Math.abs(px[i].x - midX) < d) aux[t++] = px[i];
        }
        Arrays.sort(aux, 0, t, Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < t; i++) {
            for (int j = i + 1; j < t && (aux[j].y - aux[i].y) < d; j++) {
                Counters.comparisons++;
                double dist = dist(aux[i], aux[j]);
                if (dist < d) d = dist;
            }
        }
        return d;
    }

    private static double brute(Point[] px, int l, int r) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                Counters.comparisons++;
                best = Math.min(best, dist(px[i], px[j]));
            }
        }
        return best;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
