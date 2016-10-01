
import javax.swing.JOptionPane;

public class LLSparseM implements SparseM 
{
	//Basic Row Node
		public class SparseMRow
		{
			private int row;
			private SparseMNode nextElement;
			private SparseMRow nextRow;
			public SparseMRow(int rowID, SparseMRow NextRow, SparseMNode nextColumn)
			{
				row=rowID;
				nextElement=nextColumn;
				nextRow=NextRow;
			}
			//Get Methods
			public int getRowID(){return row;}
			public SparseMNode getNextElement() {return nextElement;}
			public SparseMRow getNextRow() {return nextRow;}
			//Set Methods
			public void setNextElement(SparseMNode Nextele){nextElement=Nextele;}
			public void setNextRow(SparseMRow NR){nextRow=NR;}
		}
		
	//Basic Column Node
		public class SparseMColumn
		{
			private int column;
			private SparseMNode nextElement;
			private SparseMColumn nextColumn;
			public SparseMColumn(int columnID, SparseMNode nextRow, SparseMColumn NextColumn)
			{
				column=columnID;
				nextElement=nextRow;
				nextColumn=NextColumn;
			}
			//Get Methods
			public int getColumnID(){return column;}
			public SparseMNode getNextElement() {return nextElement;}
			public SparseMColumn getNextColumn() {return nextColumn;}
			//Set Methods
			public void setNextElement(SparseMNode Nextele){nextElement=Nextele;}
			public void setNextColumn(SparseMColumn NC){nextColumn=NC;}
		}
	
	private int ROWS=0;//Pretend its FINAL
	private int COLUMNS=0;//Pretend its FINAL
	private int numofElements=0;
	private SparseMRow rowHead;//FIRST ROW
	private SparseMColumn columnHead;//FIRST COLUMN
	
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

	public int getElement(int ridx, int cidx)
	{
		//Out of Bounds?
		if (outofBounds(ridx,cidx))
		{
			JOptionPane.showMessageDialog(null, "OUT OF BOUNDS!!!");
			return 0;
		}
		
		//Find the Element
		if (findRow(ridx)!=null && findColumn(cidx)!=null)
		{
			SparseMNode findElement=findColumn(cidx).getNextElement();
			while (findElement!=null)
			{
				if (findElement.getRowID()==ridx)
				{
					return findElement.getValue();
				}
				findElement.getNextRow();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The Element you are looking for does NOT exist!!!");
		}
		return 0;
	}

	public void clearElement(int ridx, int cidx)
	{
		//Out of Bounds?
		if (outofBounds(ridx,cidx))
		{
			JOptionPane.showMessageDialog(null, "OUT OF BOUNDS!!!");
			return;
		}
		if (findRow(ridx)!=null && findColumn(cidx)!=null)
		{
			deleteRow(ridx,cidx);//Delete with respect to Rows
			deleteColumn(ridx,cidx);//Delete with respect to Columns
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "NO SUCH ELEMENT EXISTS!!!");
			return;
		}
	}
	
	private void deleteRow(int ridx, int cidx)
	{
		SparseMNode RowTARGET=findRow(ridx).getNextElement();
		if (RowTARGET.getColumnID()==cidx)
		{
			findRow(ridx).setNextElement(RowTARGET.getNextColumn());
			return;
		}
		SparseMNode previous=RowTARGET;
		while (RowTARGET!=null)
		{
			if(RowTARGET.getColumnID()==cidx)
			{
				previous.setNextColumn(RowTARGET.getNextColumn());
				return;
			}
			else
			{
				previous=RowTARGET;
				RowTARGET=RowTARGET.getNextColumn();
			}
		}
	}
	
	private void deleteColumn(int ridx, int cidx)
	{
		SparseMNode ColumnTarget=findColumn(cidx).getNextElement();
		if (ColumnTarget.getRowID()==ridx)
		{
			findColumn(cidx).setNextElement(ColumnTarget.getNextRow());
			return;
		}
		SparseMNode CPrevious=ColumnTarget;
		while (ColumnTarget!=null)
		{
			if (ColumnTarget.getRowID()==ridx)
			{
				CPrevious.setNextRow(ColumnTarget.getNextRow());
				return;
			}
			else
			{
				CPrevious=ColumnTarget;
				ColumnTarget=ColumnTarget.getNextColumn();
			}
		}
	}
	
	public SparseMRow getRowHead()
	{
		return rowHead;
	}
	
