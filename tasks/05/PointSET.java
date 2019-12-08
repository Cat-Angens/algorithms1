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

    private SET<Point2D> point2DSET;
    
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
            if (r < rmin) {
                
                rmin = r;
                pmin = q;
            }
        }
        return pmin;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
        PointSET pset = new PointSET();
        System.out.println("Empty: " + pset.isEmpty());
        pset.insert(new Point2D(0.5, 0.5));
        pset.insert(new Point2D(0.5, 0.5));
        pset.insert(new Point2D(0.5, 0.6));
        pset.insert(new Point2D(0.5, 0.7));
        pset.insert(new Point2D(0.5, 0.8));
        pset.insert(new Point2D(0.1, 0.5));
        pset.insert(new Point2D(0.8, 0.5));
        pset.insert(new Point2D(0.1, 0.1));
        System.out.println("Empty: " + pset.isEmpty());
        System.out.println("Size: " + pset.size());
        System.out.println("Nearest to start: " + pset.nearest(new Point2D(0.0, 0.0)));
        System.out.println("Contains #1: " + pset.contains(new Point2D(0.0, 0.0)));
        System.out.println("Contains #2: " + pset.contains(new Point2D(0.5, 0.5)));
        System.out.print("Range #1: ");
        for (Point2D p : pset.range(new RectHV(0.001, 0.001, 0.002, 0.002)))
            System.out.print(p.toString() + "; ");
        System.out.print("\n");
        System.out.print("Range #2: ");
        for (Point2D p : pset.range(new RectHV(0.05, 0.05, 0.15, 0.15)))
            System.out.print(p.toString() + "; ");
        System.out.print("\n");
        System.out.print("Range #3: ");
        for (Point2D p : pset.range(new RectHV(0.25, 0.35, 0.65, 0.75)))
            System.out.print(p.toString() + "; ");
        System.out.print("\n");
        
    } 
    
}
