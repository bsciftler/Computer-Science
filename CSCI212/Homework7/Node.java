import java.io.PrintWriter;

public class Node {
	
	private int data;
	private Node next;
	
	public Node(int d)
	{
		data = d;
	}
	
	public Node(int d, Node n)
	{
		data = d;
		next = n;
	}
	
	public int getData(){return data;}
	public Node getNext(){return next;}
	public void setNext(Node n){next = n;}
	
	public int sum(Node x) throws MyException
	{	
		//base case
		if (x.getNext() == null && x.getData() >= 0) 
   	    {
   	        return x.getData();
   	    }
   		//actual recursion
		if (x.getData() >= 0)
		{
   	   		return x.getData() + sum(x.getNext());
		}
		
		else 
		{
			//Only throws if there is a negative value in the Node.
			throw new MyException();
		}
	}
		
	public void printBackwards(PrintWriter B) throws MyException
	{
	  	{
	  		if (this.next == null) 
	  		{
	            B.println(this.data);
	        } 
	  		else if (data == 9) 
	  		{
	            throw new MyException();
	        }
	  		else 
	  		{
	            next.printBackwards(B);
	            B.println(this.data);
	        }
	  	}
 	    //base case, I am leaving this here so I know how to use Node instead of Print Writer
	  	/*	if (n == null) 
	   	    {
	   	        return;
	   	    }
	  		if (n.getData()==9)
	  		{
	   			throw new MyException();
	   		}
	   		System.out.print(n.getData() + " "); //Same as Print() but recursive.
	   	   	printBackwards(n.getNext());
	   	   	System.out.print(n.getData() + " "); //PrintBackward
	    }*/              
}
}//End of Class
    

