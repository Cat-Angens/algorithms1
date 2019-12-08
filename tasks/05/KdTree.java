/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    // construct an empty kd-tree of points
    public KdTree() {
        
        // TODO
    }

    // is the tree empty?
    public boolean isEmpty() {
        
        // TODO
        return true;
    }

    // number of points in the tree
    public int size() {

        // TODO
        return 0;
    }
    
    // add the point to the tree (if it is not already in the tree)
    public void insert(Point2D p) {

        // TODO
    }

    // does the tree contain point p?
    public boolean contains(Point2D p) {

        // TODO
        return false;
    }
    
    // draw all points to standard draw
    public void draw() {

        // TODO
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        // TODO
        return new Queue<>();
    }

    // a nearest neighbor in the tree to point p; null if the tree is empty
    public Point2D nearest(Point2D p) {

        // TODO
        return null;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
        // TODO
    }
}
