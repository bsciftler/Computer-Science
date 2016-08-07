public class Homework4
{

	public static void main (String[] args)
	{
		Node2 n= new Node2(19); //First Node
		LinkedList2 Trials= new LinkedList2(n);
		Trials.append(8);
		Trials.append(17);
		Trials.append(5);
		Trials.append(6);
		Trials.print();
		Node2 f=new Node2(8); 
		System.out.println("The minimum number node f to end is: "+ Trials.findMin(f).getData());
		int size=Trials.getSize();
		for (int i=0;i<size-1;i++)
		{
			Node2 current = n;
			while (current!=null)
			{
				Node2 a=Trials.findMin(current);
				if (current.getNext()!= null)
				{
					Trials.move(a, n);
					//System.out.println("Current:" + current.getData());
					//System.out.println("Min: " + a.getData());
				}
			current=current.getNext();
			Trials.print();
			}
		}
	//Print Sorted Linked List.
	System.out.println("Newly sorted Linked List: ");
	Trials.print();
		
	}//End of Main


}//End of Class