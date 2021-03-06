
public class LLSparseM implements SparseM 
{
	//Basic Row Node
	public class SparseMRow
	{
		private int row;
		private SparseMRow nextRow;	
		private SparseMNode nextElement;
		
		public SparseMRow(int rowID, SparseMRow NextRow, SparseMNode nextColumn)
		{
			row=rowID;
			nextRow=NextRow;
			nextElement=nextColumn;
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
		private SparseMColumn nextColumn;
		private SparseMNode nextElement;
		
		public SparseMColumn(int columnID, SparseMColumn NextColumn, SparseMNode nextRow)	
		{
			column=columnID;
			nextColumn=NextColumn;
			nextElement=nextRow;
		}
		//Get Methods
		public int getColumnID(){return column;}
		public SparseMNode getNextElement() {return nextElement;}
		public SparseMColumn getNextColumn() {return nextColumn;}
		//Set Methods
		public void setNextElement(SparseMNode NextEle){nextElement=NextEle;}
		public void setNextColumn(SparseMColumn NC){nextColumn=NC;}
	}
	
	protected final int ROWS;
	protected final int COLUMNS;
	protected int numofRows=0;
	protected int numofColumns=0;
	protected int numofElements=0;
	protected SparseMRow rowTail;//I will use this for my APPEND ONLY!!!
	protected SparseMColumn columnTail;//I will use this for my APPEND ONLY!!!
	protected SparseMNode LastAppendNODE;//I am using this for Append!!!
	protected SparseMRow rowHead;//FIRST ROW
	protected SparseMColumn columnHead;//FIRST COLUMN
	
	public LLSparseM(int newRows, int newColumns)
	{
		ROWS=newRows;
		COLUMNS=newColumns;
	}

	public int nrows() {return ROWS;}
	public int ncols() {return COLUMNS;}
	public int numElements() {return numofElements;}
	public int getnumofRows() {return numofRows;}
	public int getnumofColumns() {return numofColumns;}
	public SparseMRow getRowHead() {return rowHead;}
	public SparseMColumn getColumnHead() {return columnHead;}

	public int getElement(int ridx, int cidx)
	{
		//Out of Bounds?
		if (outofBounds(ridx,cidx))
		{
			System.out.println("OUT OF BOUNDS!!!");
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
			System.out.println("The Element you are looking for does NOT exist!!!");
		}
		return 0;
	}

	public void clearElement(int ridx, int cidx)
	{
		//Out of Bounds?
		if (outofBounds(ridx,cidx))
		{
			System.out.print("OUT OF BOUNDS!!!");
			return;
		}
		if (findRow(ridx)!=null && findColumn(cidx)!=null)
		{
			deleteRow(ridx,cidx);//Delete with respect to Rows
			deleteColumn(ridx,cidx);//Delete with respect to Columns
			this.RowInspector();
			this.ColumnInspector();
			--numofElements;
			return;
		}
		else
		{
			System.out.println("NO SUCH ELEMENT EXISTS!!!");
			return;
		}
	}
	/*
	 * 	Modify it as a "Scanner" Thats it.
	 */
	private void RowInspector()
	{
		/*
		 * 	Goal: Inspect the integrity of the Rows 
		 * 	If there is an empty row, remove it.
		 */
		SparseMRow currentRow = rowHead;
		SparseMRow previousRow = rowHead;
		while (currentRow!=null)
		{
			//Empty row spotted!! 
			if (currentRow.getNextElement()==null)
			{
				previousRow.setNextRow(currentRow.getNextRow());
				return;
			}
			previousRow=currentRow;
			currentRow=currentRow.getNextRow();
		}
		
	}
	
	/*
	 * 	Goal: Inspect the integrity of the Columns
	 * 	If there is an empty column, remove it.
	 */
	private void ColumnInspector()
	{
		/*
		 * 	Goal: Inspect the integrity of the Rows 
		 * 	If there is an empty row, remove it.
		 */
		
		SparseMColumn currentCol = columnHead;
		SparseMColumn previousCol = columnHead;
		while (currentCol!=null)
		{
			//Empty row spotted!! 
			if (currentCol.getNextElement()==null)
			{
				previousCol.setNextColumn(currentCol.getNextColumn());
				return;
			}
			previousCol=currentCol;
			currentCol=currentCol.getNextColumn();
		}
		
	}
	
