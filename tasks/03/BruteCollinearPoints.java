/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class BruteCollinearPoints {
    
    private Point[] points;
    private int segCnt = 0;
    private Node first = null;
    private class Node {
        public Node next;
        public LineSegment seg;
    }
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Got null argument to constructor");
        this.points = points;
        int pcnt = points.length;
        for (int i = 0; i < pcnt; ++i) {
            if (points[i] == null)
                throw new IllegalArgumentException("Got null Point inside input array");
        }
        for (int ip = 0; ip < pcnt - 3; ++ip) {
            for (int iq = ip + 1; iq < pcnt - 2; ++iq) {
                double slope1 = points[ip].slopeTo(points[iq]);
                if (slope1 == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("Got repeated Points inside input arrays");
                for (int ir = iq + 1; ir < pcnt - 1; ++ir) {
                    double slope2 = points[ip].slopeTo(points[ir]);
                    if (slope2 == Double.NEGATIVE_INFINITY)
                        throw new IllegalArgumentException("Got repeated Points inside input arrays");
                    if (slope1 != slope2)
                        continue;
                    for (int is = ir + 1; is < pcnt; ++is) {
                        double slope3 = points[ip].slopeTo(points[is]);
                        if (slope3 == Double.NEGATIVE_INFINITY)
                            throw new IllegalArgumentException("Got repeated Points inside input arrays");
                        if (slope1 == slope3) {
                            ++segCnt;
                            int imin = getMin4PointIdx(ip, iq, ir, is);
                            int imax = getMax4PointIdx(ip, iq, ir, is);
                            Node temp = new Node();
                            temp.seg = new LineSegment(points[imin], points[imax]);
                            temp.next = first;
                            first = temp;
                        }
                    }
                }
            }
        }
        
    }

    // find index of maximum point among given 4 indexes
    private int getMax4PointIdx(int p1, int p2, int p3, int p4) {
        int maxIdx = p1;
        maxIdx = (points[maxIdx].compareTo(points[p2]) > 0) ? maxIdx : p2;
        maxIdx = (points[maxIdx].compareTo(points[p3]) > 0) ? maxIdx : p3;
        maxIdx = (points[maxIdx].compareTo(points[p4]) > 0) ? maxIdx : p4;
        return maxIdx;
    }
    private int getMin4PointIdx(int p1, int p2, int p3, int p4) {
        int minIdx = p1;
        minIdx = (points[minIdx].compareTo(points[p2]) < 0) ? minIdx : p2;
        minIdx = (points[minIdx].compareTo(points[p3]) < 0) ? minIdx : p3;
        minIdx = (points[minIdx].compareTo(points[p4]) < 0) ? minIdx : p4;
        return minIdx;
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
        
    }
}
