import javax.swing.JOptionPane;

public class NumberTheory
{
	boolean negative1Found=false;
	boolean PosorNegonefound=false;
	long value;
	public NumberTheory(int number)
	{
		value=number;
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
	
	public long GCD(long a, long b)
	{
	   if (b==0)
	   {
		   return a;
	   }
	   return GCD(b,a%b);
	}

	public void primality(String test,long base)
	{
		//I am testing for primes using Fermat's Little Theorem
		if (test.equalsIgnoreCase("Fermat"))
		{
			if (GCD(base,value)!=1)//For FLT to work properly the base and prime must be relatively prime!
			{
				JOptionPane.showMessageDialog(null, "the Base and the prime are NOT relatively prime!");
				return;
			}
			if (powerMod(2,value-1,value) == 1)
			{
				JOptionPane.showMessageDialog(null, "DEFINATELY NOT A PRIME!");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "It could be a prime...");
			}
		}
		//I am testing for prime using Miller's method
		else if (test.equalsIgnoreCase("Miller"))
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
		else
		{
			JOptionPane.showMessageDialog(null, "Invalid Primality test!");
			return;
		}
	}
}
