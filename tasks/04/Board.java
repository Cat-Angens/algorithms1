/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {
    
    private int[][] tiles;
    private int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles_) {
        n = tiles_.length;
        tiles = new int[n][n];
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col)
                tiles[row][col] = tiles_[row][col];
    }

    // string representation of this board
    public String toString() {
        String str = "" + n + "\n";
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col)
                str += " " + tiles[row][col];
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        
        int wrongPosCnt = 0;
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {
                int idx = row * n + col + 1;
                if (tiles[row][col] == 0)
                    continue;
                if (tiles[row][col] != idx)
                    ++wrongPosCnt;
            }
        
        return wrongPosCnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        
        int dist = 0;
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {
                
                if (tiles[row][col] == 0)
                    continue;
                int goalRow = tiles[row][col] / n;
                int goalCol = tiles[row][col] % n;
                
                dist += Math.abs(row - goalRow);
                dist += Math.abs(col - goalCol);
            }
        
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {

        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {

                int idx = row * n + col + 1;
                if (idx != tiles[row][col] && tiles[row][col] != 0)
                    return false;
            }
        
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this)
            return true;

        if (y.getClass() != this.getClass()) {
            return false;
        }

        final Board that = (Board) y;
        if (this.n != that.n) {
            return false;
        }

        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {

                if (this.tiles[row][col] != that.tiles[row][col])
                    return false;
            }

        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborsStack = new Stack<Board>();
        
        int rowZ = -1;
        int colZ = -1;
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {
                if (tiles[row][col] == 0) {
                    rowZ = row;
                    colZ = col;
                    break;
                }
            }
        
        if (rowZ != 0) { 
            int [][]tilesNeigh = new int[n][n];
            for (int row = 0; row < n; ++row)
                for (int col = 0; col < n; ++col)
                    tilesNeigh[row][col] = tiles[row][col];
            tilesNeigh[rowZ    ][colZ] = tiles[rowZ - 1][colZ];
            tilesNeigh[rowZ - 1][colZ] = 0;
            neighborsStack.push(new Board(tilesNeigh));
        }

        if (rowZ != n - 1) {
            int [][]tilesNeigh = new int[n][n];
            for (int row = 0; row < n; ++row)
                for (int col = 0; col < n; ++col)
                    tilesNeigh[row][col] = tiles[row][col];
            tilesNeigh[rowZ    ][colZ] = tiles[rowZ + 1][colZ];
            tilesNeigh[rowZ + 1][colZ] = 0;
            neighborsStack.push(new Board(tilesNeigh));
        }

        if (colZ != 0) {
            int [][]tilesNeigh = new int[n][n];
            for (int row = 0; row < n; ++row)
                for (int col = 0; col < n; ++col)
                    tilesNeigh[row][col] = tiles[row][col];
            tilesNeigh[rowZ][colZ    ] = tiles[rowZ][colZ - 1];
            tilesNeigh[rowZ][colZ - 1] = 0;
            neighborsStack.push(new Board(tilesNeigh));
        }

        if (colZ != n - 1) {
            int [][]tilesNeigh = new int[n][n];
            for (int row = 0; row < n; ++row)
                for (int col = 0; col < n; ++col)
                    tilesNeigh[row][col] = tiles[row][col];
            tilesNeigh[rowZ][colZ    ] = tiles[rowZ][colZ + 1];
            tilesNeigh[rowZ][colZ + 1] = 0;
            neighborsStack.push(new Board(tilesNeigh));
        }
            
        return neighborsStack;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        
        int [][]tilesTwin = new int[n][n];
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col)
                tilesTwin[row][col] = tiles[row][col];
        int rowSwap = 0;
        if (tiles[0][0] == 0 || tiles[0][1] == 0)
            ++rowSwap;
        tilesTwin[rowSwap][0] = tiles[rowSwap][1];
        tilesTwin[rowSwap][1] = tiles[rowSwap][0];
        
        return new Board(tilesTwin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
