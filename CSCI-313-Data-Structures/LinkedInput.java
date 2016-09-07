
public class LinkedInput
{
	public static void main (String [] args)
	{
		LinkedList A= new LinkedList(0,7);
		A.append(5, 17);
		//A.append(90, 31);
		LinkedList B= new LinkedList(5,3);
		B.append(25, 77);
		//B.append(88, 13);
		System.out.println("LINKED LIST A:");
		A.print();
		System.out.println("LINKED LIST B:");
		B.print();
		System.out.println("LINKED LIST C:");
		LinkedList C= LinkedList.Combine(A,B);
	}
}
