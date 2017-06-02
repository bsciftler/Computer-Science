public class RationalSparseMNode
{
	private Rational value;
	private int rowID;
	private int columnID;
	private RationalSparseMNode nextRow;
	private RationalSparseMNode nextColumn;
	
//General Purpose Node
	public RationalSparseMNode (int row, int column, Rational newValue, RationalSparseMNode NR, RationalSparseMNode NC)
	{
		value=newValue;
		rowID=row;
		columnID=column;
		nextRow=NR;
		nextColumn=NC;
	}
	
	public RationalSparseMNode (int row, int column, Rational value)
	{
		this(row,column,value,null,null);
	}
	
	//Get Methods
	public Rational getRational() {return value;}
	public int getColumnID () {return columnID;}
	public int getRowID() {return rowID;}
	public RationalSparseMNode getNextRow() {return nextRow;}
	public RationalSparseMNode getNextColumn() {return nextColumn;}
	
	//Set Methods
	public void setValue(Rational val){value=val;}
	public void setNextRow(RationalSparseMNode R){nextRow=R;}	
	public void setNextColumn(RationalSparseMNode C){nextColumn=C;}	
}
