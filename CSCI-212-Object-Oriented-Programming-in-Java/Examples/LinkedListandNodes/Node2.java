public class Node2 {
	
	private int data;
	private Node2 next;
	private Node2 previous;
	
	public Node2(int d)
	{
		data = d;
	}
	
	public Node2(int d, Node2 n)
	{
		data = d;
		next = n;
	}

	public Node2(int d, Node2 n, Node2 p)
	{
		data = d;
		next = n;
		previous = p;
	}
	
	public int getData(){return data;}
	public Node2 getNext(){return next;}
	public Node2 getPrevious() {return previous;}

	public void setNext(Node2 n){next = n;}
	public void setPrevious(Node2 p) {previous = p;}
}
