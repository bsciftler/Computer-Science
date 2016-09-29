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
	
//Basic Row Node
	public SparseMNode(int row, SparseMNode NextRow, SparseMNode NextColumn)
	{
		rowID=row;
		nextRow=NextRow;
		nextColumn=NextColumn;
	}
	
//Basic Column Node
	public SparseMNode(SparseMNode NextRow, SparseMNode NextColumn, int column)
	{
		nextRow=NextRow;
		nextColumn=NextColumn;
		columnID=column;
	}
	
	//Get Methods
	public int getValue() {return value;}
	public int getColumnID () {return columnID;}
	public int getRowID() {return rowID;}
	public SparseMNode getNextRow() {return nextRow;}
	public SparseMNode getNextColumn() {return nextColumn;}
	
	//Set Methods
	public void setValue(int val){value=val;}
	public void setNextRow(SparseMNode C){nextColumn=C;}	
	public void setNextColumn(SparseMNode R){nextRow=R;}	
}