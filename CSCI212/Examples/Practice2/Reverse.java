import javax.swing.JOptionPane;

public class Reverse {
	
	public static boolean Check (String input)
	{
		for (int i=0;i<input.length();i++)
		{
			if(Character.isDigit(input.charAt(i))==false)
			{
				JOptionPane.showMessageDialog(null, "There is a non digit integer in your input.");
				return false;
			}
		}
		return true;
	}
	
	public static char [] Flip (String input)
	{
		int swap=input.length()-1;
		char [] flip= new char[input.length()];
		for (int i=0;i<flip.length;i++)
		{
			flip[i]=new Character (input.charAt(swap));
			swap=swap-1;
		}
		return flip;
	}
	
} //End of class
