public class SparseMatrix 
{
	private int SIZE;
	private int numRows=0;
	private int numColumns=0;
	private int numofElements=0;
	private int rowHead;//FIRST ROW
	private int columnHead;//FIRST COLUMN
	
	public SparseMatrix(int length)
	{
		length=SIZE;
	}
	
	public int getSize(){return SIZE;}
	public int getRHead() {return rowHead;}
	public int getCHead() {return columnHead;}

	private boolean outofBounds(int row, int column)
	{
		if (row > numRows || row < 0)
		{
			return false;
		}
		if (column > numColumns || column < 0)
		{
			return false;
		}
		return true;
	}
	
	public int getElement (int row, int column)
	{
		if (outofBounds(row,column))
		{
			return 0;
		}
		if (find(row, column)==null)
		{
			return 0;
		}
		else
		{
			return find(row,column).getValue();
		}
		
	}
		
	private Node find(int row, int column)
	{
//THIS WORKS ASSUMING THE LIST IS SORTED IN ASCENDING ORDER!!
		Node Rcurrent = rowHead;
		Node CCurrent = columnHead;
		while (Rcurrent!=null && Current!=null)
		{
			if(current.getID()==newID)
			{
				return current;
			} 
			else if(current.getID()< newID)
			{
				current = current.getNext();
			} 
			else 
			{
				current = null;
			}
		}
		return current;
	}
	
	
	public void addElement(int row, int column, int ID, int value)
	{
		new Node (row, column, value);
		new RowNode (row);
		new ColumnNode (column);
	}
	
	public int [] getOneRowID()
	{
		int [] answer=new int [numRows];
		return answer;
	}

	public int [] getOneRowValue()
	{
		int [] answer=new int [numRows];
		return answer;
	}
	
	public int [] getOneColumnID()
	{
		int [] answer=new int [numColumns];
		return answer;
	}
	
	public int [] getOneColumnValue()
	{
		int [] answer=new int [numColumns];
		return answer;
	}
	
	public int [] getAllElements()
	{
		int [] All=new int [numofElements];
		return All;
	}
//=====================================FINAL PART================================
	
	public SparseMatrix addition (SparseMatrix B)
	{
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
	public SparseMatrix subtraction (SparseMatrix B)
	{
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
	public SparseMatrix multiplication (SparseMatrix B)
	{
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
}