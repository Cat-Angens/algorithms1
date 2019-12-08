/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

    private static class Node {
        
        private Point2D p;
        private int level;
        private Node parent;
        private Node left = null;
        private Node right = null;
    }
    
    private Node root = null;
    private int size = 0;
    
    // construct an empty kd-tree of points
    public KdTree() {

        // TODO
    }
    
    private boolean isXLevel(int level) {
        return level % 2 == 0;
    }
    
    private boolean pLessNode(Node node, Point2D p) {
        
        if (isXLevel(node.level))
            return p.x() <= node.p.x();
        return p.y() <= node.p.y();
    }
    
    private double getAxedDist(Node node, Point2D p) {
        
        if (isXLevel(node.level))
            return Math.abs(node.p.x() - p.x());
        return Math.abs(node.p.y() - p.y());
    }
    
    // is the tree empty?
    public boolean isEmpty() {
        
        return size == 0;
    }

    // number of points in the tree
    public int size() {

        return size;
    }
    
    // add the point to the tree (if it is not already in the tree)
    public void insert(Point2D p) {
        
        if (root == null) {
            root = new Node();
            root.p = p;
            root.level = 0;
            root.parent = null;
            size = 1;
            return;
        }
        
        Node x = root;
        Node parent = root;
        boolean leftDirect = true;
        int level = 0;
        while (x != null) {
            
            if (x.p.equals(p))
                return;

            parent = x;
            ++level;
            
            if (pLessNode(x, p)) {
                leftDirect = true;
                x = x.left;
            }
            else {
                leftDirect = false;
                x = x.right;
            }
        }
        
        x = new Node();
        x.p = p;
        x.level = level;
        x.parent = parent;
        if (leftDirect)
            parent.left = x;
        else
            parent.right = x;
        
        size++;
    }

    // does the tree contain point p?
    public boolean contains(Point2D p) {

        if (size == 0)
            return false;
        Node x = root;
        while (x != null) {
            if (x.p.equals(p))
                return true;
            if (pLessNode(x, p))
                x = x.left;
            else
                x = x.right;
        }

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
    
    private boolean addChildNodesToStack(Point2D p, Node n, Stack<Node> s) {
        
        if (n.left == null && n.right == null)
            return false;
        
        if (n.left == null)
            s.push(n.right);
        else if (n.right == null)
            s.push(n.left);
        else {
            if (pLessNode(n, p)) {
                s.push(n.right);
                s.push(n.left);
            } else {
                s.push(n.left);
                s.push(n.right);
            }
        }
        return true;
    }
    // a nearest neighbor in the tree to point p; null if the tree is empty
    public Point2D nearest(Point2D p) {

        if (size == 0)
            return null;
        if (size == 1)
            return root.p;

        Point2D pmin = root.p;
        double rmin = pmin.distanceTo(p);
        
        Stack<Node> nodesToCheck = new Stack<>();
        
        addChildNodesToStack(p, root, nodesToCheck);

        boolean reverseMove = false;
        
        while (!nodesToCheck.isEmpty()) {
            
            Node x = nodesToCheck.pop();
            
            if (reverseMove && getAxedDist(x.parent, p) > rmin)
                continue;
            
            double r = p.distanceTo(x.p);
            if (r < rmin) {
                rmin = r;
                pmin = x.p;
            }
            
            reverseMove = !addChildNodesToStack(p, x, nodesToCheck);
            
        }
        return pmin;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {

        KdTree ptree = new KdTree();
        System.out.println("Empty: " + ptree.isEmpty());
        ptree.insert(new Point2D(0.5, 0.5));
        ptree.insert(new Point2D(0.5, 0.5));
        ptree.insert(new Point2D(0.5, 0.6));
        ptree.insert(new Point2D(0.5, 0.7));
        ptree.insert(new Point2D(0.5, 0.8));
        ptree.insert(new Point2D(0.1, 0.5));
        ptree.insert(new Point2D(0.8, 0.5));
        ptree.insert(new Point2D(0.1, 0.1));
        System.out.println("Empty: " + ptree.isEmpty());
        System.out.println("Size: " + ptree.size());
        System.out.println("Nearest to start: " + ptree.nearest(new Point2D(0.0, 0.0)));
        Point2D p1 = new Point2D(0.5, 0.5);
        System.out.println("Nearest to p1: " + ptree.nearest(p1));
        Point2D p2 = new Point2D(0.5, 0.56);
        System.out.println("Nearest to p2: " + ptree.nearest(p2));
        Point2D p3 = new Point2D(0.7, 0.8);
        System.out.println("Nearest to p3: " + ptree.nearest(p3));
        System.out.println("Contains #1: " + ptree.contains(new Point2D(0.0, 0.0)));
        System.out.println("Contains #2: " + ptree.contains(new Point2D(0.5, 0.5)));
        // System.out.print("Range #1: ");
        // for (Point2D p : ptree.range(new RectHV(0.001, 0.001, 0.002, 0.002)))
        //     System.out.print(p.toString() + "; ");
        // System.out.print("\n");
        // System.out.print("Range #2: ");
        // for (Point2D p : ptree.range(new RectHV(0.05, 0.05, 0.15, 0.15)))
        //     System.out.print(p.toString() + "; ");
        // System.out.print("\n");
        // System.out.print("Range #3: ");
        // for (Point2D p : ptree.range(new RectHV(0.25, 0.35, 0.65, 0.75)))
        //     System.out.print(p.toString() + "; ");
        // System.out.print("\n");
    }
}
