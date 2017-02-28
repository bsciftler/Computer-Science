import java.util.ArrayList;

public class RSACracker
{
	int decoder=-1;
	int encoder=0;
	int bigPrime=0;
	ArrayList<Integer> smallPrimes;
	
	public RSACracker(int N, int e)
	{
		encoder=e;
		bigPrime=N;
		smallPrimes=FermatFactorization(bigPrime);
	}
	
	public int getDecoder()
	{
		if (decoder==-1)
		{
			decoder=RSACrackDecoder(smallPrimes.get(0),smallPrimes.get(1),encoder);
			return decoder;
		}
		else
		{
			return decoder;
		}
	}
	
	public int RSACrackDecoder(int p, int q, int e)
	{
		int LCM = LCM (p-1,q-1);
		//Formula to crack decoder:
		//de CONGRUENT to 1(Mod LCM(p-1,q-1))
		//Linear Combination:
		// e*x + LCM*y = 1
		ArrayList <Integer> linearCombo = extendedGCD(e, LCM);
		//Note for exGCD to work
		//The larger coordinate HAS to be on exGCDMatrix[0][2];
		//So which element my return depends on whether encoder or LCM is bigger
		//In my case, I need the decoder...
		if (e > LCM)
		{
			return linearCombo.get(0);
		}
		return linearCombo.get(1);
		
	}
	
	public ArrayList<Integer> extendedGCD(int x, int y)
	{
		ArrayList<Integer> solution = new ArrayList<Integer>();
		int [][] exGCDMatrix = new int [2][3];
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
			solution.add(1);
			solution.add(0);
			solution.add(x);
			return solution;
		}
		else
		{
			exGCDMatrix [0][2]=y;
			exGCDMatrix [1][2]=x;
		}
		
		int quotient;		
		int [] Row2temp = new int [3];
		
		while (true)
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
	System.out.println(x + " * " + exGCDMatrix[0][0] + " + " + y + " * " + exGCDMatrix[0][1] + " = " +exGCDMatrix[0][2]);				
				}
				else if (x < y)
				{
	System.out.println(x + " * " + exGCDMatrix[0][1] + " + " + y + " * " + exGCDMatrix[0][0] + " = " +exGCDMatrix[0][2]);
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
			exGCDMatrix[1][0]=exGCDMatrix[0][0]-(quotient*exGCDMatrix[1][0]);
			exGCDMatrix[1][1]=exGCDMatrix[0][1]-(quotient*exGCDMatrix[1][1]);
			exGCDMatrix[1][2]=exGCDMatrix[0][2]-(quotient*exGCDMatrix[1][2]);
			//Step 3: Move old Row 2 to Row 1.
			exGCDMatrix[0][0]=Row2temp[0];
			exGCDMatrix[0][1]=Row2temp[1];
			exGCDMatrix[0][2]=Row2temp[2];
		}
	}
	
	private void printMatrix(int [][] Matrix)
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
	
	public ArrayList<Integer> FermatFactorization (int N)
	{
		//Initialize Fermat Factorization
		ArrayList <Integer> factors = new ArrayList<Integer>();		
		//Create initial starting value
		while (N%2==0)
		{
			System.err.println("This number is even! Fermat Factorization will have at least one 2!");
			N=N/2;
			factors.add(2);
		}
		
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