public class SparseMNode
{
	private int value;
	private int rowID;
	private int columnID;
	private SparseMNode nextRow;
	private SparseMNode nextColumn;
	
//General Purpose Node
	public SparseMNode (int row, int column, int newValue, SparseMNode NR, SparseMNode NC)
	{
		value=newValue;
		rowID=row;
		columnID=column;
		nextRow=NR;
		nextColumn=NC;
	}
	
	public SparseMNode (int row, int column, int value)
	{
		this(row,column,value,null,null);
	}
	
	//Get Methods
	public int getValue() {return value;}
	public int getColumnID () {return columnID;}
	public int getRowID() {return rowID;}
	public SparseMNode getNextRow() {return nextRow;}
	public SparseMNode getNextColumn() {return nextColumn;}
	
	//Set Methods
	public void setValue(int val){value=val;}
	public void setNextRow(SparseMNode R){nextRow=R;}	
	public void setNextColumn(SparseMNode C){nextColumn=C;}	
}

