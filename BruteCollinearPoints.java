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

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("null argument to constructor");
        checkNullEntries(points);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
        for (int i = 0; i < (pointsCopy.length - 3); ++i)
            for (int j = i + 1; j < (pointsCopy.length - 2); ++j)
                for (int k = j + 1; k < (pointsCopy.length - 1); ++k)
                    for (int l = k + 1; l < (pointsCopy.length); ++l) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                                .slopeTo(pointsCopy[l]) &&
                                pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[i]
                                        .slopeTo(pointsCopy[k])) {
                            LineSegment tempLineSegment = new LineSegment(pointsCopy[i],
                                                                          pointsCopy[l]);
                            if (!segmentsList.contains(tempLineSegment))
                                segmentsList.add(tempLineSegment);
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

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries in given points");
            }
        }
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

        // read the n points from a file
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
