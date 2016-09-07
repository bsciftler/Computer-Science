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
		//int count=1;
		while (current!=null)
		{
			//System.out.println("Node # " + count );
			System.out.println("ID #: " + current.getID());
			System.out.println("Value: " + current.getValue());
			//count++;
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
	
	public static LinkedList Combine (LinkedList A, LinkedList B)
	{
		Node3 CurrentA=A.getHead();
		Node3 CurrentB=B.getHead();
	//Step 0: ERROR CHECKING! Empty?
		if(A.getSize()==0)
		{
			return B;
		}
		if (B.getSize()==0)
		{
			return A;
		}
	
	//Step 1: Create the new head which is smallest ID number.
		if (CurrentA.getID() < CurrentB.getID())
		{
			Addition=new LinkedList(CurrentA.getID() , CurrentA.getValue());
			CurrentA=CurrentA.getNext();
			System.out.println("HEAD ID#: "+ Addition.getHead().getID() + " VALUE: "+Addition.getHead().getValue());
		}
		else if (CurrentA.getID() == CurrentB.getID())
		{
			Addition=new LinkedList(CurrentA.getID() , CurrentA.getValue() + CurrentB.getValue());
			CurrentA=CurrentA.getNext();
			CurrentB=CurrentB.getNext();
			System.out.println("HEAD ID# "+ Addition.getHead().getID() + " VALUE: "+ Addition.getHead().getValue());
		}
		else if (CurrentA.getID() > CurrentB.getID())
		{
			Addition=new LinkedList(CurrentB.getID() , CurrentB.getValue());
			CurrentB=CurrentB.getNext();
			System.out.println("HEAD ID# "+ Addition.getHead().getID()+ " VALUE: " +Addition.getHead().getValue());
		}
		int step=0;
	//Step 2: Start appending as if they are equal size
		
		while (CurrentA!=null || CurrentB!=null)
		{
			System.out.println("ENTERING APPEND LOOP Step "+ ++step);
			System.out.println("CURRENT A ID # " + CurrentA.getID() + " VALUE " + CurrentA.getValue());
			System.out.println("CURRENT B ID # " + CurrentB.getID() + " VALUE " + CurrentB.getValue());
			
			if (CurrentA.getID() < CurrentB.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue());
				CurrentA=CurrentA.getNext();
				System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
				continue;//WHY ISNT TIHS GOING BACK TO WHILE???
			}
			
			if (CurrentA.getID()==CurrentB.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue()+CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
				System.out.println("ADDITION ID # " + Addition.getHead().getNext().getID() + " VALUE " + Addition.getHead().getNext().getValue());
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