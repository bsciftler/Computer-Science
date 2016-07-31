public class LinkedList {

	private Node head;
	
	public LinkedList(int x)
	{
		head = new Node(x);
	}
	public LinkedList(Node n)
	{
		head = n;
	}
	
	public void print()
	{
		Node current = head;
		while (current!=null)
		{
			System.out.print(current.getData()+" ");
			current = current.getNext();
		}
		System.out.println();
	}
	
	public void insert(int x)
	{
		if(x<=head.getData())
		{
			head = new Node(x, head); // case 1
			return;
		}
		Node current = head;
		Node previous = head;
		while(current!=null){
			if(x>current.getData())
			{
				previous = current;
				current = current.getNext();
			} 
			else 
			{
				previous.setNext(new Node(x,current)); // case 2
				return;
			}
		}
		previous.setNext(new Node(x)); // case 3
	}

	public boolean delete(int x)
	{
		if(head.getData()==x)
		{
			head = head.getNext(); // case 1
			return true;
		}
		Node current = head.getNext();
		Node previous = head;
		while(current!=null)
		{
			if(current.getData()==x)
			{
				previous.setNext(previous.getNext().getNext()); // cases 2 & 3
				return true;
			} 
			else if(current.getData()>x)
			{
				break;
			} 
			else 
			{
				previous = current;
				current = current.getNext();
			}
		} // while
		return false;
	}
	
	public void append(int x)
	{
		Node current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext();
		}
		current.setNext(new Node(x));
	}
	
}