/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    SET<Point2D> point2DSET;
    
    // construct an empty set of points
    public PointSET() {

        point2DSET = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        
        return point2DSET.isEmpty();
    }

    // number of points in the set
    public int size() {
        
        return point2DSET.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        if (p == null)
            throw new IllegalArgumentException("Got null object in insert()");

        point2DSET.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {

        if (p == null)
            throw new IllegalArgumentException("Got null object in contains()");

        return point2DSET.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        
        // TODO
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null)
            throw new IllegalArgumentException("Got null object in range()");
        
        Queue<Point2D> pointsInside = new Queue<>();
        
        for (Point2D p : point2DSET) {
            
            double x = p.x();
            double y = p.y();
            if (x >= rect.xmin() && x <= rect.xmax() &&
                y >= rect.ymin() && y <= rect.ymax())
                pointsInside.enqueue(p);
            
        }
        
        return pointsInside;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        
        if (p == null)
            throw new IllegalArgumentException("Got null object in nearest()");
        
        double rmin = 2.;
        Point2D pmin = null;
        
        for (Point2D q : point2DSET) {
            
            double r = q.distanceTo(p);
            if (rmin < r) {
                
                rmin = r;
                pmin = q;
            }
        }
        return pmin;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
    } 
    
}
