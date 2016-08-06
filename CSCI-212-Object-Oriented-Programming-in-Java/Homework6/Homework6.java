import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;
/*
 * For this assignment you will create an event driven program that will run through the game Sudoku.
You are expected to follow proper Three-Tier Architecture (use separate classes for the functionality, the GUI, any text files)
This Assignment will be worth 200 points. 100 for the GUI object in part b, the other 100 for parts a and c
Follow the instructions carefully. They will assist you in the designing of this program.
Part a)
1) Create a class called Homework6. Create in this class your main method. The main method should create an object of class SudokuBoard. 
Then the main method will call a static method in class Homework6 called test. 
The purpose of this method is to be able to test the components of the SudokuBoard class without having to wait and create the GUI class.
2) Create class SudokuSquare. This class should have the following variables:
- int row: representing which row in the board the square is located
- int column: representing which column in the board the square is located
- int value: representing which numeric value the square contains.  The value should be 0 if blank or a number 1-4 if not
- boolean locked: representing if this Square’s value variable can be changed.
It should have the following methods:
- a constructor that will assign a value to each of these variables
- a get method for each of these variables. Name the get method for the locked variable “isLocked”
There should not be any set methods, except for the value variable. Ensure both in the constructor and in the set method that this variable is 
set to a valid value, which means only 0-4.
3) Create class SudokuSquareNode. This class will be identical to our Node1 class, except that the data element will be of type SudokuSquare, 
instead of type int.
4) Create class SudokuSquareLinkedList. This class will be similar to out LinkedList1 class except that the nodes will be of type SudokuSquareNode,
instead of Node1. We will not need in this class all of the methods that we used in LinkedList1. The ONLY methods you will need to define here are:
- a constructor that takes in a Node as a parameter
- an append method that takes in a Node as a parameter and will add it to the end of the list
- a getNext method that will return the data square object of the next node in the list that has not yet been returned. 
To create this method, you will need to create a new instance variable of type SudokuSquareNode to keep track of which node was just called 
(or which will be called next, whichever you prefer)
5) Create class SudokuBoard. This class should maintain a 4x4 double array of SudokuSquares that will represent the board. 
(If you want you can make it an official 9x9 board. It will not make this class more complicated, 
but it will make the GUI class you create next much more lengthy.) Create the following methods:
- a constructor that takes in a SudokuSquareLinkedList object. 
The constructor should go through the nodes in this linked list and set up squares in its board with the corresponding values represented by the variables of the Square object in each Node object.
Use the new method getNext that you created in class SudokuSquareLinkedList.
For example: if a Node had values for its variables: row = 1, column = 1 and value = 3, then the top left corner square in the board should have a value of 3.
All squares given a value in the constructor based on this linked list should be locked.
All other squares in the board should be given an initial value of 0, and should not be locked.
- a method isValid which will take in a three ints as parameters: one for the row, one for the column, and one for the value. 
The method will return true if placing the passed value into the designated square is a legal move and it will return false if it is not a legal move. 
A move is legal only if it does not cause the same number to exist more than once in a row, or in a column, or in a quadrant. This method does not change any value in the board.
- a method enterMove which will take in a three ints as parameters: one for the row, one for the column, and one for the value. 
This method should call method isValid and if it returns true, should enter that number into the square. 
If method isValid returns false, then the board should not be changed. Instead an Exception of type SudokuException should be thrown. This Exception class must be created. see below. 
This Exception should be caught both in the method actionPerformed that you will create in your handler in part b and in the method test that you will create in your main method at the end of part a.
- a method reset, which will restore the original state of the board as it was after the constructor initialized the object. (Hint: use the locked variable of each square)
- a method isFull, which will return true if the entire board is filled with non-zero values. It will return false if any of the squares are still blank.
- a getSquare method, which will return the Square object located at the location of the board specified by the 2 int parameters passed to this method.
6) Return to class Homework6. Now create method test. 
Using JOptionPanes and by outputting the board to the console after every move, create a loop that will keep asking the user to input a move until the board is full.
Make sure that the original setup of the board you create has a real solution. Otherwise you will be testing forever. 
Leave this method in your Homework6 class. I want to see it.
Part II is GUI

Part III is C
part c)
Modify the original program to:
read in the file that was posted on blackboard.
for each line in that file, create a square object.
create a Node for each square object.
create a LinkedList of those Node objects
pass this LinkedList object to the constructor of your board class.
 */
