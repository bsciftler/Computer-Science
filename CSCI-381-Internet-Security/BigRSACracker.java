import java.math.BigInteger;
import java.util.ArrayList;

public class BigRSACracker
{
	BigInteger decoder=BigInteger.ZERO;
	BigInteger encoder;
	BigInteger bigPrime;
	BigInteger phi;
	ArrayList<BigInteger> smallPrimes;
	
	public BigRSACracker(BigInteger e, BigInteger N)
	{
		encoder=e;
		bigPrime=N;
		smallPrimes=Factorization(bigPrime);
	}
	
	public void getInfo()
	{
		System.out.println("Encoder: " + encoder.toString());
		System.out.println("Product of Primes: " + bigPrime.toString());
		System.out.println("Prime Factors of N:");
		for (int i=0;i<smallPrimes.size();i++)
		{
			System.out.println("Prime " + i + ": " + smallPrimes.get(i).toString());
		}
		if (decoder.equals(0))
		{
			this.getDecoder();
			System.out.println("Decoder: " + decoder);
			System.out.println("Encoder*Decoder = " + (decoder.multiply(encoder).toString()));
			return;
		}
		System.out.println("Decoder: " + decoder);
		System.out.println("Encoder*Decoder = " + (decoder.multiply(encoder).toString()));
	}
	
	public int powerMod(int message, BigInteger encoder, BigInteger Mod)
	{
		BigInteger midAnswer = BigInteger.valueOf((long)message);
		midAnswer=midAnswer.modPow(encoder, decoder);
		int answer=0;
		try 
		{
			answer=midAnswer.intValueExact();
		}
		catch (ArithmeticException AE)
		{
			System.out.println(midAnswer);
		}
		return answer; 

	}
	
	public int encrypt(int message)
	{
		int answer = powerMod(message,encoder,bigPrime);
		return answer;
	}
	
	public int decrypt(int cipherText)
	{
		if (decoder.equals(0))
		{
			this.getDecoder();
			int answer = powerMod(cipherText,decoder,bigPrime);
			return answer;
		}
		else
		{
			int answer = powerMod(cipherText,decoder,bigPrime);
			return answer;
		}
	}
	
	public void EulerPhiTest (int message)
	{
		if (decoder.equals(0))
		{
			this.getDecoder();
		}
		System.out.println("Given: " + message + " I get: " + powerMod(message,phi,bigPrime));
	}
	
	public BigInteger getDecoder()
	{
		if (decoder.equals(0))
		{
			decoder=RSACrackDecoder(smallPrimes.get(0),smallPrimes.get(1),encoder);
			phi=decoder.multiply(encoder);
			return decoder;
		}
		else
		{
			return decoder;
		}
	}
	
	public BigInteger RSACrackDecoder(BigInteger firstFactor, BigInteger secondFactor, BigInteger encoder)
	{
		//Compute LCM
		BigInteger LCM = firstFactor.multiply(secondFactor);
		//Find GCD
		BigInteger GCD = firstFactor.gcd(secondFactor);
		LCM = LCM.divide(GCD);
		
		//Formula to crack decoder:
		//de CONGRUENT to 1(Mod LCM(p-1,q-1))
		//Linear Combination:
		// e*x + LCM*y = 1
		ArrayList <BigInteger> linearCombination = extendedGCD(LCM, encoder);
		//Note for exGCD to work
		//The larger coordinate HAS to be on exGCDMatrix[0][2];
		//So which element my return depends on whether encoder or LCM is bigger
		//In my case, I need the decoder..
		BigInteger solution;
		if (encoder.subtract(LCM).signum()==1)
		//encoder > LCM
		//encoder - LCM > 0, Answer must be positive signum
		{
			solution= linearCombination.get(0);
		}
		else
		{
			solution= linearCombination.get(1);
		}
		while (solution.signum()!=1)
		{
			solution=solution.add(LCM);
		}
		return solution;
	}
	