	public SparseMColumn getColumnHead()
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
			SparseMNode Reset=findColumn(cidx).getNextElement();
			while (Reset!=null)
			{
				if (Reset.getRowID()==ridx)
				{
					Reset.setValue(value);
				}
				Reset=Reset.getNextRow();
			}
			return;
		}
		
		if (numofElements==0)
		{
			SparseMNode FirstElement=new SparseMNode(ridx,cidx,value,null,null);
			rowHead=new SparseMRow(ridx,null,FirstElement);//ROW CONS: ROWID, NEXT Row, NEXT Column
			columnHead=new SparseMColumn(cidx,FirstElement,null);//COLUMN CONS: COLID, NextRowm Next Column
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
		SparseMRow current = rowHead;
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
		SparseMColumn current= columnHead;
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
		SparseMNode current= findRow(ridx).getNextElement();
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
		SparseMNode current = findRow(ridx).getNextElement();
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
		SparseMNode current = findColumn(cidx).getNextElement();
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
		SparseMNode current = findColumn(cidx).getNextElement();
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
		SparseMRow ARow = this.getRowHead();
		SparseMColumn AColumn = this.getColumnHead();
		SparseMRow	BRow =otherM.getRowHead();
		SparseMColumn BColumn = otherM.getColumnHead();
		//Traverse Both Matrixes
		while (ARow.getNextRow()!=null)
		{
			SparseMRow TravelARow=ARow;
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
		SparseMRow ARow = this.getRowHead();
		SparseMColumn AColumn = this.getColumnHead();
		SparseMRow	BRow =otherM.getRowHead();
		SparseMColumn BColumn = otherM.getColumnHead();
		//if (ARow.getRowID()==BRow.getRowID() && ARow.getColumnID()==BRow.getColumnID())
		//{
		//	Answer.insert(ARow.getRowID(), ARow.getColumnID(), ARow.getValue()+BRow.getValue());
		//}
		return Answer;
	}

	public LLSparseM multiplication(LLSparseM otherM)throws VectorException
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			throw new VectorException("NOT THE SAME SIZE");
		}
		LLSparseM Answer=new LLSparseM(this.nrows(),this.ncols());
		SparseMRow ARow = this.getRowHead();
		SparseMColumn AColumn = this.getColumnHead();
		SparseMRow	BRow =otherM.getRowHead();
		SparseMColumn	BColumn = otherM.getColumnHead();
		
		//if (ARow.getRowID()==BRow.getRowID() && ARow.getColumnID()==BRow.getColumnID())
		//{
		//	Answer.insert(ARow.getRowID(), ARow.getColumnID(), ARow.getValue()*BRow.getValue());
		//}
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
		SparseMRow RowPrint=rowHead;
