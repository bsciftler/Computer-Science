import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LinkedList {

	private Node head;
	private int size;
	
	public LinkedList()
	{
		head=null;
	}
	
	public LinkedList(int x)
	{
		head = new Node(x);
	}
	public LinkedList(Node n)
	{
		head = n;
	}
	
	public void print()
	{
		Node current = head;
		while (current!=null)
		{
			System.out.print(current.getData()+" ");
			current = current.getNext();
		}
		System.out.println();
	}
	
	public void insert(int x)
	{
		if(head==null)
		{
			head = new Node(x);
			return;
		}
		if(x<=head.getData())
		{
			head = new Node(x, head); // case 1
			return;
		}
		Node current = head;
		Node previous = head;
		while(current!=null){
			if(x>current.getData())
			{
				previous = current;
				current = current.getNext();
			} 
			else 
			{
				previous.setNext(new Node(x,current)); // case 2
				return;
			}
		}
		previous.setNext(new Node(x)); // case 3
	}

	public boolean delete(int x)
	{
		if(head.getData()==x)
		{
			head = head.getNext(); // case 1
			return true;
		}
		Node current = head.getNext();
		Node previous = head;
		while(current!=null){
			if(current.getData()==x)
			{
				previous.setNext(previous.getNext().getNext()); // cases 2 & 3
				return true;
			} 
			else if(current.getData()>x)
			{
				break;
			} 
			else 
			{
				previous = current;
				current = current.getNext();
			}
		} // while
		return false;
	}
	
	public void append(int x)
	{
		if (head == null)
		{
			head = new Node(x);
			return;
		}
		Node current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext();
		}
		// Node temp = new Node(x);
		// current.setNext(temp);
		current.setNext(new Node(x));
	}
	
//Homework 7 Additions=========================================================================================
	
	public int getSize()
	{
		Node current=head;
		while (current!=null)
		{
			size++;
		}
		return size;
	}
	
	public void sum (int n)
	{
		int sum=0;
		if (n <= 0)
		{
			try 
			{
		           PrintWriter add= 
		        		  new PrintWriter(
		                  new BufferedWriter(
		                  new OutputStreamWriter(
		                  new FileOutputStream("sum.txt"))));
		           add.print("The sum of all values of the linked list is: " + sum);
		           add.flush();
		           add.close();
			}
			catch (IOException ioe) 
			{         
				System.out.print("IO Exception occured");
			}
			System.out.println("The sum of all values of the linked list is: " + sum);
			return; //break out of the method.
		}
	
		Node current=head;
			//Move to Node n location
			while(current!=null && n!=1)
			{
				current=current.getNext();
				n--;
			}
		
			//Sum it up
			try
			{
				sum=current.sum(current);
			}
		
			catch (MyException mE)
			{
				System.out.println("Error!!! A node in this linked list has a value less than 0!!!");
			}
			catch (NullPointerException nfe)
			{
				System.out.println("Null Pointer exception...please put a valid number of nodes");
			}
			System.out.println("The sum is: "+ sum);
			
			try 
			{
		           PrintWriter add= 
		        		  new PrintWriter(
		                  new BufferedWriter(
		                  new OutputStreamWriter(
		                  new FileOutputStream("sum.txt"))));
		           add.print("The sum of the Linked List is: "+ sum);
		           add.flush();
		           add.close();
			}
			catch (IOException ioe) 
			{         
				System.out.print("IO Exception occured");
			}
	}
	
	public void printB()
	{
		Node current=head;
		
		try
		{
			 PrintWriter PrintBack = 
				new PrintWriter( 
				new BufferedWriter(
				new OutputStreamWriter(
                new FileOutputStream("trashcan.txt"))));
	      
			 current.printBackwards(PrintBack);	
	         PrintBack.flush();
	         PrintBack.close();
		}
		
		catch (IOException ioe) 
		{         
			System.out.print("IO Exception occured");
		}
		catch (MyException mE)
		{
			System.out.println("There is a Number 9 in this Linked List...");
		}
		   
	}//end of method
	
}