public class Homework6
{
private final static int MAX=3;//I know that the amount of numbers I want is always 3

public static void main (String[] args) throws IOException
{
	Scanner file = new Scanner(new File("info.txt"));
//The contents of the file are organized as
//Row	Column	Value. If it is in this list, it must be a Locked Value.
//This is the title, I must remove it.
	final String DUMMY=file.nextLine(); //I need this string out of the way.
	
//Get the useful information
	String A=(file.nextLine());
	String B=(file.nextLine());
	String C=(file.nextLine());
	String D=(file.nextLine());
	String E=(file.nextLine());
	String F=(file.nextLine());

//Split the String on the tab Key. The Tab separates the Integers I want.
	String [] ArrayA=A.split("\t");
	String [] ArrayB=B.split("\t");
	String [] ArrayC=C.split("\t");
	String [] ArrayD=D.split("\t");
	String [] ArrayE=E.split("\t");
	String [] ArrayF=F.split("\t");
//Set up int Arrays
	int [] Line1 = new int [MAX];
	int [] Line2 = new int [MAX];
	int [] Line3 = new int [MAX];
	int [] Line4 = new int [MAX];
	int [] Line5 = new int [MAX];
	int [] Line6 = new int [MAX];
//Convert them to int 
	for (int i=0;i<MAX;i++)
	{
		Line1[i]=Integer.parseInt(ArrayA[i]);
		Line2[i]=Integer.parseInt(ArrayB[i]);
		Line3[i]=Integer.parseInt(ArrayC[i]);
		Line4[i]=Integer.parseInt(ArrayD[i]);
		Line5[i]=Integer.parseInt(ArrayE[i]);
		Line6[i]=Integer.parseInt(ArrayF[i]);
	}
	
//Finalize Set up
		SudokuSquare Square1= new SudokuSquare(Line1[0], Line1[1], Line1[2],true);
		SudokuSquare Square2= new SudokuSquare(Line2[0], Line2[1], Line2[2],true);
		SudokuSquare Square3= new SudokuSquare(Line3[0], Line3[1], Line3[2],true);
		SudokuSquare Square4= new SudokuSquare(Line4[0], Line4[1], Line4[2],true);
		SudokuSquare Square5= new SudokuSquare(Line5[0], Line5[1], Line5[2],true);
		SudokuSquare Square6= new SudokuSquare(Line6[0], Line6[1], Line6[2],true);

//Fill up remaining squares NOT in the list
										//Row Column Value
	SudokuSquare Square7= new SudokuSquare (0,	1,	0,false);
	SudokuSquare Square8= new SudokuSquare (0,	2,	0,false);
	
	SudokuSquare Square9= new SudokuSquare (0,	3,	0,false);
	SudokuSquare Square10= new SudokuSquare(1,	0,	0,false);
	SudokuSquare Square11= new SudokuSquare(1,	2,	0,false);
	SudokuSquare Square12= new SudokuSquare(2,	1,  0,false);
	
	SudokuSquare Square13= new SudokuSquare(2,	3,	0,false);
	SudokuSquare Square14= new SudokuSquare(3,	0,	0,false);
	SudokuSquare Square15= new SudokuSquare(3,	2,	0,false);
	SudokuSquare Square16= new SudokuSquare(3,	3,	0,false);
	
	//Make them into a Linked List and close scanner!
	file.close();
	
	SudokuLinkedList Puzzle= 
			
new SudokuLinkedList(Square1);
	Puzzle.append(Square2);
	Puzzle.append(Square3);
	Puzzle.append(Square4);
	Puzzle.append(Square5);
	Puzzle.append(Square6);
	Puzzle.append(Square7);
	Puzzle.append(Square8);
	Puzzle.append(Square9);
	Puzzle.append(Square10);
	Puzzle.append(Square11);
	Puzzle.append(Square12);
	Puzzle.append(Square13);
	Puzzle.append(Square14);
	Puzzle.append(Square15);
	Puzzle.append(Square16);
	
	SudokuBoard SoloSudo = new SudokuBoard(Puzzle);
	new GUI(SoloSudo, 500, 500); //If you want to play on GUI
	//test(SoloSudo); //If you want to play on console
	
}//End of main

public static boolean test(SudokuBoard Game)
{
	while(!Game.isFull())
	{
		Game.print();
		String Val=JOptionPane.showInputDialog(null, "Please place Value");
		String Row=JOptionPane.showInputDialog(null, "Please place Row");
		String Col=JOptionPane.showInputDialog(null, "Please place Column");
		int va =0;
		int ro =0;
		int co =0;
		try 
		{
			va=Integer.parseInt(Val);
			ro=Integer.parseInt(Row);
			co=Integer.parseInt(Col);
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("Why are you putting non integer values in Sudoku????");
		}
		
		try 
		{
			//Be careful, Enter Row,Column,Value as expected in Sudoku Board
			Game.enterMove(ro, co, va);
		}
		catch (SudokuException Sudo)
		{
			System.out.println("INVALID MOVE!!! Try again");
		}
	}
	return true;
}

}//End of Class

