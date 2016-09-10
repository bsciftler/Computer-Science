public class LinkedList
{
	private Node3 head=null;
	private static LinkedList Addition;
	private int size=0;
	
	public LinkedList(int ID,int val)
	{
		head = new Node3(ID, val);
		size++;
	}

	public LinkedList(Node3 n)
	{
		head = n;
		size++;
	}
	
	public void append(int ID, int val)
	{
		Node3 current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext();
		}
		current.setNext(new Node3(ID,val));
		size++;
	}
	
	public void print()
	{
		Node3 current=head;
		int count=1;
		while (current!=null)
		{
			System.out.println("Node # " + count );
			System.out.println("ID #: " + current.getID());
			System.out.println("Value: " + current.getValue());
			System.out.println(" ");
			++count;
			current=current.getNext();
		}
	}
	
	public Node3 getHead()
	{
		return head;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setHead(Node3 n)
	{
		head=n;
	}
	
	public boolean isSorted()
	{
		Node3 previous=this.getHead();
		Node3 current=this.getHead().getNext();
		while (current!=null)
		{
			if (current.getID() < previous.getID())
			{
				return false;
			}
			previous=previous.getNext();
			current=current.getNext();
		}
		return true;
	}
	
	public void addBetween(Node3 insert,Node3 before, Node3 after)
	{
		insert.setNext(after);
		before.setNext(insert);
		size++;
	}
	
	public Node3 findMinID()
	{
		Node3 min=head;
		Node3 current=head.getNext();
		while(current.getNext()!=null)//Up to Tail
		{
			if (min.getID() > current.getID())
			{
				min=current;
			}
			current=current.getNext();
		}
		if (min.getID() > current.getID())
		{
			min=current;
		}
		return min;
	}
	
	public Node3 findMaxID()
	{
		Node3 max=head;
		Node3 current=head.getNext();
		while(current.getNext()!=null)//Up to Tail
		{
			if (max.getID() < current.getID())
			{
				max=current;
			}
			current=current.getNext();
		}
		if (max.getID() < current.getID())
		{
			max=current;
		}
		return max;
	}
	
	public void sort()
	{
		//Find the Node with Minimum ID
		int size=this.getSize();//Size of Linked List
		for (int i=0;i<size;i++)
		{
			Node3 current=head;
			while (current!=null)
			{
				Node3 max=this.findMaxID();
				System.out.println("THE CURRENT MAX ID IS: " + max.getID());
				if(current.getNext()!=null)
				{
					this.move(max,current);//MOVE EVERYTHING BEHIND HEAD!!
				}
				current=current.getNext();
				this.print();
			}
		}
		return;
	}
	public void move(Node3 max, Node3 CurrentHead)
	{
		Node3 Cut=head;
		if (max==CurrentHead)
		{
			CurrentHead.setNext(head);
			while(Cut.getNext()!=CurrentHead)
			{
				Cut=Cut.getNext();
			}
			Cut.setNext(null);
			head=CurrentHead;
			return;
		}
		if (max.getNext()==null)
		{
			max.setNext(head);
			while(Cut.getNext()!=CurrentHead)
			{
				Cut=Cut.getNext();
			}
			Cut.setNext(null);
			return;
		}
		if (max.getNext()!=null)
		{	
			while(Cut.getNext()!=CurrentHead)
			{
				Cut=Cut.getNext();
			}
			Node3 Dummy=Cut;
			max.setNext(head);
			Dummy.setNext(max.getNext());
			return;
		}
	}
	
	public void Fusion(LinkedList B)//Linked List A is "This" 
//A will be the combination of Both LinkedLists! No new one is created!
	{
		Node3 CurrentA=this.getHead();
		Node3 CurrentB=B.getHead();
	//Step 1: Check for any illegal arguments
		if(this.getSize()==0 || this==null)
		{
			return;
		}
		if (B.getSize()==0 || B==null)
		{
			return;
		}
	//Step 2: Where is the new Head at?
		if (CurrentA.getID() < CurrentB.getID())
		{
			CurrentA=CurrentA.getNext();
		}
		else if (CurrentA.getID() == CurrentB.getID())
		{
			CurrentA.setValue(CurrentA.getValue()+CurrentB.getValue());
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
		}
		else if (CurrentA.getID() > CurrentB.getID())
		{
			CurrentB.setNext(CurrentA);
			this.setHead(CurrentB);
			CurrentB=CurrentB.getNext();
		}
	//Step 2: Start appending as if they are equal size
		while (CurrentA!=null && CurrentB!=null)
//NOTE TO SELF: I had to use AND. If I did Or, False OR True is True. Which can cause NPE.
		{
			if (CurrentA.getID() < CurrentB.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				//System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;
			}
			
			if (CurrentA.getID()==CurrentB.getID())
			{
				CurrentA.setValue(CurrentA.getValue()+CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				continue;
			}
		
			if (CurrentA.getID() > CurrentB.getID())
			{
				Addition.append(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
				//System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;
			}
		}
		
	//Step 3: Check if there are extra LinkedList A or B Nodes. 
		if (CurrentA==null)//A is null, fill remaining B 
		{
			while(CurrentB!=null)
			{
				this.append(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)//if B is null, fill remaining A nodes.
		{
			while (CurrentA!=null)
			{
				this.append(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
			}
		}
	}
	
	
	public static LinkedList Combine (LinkedList A, LinkedList B)
	{
		Node3 CurrentA=A.getHead();
		Node3 CurrentB=B.getHead();
	//Step 0: ERROR CHECKING! Empty?
		if(A.getSize()==0 || A==null)
		{
			return B;
		}
		if (B.getSize()==0 || B==null)
		{
			return A;
		}
	//Are Both Linked Lists sorted?
		if (!(A.isSorted()))
		{
			A.sort();
		}
		if (!(B.isSorted()))
		{
			B.sort();
		}
	
	//Step 1: Create the new head which is smallest ID number.
		if (CurrentA.getID() < CurrentB.getID())
		{
			Addition=new LinkedList(CurrentA.getID() , CurrentA.getValue());
			CurrentA=CurrentA.getNext();
			//System.out.println("HEAD ID#: "+ Addition.getHead().getID() + " VALUE: "+Addition.getHead().getValue());
		}
		else if (CurrentA.getID() == CurrentB.getID())
		{
			Addition=new LinkedList(CurrentA.getID() , CurrentA.getValue() + CurrentB.getValue());
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
			//System.out.println("HEAD ID# "+ Addition.getHead().getID() + " VALUE: "+ Addition.getHead().getValue());
		}
		else if (CurrentA.getID() > CurrentB.getID())
		{
			Addition=new LinkedList(CurrentB.getID() , CurrentB.getValue());
			CurrentB=CurrentB.getNext();
			//System.out.println("HEAD ID# "+ Addition.getHead().getID()+ " VALUE: " +Addition.getHead().getValue());
		}
	//Step 2: Start appending as if they are equal size
		while (CurrentA!=null && CurrentB!=null)
//NOTE TO SELF: I had to use AND. If I did Or, False OR True is True. Which can cause NPE.
		{
			//System.out.println("ENTERING APPEND LOOP Step "+ ++step);
			//System.out.println("CURRENT A ID # " + CurrentA.getID() + " VALUE " + CurrentA.getValue());
			//System.out.println("CURRENT B ID # " + CurrentB.getID() + " VALUE " + CurrentB.getValue());
			
			if (CurrentA.getID() < CurrentB.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				//System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;
			}
			
			if (CurrentA.getID()==CurrentB.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue()+CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				System.out.println("ADDITION ID # " + Addition.getHead().getNext().getValue() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;
			}
		
			if (CurrentA.getID() > CurrentB.getID())
			{
				Addition.append(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
				System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;
			}
		}
		
	//Step 3: Check if there are extra LinkedList A or B Nodes. 
		if (CurrentA==null)//A is null, fill remaining B 
		{
			while(CurrentB!=null)
			{
				Addition.append(CurrentB.getID(), CurrentB.getValue());
				CurrentB=CurrentB.getNext();
			}
		}
		if (CurrentB==null)//if B is null, fill remaining A nodes.
		{
			while (CurrentA!=null)
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
			}
		}
		return Addition;
	}
}