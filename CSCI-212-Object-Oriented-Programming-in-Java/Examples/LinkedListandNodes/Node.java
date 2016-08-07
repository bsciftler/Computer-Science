public class Node {
	
	private int data;
	private Node next;
	
	public Node(int d)
	{
		data = d;
	}
	
	public Node(int d, Node n)
	{
		data = d;
		next = n;
	}
	
	public int getData(){return data;}
	public Node getNext(){return next;}
	public void setNext(Node n){next = n;}
}