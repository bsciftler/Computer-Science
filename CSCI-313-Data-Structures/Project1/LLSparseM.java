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
		if (findRow(row)==null)
		{
			JOptionPane.showMessageDialog(null, "This is not the element you are looking for...Move along");
			return 0;//It doesn't exist.
		}
		else
		{
			if(findColumn(column)==null)
			{
				JOptionPane.showMessageDialog(null, "This is not the element you are looking for...Move along");
				return 0;//It doesn't exist.
			}
			else
			{
				return findColumn(column).getValue();
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
		if (findRow(ridx)==null)
		{
			JOptionPane.showMessageDialog(null, "NO SUCH ELEMENT EXISTS!!!");
		}
		else
		{
			if (findColumn(cidx)==null)
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
		if (findRow(ridx)!=null && findColumn(cidx)!=null)
		{
			findColumn(cidx).setValue(value);
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
			ColIndex[counter]=current.getColumnID();
			current=current.getNextColumn();
			++counter;
		}
		return ColIndex;
	}

	public int[] getOneRowColIndices(int ridx)
	{
		int [] RowIndex=new int[columnCount()];//USE COLUMN/ROW?
		int counter=0;
		SparseMNode current= findRow(ridx).getNextColumn();
		if (current==null)
		{
			return RowIndex;
		}
		while (current!=null)
		{
			RowIndex[counter]=current.getColumnID();
			current=current.getNextColumn();
			++counter;
		}
		return RowIndex;
	}

	public int[] getOneRowValues(int ridx)
	{
		SparseMNode current = findRow(ridx).getNextColumn();
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
		SparseMNode current = findColumn(cidx).getNextRow();
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
		SparseMNode current = findColumn(cidx).getNextRow();
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
//===========================================PART 2: Extra Credit==============================================================

	public LLSparseM addition(LLSparseM otherM) throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.ncols(),this.nrows());
		SparseMNode ARow = this.getRowHead();
		SparseMNode AColumn = this.getColumnHead();
		SparseMNode	BRow =otherM.getRowHead();
		SparseMNode	BColumn = otherM.getColumnHead();
		//Traverse Both Matrixes
		while (ARow.getNextRow()!=null)
		{
			SparseMNode TravelARow=ARow;
			while (TravelARow!=null)
			{
				
			}
		}
		return Answer;
	}

	public LLSparseM substraction(LLSparseM otherM)throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.nrows(),this.ncols());
		SparseMNode ARow = this.getRowHead();
		SparseMNode AColumn = this.getColumnHead();
		SparseMNode	BRow =otherM.getRowHead();
		SparseMNode	BColumn = otherM.getColumnHead();
		if (ARow.getRowID()==BRow.getRowID() && ARow.getColumnID()==BRow.getColumnID())
		{
			Answer.insert(ARow.getRowID(), ARow.getColumnID(), ARow.getValue()+BRow.getValue());
		}
		return Answer;
	}

	public LLSparseM multiplication(LLSparseM otherM)throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.nrows(),this.ncols());
		SparseMNode ARow = this.getRowHead();
		SparseMNode AColumn = this.getColumnHead();
		SparseMNode	BRow =otherM.getRowHead();
		SparseMNode	BColumn = otherM.getColumnHead();
		
		if (ARow.getRowID()==BRow.getRowID() && ARow.getColumnID()==BRow.getColumnID())
		{
			Answer.insert(ARow.getRowID(), ARow.getColumnID(), ARow.getValue()*BRow.getValue());
		}
		return Answer;
	}
