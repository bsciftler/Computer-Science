
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
	private int numofRows=0;
	private int numofColumns=0;
	private int numofElements=0;
	private SparseMRow rowHead;//FIRST ROW
	private SparseMColumn columnHead;//FIRST COLUMN
	
	public LLSparseM(int newRows, int newColumns)
	{
		ROWS=newRows;
		COLUMNS=newColumns;
	}

	public int nrows(){return ROWS;}
	public int ncols() {return COLUMNS;}
	public int numElements() {return numofElements;}
	public int getnumofRows(){return numofRows;}
	public int getnumofColumns(){return numofColumns;}
	public SparseMRow getRowHead(){return rowHead;}
	public SparseMColumn getColumnHead(){return columnHead;}

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
			--numofElements;
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "NO SUCH ELEMENT EXISTS!!!");
			return;
		}
	}
	
	private void RowandColumnInspector(int ridx,int cidx, String CMD)
	{
		if (CMD.equalsIgnoreCase("RowCHECK"))//Make sure I delete Row Node if needed
		{
			if (findRow(ridx).getNextElement()==null)
			{
				if (findRow(ridx)==rowHead)
				{
					rowHead=rowHead.getNextRow();
					--numofRows;
					return;
				}
				SparseMRow previous=rowHead;
				SparseMRow current=rowHead;
				while (current!=null)
				{
					if (current.getNextElement()==null)
					{
						previous.setNextRow(current.getNextRow());
						--numofRows;
						return;
					}
					else
					{
						previous=current;
						current=current.getNextRow();
					}
				}
			}
			return;
		}
		else if (CMD.equalsIgnoreCase("ColumnCheck"))//Make sure I delete Column Node if needed
		{
			if (findColumn(cidx).getNextElement()==null)
			{
				if (findColumn(cidx)==columnHead)
				{
					columnHead=columnHead.getNextColumn();
					--numofColumns;
					return;
				}
				SparseMColumn previous=columnHead;
				SparseMColumn current=columnHead;
				while (current!=null)
				{
					if (current.getNextElement()==null)
					{
						previous.setNextColumn(current.getNextColumn());
						--numofColumns;
						return;
					}
					else
					{
						previous=current;
						current=current.getNextColumn();
					}
				}
				return;
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Invaalid Command at RowandColumnsInspector method");
		}
	}
	
	private void deleteRow(int ridx, int cidx)
	{
		SparseMNode RowTARGET=findRow(ridx).getNextElement();
		if (RowTARGET.getColumnID()==cidx)
		{
			findRow(ridx).setNextElement(RowTARGET.getNextColumn());
			RowandColumnInspector(ridx,cidx, new String("RowCheck"));
			return;
		}
		SparseMNode previous=RowTARGET;
		while (RowTARGET!=null)
		{
			if(RowTARGET.getColumnID()==cidx)
			{
				previous.setNextColumn(RowTARGET.getNextColumn());
				RowandColumnInspector(ridx,cidx,new String("RowCheck"));
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
		//Delete Column Node?
		SparseMNode ColumnTarget=findColumn(cidx).getNextElement();
		if (ColumnTarget.getRowID()==ridx)
		{
			findColumn(cidx).setNextElement(ColumnTarget.getNextRow());
			RowandColumnInspector(ridx,cidx,new String("ColumnCheck"));
			return;
		}
		SparseMNode CPrevious=ColumnTarget;
		while (ColumnTarget!=null)
		{
			if (ColumnTarget.getRowID()==ridx)
			{
				CPrevious.setNextRow(ColumnTarget.getNextRow());
				RowandColumnInspector(ridx,cidx,new String("ColumnCheck"));
				return;
			}
			else
			{
				CPrevious=ColumnTarget;
				ColumnTarget=ColumnTarget.getNextColumn();
			}
		}
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
			++numofRows;
			++numofColumns;
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
		int [] Rows=new int [numofRows];
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
		int [] ColIndex=new int[numofColumns];//or use COLUMN/ROW?
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
		int [] RowIndex=new int[numofColumns];
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
		int [] RowVals=new int [numofRows];///USE COLUMN/ROW?
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
		int [] ColumnRowID=new int [numofColumns];//USE ROW/COLUMN
		if (current==null)
		{
			return ColumnRowID;
		}
		int counter=0;
		while (current!=null && counter!=numofColumns)
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
		int [] ColumnRowVals=new int [numofColumns];///USE COLUMN/ROW?
		if (current==null)
		{
			return ColumnRowVals;
		}
		int counter=0;
		while (current!=null && counter!=numofColumns)
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
		SparseMRow BRow =otherM.getRowHead();
		SparseMColumn BColumn = otherM.getColumnHead();
		//Traverse Both Matrixes
		while (ARow.getNextRow()!=null && BRow.getNextRow()!=null)
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
			return;
		}
		if (numofElements >= 1)
		{
			SparseMRow Row=rowHead;
			int RowCOUNT=1;
			while (Row !=null)
			{
				if (Row.getRowID()==RowCOUNT)
				{
					SparseMNode Column=Row.getNextElement();
					int col=1;
					while (Column != null && col !=COLUMNS+1)
					{
						if (Column.getColumnID()==col)
						{
							System.out.print(" " + Column.getValue() + " ");
							++col;
							Column=Column.getNextColumn();
						}
						else
						{
							System.out.print("0");
							++col;
						}
					}
					if (col!=COLUMNS)//Left Over 0's after Last Node
					{
						for (int i=0;i<COLUMNS-col+1;i++)
						{
							System.out.print("0");
						}
						System.out.println(" ");
					}
					RowCOUNT++;
					Row=Row.getNextRow();
					continue;
				}
				else //Print Empty Row.
				{
					for (int i=0;i<COLUMNS;i++)
					{
						System.out.print("0");
					}
					RowCOUNT++;
					System.out.println(" ");
				}
			}//End of While
			for (int j=0;j<ROWS-RowCOUNT;j++)//To accommodate any missing rows.
			{
				for (int i=0;i<COLUMNS;i++)
				{
					System.out.print("0");
				}
				System.out.println(" ");
			}
		}
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
	
	public void PrintAllColNodes()
	{
		SparseMColumn Col=columnHead;
		while (Col !=null)
		{
			SparseMNode Row=Col.getNextElement();
			while (Row != null)
			{
				System.out.println("Column: " + Row.getColumnID() + " Row: " + Row.getRowID() + " Value: " + Row.getValue());
				Row=Row.getNextRow();
			}
			Col=Col.getNextColumn();
		}
	}
	
	private void existingColumnNodefix(int rowID, SparseMNode NEWNODE, SparseMColumn FOUND)
	{
		//Case 1: Append between Found and New Node
		SparseMNode currentColumnNode=FOUND.getNextElement();
		if (rowID < currentColumnNode.getRowID())
		{
			NEWNODE.setNextRow(currentColumnNode);
			FOUND.setNextElement(NEWNODE);
			return;
		}
		
		//Case 2: Fix Matrix going Top to Bottom.
		SparseMNode previousColumnNode=FOUND.getNextElement();
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
				NEWNODE.setNextRow(currentColumnNode);
				previousColumnNode.setNextRow(NEWNODE);	
				return;
			}
		}
		//Append at Column if currentColumn.getNextColumn is null
		previousColumnNode.setNextRow(NEWNODE);
		return;
	}
	
	private void existingRowNodefix(int columnID, SparseMNode NEWNODE, SparseMRow FOUND)
	{
		//Case 1: Append between the Row Node and First element.
		SparseMNode currentRowNode=FOUND.getNextElement();
		if (columnID < currentRowNode.getColumnID())
		{
			NEWNODE.setNextColumn(currentRowNode);
			FOUND.setNextElement(NEWNODE);
			return;
		}
		//Case 2: Fix the Linked List Left to Right.
		SparseMNode previousRowNode=FOUND.getNextElement();
		while (currentRowNode!=null)
		{
			if (columnID > currentRowNode.getColumnID())
			{
				previousRowNode=currentRowNode;		
				currentRowNode=currentRowNode.getNextColumn();
				continue;
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
	
	private void insert(int rowID, int columnID, int value)
	{
		SparseMNode NEWNODE=new SparseMNode(rowID,columnID,value,null,null);
		if (rowID < rowHead.getRowID())
		{
			rowHead = new SparseMRow(rowID,rowHead,NEWNODE);//NEW ROWHEAD! (RowID, nextRow, nextColumn)
			++numofRows;
			columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
			return;
		}
		else if (rowHead.getRowID()==rowID)//Case 1: ONLY WORRY OF APPENDING BETWEEN RowHead and RowHead.getNextElement()
		{
			existingRowNodefix(columnID,NEWNODE,rowHead);
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
				existingRowNodefix(columnID,NEWNODE,currentRow);
				columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
				return;
			}
			else// if RowID < currentRow.getRow()
			{
				previousRow.setNextRow(new SparseMRow (rowID,currentRow,NEWNODE));//(RowID, nextRow, nextColumn)
				++numofRows;
				columnInsert(rowID,columnID,NEWNODE);//Fix the Columns Linked List
				return;
			}
		}
		//Create a new Row because RowID is larger than all existing rows.
		previousRow.setNextRow(new SparseMRow(rowID,null,NEWNODE));//(RowID, nextRow, nextColumn)
		columnInsert(rowID,columnID,NEWNODE);
		++numofRows;
		return;
	}
	
	private void columnInsert (int rowID, int columnID, SparseMNode NEWNODE)
	{
//NOTE THE InsertRow Method already created the new Node. This is designed to fix the Columns Linked List.
		if (columnID < columnHead.getColumnID())
		{
			columnHead = new SparseMColumn(columnID,NEWNODE,columnHead);//NEW COLUMNHEAD! (CIDX, nextRow, nextColumn)
			++numofColumns;
			return;
		}
		else if (columnID==columnHead.getColumnID())
		{
			existingColumnNodefix(rowID,NEWNODE,columnHead);
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
				existingColumnNodefix(rowID,NEWNODE,currentColumn);
				return;
			}
			else//columnID < currentColumn.getColumnID
			{
				previousColumn.setNextColumn(new SparseMColumn(columnID,NEWNODE,currentColumn));//New Column: CIDX, NextRow, NextColumn
				++numofColumns;
				return;
			}
		}
		//CurrentColumn is the last column. CID is bigger than all existing columns, build a new column.
		previousColumn.setNextColumn(new SparseMColumn(columnID,NEWNODE,null));//NEW COLUMN!!! (columnID, NextRow, NextColumn)
		++numofColumns;
		return;
	}
}//END OF CLASS