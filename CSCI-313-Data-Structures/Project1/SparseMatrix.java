public class SparseMatrix 
{
	private class MatrixBox <E> 
	{

	}
	
	private int SIZE;
	private int numRows=0;
	private int numColumns=0;
	private int numofElements=0;
	private RowNode rowHead;//FIRST ROW
	private ColumnNode columnHead;//FIRST COLUMN
	
	public SparseMatrix(int length)
	{
		length=SIZE;
	}
	
	public int getSize(){return SIZE;}
	public RowNode getRHead() {return rowHead;}
	public ColumnNode getCHead() {return columnHead;}

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
		Node current=null;
		RowNode Rcurrent = rowHead;
		ColumnNode CCurrent = columnHead;
		while (Rcurrent!=null)
		{
			if(Rcurrent.getRowID()==row)
			{
				
			}
		}
		return current;
	}
	
	
	public void addElement(int row, int column, int ID, int value)
	{
		if (value==0)
		{
			
		}
		
		if (numofElements==0)
		{
			rowHead=new RowNode (row);
			columnHead=new ColumnNode (column);
			rowHead.setNextNode(new Node (ID, row, column, value));
			columnHead.setNextNode(new Node (ID, row, column, value));
			return;
		}
		if (numofElements>=1)
		{
			
		}
		new Node (ID, row, column, value);
		new RowNode (row);
		new ColumnNode (column);
	}
	
	public int [] getOneRowID()
	{
		RowNode current=rowHead;
		int [] answer=new int [numRows];
		int counter=0;
		while(current!=null)
		{
		// answer[counter]=current.getNextNode().getRowID();
		//	current=current.getNextNode();
		}
		return answer;
	}

	public int [] getOneRowValue()
	{
		RowNode current=rowHead;
		int [] answer=new int [numRows];
		int counter=0;
		while(current!=null)
		{
			//answer[counter]=current.getNextNode().getRowID();
			//current=current.getNextNode();
		}
		return answer;
	}
	
	public int [] getOneColumnID()
	{
		RowNode current=rowHead;
		int [] answer=new int [numRows];
		int counter=0;
		while(current!=null)
		{
			//answer[counter]=current.getNextNode().getRowID();
			//current=current.getNextNode();
		}
		return answer;
	}
	
	public int [] getOneColumnValue()
	{
		RowNode current=rowHead;
		int [] answer=new int [numRows];
		int counter=0;
		while(current!=null)
		{
			answer[counter]=current.getNextNode().getRowID();
			current=current.getNextNode();
		}
		return answer;
	}
	
	public int [] getAllElements()
	{
		int [] All=new int [numofElements];
		return All;
	}
//=====================================FINAL PART================================
	
	public SparseMatrix addition (SparseMatrix B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
	public SparseMatrix subtraction (SparseMatrix B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
	public SparseMatrix multiplication (SparseMatrix B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		SparseMatrix Answer=new SparseMatrix(this.getSize());
		return Answer;
	}
	
}