import javax.swing.JOptionPane;

public class Practice {
public static void main (String[] arg)
	{
int Dicing;	
String Rolls=JOptionPane.showInputDialog("How many times do you want to roll the die???");
try
{
	Dicing=Integer.parseInt(Rolls);
}
catch (NumberFormatException nfe)
{
	System.out.print("INVALID ENTRY");
	return;
}

RollDie.Rolling(Dicing);

System.out.println("Out of " + Rolls + " rolls: 1 occurs " + RollDie.N1() + " times ");
System.out.println("Out of " + Rolls + " rolls: 2 occurs " + RollDie.N2() + " times ");
System.out.println("Out of " + Rolls + " rolls: 3 occurs " + RollDie.N3() + " times ");
System.out.println("Out of " + Rolls + " rolls: 4 occurs " + RollDie.N4() + " times ");
System.out.println("Out of " + Rolls + " rolls: 5 occurs " + RollDie.N5() + " times ");
System.out.println("Out of " + Rolls + " rolls: 6 occurs " + RollDie.N6() + " times ");
	} //end of main

}//End of Class