	private void deleteRow(int ridx, int cidx)
	{
		SparseMNode RowTARGET=findRow(ridx).getNextElement();
		//If applicable, delete The First Element in that Row.
		if (RowTARGET.getColumnID()==cidx)
		{
			findRow(ridx).setNextElement(RowTARGET.getNextColumn());
			return;
		}
		
		//Otherwise search for it.
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
		//What about delete the last element in a row?
	}
	
	private void deleteColumn(int ridx, int cidx)
	{
		SparseMNode ColumnTarget=findColumn(cidx).getNextElement();
		//If applicable, delete The First Element in that Column.	
		if (ColumnTarget.getRowID()==ridx)
		{
			findColumn(cidx).setNextElement(ColumnTarget.getNextRow());
			return;
		}
		SparseMNode ColumnPrevious=ColumnTarget;
		while (ColumnTarget!=null)
		{
			if (ColumnTarget.getRowID()==ridx)
			{
				ColumnPrevious.setNextRow(ColumnTarget.getNextRow());
				return;
			}
			else
			{
				ColumnPrevious=ColumnTarget;
				ColumnTarget=ColumnTarget.getNextColumn();
			}
		}
	}

	public void setElement(int ridx, int cidx, int value)
	{
		
		if (outofBounds(ridx,cidx))
		{
			System.out.println("INVALID ROW AND/OR COLUMN, OUT OF BOUNDS!!");
			return;
		}
		
		if (numofElements >= ROWS*COLUMNS)
		{
			System.out.println("Matrix overflow! Too many non-Zero elements!!");
			return;
		}
		
		if (value==0)
		{
			clearElement(ridx,cidx);
			return;
		}
		
	//Are you attempting to replace the value in an existing Node? UH OH HERE!!
		if (NodeSearch(ridx,cidx)!=null)
		{
			NodeSearch(ridx,cidx).setValue(value);
			return;
		}
		
		if (numofElements==0)
		{
			SparseMNode FirstElement=new SparseMNode(ridx,cidx,value,null,null);
  			rowHead=new SparseMRow(ridx,null,FirstElement);//ROW CONS: ROWID, NEXT Row, NEXT Column
			columnHead=new SparseMColumn(cidx,null,FirstElement);//COLUMN CONS: COLID, NextRowm Next Column
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
	
	protected SparseMNode NodeSearch(int ridx, int cidx)
	{
		if (findRow(ridx)==null)
		{
			return null;
		}
		else //The Row Node was found.
		{
			SparseMNode FOUND=findRow(ridx).getNextElement();
			while (FOUND!=null)
			{
				if (FOUND.getColumnID()==cidx)
				{
					return FOUND;
				}
				FOUND=FOUND.getNextColumn();
			}
			return FOUND;//it can return null if the Node was NOT found.
		}
	}

	public int [] getRowIndices()
	{
		SparseMRow current = rowHead;
		int [] Rows=new int [numofRows];
		if (current==null)
		{
			return null;
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

	public int [] getColIndices()
	{
		SparseMColumn current= columnHead;
		int [] ColIndex=new int[numofColumns];
		if (current==null)
		{
			return null;
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

	public int [] getOneRowColIndices(int ridx)
	{
		SparseMNode current= findRow(ridx).getNextElement();
		int size=0;
		if (current==null)
		{
			return null;
		}
		else
		{
			//Go through once to get the number of nodes
			while (current!=null)
			{
				++size;
				current=current.getNextColumn();
			}
		}
		int [] RowColumnID=new int [size];
		int counter=0;
		current= findRow(ridx).getNextElement();
		while (current!=null)
		{
			RowColumnID[counter]=current.getColumnID();
			current=current.getNextColumn();
			++counter;
		}
		return RowColumnID;
	}

	public int [] getOneRowValues(int ridx)
	{
		SparseMNode current= findRow(ridx).getNextElement();
		int size=0;
		if (current==null)
		{
			return null;
		}
		else
		{
			//Go through once to get the number of nodes
			while (current!=null)
			{
				++size;
				current=current.getNextColumn();
			}
		}
		int [] RowValues=new int [size];
		int counter=0;
		current= findRow(ridx).getNextElement();
		while (current!=null)
		{
			RowValues[counter]=current.getValue();
			current=current.getNextColumn();
			++counter;
		}
		return RowValues;
	}

	public int [] getOneColRowIndices(int cidx)
	{
		SparseMNode current= findColumn(cidx).getNextElement();
		int size=0;
		if (current==null)
		{
			return null;
		}
		else
		{
			//Go through once to get the number of nodes
			while (current!=null)
			{
				++size;
				current=current.getNextRow();
			}
		}
		int [] ColumnRowIDs=new int [size];
		int counter=0;
		current= findColumn(cidx).getNextElement();
		while (current!=null)
		{
			ColumnRowIDs[counter]=current.getRowID();
			current=current.getNextRow();
			++counter;
		}
		return ColumnRowIDs;
	}

	public int [] getOneColValues(int cidx)
	{
		SparseMNode current= findColumn(cidx).getNextElement();
		int size=0;
		if (current==null)
		{
			return null;
		}
		else
		{
			//Go through once to get the number of nodes
			while (current!=null)
			{
				++size;
				current=current.getNextRow();
			}
		}
		int [] ColumnValues=new int [size];
		int counter=0;
		current= findColumn(cidx).getNextElement();
		while (current!=null)
		{
			ColumnValues[counter]=current.getValue();
			current=current.getNextRow();
			++counter;
		}
		return ColumnValues;
	}
//===========================================PART 2: Extra Credit==============================================================
	public SparseM addition(SparseM otherM)
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			System.err.println("INVALID ENTRY! MATRICIES NOT THE SAME SIZE!");
			return null;
		}
		LLSparseM Answer= new LLSparseM(this.ncols(),this.nrows());
		SparseMRow ARow = rowHead;
		SparseMRow BRow = ((LLSparseM) otherM).getRowHead();
		//Traverse Both Matrixes
		while (ARow!=null && BRow!=null)
		{
			if (ARow.getRowID() < BRow.getRowID())
			{
				//I am essentially going to copy and paste this Row from Matrix A to the new Matrix.
				SparseMNode NodeA=ARow.getNextElement();
				while (NodeA!=null)
				{
					Answer.append(new SparseMNode(NodeA.getRowID(),NodeA.getColumnID(),NodeA.getValue()));
					NodeA=NodeA.getNextColumn();
				}
				ARow=ARow.getNextRow();
			}
			else if (ARow.getRowID() > BRow.getRowID())
			{
				//I am essentially going to copy and paste this Row from Matrix B to the new Matrix.
				SparseMNode NodeB=BRow.getNextElement();
				while (NodeB!=null)
				{
					Answer.append(new SparseMNode(NodeB.getRowID(),NodeB.getColumnID(),NodeB.getValue()));
					NodeB=NodeB.getNextColumn();
				}
				BRow=BRow.getNextRow();
			}
			else //Both are equal..therefore I can use something very similar to the Sparse Vector
			{
				SparseMNode NodeA= ARow.getNextElement();
				SparseMNode NodeB= BRow.getNextElement();
				while (NodeA!=null && NodeB!=null)
				{
					if (NodeA.getColumnID() > NodeB.getColumnID())
					{
						Answer.append(new SparseMNode(NodeB.getRowID(), NodeB.getColumnID() , NodeB.getValue()));
						NodeB=NodeB.getNextColumn();
					}
					
					else if (NodeA.getColumnID() < NodeB.getColumnID())
					{
						Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID() , NodeA.getValue()));
						NodeA=NodeA.getNextColumn();
						continue;
					}
					else //NodeA.getColumnID() == NodeB.getColumnID()
					{
						Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID() , NodeA.getValue() + NodeB.getValue()));
						NodeA=NodeA.getNextColumn();
						NodeB=NodeB.getNextColumn();
						continue;
					}
				}
				//Take care of any stranded columns.
				while (NodeA!=null)
				{
					Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID(),NodeA.getValue()));
					NodeA=NodeA.getNextColumn();
				}
				
				while (NodeB!=null)
				{
					Answer.append(new SparseMNode(NodeB.getRowID(), NodeB.getColumnID(), NodeB.getValue()));
					NodeB=NodeB.getNextColumn();
				}
				ARow=ARow.getNextRow();
				BRow=BRow.getNextRow();
			}
		}//END OF WHILE LOOP OF AROW AND BROW
	//Take care of any stranded Rows. 
		while (ARow!=null)
		{
			SparseMNode NodeA=ARow.getNextElement();
			while (NodeA!=null)
			{
				Answer.append(new SparseMNode(NodeA.getRowID(),NodeA.getColumnID(), NodeA.getValue()));
				NodeA=NodeA.getNextColumn();
			}
			ARow=ARow.getNextRow();
		}
		
