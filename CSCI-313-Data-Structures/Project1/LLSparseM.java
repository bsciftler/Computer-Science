import javax.swing.JOptionPane;

public class LLSparseM implements SparseM 
{
	private int ROWS=0;//Pretend its FINAL
	private int COLUMNS=0;//Pretend its FINAL
	private int numofElements=0;
	private SparseMNode rowHead;//FIRST ROW
	private SparseMNode columnHead;//FIRST COLUMN
	
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
		//Out of Bounds?
		if (outofBounds(row,column))
		{
			JOptionPane.showMessageDialog(null, "OUT OF BOUNDS!!!");
			return 0;
		}
		
		//Find the Element
		if (findRowID(row)==null)
		{
			JOptionPane.showMessageDialog(null, "This is not the element you are looking for...Move along");
			return 0;//It doesn't exist.
		}
		else
		{
			if(findColumnID(column)==null)
			{
				JOptionPane.showMessageDialog(null, "This is not the element you are looking for...Move along");
				return 0;//It doesn't exist.
			}
			else
			{
				return findColumnID(column).getValue();
			}
		}
	}

	public void clearElement(int ridx, int cidx)
	{
		//Out of Bounds?
		if (outofBounds(ridx,cidx))
		{
			JOptionPane.showMessageDialog(null, "OUT OF BOUNDS!!!");
			return;
		}
		if (findRowID(ridx)==null)
		{
			JOptionPane.showMessageDialog(null, "NO SUCH ELEMENT EXISTS!!!");
		}
		else
		{
			if (findColumnID(cidx)==null)
			{
				JOptionPane.showMessageDialog(null, "NO SUCH ELEMENT EXISTS!!!");
				return;
			}
			else //The fun part of deleting the element!!!
			{
				
			}
		}
		
	}
	
	public SparseMNode getRowHead()
	{
		return rowHead;
	}
	
	public SparseMNode getColumnHead()
	{
		return columnHead;
	}

	public void setElement(int ridx, int cidx, int value)
	{
		
		if (outofBounds(ridx,cidx))
		{
			JOptionPane.showMessageDialog(null, "INVALID ROW AND/OR COLUMN, OUT OF BOUNDS!!");
			return;
		}
		
		if (value==0)
		{
			clearElement(ridx,cidx);
			return;
		}
		
	//Are you attempting to replace the value in an existing Node?
		if (findRowID(ridx)!=null && findColumnID(cidx)!=null)
		{
			findColumnID(cidx).setValue(value);
			return;
		}
		
		if (numofElements==0)
		{
			SparseMNode FirstElement=new SparseMNode(ridx,cidx,value,null,null);
			rowHead=new SparseMNode(ridx,null,FirstElement);//ROW CONS: ROWID, NEXT Row, NEXT Column
			columnHead=new SparseMNode(FirstElement,null,cidx);//COLUMN CONS: NEXT ROW, NEXT COLUMN, COLUMN ID
			++numofElements;
			return;
		}
		
		if (numofElements>=1)
		{
			insert(ridx,cidx,value);
			++numofElements;
			return;
		}
	}

	public int [] getRowIndices()
	{
		SparseMNode current = rowHead;
		int [] Rows=new int [rowCount()];//Or use COLUMN/ROW?
		if (current==null)
		{
			return Rows;
		}
		int counter=0;
		while (current!=null)
		{
			Rows[counter]=current.getRowID();
			current=current.getNextRow();
			++counter;
		}
		return Rows;
	}

	public int[] getColIndices()
	{
		SparseMNode current= columnHead;
		int [] ColIndex=new int[columnCount()];//or use COLUMN/ROW?
		if (current==null)
		{
			return ColIndex;
		}
		int counter=0;
		while (current!=null)
		{
			ColIndex[counter]=current.getColumn();
			current=current.getNextColumn();
			++counter;
		}
		return ColIndex;
	}

	public int[] getOneRowColIndices(int ridx)
	{
		int [] RowIndex=new int[columnCount()];//USE COLUMN/ROW?
		int counter=0;
		SparseMNode current= findRowID(ridx).getNextColumn();
		if (current==null)
		{
			return RowIndex;
		}
		while (current!=null)
		{
			RowIndex[counter]=current.getColumn();
			current=current.getNextColumn();
			++counter;
		}
		return RowIndex;
	}

	public int[] getOneRowValues(int ridx)
	{
		SparseMNode current = findRowID(ridx).getNextColumn();
		int [] RowVals=new int [columnCount()];///USE COLUMN/ROW?
		if (current==null)
		{
			return RowVals;
		}
		
		int counter=0;
		while (current!=null)
		{
			RowVals[counter]=current.getValue();
			current=current.getNextColumn();
			++counter;
		}
		return RowVals;
	}

	public int[] getOneColRowIndices(int cidx)
	{
		SparseMNode current = findColumnID(cidx).getNextRow();
		int [] ColumnRowID=new int [rowCount()];//USE ROW/COLUMN
		if (current==null)
		{
			return ColumnRowID;
		}
		int counter=0;
		while (current!=null)
		{
			ColumnRowID[counter]=current.getRowID();
			current=current.getNextRow();
			++counter;
		}
		return ColumnRowID;
	}

	public int[] getOneColValues(int cidx)
	{
		SparseMNode current = findColumnID(cidx).getNextRow();
		int [] ColumnRowVals=new int [rowCount()];///USE COLUMN/ROW?
		if (current==null)
		{
			return ColumnRowVals;
		}
		int counter=0;
		while (current!=null)
		{
			ColumnRowVals[counter]=current.getValue();
			current=current.getNextRow();
			++counter;
		}
		return ColumnRowVals;
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
	
	public void print()
	{
		if (numofElements==0)
		{
			for (int i=0;i<ROWS;i++)
			{
				for (int j=0;j<COLUMNS;j++)
				{
					System.out.println("0");
				}
				System.out.println(" ");
			}
		}
		SparseMNode RowPrint=rowHead;
//THIS ONLY WORKS FOR THE FIRST ROW!!!!!!!!!!!!!!!!!!
		if (numofElements >= 1)
		{
			for (int i=0;i<ROWS;i++)
			{
				for (int j=0;j<COLUMNS;j++)
				{
					if (RowPrint!=null && RowPrint.getColumn()==j)
					{
						System.out.print(RowPrint.getValue());
						RowPrint=RowPrint.getNextColumn();
					}
					else
					{
						System.out.print("0");
					}
				}
				System.out.println(" ");
			}
		}
		return;
	}
	
	
	private boolean outofBounds(int row, int column)
	{
		if (row > ROWS || row < 0 || column < 0 || column > COLUMNS)
		{
			return true;
		}
		return false;
	}
	
	private SparseMNode findRowID (int rowSearch)
	{
		SparseMNode current= rowHead;
		while (current!=null)
		{
			if (current.getRowID()==rowSearch)
			{
				return current;
			}
			current=current.getNextRow();
		}
		return current;//return null if thats the case
	}
	
	private SparseMNode findColumnID (int columnSearch)
	{
		SparseMNode current= columnHead;
		while (current!=null)
		{
			if (current.getColumn()==columnSearch)
			{
				return current;
			}
			current=current.getNextColumn();
		}
		return current; //return null if thats the case
	}
	
	private int rowCount()
	{
		SparseMNode current=rowHead;
		int count=0;
		while (current!=null)
		{
			current=current.getNextRow();
			++count;
		}
		return count;
	}
	
	private int columnCount()
	{
		SparseMNode current=columnHead;
		int count=0;
		while (current!=null)
		{
			current=current.getNextColumn();
			++count;
		}
		return count;
	}
	
	//STILL IN PROGRESS
	private void insert(int rowID, int columnID, int value)
	{
		SparseMNode NEW= new SparseMNode(rowID,columnID,value,null,null);
		SparseMNode CurrentRow=rowHead;
		SparseMNode CurrentColumn=columnHead;

		//CASE 1A: New Row, New Column
		if(rowID<rowHead.getRowID() && columnID < columnHead.getColumn())
		{
			rowHead = new SparseMNode(rowID, rowHead, NEW); //Append before the Head
			columnHead = new SparseMNode(NEW,columnHead,columnID);
			return;
		}
		
		if (CurrentRow.getRowID()==rowID && CurrentColumn.getColumn() < columnID)
		{
			NEW.setNextColumn(CurrentRow.getNextColumn());
			CurrentRow.setNextColumn(NEW);
		}		
		
		if (CurrentRow.getRowID() < rowID && CurrentColumn.getColumn() == columnID)
		{
			NEW.setNextRow(CurrentColumn.getNextRow());
			CurrentRow.setNextRow(NEW);
		}
	//Case 2: Append in between Originals and Tails Case 2A: Row
//ROW CONSTRUCTION: ROWID, NEXT Row, NEXT Column
//COLUMN CONSTRUCTION: NEXT ROW, NEXT COLUMN, COLUMN ID
		while(CurrentRow.getNextColumn()!=null) 
		{
			SparseMNode SlowRow=CurrentRow;
			CurrentRow=CurrentRow.getNextRow();
			if (rowID > CurrentRow.getRowID())
			{
				SlowRow=SlowRow.getNextRow();
				CurrentRow=CurrentRow.getNextRow();
				continue;
			}
			else
			{
				SlowRow.setNextRow(new SparseMNode(rowID,NEW,CurrentRow));
			}
		}
	//Case 2B: Fix the Columns
		while (CurrentColumn.getNextRow()!=null)
		{
			SparseMNode SlowColumn=CurrentColumn;
			CurrentColumn=CurrentColumn.getNextColumn();
			if (columnID > CurrentColumn.getColumn())
			{
				SlowColumn=SlowColumn.getNextColumn();
				CurrentColumn=CurrentColumn.getNextColumn();
				continue;
			}
			else
			{
				SparseMNode CModify=SlowColumn;
				SlowColumn=SlowColumn.getNextRow();
				while (SlowColumn.getNextRow()!=null)
				{
					if (rowID > SlowColumn.getRowID())
					{
						CModify=CModify.getNextRow();
						SlowColumn=SlowColumn.getNextRow();
						continue;
					}
					else
					{
						CModify.setNextRow(NEW);
						NEW.setNextRow(SlowColumn);
					}
				}
			}
		}
		CurrentRow.setNextColumn(new SparseMNode(rowID,null,NEW));
		CurrentColumn.setNextRow(new SparseMNode(rowID,null,NEW));
	}
}