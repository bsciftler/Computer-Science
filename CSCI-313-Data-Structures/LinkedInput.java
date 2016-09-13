
public class LinkedInput
{
	public static void main (String [] args)
	{
	/*
		LinkedList A= new LinkedList(5,7);
		A.append(4, 17);
		A.append(3, 31);
		A.append(30, 40);
		LinkedList B= new LinkedList(5,3);
		B.append(25, 77);
		B.append(88, 13);
		System.out.println("LINKED LIST A:");
		A.print();
		System.out.println("LINKED LIST B:");
		B.print();
		System.out.println("LINKED LIST C:");
		LinkedList C= LinkedList.Combine(A,B);
		System.out.println("LINKED LIST C PRINT");
		C.print();
		System.out.println(C.getSize());
		A.sort();
		System.out.println("=================Sorted A===================================");
	*/
		
		SparseVector Lion=new SparseVector(8);
		try
		{
			Lion.set(4,10);
			Lion.set(7,15);
			Lion.set(9,140);
			Lion.set(10,14);
			Lion.set(15,116);
			//Lion.set(100, 0);
		}
		catch (VectorException V)
		{
			return;
		}
		Lion.print();
	}
}
