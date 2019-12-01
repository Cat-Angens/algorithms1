/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private MinPQ<SearchNode> priorityQueue;
    private int n;
    private boolean solvable;
    
    private class SearchNode implements Comparable<SearchNode> {
        Board currBoard;
        SearchNode prevNode;
        int moves;
        public int compareTo(SearchNode that) {
            return Integer.compare(this.currBoard.manhattan() + this.moves,
                                   that.currBoard.manhattan() + that.moves);
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        
        if (initial == null)
            throw new IllegalArgumentException("Got null as Board object\n");
        n = initial.dimension();
        int [][]tilesGoal = new int[n][n];
        for (int row = 0; row < n; ++row)
            for (int col = 0; col < n; ++col) {
                int idx = row * n + col + 1;
                tilesGoal[row][col] = idx;
            }
        tilesGoal[n - 1][n - 1] = 0;
        Board goal = new Board(tilesGoal);
        
        solvable = true;

        priorityQueue = new MinPQ<SearchNode>();
        SearchNode start = new SearchNode();
        start.currBoard = initial;
        start.prevNode = null;
        start.moves = 0;
        priorityQueue.insert(start);

        MinPQ<SearchNode> priorityQueueSwapped = new MinPQ<SearchNode>();
        SearchNode startSwapped = new SearchNode();
        startSwapped.currBoard = initial.twin();
        startSwapped.prevNode = null;
        startSwapped.moves = 0;
        priorityQueueSwapped.insert(startSwapped);

        while (true) {
            
            SearchNode minNode = priorityQueue.min();
            if (minNode.currBoard.equals(goal))
                break;

            priorityQueue.delMin();
            
            for (Board neighbor : minNode.currBoard.neighbors()) {
                if (minNode.prevNode != null && neighbor.equals(minNode.prevNode.currBoard))
                    continue;
                SearchNode newNode = new SearchNode();
                newNode.currBoard = neighbor;
                newNode.prevNode = minNode;
                newNode.moves = minNode.moves + 1;
                priorityQueue.insert(newNode);
            }

            SearchNode minNodeSwapped = priorityQueueSwapped.min();
            if (minNodeSwapped.currBoard.equals(goal)) {
                solvable = false;
                break;
            }
            priorityQueueSwapped.delMin();

            for (Board neighbor : minNodeSwapped.currBoard.neighbors()) {
                if (minNodeSwapped.prevNode != null && neighbor.equals(minNodeSwapped.prevNode.currBoard))
                    continue;
                SearchNode newNode = new SearchNode();
                newNode.currBoard = neighbor;
                newNode.prevNode = minNodeSwapped;
                newNode.moves = minNodeSwapped.moves + 1;
                priorityQueueSwapped.insert(newNode);
            }
            
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (solvable)
            return priorityQueue.min().moves;
        return 0;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solutionStack = new Stack<>();
        SearchNode node = priorityQueue.min();
        solutionStack.push(node.currBoard);
        while (node.prevNode != null) {
            node = node.prevNode;
            solutionStack.push(node.currBoard);
        }
        return solutionStack;
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
