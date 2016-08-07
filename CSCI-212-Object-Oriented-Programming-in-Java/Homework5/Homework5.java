public class Homework5 
{
	public static void main (String [] args)
	{	
	
	//Create Stack
	Node S= new Node(1);
	Stack A = new Stack(S);
	
	//Create Queue
	Node Q= new Node(1);
	Queue B= new Queue(Q);
	
	//Fill them both up
	for (int i=2;i<=20;i++)
	{
		A.insert(i);
		B.insert(i);
	}
	System.out.println("Clearing Stack...");
	A.clearAll();
	System.out.println(" ");
	System.out.println("Clearing Queue...");
	B.clearAll();
	}
	
}

