public class Homework5 {

/*
In this assignment you will be creating 2 new types of dynamic data structures. One will behave like a Stack, and the other will behave like a Queue
Create 2 classes, Stack and Queue, both of which will inherit from class LinkedList1 (not LinkedList2) that we created together in class.The complete LinkedList1 class is posted on blackboard.
Here is what you have to do:
In LinkedList1:
1) add a variable of type int called count, which will keep track of how many nodes are in the list.
2) make both variables count and head protected scope

****For Stack and Queue, the order is based on the order in which they were added. These lists should not be sorted based on any value.
Therefore, the insert and delete methods must be overridden.
In both Stack and Queue:
1) Create one constructor that takes in a parameter of type int.
   The constructor should create a node with that int, and have the head variable point to it.
2) Create a method to override the parent classes insert method
3) Create a method to override the parent classes delete method.  **** Note: see bottom****
4) Remember to update the count variable in all 3 of these methods.
5) Create method clearAll in both classes. The method should look at the node at the top or front and print out to the console its int value, and then delete that node from the list, calling method delete. Continue printing and deleting until the structure (stack or queue) is empty. Use the count variable to determine when the structure is empty.

For class Stack, the head variable should point to the node at the top of the stack.
For class Queue, the head variable should point to the node at the front of the queue. In addition, I recommend that you have a variable called "back" or "last" to point to the last node in the list.

IN ADDITION:
I want you to create a main method in another class called Homework5.
The main method should create an instance of each of your classes, stack and queue.
Using a for loop, insert an object into both the stack and the queue for the numbers 1 through 20. (use the for loop counter as the parameter being passed to the insert method.

Then call method clearAll from both the queue and the stack.

****Note****
The delete method in class LinkedList1 took in an int as a parameter. In order for the children classes to override the parent classes' method, the method signature must match EXACTLY, including the parameter list. Therefore, the delete method of the children classes must take in an int value. The int taken as a parameter is NOT the value of the node to be deleted, since only one node could possibly be deleted in these structures (either the top or the front). Instead the int value is the number of nodes that should be deleted from the stack or queue. If the int passes as a parameter is greater than the number of nodes in the list, then the list should be completely emptied. Be careful to avoid null pointer exceptions.

 */
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

