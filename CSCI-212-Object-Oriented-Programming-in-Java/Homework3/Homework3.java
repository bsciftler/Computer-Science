import javax.swing.JOptionPane;

public class Homework3
{
	public static void main (String [] args)
	{
		
		
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
		moneyInput.makeAnswer(avg, pplAmt);
		Node current = n;
		System.out.print(current.getData()); 
	}//End of main
	
}//End of class