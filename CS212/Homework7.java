import java.io.File;

/*
 * In this asignment, you will be showing your ability to code recursively and to generate and handle your own exceptions.

Part a)

Make a class called MyException. This class should inherit directly from class Exception.
Make 2 constructors for this class, one that takes in a String as a parameter and the other does not. Both constructors should call the constructor of the parent class.

Part b)

I am asking you to create 2 new methods in class Node1 AND 2 new methods in class LinkedList1

Each of the new methods in class Node1 should propagate MyException and each new method in class LinkedList1 should handle MyException.
you can submit the entire classes, or just the 4 methods.

1)

a) create a method called sum in class Node1 that will recursively calculate the sum of all of the int data values from that node to the last node in the list.
(HINT: using the next pointer) This method should return (NOT PRINT OUT) the sum that was calculated.
If the data value of that node is negative, throw a MyException.

b) IN CLASS LINKEDLIST1, create a method called sum that will take an int, n, as a parameter. It will call method sum (of class Node) on the nth node in the list. 
(the head is node 1, the node after head is 2, etc.) And then print out to a file the sum that was calculated. If the parameter passed is negative or zero, then the sum should be zero. 
If n is greater than the number of nodes in the list, then throw an appropriate exception.

2)
a) create a method called printBackwards in the same Node1 class. 
The method should take as a parameter an object of type PrintWriter. It should be a recursive method. 
The purpose of the method is to print all of the values from all of the nodes from the current node (as in the this  keyword) until the end of the list IN BACKWARDS ORDER. 
In other words, the last node in the list will appear at the beginning of the output text file, and the current node will appear later in the text file. If the data value is equal to 9, throw a MyException

b) create a method printB in the same LinkedList class. 
The method should create an instance of PrintWriter and call the method printBackwards from class Node. 

 */

public class Homework7 {

	public static void main(String[] args)
	{
		LinkedList Numbers = new LinkedList(5);
		Numbers.insert(5);
		Numbers.insert(17);
		Numbers.insert(6);
		Numbers.insert(24);
		Numbers.insert(10);
		Numbers.print();
		Numbers.sum(0);
		Numbers.printB();
		
		//This is how to find where the Printed Files are Located.
		//I am just keeping this here, since it might be useful information. 
		System.out.println("Printed Files are stored at: "+ new File(".").getAbsolutePath());
		
	}
}
