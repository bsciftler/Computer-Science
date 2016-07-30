public class SudokuBoard {
	final static int SIZE=4;
	public static SudokuSquare [][] Board = new SudokuSquare [SIZE][SIZE];
	public SudokuBoard (SudokuLinkedList game)
	{
	SudokuSquareNode current;
		while ((current=game.getNext())!=null)
		{
Board[current.getData().getRow()][current.getData().getColumn()]=current.getData();
		}
	}//End of Board set up
	public boolean isValid(int row, int column, int value)
	{
		//Check for Array out of Bounds...
		if (!(row>=0 && row<=SIZE-1))
		{
			return false;
		}
		if (!(column>=0 && column<=SIZE-1))
		{
			return false;
		}
		
		//Check if the value is even valid to being with...
		if (!(value>=1 && value <=4))
		{
			return false;
		}
		
		//Check if this is a locked square or not...
		if (Board[row][column].isLocked())//if it is true(locked)...
		{
			return false;
		}
		
		//check Row
		for (int j=0;j<SIZE;j++)
		{
			if (Board[row][j].getValue()==value)
			{
				//System.out.println(Board[row][j].getValue() + " " + value);
				return false;
			}
		}
		
		//check column
		for (int k=0;k<SIZE;k++)
		{
			if(Board[k][column].getValue()==value)
			{
				return false;
			}
		}
//Always Row;Column!!
		//Check Quadrant 1
		//Always Row;Column!!
				//Check Quadrant 1
				//Quad (0,0) (0,1) (1,0) (1,1)
				if (row>=0 && row<=1 && column>=0 && column<=1)
				{	
					if(!Quad1(row,column,value))
					{
						return false;
					}
				}
				//Check Quadrant 2
		//Quad (2,0) (2,1) (3.0) (3,1)
				if (row>=2 && row<=3 && column>=0 && column<=1)
				{	
					if(!Quad2(row,column,value))
					{
						return false;
					}
				}
			
				//Check Quadrant 3
		// (0,2) (0,3) (1,2) (1,3)
				if (row>=0 && row<=1 && column>=2 && column<=3)
				{	
					if(!Quad3(row,column,value))
					{
						return false;
					}
				}

				//Check Quadrant 4
		//(2,2) (2,3) (3,2) (3,3) 
				if (row>=2 && row<=3 && column>=2 && column<=3)
				{	
					if(!Quad4(row,column,value))
					{
						return false;
					}
				}
		//If it passes all this, well it seems legitimate.
		return true;
	}
	
	public void enterMove(int row, int column, int value) throws SudokuException
	{
		if (isValid(row,column,value))
		{
			SudokuBoard.getSquare(row,column).setValue(value);
		}
		else 
		{
			throw new SudokuException();
		}
	}
	
	public void reset()
	{
		for (int i=0;i<SIZE;i++)
		{
			for (int j=0;j<SIZE;j++)
			{
				if (Board[i][j].isLocked()==false)
				{
					Board[i][j].setValue(0);
				}
			}
		}
	}
	
	public boolean isFull()
	{	
		for (int i=0;i<SIZE;i++)
		{
			for (int j=0;j<SIZE;j++)
			{
				if (Board[i][j].getValue()==0)
				{
					return false;
				}
			}
		}
		return true;
	}

	public static SudokuSquare getSquare(int row, int column)
	{
		return Board[row][column];
	}
	
	public void print()
	{
		System.out.println("Sudoku Progress");
		for (int i=0;i<SIZE;i++)
		{
			for (int j=0;j<SIZE;j++)
			{
				System.out.print(Board[i][j].getValue() + " ");
			}
			System.out.println(" ");
		}
	}
	
//--------------------------_QUADRANT CHECKS----------------------------------------------------------
	public boolean Quad1(int row, int column, int value)
	{
		for (int i=0;i<=1;i++)
			{
				for (int j=0;j<=1;j++)
				{
					if (Board[i][j].getValue()==value)
					{
						return false;
					}
				}
			}
		return true;
	}
	
	public boolean Quad2(int row, int column, int value)
	{
		for (int i=2;i<=3;i++)
		{
			for (int j=0;j<=1;j++)
			{
				if (Board[i][j].getValue()==value)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean Quad3(int row, int column, int value)
	{
		for (int i=0;i<=1;i++)
		{
			for (int j=2;j<=3;j++)
			{
				if (Board[i][j].getValue()==value)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean Quad4(int row, int column, int value)
	{
		for (int i=2;i<=3;i++)
		{
			for (int j=2;j<=3;j++)
			{
				if (Board[i][j].getValue()==value)
				{
					return false;
				}
			}
		}
		return true;
	}
}
