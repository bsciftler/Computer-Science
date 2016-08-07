public class Queue extends LinkedList {
//QUEUE IS A LINE.
//Tail is the Rightward most Node.
//Tail will be the way to append. 
//Head is the Leftward most Node
//Queue is FIFO, First in First Out
private Node tail;
	
	public Queue(int x)
	{
		super(x);
	}
	
	public Queue(Node n)
	{
		super(n);
		count=count+1;
	}
	
	public void insert(int x)
	{
		//Head will always be the Far Left Node. Tail must always Change.
		if (count==1)//If only the head node exists.
		{
			tail = new Node (x);
			head.setNext(tail);
			count=count+1;//increment count.
			return; 
		}
		
		if (count > 1)
		{
			Node temp = new Node (x);
			tail.setNext(temp);
			tail=temp;//Or tail.getNext()
			count=count+1; //increment count.
			return;
		}
	}
	
	public boolean delete(int x) //How many Nodes will be Deleted
	{
		if (x>count)
		{
			x=count;//You can't delete more Nodes than the amount of Nodes that exist.
		}
		
		if (x < 0)
		{
			return false; //You can't delete negative numbers.
		}
		
		Node current=head;
			while (current!=null && x!=0)
			{
				head = head.getNext();
				count=count-1; //update count
				x=x-1;
				current=current.getNext();//travel
			}
		
		return true;
	}

	public void clearAll()
	{
		//Delete EVERYTHING from Head(Left) to Tail (Right)
		Node current=head; 
		while (current!=null)
		{
			System.out.print(current.getData() + " ");
			delete(1);
			current=current.getNext();	
		}
	}
}