
public class Stack extends LinkedList {
//Imagine if this was a stack of Cards.
//Head is the top most Node.
//Stack is LIFO
//Last In First Out

	public Stack (int x)
	{
		super(x); //Copied from Linked List 
	}
	
	public Stack (Node n) //Copied from Linked List setNext()
	{
		super(n);
		count++; //Head means one Node
	}
	
	public void insert(int x)
	{
		//Head MUST ALWAYS change as there is a new node appended.
		head=new Node(x, head);
		count++;
		//The new value is the head (top of the stack).
		return;
	}
	
	public boolean delete(int x) //HOW MANY NODES WILL BE DELETED FROM STACK?
	{		
		if (x > count)
		{
			x=count;
//You can't delete more Nodes that Nodes that exist in the stack.
		}
		
		for (int i=0;i<x;i++)
		{
//This for loop will automatically break if x < 0.
//No need to check if x < 0.
			head = head.getNext(); 
	//This is how to delete head.
	//If a Node is alone it will automatically be deleted.
			count= count-1;
		}
		return true;
	}
	
	public void clearAll()
	{
		Node current=head;
		while (current!=null)
		{
			System.out.print(current.getData() + " "); //Print
			delete(1); //Delete 1 Node at a time
			current=current.getNext(); //How to delete
		}
	}
	
}


