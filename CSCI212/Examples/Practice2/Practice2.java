import javax.swing.JOptionPane;

public class Practice2 {
	public static void main(String[] args)
	{	
	String input=JOptionPane.showInputDialog("Insert a number:");
	
	//Initialize empty char array
	char [] flipped= new char [input.length()];
	
	//Check if the string is valid.
	if (Reverse.Check(input))
	{
		//Then flip the integers in a char array.
		flipped=Reverse.Flip(input);
	}
//It needs to be converted to string again so I can print on JOptionPane
String output=new String(flipped);
	
JOptionPane.showMessageDialog(null, "The reverse order of your input is: " + output);

	}//end of main
}
