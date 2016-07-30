//September
public abstract class LinkedList {
//Protected means that both LinkedList and child classes can USE these values
	protected Node head;
	protected int count=0;
	
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
		System.out.println(" ");
	}
	
	public void insert(int x)
	{
		
		if(x<=head.getData())
		{ 
			head = new Node(x, head); //You are creating a new node with value X=1. the next value points to the head (7).
			return;
		}
		Node current = head;
		Node previous = head;
		//x is 18 in the notes. So now it find 31. It will fail. But it is too late now.
		//We create previous. Previous is always 1 behind current.
		while(current!=null)
		{
			if(x>current.getData())
			{//Case 1
				previous = current;
				current = current.getNext();
			} 
			else 
			{
				previous.setNext(new Node(x,current)); // Case 2
				return;
				//First is constructor. the New Node. The Node is x=18. 
				//Current is pointing to 31 for the 18. 
				//You pass into setnext to change the pointer to 18.
				//12 has its pointer changed. 
				//OR, 3 lines
				//Node temp= new Node(x);
				//temp.setNext(current);
				//previous.SetNext(temp); They are the same
			}
		}
		previous.setNext(new Node(x)); // case 3
		//if X=40. Then you do this.
		//We need this because if X=40, you break while loop. So just do this.
	}

	//Adding Nodes
	public boolean delete(int x)
	{
		if(head.getData()==x)
		{
			head = head.getNext(); // case 1
			//This deletes the head. 
			return true; //confirm deletion
		}
		Node current = head.getNext();
		Node previous = head;
		//Getting rid of current. It deletes by changing previous pointer to skip the Node.
		while(current!=null){
			if(current.getData()==x)
			{
				previous.setNext(previous.getNext().getNext()); // cases 2 & 3
				return true;
			} 
			else if(current.getData()>x)
			{ 	//What if the number doesn't exist in the Nodes?
				break;
			} 
			else 
			{
				previous = current;
				current = current.getNext();
			}
		} // while
		return false; //Nothing to delete
	}
	
	public void append(int x)
	{//Append is add to the end
		Node current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext(); //Keep going until no null pointer
		}
		// Node temp = new Node(x);
		// current.setNext(temp);
		current.setNext(new Node(x)); //Create node where the is null pointer.
	}
	
	public int getSize()
	{
		Node current=head;
		while (current!=null)
		{
			count=count+1;
			current=current.getNext();
		}
		return count;
	}
	abstract void clearAll();	
}
