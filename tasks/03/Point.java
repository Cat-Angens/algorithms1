/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y)
            return +0.0;
        return ((double) that.y - (double) this.y) / ((double) that.x - (double) this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y)
            return -1;
        if (this.y > that.y)
            return 1;
        return Integer.compare(this.x, that.x);
    }
    
    private class SlopeOrder implements Comparator<Point>
    {
        public int compare(Point p1, Point p2) {
            if (p1.x == p2.x && p1.y == p2.y)
                return 0;
            double a1 = slopeTo(p1);
            double a2 = slopeTo(p2);
            return Double.compare(a1, a2);
        }
    }
    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(1, 0);
        Point p4 = new Point(3, 0);
        Point p5 = new Point(3, -1);
        Point p6 = new Point(3, 1);
        Point p7 = new Point(0, 3);
        Point p8 = new Point(0, 3);
        Point p9 = new Point(0, -1);
        Point p10 = new Point(-2, 2);
        Point p11 = new Point(-2, -2);
        Point p12 = new Point(2, -2);
        Point p13 = new Point(-2, 0);
        
        System.out.println(p1.compareTo(p2) == 0);
        System.out.println(p1.compareTo(p3) < 0);
        System.out.println(p1.compareTo(p5) > 0);
        System.out.println(p1.compareTo(p6) < 0);
        System.out.println(p1.compareTo(p13) > 0);
        
        System.out.println(p1.slopeTo(p2));
        System.out.println(p1.slopeTo(p3));
        System.out.println(p1.slopeTo(p4));
        System.out.println(p1.slopeTo(p5));
        System.out.println(p1.slopeTo(p6));
        System.out.println(p1.slopeTo(p7));
        System.out.println(p1.slopeTo(p8));
        System.out.println(p1.slopeTo(p9));
        System.out.println(p1.slopeTo(p10));
        System.out.println(p1.slopeTo(p11));
        System.out.println(p1.slopeTo(p12));
        System.out.println(p1.slopeTo(p13));
        
        System.out.println("end");
    }
}
