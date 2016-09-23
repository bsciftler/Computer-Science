import javax.swing.JOptionPane;

public class LLSparseVec implements SparseVec 
{
private LLSparseVecNode head;
private int numOfElements=0;
private int SIZE=0;

	public LLSparseVec(int newSize)
	{
		SIZE=newSize;//Using all caps, as I am treating this as "final".
	}

	public int getLength() 
	{
		return SIZE;
	}

	public int numElements()
	{
		return numOfElements;
	}

	public int getElement(int index)
	{
		if (find(index)==null)
		{
			return 0;
		}
		else
		{
			return find(index).getValue();
		}
	}

	public void clearElement(int newID)
	{
		if (delete(newID))
		{
			JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" has been deleted!");
			--numOfElements;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" does not exist!");
		}
		return;
	}

	public void setElement(int newID, int newValue) throws VectorException
	{
		if (newValue==0)
		{
			if (delete(newID))
			{
				JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" has been deleted!");
				--numOfElements;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Sparse Node with ID "+ newID+" does not exist!");
			}
			return;
		}
		
		if (find(newID)!=null)
		{
			find(newID).setValue(newValue);
			return;
		}
		
		if (numOfElements >= SIZE)
		{
			throw new VectorException("OVERFLOW!!!");
		}
		
		if (numOfElements == 0)
		{
			head=new LLSparseVecNode (newID, newValue);
			++numOfElements;
			return;
		}
		
		if (numOfElements >= 1)
		{
			insert(newID,newValue);
			++numOfElements;
			return;
		}
	}

	public int[] getAllIndices()
	{
		int [] Index=new int[numOfElements];
		LLSparseVecNode current=head;
		int counter=0;
		while (current!=null)
		{
			Index[counter]=current.getValue();
			current=current.getNext();
			++counter;
		}
		return Index;
	}

	public int[] getAllValues()
	{
		int [] values=new int[numOfElements];
		LLSparseVecNode current=head;
		int counter=0;
		while (current!=null)
		{
			values[counter]=current.getID();
			current=current.getNext();
			++counter;
		}
		return values;
	}

	public LLSparseVec addition(SparseVec B) throws VectorException
	{
		if (this.getLength()!=B.getLength())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		LLSparseVec Answer=new LLSparseVec(this.getLength());
		
		LLSparseVecNode CurrentA=head;
		LLSparseVecNode CurrentB=B.getHead();
		
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
				Answer.setElement(CurrentA.getID(), CurrentA.getValue()+ CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				Answer.setElement(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
				Answer.setElement(CurrentB.getID(), CurrentB.getValue());
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

	public LLSparseVec substraction(SparseVec B) throws VectorException
	{
		if (this.getLength()!=B.getLength())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		LLSparseVec Answer=new LLSparseVec(this.getLength());
		LLSparseVecNode CurrentA=head;
		LLSparseVecNode CurrentB=B.getHead();
		
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
				Answer.setElement(CurrentA.getID(), CurrentA.getValue()+ CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				Answer.setElement(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
				Answer.setElement(CurrentB.getID(), CurrentB.getValue());
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

	public LLSparseVec multiplication(SparseVec B) throws VectorException
	{
		if (this.getLength()!=B.getLength())
		{
			throw new VectorException("THE SIZE OF BOTH SPARE VECTORS ARE NOT THE SAME!!");
		}
		LLSparseVec Answer=new LLSparseVec(this.getLength());
		LLSparseVecNode CurrentA=head;
		LLSparseVecNode CurrentB=B.getHead();
		
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
				Answer.setElement(CurrentA.getID(), CurrentA.getValue()* CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
			if (CurrentA.getID() < CurrentB.getID())
			{
				CurrentA=CurrentA.getNext();
				continue;
			}
			if (CurrentA.getID() > CurrentB.getID())
			{
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
		return Answer;
	}
//=================================================EXTRA USED CLASSES==============================
	public LLSparseVecNode getHead()
	{
		return head;
	}
	
	public void append (LLSparseVecNode input)
	{
		LLSparseVecNode current=head;
		while (current.getNext()!=null)
		{
			current=current.getNext();
		}
		input.setNext(null);
		input.setPrevious(current);
	}
	
	public void print()
	{
		if (numOfElements==0)
		{
			JOptionPane.showMessageDialog(null, "THE SPARSE VECTOR IS EMPTY!!!!");
			return;
		}
		LLSparseVecNode current=head;
		//int counter=0;
		while (current!=null)
		{
			//++counter;
			//System.out.println("Node # " + counter );
			System.out.print("Node ID# " + current.getID() + "  ");
			System.out.print("Node Value " + current.getValue());
			System.out.println(" ");
			current=current.getNext();
		}
		return;
	}
	
	
	private void insert(int newID, int newValue)
	{
		if(newID <= head.getID())
		{
			head = new LLSparseVecNode(newID, newValue, head); //Append before the Head
			head.getNext().setPrevious(head);
			return;
		}
		LLSparseVecNode current = head;
		while(current.getNext()!=null) //Case 2: Append in between
		{
			current = current.getNext();
			if(newID > current.getID())
			{
				continue;
			}
			else 
			{
				current.getPrevious().setNext(new LLSparseVecNode(newID, newValue,current, current.getPrevious())); 
				current.setPrevious(current.getPrevious().getNext());
				return;
			}
		}// while
		current.setNext(new LLSparseVecNode(newID, newValue, null, current));//Case 3: Append at the end
	}
	
	private LLSparseVecNode find(int newID)
	{
		LLSparseVecNode current = head;
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
		LLSparseVecNode found = find(newID);
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