//THIS ONLY WORKS FOR THE FIRST ROW!!!!!!!!!!!!!!!!!!
		if (numofElements >= 1)
		{
			for (int i=0;i<ROWS;i++)
			{
				for (int j=0;j<COLUMNS;j++)
				{
					SparseMNode Print=RowPrint.getNextElement();
					if (Print!=null && Print.getColumnID()==j)
					{
						System.out.print(Print.getValue());
						Print=Print.getNextColumn();
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
	
	private SparseMRow findRow (int rowSearch)
	{
		SparseMRow current= rowHead;
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
	
	private SparseMColumn findColumn (int columnSearch)
	{
		SparseMColumn current= columnHead;
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
		SparseMRow current=rowHead;
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
		SparseMColumn current=columnHead;
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
		SparseMRow Row=rowHead;
		while (Row !=null)
		{
			SparseMNode Column=Row.getNextElement();
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
		SparseMNode Row=rowHead.getNextElement();
		while (Row!=null)
		{
			System.out.println("Row: " + Row.getRowID() + " Column: " +Row.getColumnID() +" Value: " + Row.getValue());
			Row=Row.getNextColumn();
		}
	}
	
	public void printColumn()
	{
		SparseMNode Col=columnHead.getNextElement();
		while (Col!=null)
		{
			System.out.println("Row: " + Col.getRowID() + " Column: " + Col.getColumnID() +" Value: " + Col.getValue());
			Col=Col.getNextRow();
		}
	}
	
	private void existingNodefix(int rowID, int columnID, SparseMNode NEWNODE, String CMD)
	{
		if (CMD.equalsIgnoreCase("ROW"))
		{
//NOTE: THIS WILL NOT CORRECT COLUMNS!!
			SparseMNode currentRowNode=rowHead.getNextElement();
			if (columnID < currentRowNode.getColumnID())
			{
				NEWNODE.setNextColumn(currentRowNode);
				rowHead.setNextElement(NEWNODE);
				columnInsert(rowID,columnID,NEWNODE);
				return;
			}
			SparseMNode previousRowNode=rowHead.getNextElement();
			while (currentRowNode!=null)
			{
				if (columnID > currentRowNode.getColumnID())
				{
					previousRowNode=currentRowNode;
					currentRowNode=currentRowNode.getNextColumn();
				}
				else
				{
					NEWNODE.setNextColumn(currentRowNode);
					previousRowNode.setNextColumn(NEWNODE);
					return;
				}
			}
			previousRowNode.setNextColumn(NEWNODE);
			return;	
		}
		else if(CMD.equalsIgnoreCase("COLUMN"))
		{
			SparseMNode currentColumnNode=columnHead.getNextElement();
			//Travel Down the Rows as the column already exists.
			//Do another Insert Sort with respect to the Column (Top to bottom of Matrix)
			if (rowID < currentColumnNode.getRowID())
			{
				columnHead.setNextElement(NEWNODE);
				NEWNODE.setNextRow(currentColumnNode);
				return;
			}
			
			SparseMNode previousColumnNode=columnHead.getNextElement();
			while (currentColumnNode!=null)
			{
				if (rowID > currentColumnNode.getRowID())
				{
					previousColumnNode=currentColumnNode;
					currentColumnNode=currentColumnNode.getNextRow();
					continue;
				}
				else
				{
					previousColumnNode.setNextRow(NEWNODE);							
					NEWNODE.setNextRow(currentColumnNode);
					return;
				}
			}
			//Append at Column if currentColumn.getNextColumn is null
			previousColumnNode.setNextRow(NEWNODE);
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "INVALID ENTRY!!");
			return;
			//If I get this error, it means somehow someone outside of this class got to call this method.
			//Either that, or someone mistyped row/column in this class.
		}
	}
	
	private void insert(int rowID, int columnID, int value)
	{
		SparseMNode NEWNODE=new SparseMNode(rowID,columnID,value,null,null);
		if (rowHead.getRowID() < rowID)
		{
			rowHead = new SparseMRow(rowID,rowHead,NEWNODE);//NEW ROWHEAD! (RowID, nextRow, nextColumn)
			columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
			return;
		}
		else if (rowHead.getRowID()==rowID)
		{
			String COMMAND="ROW";
			existingNodefix(rowID,columnID,NEWNODE,COMMAND);
			columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
			return;
		}

		SparseMRow previousRow=rowHead;
		SparseMRow currentRow=rowHead;
		
		while (currentRow!=null)
		{
			if (rowID > currentRow.getRowID())
			{
				previousRow=currentRow;
				currentRow=currentRow.getNextRow();
				continue;
			}
			else if (currentRow.getRowID()==rowID)
			{
//This means that I have to an insert sort with respect to the rows (Left to Right on the Matrix)				
				String COMMAND="ROW";
				existingNodefix(rowID,columnID,NEWNODE,COMMAND);
				columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
				return;
			}
			else// if RowID < currentRow.getRow()
			{
				SparseMRow NEWROW= new SparseMRow (rowID,currentRow,NEWNODE);//(RowID, nextRow, nextColumn)
				previousRow.setNextRow(NEWROW);
				NEWROW.setNextRow(currentRow);
				NEWROW.setNextElement(NEWNODE);
				columnInsert(rowID,columnID,NEWNODE);//Fix the Columns Linked List
				return;
			}
		}
		//Create a new Row because RowID is larger than all existing rows.
		previousRow.setNextRow(new SparseMRow(rowID,null,NEWNODE));
		columnInsert(rowID,columnID,NEWNODE);
		return;
	}
	
	private void columnInsert (int rowID, int columnID, SparseMNode NEWNODE)
	{
//NOTE THE InsertRow Method already created the new Node. This is designed to fix the Columns Linked List.
		if (columnID < columnHead.getColumnID())
		{
			columnHead = new SparseMColumn(columnID,NEWNODE,columnHead);//NEW COLUMNHEAD! (CIDX, nextRow, nextColumn)
			return;
		}
		else if (columnID==columnHead.getColumnID())
		{
			String COMMAND="COLUMN";
			existingNodefix(rowID,columnID,NEWNODE,COMMAND);
			return;
		}
		
		SparseMColumn currentColumn=columnHead;
		SparseMColumn previousColumn=columnHead;
		while (currentColumn!=null)
		{
			if (columnID > currentColumn.getColumnID())
			{
				//Keep Traveling across the Columns LinkedList (Left To Right)
				previousColumn=currentColumn;
				currentColumn=currentColumn.getNextColumn();
				continue;
			}
			
			else if(currentColumn.getColumnID()==columnID)
			{
				String COMMAND="COLUMN";
				existingNodefix(rowID,columnID,NEWNODE,COMMAND);
				return;
			}
			else//columnID < currentColumn.getColumnID
			{
				SparseMColumn NEWCOLUMN = new SparseMColumn(columnID,NEWNODE,currentColumn);//NEW COLUMN!!!(nextRow, nextColumn, columnID)
				//Because a new Column was made and I know it was a new Row. I can just connect New no problem.
				previousColumn.setNextColumn(NEWCOLUMN);
				NEWCOLUMN.setNextColumn(currentColumn);
				NEWCOLUMN.setNextElement(NEWNODE);
				return;
			}
		}
		//CurrentColumn is the last column. CID is bigger than all existing columns, build a new column.
		previousColumn.setNextColumn(new SparseMColumn(columnID,NEWNODE,null));//NEW COLUMN!!!(nextRow, nextColumn, columnID)
		return;
		}

}//END OF CLASS