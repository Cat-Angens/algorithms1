/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int segCnt = 0;
    private Node first = null;
    private class Node {
        public Node next;
        public LineSegment seg;
        public int ipStart;
        public int ipEnd;
    }
    private class Segment implements Comparable<Segment> {
        public double angle;
        public int p1;
        public int p2;

        public int compareTo(Segment that) {
            return Double.compare(this.angle, that.angle);
        }
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException("Got null argument to constructor");

        int pcnt = points.length;
        for (int i = 0; i < pcnt; ++i) {
            if (points[i] == null)
                throw new IllegalArgumentException("Got null Point inside input array");
        }
        Segment[] sgmts = new Segment[pcnt];
        for (int i = 0; i < pcnt; ++i)
            sgmts[i] = new Segment();

        for (int ip = 0; ip < pcnt - 3; ++ip) {
            // TODO make resize?
            // fill segments array
            for (int i = 0; i < pcnt; ++i) {
                sgmts[i].p1 = ip;
                sgmts[i].p2 = i;
                sgmts[i].angle = points[ip].slopeTo(points[i]);
            }
            Arrays.sort(sgmts);
            int i = 1;
            while (i < pcnt) {
                int i1 = i;
                double angle = sgmts[i].angle;
                if (angle == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("Input array contains a repeated point");
                while (i < pcnt && angle == sgmts[i].angle)
                    ++i;
                int i2 = i;
                if (i2 - i1 > 2) {
                    int imin = 0;
                    int imax = 0;
                    for (int j = i1; j < i2; ++j) {
                        if (points[sgmts[j].p2].compareTo(points[sgmts[imin].p2]) < 0)
                            imin = j;
                        if (points[sgmts[j].p2].compareTo(points[sgmts[imax].p2]) > 0)
                            imax = j;
                    }
                    if (checkLineSegExists(sgmts[imin].p2, sgmts[imax].p2))
                        continue;
                    Node node = new Node();
                    node.seg = new LineSegment(points[sgmts[imin].p2], points[sgmts[imax].p2]);
                    node.ipStart = sgmts[imin].p2;
                    node.ipEnd = sgmts[imax].p2;
                    node.next = first;
                    first = node;
                    ++segCnt;
                }
            }
        }
    }
    
    private boolean checkLineSegExists(int p1, int p2) {
        Node node = first;
        while (node != null) {
            if (node.ipStart == p1 && node.ipEnd == p2)
                return true;
            node = node.next;
        }
        return false;
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segCnt;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[segCnt];
        Node node = first;
        for (int i = 0; i < segCnt; ++i) {
            lines[i] = node.seg;
            node = node.next;
        }
        return lines;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.printf("Found %d segments\n", collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
