/*******
  * Author:        Paul Rodriguez
  * Created:       3/6/2014
  * Last Updated:  3/6/2014
  * 
  *******/

import java.util.Arrays;

public class Board 
{
    private int[][] boardBlock;
    int dim;
    int manhattanDistance = -1;
    public Board(int[][] blocks)
    {
        for (int i = 0; i < blocks.length; i++)
        {
            for (int j = 0; j < blocks.length; j++)
            {
                this.boardBlock[i][j] = blocks[i][j];
            }
        }
        
        dim = blocks.length;
    }
    
    public int dimension()
    {
        return this.dim;
    }
    
    public int hamming()
    {
        int outOfPlace = 0;
        int desiredValue = 1;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if(blocks[i][j] != desiredValue)
                {
                    outOfPlace++;
                }
                desiredValue++;
            }
        }
        
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
                row = (this.boardBlock[i][j] - 1) / dim;
                col = (this.boardBlock[i][j] - 1) % dim;
                mDistance += Math.abs(i - row) + Math.abs(j - col);
            }
        }
        this.manhattanDistance = mDistance;
        return mDistance;
    }
    
    public boolean isGoal()
    {
        if (boardBlock[dim-1][dim-1] != 0)
        {
            return false;
        }
        
        int desiredValue = 1;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (this.boardBlock[i][j] != desiredValue)
                {
                    return false;
                }
                desiredValue++;
            }
        }
        
        return true;
    }
    
    public boolean equals (Object y)
    {
        if (y == this) return true;
        if(this.getClass() != y.getClass()) return false;
        
        Board other = (Board) y;
        if(dim != other.length) return false;
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; i++)
            {
                if (this.boardBlock[i][j] != other[i][j])
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors()
    {
    
    }
    
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}