		while (BRow!=null)
		{
			SparseMNode NodeB=BRow.getNextElement();
			while (NodeB!=null)
			{
				Answer.append(new SparseMNode(NodeB.getRowID(),NodeB.getColumnID(), NodeB.getValue()));
				NodeB=NodeB.getNextColumn();
			}
			BRow=BRow.getNextRow();
		}
	
		return Answer;
	}
	
	public SparseM subtraction(SparseM otherM)
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			System.err.println("INVALID ENTRY! MATRICIES NOT THE SAME SIZE!");
			return null;
		}
		LLSparseM Answer=new LLSparseM(this.nrows(),this.ncols());
		SparseMRow ARow = rowHead;
		SparseMRow BRow =((LLSparseM) otherM).getRowHead();
		//Traverse Both Matrixes
		while (ARow!=null && BRow!=null)
		{
			if (ARow.getRowID() < BRow.getRowID())
			{
				SparseMNode NodeA=ARow.getNextElement();
				while (NodeA!=null)
				{
					Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID(), NodeA.getValue()));
					NodeA=NodeA.getNextColumn();
				}
				ARow=ARow.getNextRow();
			}
			else if (ARow.getRowID() > BRow.getRowID())
			{
				SparseMNode NodeB=BRow.getNextElement();
				while (NodeB!=null)
				{
					Answer.append(new SparseMNode(NodeB.getRowID(), NodeB.getColumnID(), -1*NodeB.getValue()));
					NodeB=NodeB.getNextColumn();
				}
				BRow=BRow.getNextRow();
			}
			else //As both Rows are Equal I can proceed using a similar method to the Sparse Vector
			{
				SparseMNode NodeA= ARow.getNextElement();
				SparseMNode NodeB= BRow.getNextElement();
				while (NodeA!=null && NodeB!=null)
				{
					if (NodeA.getColumnID()==NodeB.getColumnID())
					{
						if (NodeA.getValue()-NodeB.getValue()==0)
						{
							NodeA=NodeA.getNextColumn();
							NodeB=NodeB.getNextColumn();
							continue;
						}
						Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID() , NodeA.getValue() - NodeB.getValue()));
						NodeA=NodeA.getNextColumn();
						NodeB=NodeB.getNextColumn();
					}
					else if (NodeA.getColumnID() < NodeB.getColumnID())
					{
						Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID(), NodeA.getValue()));
						NodeA=NodeA.getNextColumn();
					}
					else //NodeA.getColumnID() > NodeB.getColumnID()
					{
						Answer.append(new SparseMNode(NodeB.getRowID(), NodeB.getColumnID(), -1*NodeB.getValue()));
						NodeB=NodeB.getNextColumn();
					}
				}
				//Take care of any stranded columns.
				while (NodeA!=null)
				{
					Answer.append(new SparseMNode(NodeA.getRowID(), NodeA.getColumnID(), NodeA.getValue()));
					NodeA=NodeA.getNextColumn();
				}
				
				while (NodeB!=null)
				{
					Answer.append(new SparseMNode(NodeB.getRowID(), NodeB.getColumnID(), -1*NodeB.getValue()));
					NodeB=NodeB.getNextColumn();
				}
				ARow=ARow.getNextRow();
				BRow=BRow.getNextRow();
			}
		}
		//Take care of any stranded Rows. So...Which one is Leftover?
		while (ARow!=null)
		{
			SparseMNode NodeA=ARow.getNextElement();
			while (NodeA!=null)
			{
				Answer.append(new SparseMNode(NodeA.getRowID(),NodeA.getColumnID(),NodeA.getValue()));
				NodeA=NodeA.getNextColumn();
			}
			ARow=ARow.getNextRow();
		}
		
		while (BRow!=null)
		{
			SparseMNode NodeB=BRow.getNextElement();
			while (NodeB!=null)
			{
				Answer.append(new SparseMNode(NodeB.getRowID(),NodeB.getColumnID(),-1*NodeB.getValue()));
				NodeB=NodeB.getNextColumn();
			}
			BRow=BRow.getNextRow();
		}
		return Answer;
	}

	public SparseM multiplication(SparseM otherM)
	{
		if (this.nrows()!=otherM.nrows() && this.ncols()!=otherM.ncols())
		{
			System.err.println("INVALID ENTRY! MATRICIES NOT THE SAME SIZE!");
			return null;
		}
		LLSparseM Answer=new LLSparseM(this.nrows(),this.ncols());
		SparseMRow ARow = this.getRowHead();
		SparseMRow	BRow =((LLSparseM) otherM).getRowHead();
		while (ARow!=null && BRow!=null)
		{
			if (ARow.getRowID() < BRow.getRowID())
			{
				ARow=ARow.getNextRow(); //I can just skip as all values will be multiplied by 0.
			}
			else if (ARow.getRowID() > BRow.getRowID())
			{
				BRow=BRow.getNextRow(); //I can just skip as all values will be multiplied by 0.
			}
			else//They are equal so therefore there are Node Values to multiply
			{
				SparseMNode NodeA= ARow.getNextElement();
				SparseMNode NodeB= BRow.getNextElement();
				while (NodeA!=null && NodeB!=null)
				{
					if (NodeA.getColumnID()==NodeB.getColumnID())
					{
						Answer.append(new SparseMNode(NodeA.getRowID(),NodeA.getColumnID(),NodeA.getValue()*NodeB.getValue()));
						NodeA=NodeA.getNextColumn();
						NodeB=NodeB.getNextColumn();
					}
					else if (NodeA.getColumnID() < NodeB.getColumnID())
					{
						NodeA=NodeA.getNextColumn();
					}
					else //NodeA.getColumnID() > NodeB.getColumnID()
					{
						NodeB=NodeB.getNextColumn();
					}
				}
				ARow=ARow.getNextRow();
				BRow=BRow.getNextRow();
			}
		}
		//I do not check any leftover nodes as any leftover nodes will be multiplied by 0 (Deleted).
		return Answer;
	}
