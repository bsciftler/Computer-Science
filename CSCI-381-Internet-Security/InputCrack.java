
public class InputCrack
{
	public static void main (String [] args)
	{
		//Info came from Exam 3 from Number Theory...
		RSACracker BonSy = new RSACracker(661,257821);
		BonSy.getInfo();
		
		//What if message is NOT relatively prime to N??
		BonSy.EulerPhi(6);
	}
}