//==============================================END OF EXTRA CREDIT==================================================================================

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
					if (RowPrint!=null && RowPrint.getColumnID()==j)
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
	
	private SparseMNode findRow (int rowSearch)
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
	
	private SparseMNode findColumn (int columnSearch)
	{
		SparseMNode current= columnHead;
		while (current!=null)
		{
			if (current.getColumnID()==columnSearch)
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
	
	public void printAllNodes()
	{
		SparseMNode Row=rowHead;
		while (Row !=null)
		{
			SparseMNode Column=Row.getNextColumn();
			while (Column != null)
			{
				System.out.println("Row: " + Column.getRowID() + " Column: " +Column.getColumnID() +" Value: " + Column.getValue());
				Column=Column.getNextColumn();
			}
			Row=Row.getNextRow();
		}
	}
	
	public void printRow()
	{
		SparseMNode Row=rowHead.getNextColumn();
		while (Row!=null)
		{
			System.out.println("Row: " + Row.getRowID() + " Column: " +Row.getColumnID() +" Value: " + Row.getValue());
			Row=Row.getNextColumn();
		}
	}
	
	//STILL IN PROGRESS
	private void insert(int rowID, int columnID, int value)
	{
		SparseMNode NEWNODE=new SparseMNode(rowID,columnID,value,null,null);
		if (rowHead.getRowID() < rowID)
		{
			rowHead = new SparseMNode(rowID,rowHead,NEWNODE);//NEW ROWHEAD! (RowID, nextRow, nextColumn)
			columnInsert(rowID,columnID,NEWNODE);//Adjust the Columns LinkedList.
			return;
		}
		SparseMNode previousRow=rowHead;
		SparseMNode currentRow=rowHead;
		
		while (currentRow.getNextRow()!=null)
		{
			if (rowID > currentRow.getRowID())
			{
				previousRow=currentRow;
				currentRow=currentRow.getNextRow();
				continue;
			}
			else if (currentRow.getRowID()==rowID)
			{
		//This is insertion sort if the Row Already exists.
		//This will adjust the LinkedList Left to Right. I will need another method to alter Top to bottom.
		//Travel Left to Right through the Rows as the column already exists.
		//Do another Insert Sort with respect to The Left to Right Linked List.
				SparseMNode previousRowNode=currentRow.getNextColumn();
				SparseMNode currentRowNode=currentRow.getNextColumn();
				
				if (currentRowNode.getColumnID() < columnID)
				{
					currentRow.setNextColumn(NEWNODE);
					NEWNODE.setNextColumn(currentRowNode);
					columnInsert(rowID,columnID,NEWNODE);
					return;
				}
		
				while (currentRowNode.getNextColumn()!=null)
				{
					if (columnID > currentRowNode.getColumnID())
					{
						previousRowNode=currentRowNode;
						currentRowNode=currentRowNode.getNextColumn();
						continue;
					}
					else
					{
						previousRowNode.setNextColumn(NEWNODE);
						NEWNODE.setNextColumn(currentRowNode);
						columnInsert(rowID,columnID,NEWNODE);//Fix the Columns Linked List
						return;
					}
				}
				currentRow.setNextColumn(NEWNODE);
				columnInsert(rowID,columnID,NEWNODE);
				return;
			}
			else// if RowID < currentRow.getRow()
			{
				SparseMNode NEWROW= new SparseMNode (rowID,currentRow,NEWNODE);//(RowID, nextRow, nextColumn)
				previousRow.setNextRow(NEWROW);
				NEWROW.setNextRow(currentRow);
				NEWROW.setNextColumn(NEWNODE);
				columnInsert(rowID,columnID,NEWNODE);//Fix the Columns Linked List
				return;
			}
		}
		//Create a new Row because RowID is larger than all existing rows.
		SparseMNode NEWROW=new SparseMNode(rowID,null,NEWNODE);//(RowID, nextRow, nextColumn)
		currentRow.setNextRow(NEWROW);
		columnInsert(rowID,columnID,NEWNODE);
		return;
	}
	
	private void columnInsert (int rowID, int columnID, SparseMNode NEWNODE)
	{
//NOTE THE InsertRow Method already created the new Node. This is designed to fix the Columns Linked List.
		if (columnID < columnHead.getColumnID())
		{
			columnHead = new SparseMNode(NEWNODE,columnHead,columnID);//NEW COLUMNHEAD! (nextRow, nextColumn, columnID)
			return;
		}
		SparseMNode currentColumn=columnHead;
		SparseMNode previousColumn=columnHead;
		while (currentColumn.getNextColumn()!=null)
		{
			if (columnID > currentColumn.getColumnID())
			{
				//Keep Traveling across the Columns LinkedList (Top to Bottom)
				previousColumn=currentColumn;
				currentColumn=currentColumn.getNextRow();
				continue;
			}
			
			else if(currentColumn.getColumnID()==columnID)
			{
				//Travel Down the Rows as the column already exists. Do another Insert Sort.
				if (currentColumn.getNextRow().getRowID() < rowID)
				{
					SparseMNode PlaceHolder=currentColumn.getNextRow();
					currentColumn.setNextRow(NEWNODE);
					NEWNODE.setNextRow(PlaceHolder);
					return;
				}
				SparseMNode previousRowNode=currentColumn.getNextRow();
				SparseMNode currentRowNode=currentColumn.getNextRow();
				while (currentRowNode.getNextRow()!=null)
				{
					if (rowID > currentRowNode.getRowID())
					{
						previousRowNode=currentRowNode;
						currentRowNode=currentRowNode.getNextRow();
						continue;
					}
					else
					{
						previousRowNode.setNextRow(NEWNODE);							
						NEWNODE.setNextRow(currentRowNode);
						return;
					}
				}
				//Append at Column if currentColumn.getNextColumn is null
				currentRowNode.setNextRow(NEWNODE);
				return;
			}
			else//columnID < currentColumn.getColumnID
			{
				SparseMNode NEWCOLUMN = new SparseMNode(NEWNODE,currentColumn,columnID);//Create new Column in between current and previous
				//NEW COLUMN!!!(nextRow, nextColumn, columnID)
				//Because a new Column was made and I know it was a new Row. I can just connect New no problem.
				previousColumn.setNextColumn(NEWCOLUMN);
				NEWCOLUMN.setNextColumn(currentColumn);
				NEWCOLUMN.setNextRow(NEWNODE);
				return;
			}
		}
		//CurrentColumn is the last column. CID is bigger than all existing columns, build a new column.
		currentColumn.setNextColumn(new SparseMNode(NEWNODE,null,columnID));//NEW COLUMN!!!(nextRow, nextColumn, columnID)
		return;
		}

}//END OF CLASS