//==============================================END OF EXTRA CREDIT==================================================================================

//=====================Other Methods I made-===================================================================
	public void printMatrix()
	{
		System.out.println("Matrix Print...");
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
			int RowCOUNT=0;
			while (Row !=null)
			{
				if (Row.getRowID()==RowCOUNT)
				{
					SparseMNode Column=Row.getNextElement();
					int col=0;
					while (Column != null)
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
						for (int i=0;i< COLUMNS-col;i++)
						{
							System.out.print("0");
						}
					}
					System.out.println(" ");
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
					++RowCOUNT;
					System.out.println(" ");
				}
			}//End of While (Printing Rows).
			for (int j=0;j<ROWS-RowCOUNT;j++)//To Print BLANK Rows.
			{
				for (int i=0;i<COLUMNS;i++)
				{
					System.out.print("0");
				}
				System.out.println(" ");
			}
		}
	}
	
	protected boolean outofBounds(int row, int column)
	{
		if (row >= ROWS || row < 0 || column < 0 || column >= COLUMNS)
		{
//This is because I am counting from 0 to Row/Column - 1
//Instead of 1 to Column/Row	
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
		
	public void printAllNodesByRow()
	{
		System.out.println("Print by Row");
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
	
	public void printAllNodesByColumn()
	{
		System.out.println("Print by Column");
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
		SparseMNode NEWNODE=new SparseMNode(rowID,columnID,value);
		if (rowID < rowHead.getRowID())
		{
			rowHead = new SparseMRow(rowID,rowHead,NEWNODE);//NEW ROWHEAD! (RowID, nextRow, nextColumn)
			rowTail=rowHead;
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
		rowTail=previousRow.getNextRow();
		columnInsert(rowID,columnID,NEWNODE);
		++numofRows;
		return;
	}
	
	private void columnInsert (int rowID, int columnID, SparseMNode NEWNODE)
	{
//NOTE THE InsertRow Method already created the new Node. This is designed to fix the Columns Linked List.
		if (columnID < columnHead.getColumnID())
		{
			columnHead = new SparseMColumn(columnID,columnHead,NEWNODE);//NEW COLUMNHEAD! (CIDX, nextRow, nextColumn)
			columnTail=columnHead;//This is just a dummy node.
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
			}
			else if(currentColumn.getColumnID()==columnID)
			{
				existingColumnNodefix(rowID,NEWNODE,currentColumn);
				return;
			}
			else//columnID < currentColumn.getColumnID
			{
				previousColumn.setNextColumn(new SparseMColumn(columnID,currentColumn,NEWNODE));//New Column: CIDX, NextRow, NextColumn
				++numofColumns;
				return;
			}
		}
		//CurrentColumn is the last column. CID is bigger than all existing columns, build a new column.
		previousColumn.setNextColumn(new SparseMColumn(columnID,null,NEWNODE));//NEW COLUMN!!! (columnID, NextRow, NextColumn)
		columnTail=previousColumn.getNextColumn();
		++numofColumns;
		return;
	}
	
	public void append(SparseMNode APPENDNODE)
	{
//I use this only if I know when I add rows, I am adding in ascending order.
		if (numofElements==0)
		{
			rowHead=new SparseMRow(APPENDNODE.getRowID(),null,APPENDNODE);//ROW CONS: ROWID, NEXT Row, NEXT Column
			columnHead=new SparseMColumn(APPENDNODE.getColumnID(),null,APPENDNODE);//COLUMN CONS: COLID, NextRow Next Column
			rowTail=rowHead;
			columnTail=columnHead;
			LastAppendNODE=APPENDNODE;
			++numofElements;
			++numofRows;
			++numofColumns;
			return;
		}
		SparseMColumn AppendCOL=findColumn(APPENDNODE.getColumnID());//ColumnID of New Node to be Added
		if (AppendCOL!=null && LastAppendNODE.getColumnID()==APPENDNODE.getColumnID())
		{
			//If I can save time...Great!!
			LastAppendNODE.setNextRow(APPENDNODE);
			++numofElements;
			AppendROW(APPENDNODE);
			return;
		}
		if (AppendCOL!=null)//The Column Already Exists for New Node. Insert.
		{
			//I know I have to travel down the column to append the Node.
			//Unfortunately there are no shortcuts here so I just recycle a method O(n).
			existingColumnNodefix(APPENDNODE.getRowID(),APPENDNODE,AppendCOL);
			++numofElements;
			AppendROW(APPENDNODE);
			return;
		}
		if (APPENDNODE.getColumnID() > columnTail.getColumnID())
		{
			//New Column must be created for the New Node After Column Tail.
			columnTail.setNextColumn(new SparseMColumn(APPENDNODE.getColumnID(),null,APPENDNODE));
			columnTail=columnTail.getNextColumn();
			++numofColumns;
			++numofElements;
			AppendROW(APPENDNODE);
			return;
		}
		
		else if (AppendCOL==null)//Build new Column Before Column Tail.
		{
			columnInsert(APPENDNODE.getRowID(),APPENDNODE.getColumnID(),APPENDNODE);
			return;
		}
	}
	
	private void AppendROW(SparseMNode APPENDNODE)
	{
		SparseMRow ROW=findRow(APPENDNODE.getRowID());
		if (ROW==null)
		{
			rowTail.setNextRow(new SparseMRow(APPENDNODE.getRowID(),null,APPENDNODE));
			rowTail=rowTail.getNextRow();
			LastAppendNODE=rowTail.getNextElement();
			++numofRows;
			return;
		}
		//Case 2: Append at the end. Make sure you update NodeTail
		LastAppendNODE.setNextColumn(APPENDNODE);
		LastAppendNODE=APPENDNODE;
		return;
	}
	
	public void info()
	{
		System.out.println("This Sparse Matrix is a " + ROWS + " X " + COLUMNS + " Matrix");
		if (numofElements==0)
		{
			System.out.println("This Sparse Matrix is EMPTY!!!");
			return;
		}
		System.out.println("Row Head RowID: "+ rowHead.getRowID());//ROW HEAD, ROW ID
		System.out.println("Column Head Column ID: "+ columnHead.getColumnID());//COLUMN HEAD, COLUMN ID
		System.out.println("Row Tail RowID: "+ rowTail.getRowID());//ROW TAIL, ROW ID
		System.out.println("Column Tail Column ID: "+ columnTail.getColumnID());//COLUMN TAIL, COLUMN ID
		System.out.println("Active Rows: " + numofRows + " Active Columns " + numofColumns + " and " + numofElements + " Non-Zero Elements");
	}

}//END OF CLASS