	public ArrayList<BigInteger> extendedGCD(BigInteger x, BigInteger LCM)
	{
		ArrayList<BigInteger> solution = new ArrayList<BigInteger>();
		BigInteger [][] exGCDMatrix = new BigInteger [2][3];
		exGCDMatrix [0][0]=BigInteger.ONE;
		exGCDMatrix [0][1]=BigInteger.ZERO;
		exGCDMatrix [1][0]=BigInteger.ZERO;
		exGCDMatrix [1][1]=BigInteger.ONE;
		
		if (x.subtract(LCM).signum()==0)
		//Same as testing if x < LCM
		// x - LCM < 0
		{
			exGCDMatrix [0][2]=x;
			exGCDMatrix [1][2]=LCM;
		}
		else if (x==LCM)
		{
			solution.add(x);
			solution.add(BigInteger.ZERO);
			solution.add(x);
			return solution;
		}
		else
		{
			exGCDMatrix [0][2]=LCM;
			exGCDMatrix [1][2]=x;
		}
		
		BigInteger quotient;		
		BigInteger [] Row2temp = new BigInteger [3];
		
		while (true)
		{
			//System.out.println("Current Matrix");
			//printMatrix(exGCDMatrix);
			if (exGCDMatrix[1][2].equals(0))
			{
				solution.add(exGCDMatrix[0][0]);
				solution.add(exGCDMatrix[0][1]);
				solution.add(exGCDMatrix[0][2]);
				
	/*			Print the Linear Combination
				if (x > y)
				{
	System.out.println(x + " * " + exGCDMatrix[0][0] + " + " + y + " * " + exGCDMatrix[0][1] + " = " +exGCDMatrix[0][2]);				
				}
				else if (x < y)
				{
	System.out.println(x + " * " + exGCDMatrix[0][1] + " + " + y + " * " + exGCDMatrix[0][0] + " = " +exGCDMatrix[0][2]);
				}
	 */
				return solution;
			}
			//Step 1: Store value of Row 2 in Temporary row.
			Row2temp[0]=exGCDMatrix[1][0];
			Row2temp[1]=exGCDMatrix[1][1];
			Row2temp[2]=exGCDMatrix[1][2];
			//Step 2: compute value of new Row 2.
			//Row 1 - (q* Row2)
			quotient = exGCDMatrix[0][2].divide(exGCDMatrix[1][2]);
			exGCDMatrix[1][0]=exGCDMatrix[0][0].subtract((quotient.multiply(exGCDMatrix[1][0])));
			exGCDMatrix[1][1]=exGCDMatrix[0][1].subtract((quotient.multiply(exGCDMatrix[1][1])));
			exGCDMatrix[1][2]=exGCDMatrix[0][2].subtract((quotient.multiply(exGCDMatrix[1][2])));
			//Step 3: Move old Row 2 to Row 1.
			exGCDMatrix[0][0]=Row2temp[0];
			exGCDMatrix[0][1]=Row2temp[1];
			exGCDMatrix[0][2]=Row2temp[2];
		}
	}
	
	public ArrayList<BigInteger> Factorization (BigInteger N)
	{
		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
		while (N.mod(BigInteger.valueOf(2)).equals(0))
		{
			primes.add(BigInteger.valueOf(2));
			N=N.divide(BigInteger.valueOf(2));
		}
		BigInteger factor = BigInteger.valueOf(3);
		while (!N.equals(1))//Prime Factorization is unique
		{
			if (N.mod(factor).equals(0))
			{
				primes.add(factor);
				N=N.divide(factor);
			}
			else
			{
				factor=factor.add(BigInteger.valueOf(2));
			}
		}
		return primes;
	}
	
	public void printMatrix(BigInteger [][] Matrix)
	{
		for (int i=0;i<Matrix.length;i++)
		{
			for (int j=0;j<Matrix[0].length;j++)
			{
				System.out.print(Matrix[i][j].toString() + " ");
			}
			System.out.println(" ");
		}
	}
	
