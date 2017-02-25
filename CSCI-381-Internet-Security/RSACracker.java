import java.util.ArrayList;
import java.math.*;;

public class RSACracker
{
	public int RSACrackDecoder(int p, int q, int e)
	{
		int decoder=0;
		int LCM = LCM (p-1,q-1);
		//Formula to crack decoder:
		//de CONGRUENT to 1(Mod LCM(p-1,q-1))
		//Linear Combination:
		// e*x + LCM*y = 1
	
		return decoder;
	}
	
	public ArrayList<Integer> FermatFactorization (int N)
	{
		//Initialize Fermat Factorization
		ArrayList <Integer> factors = new ArrayList<Integer>();		
		//Create initial starting value
		double dummy=Math.sqrt(N);
		int x=(int) Math.ceil(dummy);
		
		
		int testValue;
		int sqrtofa=0;
		/*
		 *  Structure of Fermat Factorization:
		 *  testValue^2 = (x^2) - N
		 *  goal of method: 
		 *  if TestValue is a perfect square, I am done.
		 */
		while (true)
		{
			testValue =(x*x) - N;
			sqrtofa = (int) Math.sqrt(testValue);
			System.out.println(x);
			System.out.println("TestValue: " + testValue + " SQRT: " + sqrtofa);
			
			if (sqrtofa*sqrtofa == testValue)
			{
				break;
			}
			else
			{
				++x;
			}
		}
		//Factors are found!
		// x^2 + y^2 = (x+y)(x-y)
		factors.add(x+sqrtofa);
		factors.add(x-sqrtofa);
		return factors;
	}
	
	public ArrayList<Integer> Factorization (int N)
	{
		ArrayList<Integer> primes = new ArrayList<Integer>();
		while (N%2==0)
		{
			primes.add(2);
			N=N/2;
		}
		int factor = 3;
		while (N!=1)//Prime Factorization is unique
		{
			if (N%factor==0)
			{
				primes.add(factor);
				N=N/factor;
			}
			else
			{
				factor+=2;
			}
		}
		return primes;
	}
	
	public int LCM (int a, int b)
	{
		int GCD = gcd(a,b);
		return ((a*b)/GCD);
	}
	
	public int gcd (int a, int b)
	{
		if (b!=0)
		{
			return gcd(b,a%b);
		}
		return a;
	}
}
