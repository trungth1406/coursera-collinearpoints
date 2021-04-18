/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        checkNullEntries(points);
        Point[] sorted = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentsList = new ArrayList<>();
        LinkedList<Point> pointList = new LinkedList<>();
        for (int i = 0; i < sorted.length; i++) {
            Arrays.sort(sorted);
            Arrays.sort(sorted, sorted[i].slopeOrder());
            Point origin = sorted[0];
            for (int j = 0; j < sorted.length - 1; j++) {
                pointList.add(sorted[j]);
                while (j + 1 < sorted.length && origin.slopeTo(sorted[j]) == origin.slopeTo(sorted[j + 1])) {
                    pointList.add(sorted[++j]);
                }
                if (pointList.size() >= 3 && origin.compareTo(pointList.get(0)) < 0) {
                    LineSegment lineSegment = new LineSegment(origin, pointList.get(pointList.size() - 1));
                    segmentsList.add(lineSegment);
                }
                pointList.clear();
            }
        }

        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }


    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }


    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException(
                        "One of the point in points array is null");
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
