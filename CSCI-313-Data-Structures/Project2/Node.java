public class Node 
{ 
	
	private int data; 
	private Node next; 
	//We use instance variables NOT static 
	//Because there are many Nodes.
	public Node(int d) //Crate Node
	{
		data = d; //Place integer value in the Object
	}
	public Node(int d, Node n) //Create Node
	{
		data = d; //Value inside the Object
		next = n; //Point to the Next Node
	}
	public int getData(){return data;} //Get Value
	public Node getNext(){return next;} //Get the Next Node Object
	public void setNext(Node n){next=n;} //Change the Pointer
}