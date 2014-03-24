/*******
  * Author:        Paul Rodriguez
  * Created:       3/6/2014
  * Last Updated:  3/7/2014
  ****/
public class Solver 
{
    private SearchNode res;
    
    private class SearchNode implements Comparable<SearchNode>
    {
        private Board  board;
        private SearchNode  prev;
        private int    moves;
        private int    priority;
        
        private SearchNode(Board initial, SearchNode previous)
        {
            board = initial;
            prev = previous;
            if (prev == null)
            {
                moves = 0;
            }
            else
            {
                moves = prev.moves+1;
            }
            priority = board.manhattan() + moves;
        }
        
        public Board getBoard()
        {
            return board;
        }
        public int compareTo(SearchNode other)
        {
            return this.priority - other.priority;
        }
        
        
    }
    
    public Solver(Board initial)
    {
        //StdOut.println(initial.toString());
        if (initial.isGoal())
        {
            res = new SearchNode(initial, null);
        }
        else
        {
            MinPQ<SearchNode> m = new MinPQ<SearchNode>();
            MinPQ<SearchNode> twin = new MinPQ<SearchNode>();
            m.insert(new SearchNode(initial, null));
            twin.insert(new SearchNode(initial.twin(), null));
            
            boolean notFound = true;
            
            while (notFound)
            {
                //  get smallest in priority queue
                SearchNode smallest = m.delMin();
                SearchNode twinSmallest = twin.delMin();
                //StdOut.println("smallest: " + smallest.board.toString());
                if (smallest.getBoard().isGoal()) 
                {
                    res = smallest;
                   return;
                }
                else if (twinSmallest.getBoard().isGoal())
                {
                    res = null;
                    return;
                }
                //  get all neighbors of smallest  node obtained above
                for (Board neighbor : smallest.getBoard().neighbors())
                {
                    //  peform critical optimization
                    if (smallest.prev == null)
                    {
                        m.insert(new SearchNode(neighbor, smallest));
                    }
                    else
                    {
                        if (!neighbor.equals(smallest.prev.getBoard()))
                        {
                            m.insert(new SearchNode(neighbor, smallest));
                        }
                    }
                }
                
                //  run puzzle for twin
                for (Board neighbor : twinSmallest.getBoard().neighbors())
                {
                    //  peform critical optimization
                    if (twinSmallest.prev == null)
                    {
                        twin.insert(new SearchNode(neighbor, twinSmallest));
                    }
                    else
                    {
                        if (!neighbor.equals(twinSmallest.prev.getBoard()))
                        {
                            twin.insert(new SearchNode(neighbor, twinSmallest));
                        }
                    }
                }
            }
        }
    }
    
    public boolean isSolvable()
    {
        if (res != null)
            return true;
        return false;
    }
    
    public int moves()
    {
        if (res != null)
            return res.moves;
        return -1;
    }
    
    public Iterable<Board> solution()
    {
        if (res == null)
            return null;
        Stack<Board> nodes = new Stack<Board>();
        SearchNode s = res;
        while (s != null)
        {
            nodes.push(s.board);
            s = s.prev;
        }
        
        return nodes;
    }
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
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