package com.codingame.game;

public class Geometry {
    public static double dist(int x1, int y1, int x2, int y2) {
        long dx = x2 - x1;
        long dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static long crossProduct(int ax, int ay, int bx, int by, int cx, int cy) {
        return (long) (bx - ax) * (cy - ay) - (long) (by - ay) * (cx - ax);
    }

    private static boolean onSegment(int ax, int ay, int bx, int by, int px, int py) {
        return px >= Math.min(ax, bx) && px <= Math.max(ax, bx)
            && py >= Math.min(ay, by) && py <= Math.max(ay, by);
    }

    /**
     * Checks if segment AB and segment CD intersect (excluding shared endpoints).
     */
    public static boolean segmentsIntersect(
        int ax, int ay, int bx, int by,
        int cx, int cy, int dx, int dy
    ) {
        // Ignore if they share an exact endpoint
        if ((ax == cx && ay == cy) || (ax == dx && ay == dy)
            || (bx == cx && by == cy) || (bx == dx && by == dy)) {
            return false;
        }

        long d1 = crossProduct(cx, cy, dx, dy, ax, ay);
        long d2 = crossProduct(cx, cy, dx, dy, bx, by);
        long d3 = crossProduct(ax, ay, bx, by, cx, cy);
        long d4 = crossProduct(ax, ay, bx, by, dx, dy);

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0))
            && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        }

        if (d1 == 0 && onSegment(cx, cy, dx, dy, ax, ay)) return true;
        if (d2 == 0 && onSegment(cx, cy, dx, dy, bx, by)) return true;
        if (d3 == 0 && onSegment(ax, ay, bx, by, cx, cy)) return true;
        if (d4 == 0 && onSegment(ax, ay, bx, by, dx, dy)) return true;

        return false;
    }

    /**
     * Returns approximate intersection point [ix, iy] of segments AB and CD.
     */
    public static int[] intersectionPoint(
        int ax, int ay, int bx, int by,
        int cx, int cy, int dx, int dy
    ) {
        double a1 = by - ay;
        double b1 = ax - bx;
        double c1 = a1 * ax + b1 * ay;

        double a2 = dy - cy;
        double b2 = cx - dx;
        double c2 = a2 * cx + b2 * cy;

        double det = a1 * b2 - a2 * b1;
        if (Math.abs(det) < 1e-6) {
            return new int[]{(ax + bx + cx + dx) / 4, (ay + by + cy + dy) / 4};
        }
        int ix = (int) Math.round((b2 * c1 - b1 * c2) / det);
        int iy = (int) Math.round((a1 * c2 - a2 * c1) / det);
        return new int[]{ix, iy};
    }
}