	public ArrayList<BigInteger> FermatFactorization (BigInteger N)
	{
		//Initialize Fermat Factorization
		ArrayList <BigInteger> factors = new ArrayList<BigInteger>();		
		//Create initial starting value
		while (bigPrime.mod(BigInteger.valueOf(2)).equals(0))
		{
			System.err.println("This number is even! Fermat Factorization will have at least one 2!");
			bigPrime=bigPrime.divide(BigInteger.valueOf(2));
			factors.add(BigInteger.valueOf(2));
		}
		
		BigInteger x = bigIntSqRootCeil(N);	
		BigInteger testValue;
		BigInteger sqrtofa=BigInteger.ZERO;
		/*
		 *  Structure of Fermat Factorization:
		 *  testValue^2 = (x^2) - N
		 *  goal of method: 
		 *  if TestValue is a perfect square, I am done.
		 */
		while (true)
		{
			testValue = x.multiply(x).subtract(N);
			sqrtofa = sqrt(testValue);
			//System.out.println(x);
			//System.out.println("TestValue: " + testValue + " SQRT: " + sqrtofa);
			
			if (sqrtofa.multiply(sqrtofa) == testValue)
			{
				break;
			}
			else
			{
				x=x.add(BigInteger.ONE);
			}
		}
		//Factors are found!
		// x^2 + y^2 = (x+y)(x-y)
		factors.add((x.add(sqrtofa)));
		factors.add((x.add(sqrtofa)));
		return factors;
	}
	
	public static BigInteger sqrt(BigInteger n)
	{
	    if (n.signum() >= 0)
	    {
	        final int bitLength = n.bitLength();
	        BigInteger root = BigInteger.ONE.shiftLeft(bitLength / 2);

	        while (!isSqrt(n, root))
	        {
	            root = root.add(n.divide(root)).divide(BigInteger.valueOf(2));
	        }
	        return root;
	    }
	    else
	    {
	        throw new ArithmeticException("square root of negative number");
	    }
	}


	private static boolean isSqrt(BigInteger n, BigInteger root)
	{
	    final BigInteger lowerBound = root.pow(2);
	    final BigInteger upperBound = root.add(BigInteger.ONE).pow(2);
	    return lowerBound.compareTo(n) <= 0
	        && n.compareTo(upperBound) < 0;
	}
	
	public static BigInteger bigIntSqRootCeil(BigInteger x) throws IllegalArgumentException 
	{
	    if (x.compareTo(BigInteger.ZERO) < 0) 
	    {
	        throw new IllegalArgumentException("Negative argument.");
	    }
	    // square roots of 0 and 1 are trivial and
	    // y == 0 will cause a divide-by-zero exception
	    if (x == BigInteger.ZERO || x == BigInteger.ONE)
	    {
	        return x;
	    }
	    BigInteger two = BigInteger.valueOf(2L);
	    BigInteger y;
	    // starting with y = x / 2 avoids magnitude issues with x squared
	    for (y = x.divide(two);
	            y.compareTo(x.divide(y)) > 0;
	            y = ((x.divide(y)).add(y)).divide(two));
	    if (x.compareTo(y.multiply(y)) == 0)
	    {
	        return y;
	    }
	    else
	    {
	        return y.add(BigInteger.ONE);
	    }
	}
	
	public static void main (String [] args)
	{
		BigInteger encoder=BigInteger.valueOf(3);
		BigInteger p= new BigInteger("2351771");
		BigInteger q= new BigInteger("15485849");
		BigInteger N = p.multiply(q);
		BigRSACracker BonSy = new BigRSACracker(encoder,N);
		
		int message=46509;
		//BonSy.EulerPhi(message);
		//int e = BonSy.encrypt(5872);
		//System.out.println("Encrypt: " + e);
		//System.out.println("Decrypt after Encrypting: " + BonSy.decrypt(e));
		BonSy.EulerPhiTest(message);
		//System.out.println(BonSy.gcd(message, BonSy.bigPrime));
	}
}