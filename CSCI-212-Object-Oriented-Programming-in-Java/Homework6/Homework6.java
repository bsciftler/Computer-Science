import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

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

