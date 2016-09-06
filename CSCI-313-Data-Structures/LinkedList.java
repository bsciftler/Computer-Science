public class LinkedList {

	private Node3 head;
	private static LinkedList Addition;
	private int size=0;
	
	public LinkedList(int ID,int val)
	{
		head = new Node3(ID, val);
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
			count++;
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
	
	public static LinkedList Addition (LinkedList A, LinkedList B)
	{
		Node3 CurrentA;
		Node3 CurrentB;
	//Step 0: ERROR CHECKING! Empty?
	
	//Step 1: Create the new head which is smallest value.
		if (A.getHead().getID() < B.getHead().getID())
		{
			Addition=new LinkedList(A.getHead().getID() , A.getHead().getValue());
			CurrentA=A.getHead().getNext();
			CurrentB=B.getHead();
		}
		else if (A.getHead().getID() == B.getHead().getID())
		{
			Addition=new LinkedList(A.getHead().getID() , A.getHead().getValue()+B.getHead().getValue());
			CurrentA=A.getHead().getNext();
			CurrentB=B.getHead().getNext();
		}
		else
		{
			Addition=new LinkedList(B.getHead().getID() , B.getHead().getValue());
			CurrentA=A.getHead();
			CurrentB=B.getHead().getNext();
		}
		
	//Step 2: Start appending. Assume they are both the same size.
		while (CurrentA.getNext()!=null || CurrentB.getNext()!=null)
		{
			if (CurrentB.getID()==CurrentA.getID())
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue()+CurrentB.getValue());
				CurrentA=CurrentA.getNext();
				CurrentB=CurrentB.getNext();
			}
			else if (CurrentA.getID() < CurrentB.getID())
			{
				Addition.append(A.getHead().getID(), A.getHead().getValue());
				CurrentA=CurrentA.getNext();
			}
			else
			{
				Addition.append(B.getHead().getID(), B.getHead().getValue());
				CurrentB=CurrentB.getNext();
			}
		}
	//Step 3: Check which LinkedList was bigger. From here on out finish up or conclude.
		int diff=A.getSize()-B.getSize();
		if (diff > 0) //A is the bigger Linked List
		{
			while(CurrentA!=null)
			{
				Addition.append(CurrentA.getID(), CurrentA.getValue());
			}
			return Addition;
		}
		else if (diff==0)
		{
			return Addition;
		}
		else //B is the bigger linked List.
		{
			while(CurrentB!=null)
			{
				Addition.append(CurrentB.getID(), CurrentB.getValue());
			}
			return Addition;
		}
	}
}