import javax.swing.JOptionPane;
import java.math.*;
import java.util.ArrayList;

public class NumberTheory
{
	boolean negative1Found=false;
	BigInteger value;
	
	public NumberTheory(int number)
	{	
		value = new BigInteger(convertToBinary(number));
	}
	
	private String convertToBinary(int number)
	{
		String Binary="";
		return Binary;
	}
	
	private long powerMod(long base, long exponent, long MOD)
	{
		long answer=base%MOD;
		while (exponent!=0)
		{
			answer=(answer*base)%MOD;
			--exponent;
		}
		return answer;
	}
	
	public BigInteger GCD(BigInteger a, BigInteger b)
	{
	   if (b.equals(0))
	   {
		   return a;
	   }
	   return GCD(b,b.mod(a));
	}

	public void FLTPrimalityTest(int base)
	{
		BigInteger bigBase = new BigInteger(convertToBinary(base));
	//I am testing for primes using Fermat's Little Theorem	
		if (!bigBase.gcd(value).equals(1))//For FLT to work properly the base and prime must be relatively prime!
		{
			JOptionPane.showMessageDialog(null, "the Base and the prime are NOT relatively prime!");
			return;
		}
		/*
		if (bigBase.modPow(value.add(-1), value).equals(1))
		{
			JOptionPane.showMessageDialog(null, "It could be a prime...");
		}	
		else
		{
			JOptionPane.showMessageDialog(null, "DEFINATELY NOT A PRIME!");
		}
		*/
	}
	
	public void AKSPrimalityTest()
	{
		//I will use Binomial Theorem to expand the terms out.
		//(X-1)^p Where p is being test if it is a prime or not...
		ArrayList <Integer> coefficients = new ArrayList<Integer>();
		
	}
	public static void AKSPrimalityTest(int p)
	{
		//I will use Binomial Theorem to expand the terms out.
		//(X-1)^p Where p is being test if it is a prime or not...
		//Let a=b=-1 
		ArrayList <Integer> coefficients = new ArrayList<Integer>();
		for (int i=1;i<p;i++)
		{
			//Always skip the First and Last term!
			System.out.println("NCR: " + nCr(p,i) + " i: " + i);
			coefficients.add((int) (Math.pow(-1, i)*nCr(p,i)));
		}
		for (int i=0;i<coefficients.size();i++)
		{
			System.out.println(coefficients.get(i));
		}
		//when I am done collecting the terms, mod and see what I get.
		for (int i=0;i<coefficients.size();i++)
		{
			if(coefficients.get(i)%p!=0)
			{
				System.out.println("NOT A PRIME");
				return;
			}
		}
		System.out.println("It is a Prime");
	}
	
	public static int nCr (int n, int r)
	{
		//Problem, Use Big Decimal
		int answer=nPr(n,(n-r));//n!/(n-r)!
		System.out.println(answer);
		answer=answer/factorial(r);
		return answer;
	}
	
	public static int factorial (int n)
	{
		if (n==1)
		{
			return 1;
		}
		return n * factorial(n-1);
	}
	
	public static int nPr(int n, int k)
	{
		if (n==k+1)
		{
			return n;
		}
		return n * nPr(n-1,k);
	}
	/*
	
	public void MillerRabinTest(long base)
	{
		long s=value-1;//2^r*s=n-1
		long r=0; //Exponent for 2
		while (s%2==0)
		{
			System.out.println("Value of r: " + r);
			System.out.println("Value of s: " + s);
			++r;
			s=s/2;
			continue;
		}
		
		System.out.println("FINAL Value of r: " + r);
		System.out.println("FINAL Value of s: " + s);
		long temp=(powerMod(base,s,value));
		
		if (temp==1)
		{
			JOptionPane.showMessageDialog(null, "It could be a prime...");
			return;
		}
		else //Start Squaring
		{
			long counter=0;
			long MOD=value-1;//This will test if I get residue negative 1.
			
			while (true)
			{
				if (counter==r)
				{
					JOptionPane.showMessageDialog(null, "DEFINATELY NOT A PRIME! because counter is equal to r");
					System.out.println("--------------------Final Results-----------------------------------------");
					System.out.println("Trial: " + counter );
					System.out.println("Base: " + base);
					System.out.println("Exponent: " + s);
					System.out.println("TEMP RESIDUE: " + temp);
					return;
				}
				if (temp==1 && negative1Found==true)
				{
					JOptionPane.showMessageDialog(null, "It could be a prime...");
					System.out.println("--------------------Final Results-----------------------------------------");
					System.out.println("Trial: " + counter );
					System.out.println("Base: " + base);
					System.out.println("Exponent: " + s);
					System.out.println("TEMP RESIDUE: " + temp);
					return;
				}
				System.out.println("Trial: " + counter );
				System.out.println("Base: " + base);
				System.out.println("Exponent: " + s);
				System.out.println("TEMP RESIDUE: " + temp);
				System.out.println(" ");
				if (temp==MOD)//-1(Mod n)
				{
					negative1Found=true;
					continue;
				}
				base=temp=(powerMod(base,s,value));
				s=2;
				++counter;
				continue;
			}
		}
	}

	*/
}