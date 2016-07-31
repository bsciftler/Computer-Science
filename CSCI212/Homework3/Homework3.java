import javax.swing.JOptionPane;

/*
 * The Trip
A number of students are members of a club that travels annually to exotic locations. Their destinations
in the past have included Indianapolis, Phoenix, Nashville, Philadelphia, San Jose, and Atlanta. This
spring they are planning a trip to Eindhoven.
The group agrees in advance to share expenses equally, but it is not practical to have them share every expense as it occurs. So individuals in the group pay for particular things, like meals, hotels, taxi rides, plane tickets, etc. After the trip, each student’s expenses are tallied and money is exchanged so that the net cost to each is the same, to within one cent. In the past, this money exchange has been tedious and time consuming. Your job is to compute, from a list of expenses, the minimum amount of money that must change hands in order to equalize (within a cent) all the students’ costs.
Input
Standard input will contain the information for several trips. The information for each trip consists of a line containing a positive integer, n, the number of students on the trip, followed by
n lines of input, each containing the amount, in dollars and cents, spent by a student. There are no more than 1000
students and no student spent more than $10,000.00. A single line containing 0 follows the information for the last trip.
Output
For each trip, output a line stating the total amount of money, in dollars and cents, that must be
exchanged to equalize the students’ costs.

Inputs 

3
10.00
20.00
30.00
Output: $10.00
4
15.00
15.01
3.00
3.01
Output: $11.99
5
5000.00
11.11
11.11
11.11
11.11
Output: $3991.11
3
0.01
0.03
0.03
Output: $0.01
4
25.00
25.00
25.00
28.00
Output: $2.25
3
10.01
15.25
18.96
Output: $4.73
4
25.03
25.00
25.00
25.00
Output: $0.02
21 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.01 
0.03
Output: $0.01
12 
123.12 
6.13 
9.44 
89.08 
278.78 
223.78 
78.45 
912.89 
554.76 
547.57 
1781.89 
907.07 
0
Output: $2407.09
 */
public class Homework3 {
	public static void main (String [] args){
		
		
		int pplAmt = Integer.parseInt
		(
			JOptionPane.showInputDialog("Enter the amount of people on the trip.")
		); //asks user pplAmt, parses that number into pplAmt
		
		int x = Integer.parseInt
		(
			JOptionPane.showInputDialog("How much did the first person spend?") //
		);	//asks user 1st money paid, parses into x

		Node n = new Node(x); //creates  new node with value x (aka the amount the first person on the trip paid)
		
		LinkedList moneyInput = new LinkedList(n); //initializes a LinkedList using the node n
		
		
		for (int i = 0; i < (pplAmt-1); i++)
		{ //because first is already accounted for, minus 1 from pplAmt
			moneyInput.insert
			(                //calls insert method and inserts the nodes into LinkedList
				Integer.parseInt
				(
					JOptionPane.showInputDialog("How much did the next person spend?") 
				)
			);
		} 
		
		moneyInput.print();
		
		//so now we have the value every person paid in a linked list from least to greatest
		
		int avg = moneyInput.sum(pplAmt)/pplAmt;
		int remainder = moneyInput.sum(pplAmt)%pplAmt;
		
		System.out.println(" The average: " + avg ); // avg
		System.out.println(" The remainder: " + remainder); // remainder
		
		MakeAnswer answer = new MakeAnswer(pplAmt,avg);
		
		answer.calcAnswer(n);
	//	moneyInput.makeAnswer(avg, pplAmt);
		//Node current = n;
	//	System.out.print(current.getData()); 
	}//End of main
}//End of class