public class Node
{
	private int ID;
	private int value;
	private Node next;
	private Node previous;
	
	public Node (int newID, int newValue)
	{
		ID=newID;
		value=newValue;
	}

	public Node (int newID, int newValue, Node n)
	{
		ID=newID;
		value=newValue;
		next=n;
	}
	
	public Node (int newID, int newValue, Node n, Node p)
	{
		ID=newID;
		value=newValue;
		next=n;
		previous=p;
	}
	
	
	//Get Methods
	public int getID(){return ID;}
	public int getValue() {return value;}
	public Node getNext() {return next;}
	public Node getPrevious() {return previous;}
	
	//Set Methods
	public void setID(int id){ID=id;};
	public void setValue(int val){value=val;}
	public void setPrevious (Node P) {previous=P;}
	public void setNext(Node N){next=N;}	
}