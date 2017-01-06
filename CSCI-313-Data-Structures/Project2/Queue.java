
public class Queue extends MyLinkedList
{
	private Node tail;
	
	public Queue()
	{
		super();
	}
		
	public Queue(int x)
	{
		super(x);
		++count;
	}
		
	public void insert(int x)
	{
		//Head will always be the Far Left Node. Tail must always Change.
		if (count == 0)
		{
			head=new Node(x);
			tail=head;
			++count;
			return;
		}
		if (count==1)//If only the head node exists.
		{
			tail = new Node (x);
			head.setNext(tail);
			++count;//increment count.
			return; 
		}
			
		if (count > 1)
		{
			Node temp = new Node (x);
			tail.setNext(temp);
			tail=temp;//Or tail.getNext()
			++count; //increment count.
			return;
		}
	}
		
	public int pull()
	{
		int answer=head.getData();
		head=head.getNext();
		--count;
		return answer;
	}

	public void clearAll() 
	{
		while(!isEmpty())
		{
			deleteHead();
		}
	}
		
	public int peek()
	{
		return head.getData();
	}
		
	public int peekTail()
	{
		return tail.getData();
	}
	
	public void deleteHead()
	{
		if (count > 1)
		{
			head=head.getNext();
			--count;
			return;
		}
		if (count==1)
		{
			head=null;
			tail=null;
			--count;
			return;
		}
		if (count==0)
		{
			return;
		}
	}
	
	public void printHead()
	{
		System.out.println("Head: " + head.getData());
	}
	
	public void printTail()
	{
		System.out.println("Tail: " + tail.getData());
	}
	
	public void deleteTail()
	{
		if (count==1)
		{
			head=null;
			tail=null;
			--count;
			return;
		}
		Node current=head;
		while (current.getNext()!=tail)
		{
			current=current.getNext();
		}
		tail=current;
		tail.setNext(null);
		--count;
		return;
	}
		
	public Queue createCopy()
	{
		Queue clone = new Queue();
		Node current = head;
		while (current!=null)
		{
			clone.insert(current.getData());
			current=current.getNext();
		}
		return clone;
	}
	
	public boolean contains(int x)
	{
		Node current = head;
		while (current!=null)
		{
			if (current.getData()==x)
			{
				return true;
			}
			current=current.getNext();
		}
		return false;
	}
	
	public void appendTail(int x)
	{
		Node APPEND = new Node (x);
		tail.setNext(APPEND);
		tail=APPEND;
		++count;
		return;
	}
	
	public boolean isEmpty() {return (count==0);}
}

