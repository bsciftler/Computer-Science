import java.math.BigInteger;
import java.util.ArrayList;

public class SmallRSACracker
{
	long decoder=0;
	long encoder=0;
	long bigPrime=0;
	long phi=0;
	long LCM=0;
	ArrayList<Long> smallPrimes;
	
	public SmallRSACracker(long e, long N)
	{
		encoder=e;
		bigPrime=N;
		smallPrimes=FermatFactorization(bigPrime);
	}	
	
	public void getInfo()
	{
		System.out.println("=====================================INFO=============================================");
		System.out.println("Encoder: " + encoder);
		System.out.println("Product of Primes: " + bigPrime);
		System.out.println("Prime Factors of N:");
		for (int i=0;i<smallPrimes.size();i++)
		{
			System.out.println("Prime " + i + ": " + smallPrimes.get(i));
		}
		System.out.println("LCM of both (p-1) and (q-1) " + LCM);
		if (decoder == 0)
		{
			this.getDecoder();
		}
		System.out.println("Decoder: " + decoder);
		System.out.println("Encoder*Decoder = " + phi);
		System.out.println("=====================================END OF INFO=============================================");
	}
	
	private int powerMod (int base, long exponent, long mod)
	{
		BigInteger midAnswer = BigInteger.valueOf((long)base);
		midAnswer=midAnswer.modPow(BigInteger.valueOf(exponent),BigInteger.valueOf(mod));
		int answer=0;
		try 
		{
			answer=midAnswer.intValueExact();
		}
		catch (ArithmeticException AE)
		{
			System.out.println("OVERFLOW AT POWERMOD: " + midAnswer);
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
		if (decoder==0)
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
	
	public void TestRSA (int message)
	{
		if (decoder==0)
		{
			this.getDecoder();
		}
		int EulerPhi=powerMod(message,phi,bigPrime);
		System.out.println("Given: " + message + " I get: " + EulerPhi);
		if (EulerPhi!=message)
		{
			System.out.println("ERROR AT TESTING RSA!!!");
		}
	}
	
	public long getDecoder()
	{
		if (decoder==0)
		{
			decoder=RSACrackDecoder(smallPrimes.get(0),smallPrimes.get(1),encoder);
			try
			{
				phi=encoder*decoder;
			}
			catch(ArithmeticException AE)
			{
				System.err.println("OVERFLOW AT CALCULATING PHI(P,Q");
			}
			while (phi < 0)
			{
				phi+=LCM;
			}
			return decoder;
		}
		else
		{
			return decoder;
		}
	}
	
	public long RSACrackDecoder(long p, long q, long e)
	{
		long lcm = computeLCM (p-1,q-1);
		LCM=lcm;
		//Formula to crack decoder:
		//decrypt * encrypt CONGRUENT to 1(Mod LCM(p-1,q-1))
		//Linear Combination:
		// e*x + LCM*y = 1
		ArrayList <Long> linearCombo = extendedGCD(e, LCM);
		//Note for exGCD to work
		//The larger coordinate HAS to be on exGCDMatrix[0][2];
		//So which element my return depends on whether encoder or LCM is bigger
		//In my case, I need the decoder..
		long solution;
		if (e > LCM)
		{
			solution= linearCombo.get(0);
		}
		solution= linearCombo.get(1);
		while (solution < 0)//Avoid Negative answer
		{
			solution+=LCM;
		}
		return solution;
	}
	
	public ArrayList<Long> extendedGCD(long x, long y)
	{
		ArrayList<Long> solution = new ArrayList<Long>();
		long [][] exGCDMatrix = new long [2][3];
		exGCDMatrix [0][0]=1;
		exGCDMatrix [0][1]=0;
		exGCDMatrix [1][0]=0;
		exGCDMatrix [1][1]=1;
		
		if (x > y)
		{
			exGCDMatrix [0][2]=x;
			exGCDMatrix [1][2]=y;
		}
		else if (x==y)
		{
			solution.add((long) 1);
			solution.add((long)0);
			solution.add((long)x);
			return solution;
		}
		else
		{
			exGCDMatrix [0][2]=y;
			exGCDMatrix [1][2]=x;
		}
		
		long quotient;		
		long [] Row2temp = new long [3];
		
		while (true)
		{
			try
			{
				System.out.println("Current Matrix");
				printMatrix(exGCDMatrix);
				if (exGCDMatrix[1][2]==0)
				{
					solution.add(exGCDMatrix[0][0]);
					solution.add(exGCDMatrix[0][1]);
					solution.add(exGCDMatrix[0][2]);
					if (x > y)
					{
		//System.out.println(x + " * " + exGCDMatrix[0][0] + " + " + y + " * " + exGCDMatrix[0][1] + " = " +exGCDMatrix[0][2]);				
					}
					else if (x < y)
					{
		//System.out.println(x + " * " + exGCDMatrix[0][1] + " + " + y + " * " + exGCDMatrix[0][0] + " = " +exGCDMatrix[0][2]);
					}
					return solution;
				}
				//Step 1: Store value of Row 2 in Temporary row.
				Row2temp[0]=exGCDMatrix[1][0];
				Row2temp[1]=exGCDMatrix[1][1];
				Row2temp[2]=exGCDMatrix[1][2];
				//Step 2: compute value of new Row 2.
				//Row 1 - (q* Row2)
				quotient = exGCDMatrix[0][2]/exGCDMatrix[1][2];
				exGCDMatrix[1][0]=exGCDMatrix[0][0]-(Math.multiplyExact(quotient,exGCDMatrix[1][0]));
				exGCDMatrix[1][1]=exGCDMatrix[0][1]-(Math.multiplyExact(quotient,exGCDMatrix[1][1]));
				exGCDMatrix[1][2]=exGCDMatrix[0][2]-(Math.multiplyExact(quotient,exGCDMatrix[1][2]));
				//Step 3: Move old Row 2 to Row 1.
				exGCDMatrix[0][0]=Row2temp[0];
				exGCDMatrix[0][1]=Row2temp[1];
				exGCDMatrix[0][2]=Row2temp[2];
			}
			catch (ArithmeticException AE)
			{
				System.err.println("OVERFLOW DETECTED AT EXTENDEGCD METHOD");
			}
		}
	}
	
	public void printMatrix(long [][] Matrix)
	{
		for (int i=0;i<Matrix.length;i++)
		{
			for (int j=0;j<Matrix[0].length;j++)
			{
				System.out.print(Matrix[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
	public ArrayList<Long> FermatFactorization (long N)
	{
		//Initialize Fermat Factorization
		ArrayList <Long> factors = new ArrayList<Long>();		
		//Create initial starting value
		while (N%2==0)
		{
			System.err.println("This number is even! Fermat Factorization will have at least one 2!");
			N=N/2;
			factors.add((long)2);
		}
		
		double dummy=Math.sqrt(N);
		long x=(long) Math.ceil(dummy);
		
		
		long testValue;
		long sqrtofa=0;
		/*
		 *  Structure of Fermat Factorization:
		 *  testValue^2 = (x^2) - N
		 *  goal of method: 
		 *  if TestValue is a perfect square, I am done.
		 */
		while (true)
		{
			testValue =(x*x) - N;
			sqrtofa = (long) Math.sqrt(testValue);
			//System.out.println(x);
			//System.out.println("TestValue: " + testValue + " SQRT: " + sqrtofa);
			
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
	
	public ArrayList<Long> Factorization (int N)
	{
		ArrayList<Long> primes = new ArrayList<Long>();
		while (N%2==0)
		{
			primes.add((long)2);
			N=N/2;
		}
		int factor = 3;
		while (N!=1)//Prime Factorization is unique
		{
			if (N%factor==0)
			{
				primes.add((long)factor);
				N=N/factor;
			}
			else
			{
				factor+=2;
			}
		}
		return primes;
	}
	
	public long computeLCM (long a, long b)
	{
		long GCD = gcd(a,b);
		long p = 0;
		try 
		{
			p=Math.multiplyExact(a, b);
		}
		catch (ArithmeticException AE)
		{
			System.err.println("OVERFLOW SPOTTED AT COMPUTING LCM OF (p-1) and (q-1)");
		}
		return p/GCD;
	}
	
	public long gcd (long a, long b)
	{
		if (b!=0)
		{
			return gcd(b,a%b);
		}
		return a;
	}
	
	public static void main (String [] args)
	{
		//Info came from Exam 3 from Number Theory...
		long encoder=3;
		long p = 2351771;
		long q = 15485849;
		long ProductofPrimes=0;
		try
		{
			ProductofPrimes=Math.multiplyExact(p, q);
		}
		catch(ArithmeticException AE)
		{
			System.err.println("OVERFLOW DETECTED AT CREAING N: EXITING");
		}
		
		SmallRSACracker BonSy = new SmallRSACracker(encoder,ProductofPrimes);
		BonSy.getInfo();
	
		
		//Message
		int message=46509;
		BonSy.TestRSA(message);
		//System.out.println("Encrypt: " + BonSy.encrypt(message));
	}
}