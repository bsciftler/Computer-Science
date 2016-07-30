public class SudokuSquare
{
	//Data Members
	private int row=0;
	private int column=0;
	private int value=0;
	boolean locked=true;
	
	//Acceptable values for "value".
	final int MINVALUE=0;//Lowest Row/Column Value
	final int MAXVALUE=4;//Highest Value Number in this game
	
	//Constructor 
	public SudokuSquare (int newRow, int newColumn, int newValue, boolean Locked)
	{
//If Locked is true, you can NOT change Value
//If Locked is False, you can change Value
		if (newRow >= 0 && newRow <= MAXVALUE-1) {row=newRow;}
		if (newColumn >= 0 && newColumn <= MAXVALUE-1) {column=newColumn;}
		value=checkValue(newValue);
		locked=Locked;
	}
	
	//Access Methods
	public int getRow(){return row;}
	public int getColumn(){return column;}
	public int getValue(){return value;}
	public boolean isLocked(){return locked;}
	
	public void setValue(int newV){value=checkValue(newV);}
	
	//Another way to check numbers.
	//Automatically set to 0 if it is invalid
	public int checkValue(int given)
	{
		int result=0;
		if (given >=MINVALUE && given <=MAXVALUE)
		{
			result=given;
		}
		return result;
	}
}