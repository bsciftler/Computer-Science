
import javax.swing.JOptionPane;

/*
 * Writing Solid, Robust & Reusable Code Blocks

a) Write a method called CheckInteger that will check that a value entered is actually a parsable integer value. This method will receive/accept a string variable as an argument and return a Boolean value to indicate that it is in fact an integer. 
b) Write a method called CalculateSum that will accept an integer array as parameter and returns an integer in the return value. Using a for loop, calculate the sum of all integers in this array and return such value. 
c) Write a method called ProcessInput that will perform input processing. This method will receive/accept a String value parameter and return a Boolean value to indicate failure or success. 
Inside this method, perform the following:
i) First, Use the String.split method to split the entered values into a String Array. 
ii) Then, use the CheckInteger method that you have created in part (a) to check that each of the values entered is a valid integer. IF any of the values entered failed the test, return false and exit the method.
iii) If all the integers are validated, ask user to confirm using JOptionPane that what they entered is correct. By displaying message in this format: 
“You have entered <array value>. Is this correct?”
iv) Once they pressed OK at the confirmation dialog, convert the string values into integer and store it in an integer array. DO NOT HARDCODE anything. Use the length from your String array to construct your integer array. However if they did not press OK, exit the method by returning false. 
v) Finally, if all the above tests has passed, call the CalculateSum method that you have created in part (b) to calculate the sum by passing in the integer array. Display the sum in the following message format to the user using JOptionPane: 
The average of the numbers entered is: <average> 
Return true and exit the method;
d) In your main method:
i) Ask user to enter/input an array of integers separated by a comma using JOptionPane and store it in a string variable. 
j) Call the ProcessInput method and pass the variable value to this method. Check that it is successful by the Boolean return value. Continue calling this method until the method returns true. In which point, the program should exit.  

 */
public class Homework1 
	{
	public static void main(String[] args){
	String numbers=JOptionPane.showInputDialog(null, "Please enter numbers so that each number is seperated by a comma: ");
	ProcessInput(numbers);
	}

	public static void ProcessInput (String numbers)
	{
	String [] IntArray=numbers.split(",");
	int [] x = new int [IntArray.length]; //declare new int array with same size
	if (CheckInteger(IntArray))
	{
		int Confirm=JOptionPane.showConfirmDialog(null, "You have entered " + numbers + ". Is this correct?");
		if (Confirm==JOptionPane.YES_OPTION)
		{
			for (int i=0;i<x.length;i++)
			{
				x[i]=Integer.parseInt(IntArray[i]);
			}
		}
		else 
		{
			CheckInteger(IntArray);
		}
	}
	int sum=CalculateSum(x);
	JOptionPane.showMessageDialog(null, "The sum of the numbers entered is: " + sum);
	}

	public static boolean CheckInteger(String [] IntArray) 
	{
	    for (int i=0;i<IntArray.length;i++)
	    {
	    	try 
	    	{
	    		Integer.parseInt(IntArray[i]);
	    	}
	    	catch (NumberFormatException NFE) { return false; }
	    }
	    return true; 
	}

	public static int CalculateSum (int [] x)
	{
		int sum=0;
		for (int i=0;i<x.length;i++)
		{sum+=x[i];}
		return sum;
	}
}