
public class Node3
{
	private int ID;
	private int value;
	private Node3 next;

	public Node3 (int newID, int newValue)
	{
		ID=newID;
		value=newValue;
	}
	
	public Node3 (int newID, int newValue, Node3 n)
	{
		ID=newID;
		value=newValue;
		next=n;
	}
	//Get Methods
	public int getID(){return ID;}
	public int getValue() {return value;}
	public Node3 getNext() {return next;}
	
	//Set Methods
	public void setID(int id){ID=id;};
	public void setValue(int val){value=val;}
	public void setNext(Node3 N){next=N;}	
}

