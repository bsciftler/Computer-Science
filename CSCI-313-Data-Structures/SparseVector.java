import javax.swing.JOptionPane;

public class SparseVector 
{
	private Node head;
	private int ID;
	private int SIZE;
	private int dataSize=0;
	private int value;
	
	public SparseVector(int newSize)
	{
		SIZE=newSize;//Using all caps, as I am treating this as "final".
	}
	
	public Node getHead()
	{
		return head;
	}
	
	public void print()
	{
		if (dataSize==0)
		{
			JOptionPane.showMessageDialog(null, "THE SPARSE VECTOR IS EMPTY!!!!");
			return;
		}
		Node current=head;
		//int counter=0;
		while (current!=null)
		{
			//++counter;
			//System.out.println("Node # " + counter );
			System.out.println("Node ID# " + current.getID() );
			System.out.println("Node Value " + current.getValue());
			System.out.println(" ");
			current=current.getNext();
		}
		return;
	}
	
	public void set (int newID, int newValue) throws VectorException
	{
		if (newValue==0)
		{
			if (delete(newID))
			{
				JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" has been deleted!");
				--dataSize;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" does not exist!");
			}
			return;
		}
		
		if (dataSize >= SIZE)
		{
			throw new VectorException("OVERFLOW!!!");
		}
		
		if (dataSize == 0)
		{
			head=new Node (newID, newValue);
			++dataSize;
		}
		
		if (dataSize >= 1)
		{
			insert(newID,newValue);
		}
	}
	
	private void insert(int newID, int newValue)
	{
		if(newID <= head.getID())
		{
			head = new Node(newID, newValue, head); //Append before the Head
			head.getNext().setPrevious(head);
			return;
		}
		
		Node current = head;
		while(current.getNext()!=null) //Case 2: Append in between
		{
			current = current.getNext();
			if(current.getID() > current.getID())
			{
				continue;
			}
			else 
			{
				current.getPrevious().setNext(new Node(newID, newValue,current, current.getPrevious())); // case 2
				current.setPrevious(current.getPrevious().getNext());
				return;
			}
		}// while
		current.setNext(new Node(newID, newValue, null, current));//Case 3: Append at the end
		++dataSize;
	}
	
	public int getValue(int searchID)
	{
		if (find(searchID)==null)
		{
			return 0;
		}
		else
		{
			return find(searchID).getValue();
		}
	}
	
	private void append (Node input)
	{
		Node current=head;
		while (current.getNext()!=null)
		{
			current=current.getNext();
		}
		input.setNext(null);
		input.setPrevious(current);
	}
	
	public void add (SparseVector B)
	{
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				CurrentA.setValue(CurrentA.getID()+CurrentB.getID());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
		}
//==================LEFTOVER NODES CHECK================
		if (CurrentA==null)
		{
			while (CurrentB!=null)
			{
				this.append(CurrentB);
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)
		{
			while (CurrentA!=null)
			{
				this.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
	}
	
	public void subtract (SparseVector B)
	{
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue() - CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					//OR YOU DELETE???
					continue;
				}
				else
				{
					this.insert(CurrentA.getID(), CurrentA.getID() * CurrentB.getID());
					//OR DO I DELETE?
					continue;
				}
			}
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
		}
		//==================LEFTOVER NODES CHECK================
		if (CurrentA==null)
		{
			while (CurrentB!=null)
			{
				this.append(CurrentB);
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)
		{
			while (CurrentA!=null)
			{
				this.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
	}
	
	public void multiply (SparseVector B)
	{
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue()==0 || CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					//OR YOU DELETE???
					continue;
				}
				else
				{
					this.insert(CurrentA.getID(), CurrentA.getID() * CurrentB.getID());
					//OR DO I DELETE?
					continue;
				}
			}
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
		}
//==================LEFTOVER NODES CHECK================
		if (CurrentA==null)
		{
			while (CurrentB!=null)
			{
				if (CurrentB.getValue()==0)
				{
					CurrentB=CurrentB.getNext();
					continue;
				}
				this.append(CurrentB);
				CurrentB=CurrentB.getNext();
			}	
		}
		if (CurrentB==null)
		{
			while (CurrentA!=null)
			{
				if (CurrentA.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					continue;
				}
				this.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
	}
	
	public void divide (SparseVector B)
	{
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue()==0 || CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					//OR YOU DELETE???
					continue;
				}
				else
				{
					this.insert(CurrentA.getID(), CurrentA.getID() * CurrentB.getID());
					//OR DO I DELETE?
					continue;
				}
			}
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
		}
		//==================LEFTOVER NODES CHECK================
				if (CurrentA==null)
				{
					while (CurrentB!=null)
					{
						if (CurrentB.getValue()==0)
						{
							CurrentB=CurrentB.getNext();
							continue;
						}
						this.append(CurrentB);
						CurrentB=CurrentB.getNext();
					}
				}
				if (CurrentB==null)
				{
					while (CurrentA!=null)
					{
						if (CurrentA.getValue()==0)
						{
							CurrentA=CurrentA.getNext();
							continue;
						}
						this.append(CurrentA);
						CurrentA=CurrentA.getNext();
					}
				}
	}
	
	private Node find(int newID)
	{
//THIS WORKS ASSUMING THE LIST IS SORTED IN ASCENDING ORDER!!
		Node current = head;
		while (current!=null)
		{
			if(current.getID()==newID)
			{
				return current;
			} 
			else if(current.getID()< newID)
			{
				current = current.getNext();
			} 
			else 
			{
				current = null;
			}
		}
		return current;
	}

	private boolean delete(int newID)
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
