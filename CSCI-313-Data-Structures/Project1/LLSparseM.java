
public class LLSparseM implements SparseM 
{
	private int SIZE;
	private int ROWS=0;
	private int COLUMNS=0;
	private int numofElements=0;
	private LLSparseVecNode rowHead;//FIRST ROW
	private LLSparseVecNode columnHead;//FIRST COLUMN
	
	public LLSparseM(int newRows, int newColumns)
	{
		ROWS=newRows;
		COLUMNS=newColumns;
	}

	public int nrows()
	{
		return ROWS;
	}

	public int ncols()
	{
		return COLUMNS;
	}

	public int numElements()
	{
		return numofElements;
	}

	public int getElement(int row, int column)
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

	@Override
	public void clearElement(int ridx, int cidx)
	{
		// TODO Auto-generated method stub

	}

	public void setElement(int ridx, int cidx, int value)
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

	public int[] getRowIndices()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getColIndices()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getOneRowColIndices(int ridx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getOneRowValues(int ridx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getOneColRowIndices(int cidx)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int[] getOneColValues(int cidx)
	{
		// TODO Auto-generated method stub
		return null;
	}
//===========================================PART 2==============================================================
	public SparseM addition(SparseM otherM) throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.ncols(),this.nrows());
		return Answer;
	}

	public SparseM substraction(SparseM otherM)throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.ncols(),this.nrows());
		return Answer;
	}

	public SparseM multiplication(SparseM otherM)throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.ncols(),this.nrows());
		return Answer;
	}

//=====================Other Methods I made-===================================================================
	private boolean outofBounds(int row, int column)
	{
		if (row > ROWS || row < 0)
		{
			return false;
		}
		if (column > COLUMNS || column < 0)
		{
			return false;
		}
		return true;
	}

}