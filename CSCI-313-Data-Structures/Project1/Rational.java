/*
 * Created by Andrew Quijano
 * Based on my CSCI-211 Project.
 * Purpose: Instead of relying on decimals, I want to build
 * a Rational number object for my sparse matrix and statistics class to 
 * use instead of relying on integer/double datatype
 */
public class Rational
{
	private int numerator;
	private int denominator;
	
/*
 * 	Comment on Rational Creation...
 * 	Negative/Positive should ONLY be found at Numerator
 * 	Therefore, ban denominators from being negative.
 * 	Prevent Division by 0 by banning all 0 denominators.
 */
	public Rational ()
	{
		numerator=1;
		denominator=1;
	}

	public Rational (int n, int d)
	{
		if (d<=0)
		{
			throw new IllegalArgumentException();
		}
		numerator=n;
		denominator=d;
		this.simplify();
	}
	public Rational(int integer)
	{
		numerator=integer;
		denominator=1;
	}
	
/*
 * 		GET METHODS
 */
	
	public int getNumerator()
	{
		return numerator;
	}
	
	public int getDenominator()
	{
		return denominator;
	}
/*
 * 		END OF GET METHODS
 */

/*
 * 		SET METHODS
 */
	public void setNumerator(int newN)
	{
		numerator=newN;
	}
	public void setDenominator(int newD)
	{
		denominator=newD;
	}
/*
 * 		END OF SET METHODS
 */
	
/*
 * 		METHODS
 */
	public void print()
	{
		System.out.println(numerator+"/"+denominator);
	}
	
	public void multiply(Rational y)
	{
		int newN = this.getNumerator() * y.getNumerator();
		int newD = this.getDenominator() * y.getDenominator();
		this.setNumerator(newN);
		this.setDenominator(newD);
	}
	
	public void divide(Rational y)
	{
		int newN = this.getNumerator() * y.getDenominator();
		int newD = this.getDenominator() * y.getNumerator();
		this.setNumerator(newN);
		this.setDenominator(newD);
	}
	
	public void add(Rational y)
	{
		int newN = this.getNumerator() * y.getDenominator();
		newN = newN + this.getDenominator() * y.getNumerator();
		int newD = this.getDenominator() * y.getDenominator();
		
		this.setNumerator(newN);
		this.setDenominator(newD);
	}
	
	public void subtract (Rational y)
	{
		int newN = this.getNumerator() * y.getDenominator();
		newN = newN - this.getDenominator() * y.getNumerator();
		int newD = this.getDenominator() * y.getDenominator();
		
		this.setNumerator(newN);
		this.setDenominator(newD);
	}
	
	private void simplify()
	{
		int tempN=this.getNumerator();
		int tempD=this.getDenominator();
		int GCD=gcd(tempN,tempD);
		this.setNumerator((tempN/GCD));
		this.setDenominator((tempD/GCD));
	}
	
	private static int gcd(int a, int b)
	{
	  if(a == 0 || b == 0)
	  {
		  return a+b; // base case
	  }
	  return gcd(b,a%b);
	}
	
	public static void main (String [] args)
	{
		Rational number = new Rational(1,3);
		number.print();
	}
}