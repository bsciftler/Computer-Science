import javax.swing.JOptionPane;

public class RationalLLSparseM extends LLSparseM
{
	public class RationalSparseMRow
	{	
		private int row;
		private RationalSparseMRow nextRow;	
		private RationalSparseMNode nextElement;
		
		public RationalSparseMRow(int rowID, RationalSparseMRow nextR, RationalSparseMNode nextEle)
		{
			row=rowID;
			nextRow=nextR;
			nextElement=nextEle;
		}	
		//Get Methods
		public int getRowID(){return row;}
		public RationalSparseMNode getNextElement() {return nextElement;}			
		public RationalSparseMRow getNextRow() {return nextRow;}
		//Set Methods
		public void setNextElement(RationalSparseMNode Nextele){nextElement=Nextele;}			
		public void setNextRow(RationalSparseMRow NR){nextRow=NR;}
	}
		
	//Basic Column Node
	public class RationalSparseMColumn
	{		
		private int column;
		private RationalSparseMNode nextElement;
		private RationalSparseMColumn nextColumn;			
		public RationalSparseMColumn(int columnID, RationalSparseMNode nextRow, RationalSparseMColumn NextColumn)	
		{
			column=columnID;
			nextElement=nextRow;
			nextColumn=NextColumn;
		}
		//Get Methods
		public int getColumnID(){return column;}
		public RationalSparseMNode getNextElement() {return nextElement;}
		public RationalSparseMColumn getNextColumn() {return nextColumn;}
		//Set Methods
		public void setNextElement(RationalSparseMNode NextEle){nextElement=NextEle;}
		public void setNextColumn(RationalSparseMColumn NC){nextColumn=NC;}
	}
	

	public RationalLLSparseM(int newRows, int newColumns)
	{
		super(newRows, newColumns);
	}
	
	protected RationalSparseMRow rowHead;//FIRST ROW
	protected RationalSparseMColumn columnHead;//FIRST COLUMN
	protected RationalSparseMRow rowTail;//Last ROW
	protected RationalSparseMColumn columnTail;//Last COLUMN
		
	public void setElement(int ridx, int cidx, Rational value)
	{
		
		if (outofBounds(ridx,cidx))
		{
			JOptionPane.showMessageDialog(null, "INVALID ROW AND/OR COLUMN, OUT OF BOUNDS!!");
			return;
		}
		
		if (numofElements >= ROWS*COLUMNS)
		{
			JOptionPane.showMessageDialog(null, "Matrix overflow! Too many non-Zero elements!!");
			return;
		}
		
		if (value.getNumerator()==0)
		{
			clearElement(ridx,cidx);
			return;
		}
		
	//Are you attempting to replace the value in an existing Node? UH OH HERE!!
		if (NodeSearch(ridx,cidx)!=null)
		{
			//NodeSearch(ridx,cidx).setValue(value);
			return;
		}
		
		if (numofElements==0)
		{
			RationalSparseMNode FirstElement=new RationalSparseMNode(ridx,cidx,value,null,null);
  			rowHead=new RationalSparseMRow(ridx,null,FirstElement);//ROW CONS: ROWID, NEXT Row, NEXT Column
			columnHead=new RationalSparseMColumn(cidx,FirstElement,null);//COLUMN CONS: COLID, NextRowm Next Column
			rowTail=rowHead;
			columnTail=columnHead;
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
	
	private void insert(int rowID, int columnID, Rational value)
	{
		RationalSparseMNode NEWNODE=new RationalSparseMNode(rowID,columnID,value);
		if (rowID < rowHead.getRowID())
		{
			rowHead = new RationalSparseMRow(rowID,rowHead,NEWNODE);//NEW ROWHEAD! (RowID, nextRow, nextColumn)
			rowTail=rowHead;
			++numofRows;
			//columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
			return;
		}
		else if (rowHead.getRowID()==rowID)//Case 1: ONLY WORRY OF APPENDING BETWEEN RowHead and RowHead.getNextElement()
		{
			//existingRowNodefix(columnID,NEWNODE,rowHead);
			//columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
			return;
		}

		RationalSparseMRow previousRow=rowHead;
		RationalSparseMRow currentRow=rowHead;
		
		while (currentRow!=null)
		{
			if (rowID > currentRow.getRowID())
			{
				previousRow=currentRow;
				currentRow=currentRow.getNextRow();
			}
			else if (currentRow.getRowID()==rowID)
			{
//This means that I have to an insert sort with respect to the rows (Left to Right on the Matrix)				
				//existingRowNodefix(columnID,NEWNODE,currentRow);
				//columnInsert(rowID,columnID,NEWNODE);//Fix the Columns LinkedList.
				return;
			}
			else// if RowID < currentRow.getRow()
			{
				previousRow.setNextRow(new RationalSparseMRow (rowID,currentRow,NEWNODE));//(RowID, nextRow, nextColumn)
				++numofRows;
				//columnInsert(rowID,columnID,NEWNODE);//Fix the Columns Linked List
				return;
			}
		}
		//Create a new Row because RowID is larger than all existing rows.
		previousRow.setNextRow(new RationalSparseMRow(rowID,null,NEWNODE));//(RowID, nextRow, nextColumn)
		rowTail=previousRow.getNextRow();
		//columnInsert(rowID,columnID,NEWNODE);
		++numofRows;
		return;
	}
}
