import javax.swing.JOptionPane;

public class SparseVector extends LinkedList2
{
	private int dataSize=0;
	
	public SparseVector(int newSize)
	{
		SIZE=newSize;//Using all caps, as I am treating this as "final".
	}
	
	public int getDataSize() { return dataSize;}
	public int getSize() {return SIZE;}
	public int getNumElement() {return dataSize;}
	
	public int [] getAllIDs()
	{
		int [] values=new int[dataSize];
		Node current=head;
		int counter=0;
		while (current!=null)
		{
			values[counter]=current.getValue();
			current=current.getNext();
			++counter;
		}
		return values;
	}
	
	public int [] getAllValues()
	{
		int [] values=new int[dataSize];
		Node current=head;
		int counter=0;
		while (current!=null)
		{
			values[counter]=current.getID();
			current=current.getNext();
			++counter;
		}
		return values;
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
			return;
		}
		
		if (dataSize >= 1)
		{
			insert(newID,newValue);
			++dataSize;
			return;
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
			if(newID > current.getID())
			{
				continue;
			}
			else 
			{
				current.getPrevious().setNext(new Node(newID, newValue,current, current.getPrevious())); 
				current.setPrevious(current.getPrevious().getNext());
				return;
			}
		}// while
		current.setNext(new Node(newID, newValue, null, current));//Case 3: Append at the end
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
	
	protected Node find(int newID)
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
//=============================THREE MATH FUNTIONS=========================================================

	public SparseVector addition (SparseVector B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		SparseVector Answer=new SparseVector(this.getSize());
		
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue()-CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					continue;
				}
				Answer.set(CurrentA.getID(), CurrentA.getValue()+ CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				Answer.set(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
				Answer.set(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
				continue;
			}
		}
//==================LEFTOVER NODES CHECK================
		if (CurrentA==null)
		{
			while (CurrentB!=null)
			{
				Answer.append(CurrentB);
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)
		{
			while (CurrentA!=null)
			{
				Answer.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
		return Answer;
	}
	
	public SparseVector subtract (SparseVector B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		SparseVector Answer=new SparseVector(this.getSize());
		
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue()+CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					continue;
				}
				Answer.set(CurrentA.getID(), CurrentA.getValue()+ CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				Answer.set(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
				Answer.set(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
				continue;
			}
		}
		//==================LEFTOVER NODES CHECK================
		if (CurrentA==null)
		{
			while (CurrentB!=null)
			{
				Answer.append(CurrentB);
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)
		{
			while (CurrentA!=null)
			{
				Answer.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
		return Answer;
	}
	
	public void multiply (SparseVector B) throws VectorException
	{
		if (this.getSize()!=B.getSize())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		SparseVector Answer=new SparseVector(this.getSize());
		Node CurrentA=head;
		Node CurrentB=B.getHead();
		
		while (CurrentA != null && CurrentB!= null)
		{
			if (CurrentA.getID()==CurrentB.getID())
			{
				if (CurrentA.getValue()== 0 || CurrentB.getValue()==0)
				{
					CurrentA=CurrentA.getNext();
					CurrentB=CurrentB.getNext();
					continue;
				}
				Answer.set(CurrentA.getID(), CurrentA.getValue()+ CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				Answer.set(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
				Answer.set(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
				continue;
			}
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
				Answer.append(CurrentB);
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
				Answer.append(CurrentA);
				CurrentA=CurrentA.getNext();
			}
		}
	}
}//END OF CLASS