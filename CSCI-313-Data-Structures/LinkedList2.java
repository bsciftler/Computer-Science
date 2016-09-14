
public abstract class LinkedList2 
{
	protected Node head;
	protected int SIZE;
	
	public Node getHead()
	{
		return head;
	}
	
	protected void append (Node input)
	{
		Node current=head;
		while (current.getNext()!=null)
		{
			current=current.getNext();
		}
		input.setNext(null);
		input.setPrevious(current);
	}
	
	protected int getSize(){return SIZE;}
	
	protected abstract Node find(int x);
	
	protected boolean delete(int newID)
	{
		Node found = find(newID);
		if (found == null)
		{
			return false;
		}
		if(found.getPrevious()!=null)
		{
			found.getPrevious().setNext(found.getNext());
		} 
		else
		{
			head = head.getNext();
		}
		if(found.getNext()!=null)
		{
			found.getNext().setPrevious(found.getPrevious());
		}
		return true;
	}
}
