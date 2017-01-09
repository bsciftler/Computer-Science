
public class LinkedList
{
	public class Node
	{
		private Node next;
		private Node previous;
		public Node(int [] x, Node n, Node p)
		{
			
		}
	}
	
	private Node head;
	private Node tail;
	
	public LinkedList (int [] x)
	{
		head=new Node(x,null,null);
	}
}
