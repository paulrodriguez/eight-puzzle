/*******
  * Author:        Paul Rodriguez
  * Created:       3/6/2014
  * Last Updated:  3/7/2014
  * 
  *******/



public class Board 
{
    private int[][] boardBlock;
    private int dim;
    private int manhattanDistance = -1;
    private int hammingDistance = -1;
    public Board(int[][] blocks)
    {
        if (blocks == null)
            throw new RuntimeException();
        
        this.boardBlock = copyBoard(blocks);
  
        dim = blocks.length;
    }
    
    public int dimension()
    {
        return this.dim;
    }
    
    public int hamming()
    {
        if (hammingDistance >= 0)
            return hammingDistance;
        int outOfPlace = 0;
        int desiredValue = 1;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (boardBlock[i][j] != desiredValue)
                {
                    outOfPlace++;
                }
                desiredValue++;
            }
        }
        hammingDistance = outOfPlace - 1;
        return outOfPlace - 1;
    }
    
    public int manhattan()
    {
        if (manhattanDistance >= 0)
        {
            return this.manhattanDistance;
        }
        int mDistance = 0;
        int row = 0;
        int col = 0;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (this.boardBlock[i][j] != 0)
                {
                    row = (this.boardBlock[i][j] - 1) / dim;
                    col = (this.boardBlock[i][j] - 1) % dim;
                    mDistance += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        
        this.manhattanDistance = mDistance;
        return mDistance;
    }
    
    public Board twin()
    {
        int[][] twin = copyBoard(boardBlock);
        if (dim <= 1)
        {
            return new Board(twin);
        }
        int row = 0;
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (twin[i][j] == 0)
                {
                    row = i;
                    break;
                }
            }
        }
        int temp = 0;
        //  if empty value is at first row
        if (row == 0)
        {
            temp = twin[dim - 1][0];
            twin[dim - 1][0] = twin[dim - 1][1];
            twin[dim - 1][1] = temp;
        }
        else
        {
            temp = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = temp;
        }
        
        return new Board(twin);
    }
    
    public boolean isGoal()
    {
        //StdOut.println("inside isGoal");
        if (boardBlock[dim-1][dim-1] != 0)
        {
            return false;
        }
        
        int desiredValue = 1;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (i == dim -1 && j == dim -1)
                {
                    //StdOut.println("last value: " + this.boardBlock[i][j]);
                    if (boardBlock[i][j] != 0)
                        return false;
                }
                else if (this.boardBlock[i][j] != desiredValue)
                {
                    return false;
                }
                desiredValue++;
            }
        }
        
        return true;
    }
    
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        
        Board other = (Board) y;
        
        if (dim != other.dimension()) return false;
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (this.boardBlock[i][j] != other.boardBlock[i][j])
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private int[][] copyBoard(int[][] toCopy)
    {
        int len = toCopy.length;
        int[][] copy = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++)
                copy[i][j] = toCopy[i][j];
        }
        return copy;
    }
    
    public Iterable<Board> neighbors()
    {
        Queue<Board> neighbors = new Queue<Board>();
        
        int row = 0;
        int col = 0;
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (this.boardBlock[i][j] == 0)
                {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        
        if (row > 0)
        {
            int[][] swapWithTop = copyBoard(this.boardBlock);
            int temp = swapWithTop[row][col];
            swapWithTop[row][col] = swapWithTop[row - 1][col];
            swapWithTop[row - 1][col] = temp;
            neighbors.enqueue(new Board(swapWithTop));
        }
        if (row < dim - 1)
        {
            int[][] swapWithBottom = copyBoard(this.boardBlock);
            int temp = swapWithBottom[row][col];
            swapWithBottom[row][col] = swapWithBottom[row + 1][col];
            swapWithBottom[row + 1][col] = temp;
            neighbors.enqueue(new Board(swapWithBottom));
        }
        if (col > 0)
        {
            int[][] swapWithLeft = copyBoard(this.boardBlock);
            int temp = swapWithLeft[row][col];
            swapWithLeft[row][col] = swapWithLeft[row][col - 1];
            swapWithLeft[row][col - 1] = temp;
            neighbors.enqueue(new Board(swapWithLeft));
        }
        if (col < dim - 1)
        {
            int[][] swapWithRight = copyBoard(this.boardBlock);
            int temp = swapWithRight[row][col];
            swapWithRight[row][col] = swapWithRight[row][col + 1];
            swapWithRight[row][col + 1] = temp;
            neighbors.enqueue(new Board(swapWithRight));
        }
        
        return neighbors;
    }
    
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        int N = dim;
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", boardBlock[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}