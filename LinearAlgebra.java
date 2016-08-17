public class LinearAlgebra 
{
	public static int [][] Multiply(int [][] MatrixA, int [][] MatrixB, int MatrixARows, int AColumnBRows, int MatrixBColumns)
	{
		int Answer [][];
		Answer= new int [MatrixARows][MatrixBColumns];

		for (int i=0;i<MatrixARows;i++) //Matrix A shift
		{
			for (int k=0;k<MatrixBColumns;k++) //Matrix B shift
			{
				for (int j=0;j<AColumnBRows;j++)
				{
					Answer[i][k]+=MatrixA[i][j]*MatrixB[j][k];
				}
			}
		}
	return Answer;
	}
	
	public static int [][] Addition(int [][] MatrixA, int [][] MatrixB, int size)
	{
		int Answer [][];
		Answer=new int [size][size];
		for (int i=0;i<size;i++)
		{
			for (int j=0;j<size;j++)
			{
				Answer[i][j]=MatrixA[i][j]+MatrixB[i][j];
			}
		}
		return Answer;
	}
	
	public static int Determinant(int [][] Matrix, int SIZE)//IN PROGRESS
	{
		int Determinant; int [][] Modified = null;
		if (SIZE==2)
		{
			Determinant=(Matrix[0][0]*Matrix[1][1])-(Matrix[0][1]*Matrix[1][0]);
		}
//Create an Upper or Lower Triangular Matrix. Then I just multiply along the diagonal line.
		else 
		{
			Determinant=1;
			Modified=UTM(Matrix);
		}
		for (int i=0;i<SIZE;i++)
		{
			Determinant*=Modified[i][i];
		}
		return Determinant;
	}
	public static int Eigenvalue (int [][] Matrix)//IN PROGRESS
	{
		int Eigenvalue=0; int [][] Diagonal;
		Diagonal=UTM(Matrix);//Create UTM to get the determinant easier.
		//Get the Characteristic Polynomial.
		//Solve the Equation.
		return Eigenvalue;
	}
	
	public static int [] derivative (int [] Polynomial)
	{
		int [] differentialequation= new int [Polynomial.length-1];
//Note the Position of the Polynomial in the Array determines Exponent.
//For example {40, 10, 20 10} is 40+10x+20x^2+10x^3.
//For the derivative to work as intended, the position has to shift left by 1.
		for (int i=1;i<Polynomial.length;i++)
		{
		//I can skip the first step as the derivative of a constant is always 0.
			differentialequation[i-1]=i*Polynomial[i];
		}
//With this I get the Array {10, 40, 30} or
//10+40x+30x^2 which is the correct derivative.
		return differentialequation;
	}
	
	public static double [] integral (int [] Polynomial)
	{
		double [] integralequation= new double [Polynomial.length+1];
		integralequation[0]=99.999;//I will leave this as "C".
		for (int i=1;i<Polynomial.length+1;i++)
		{
			integralequation[i]=(double)Polynomial[i-1]/i;
		}
		return integralequation;
	}
	
	public static int trace (int [][] Matrix)
	{
		int trace=0;
		for (int i=0;i<Matrix.length;i++)
		{
			trace+=Matrix[i][i];
		}
		return trace;
	}
	
	private static int [][] UTM(int [][] Matrix)//IN PROGRESS
	{
		int [][] transform = null;
		return transform;
	}
}