import java.util.ArrayList;

public class InputCrack
{
	public static void main (String [] args)
	{
		int N; //This is the product of primes
		int e; //This is the public encryption key
		RSACracker testing = new RSACracker();
		//ArrayList<Integer> factors = testing.FermatFactorization(11523);
		ArrayList<Integer> Factors = testing.Factorization(23636342);
		for (int i=0;i<Factors.size();i++)
		{
			System.out.println(Factors.get(i));
		}
	}
}
