public class MatrixMath
{	
	public static int [][] Multiply(int [][] MatrixA, int [][] MatrixB) throws MatrixException
	{
		if (MatrixA[0].length!=MatrixB.length)
		{	
			throw new MatrixException("Number of Columns in Matrix A NOT EQUAL to Number of Rows in Matrix B");
		}
		
		int Answer [][]= new int [MatrixA.length][MatrixB[0].length];//Matrix A Rows and Matrix B Columns

		for (int i=0;i<MatrixA.length;i++) //Matrix A Row shift
		{
			for (int k=0;k<MatrixB[0].length;k++) //Matrix B Column shift
				
			{
				for (int j=0;j<MatrixA[0].length;j++)
				{
					Answer[i][k]+=MatrixA[i][j]*MatrixB[j][k];
				}
			}
		}
		return Answer;
	}
	
	public static int [][] Addition(int [][] MatrixA, int [][] MatrixB)throws MatrixException
	{
		if (MatrixA.length!=MatrixA[0].length || MatrixB.length!=MatrixB[0].length //Are A and B Squares?
			|| MatrixA.length!=MatrixB.length  //Do both A and B have the same number of rows?
			|| MatrixA[0].length !=MatrixB[0].length)//Do both A and B have the same number of of columns?
		{
			throw new MatrixException("The two matrices are not square matrices that are not of the same size");
		}
		int Answer [][]=new int [MatrixA.length][MatrixA.length];
		for (int i=0;i<MatrixA.length;i++)
		{
			for (int j=0;j<MatrixA.length;j++)
			{
				Answer[i][j]=MatrixA[i][j]+MatrixB[i][j];
			}
		}
		return Answer;
	}
	
	public static int Determinant(int [][] Matrix) throws MatrixException//IN PROGRESS
	{
		if (Matrix.length!=Matrix[0].length)
		{
			throw new MatrixException("This is NOT a square matrix!");
		}
		
		int Determinant=1; 
		int [][] Modified = null;
		if (Matrix.length==2 && Matrix[0].length==2)
		{
			Determinant=(Matrix[0][0]*Matrix[1][1])-(Matrix[0][1]*Matrix[1][0]);
		}
		else //Create an Upper or Lower Triangular Matrix. Then I just multiply along the diagonal line.
		{
			Modified=Diagonal(Matrix);
			Determinant=1;
		}
		for (int i=0;i<Matrix.length;i++)
		{
			Determinant*=Modified[i][i];
		}
		return Determinant;
	}
	
	public static int Eigenvalue (int [][] Matrix)//IN PROGRESS
	{
		int Eigenvalue=0; int [][] Diagonal=null;
		Diagonal=Diagonal(Matrix);//Create UTM to get the determinant easier.
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
		integralequation[0]=(int)'C';//I will leave this as "C" in the intergral formula.
		for (int i=1;i<Polynomial.length+1;i++)
		{
			integralequation[i]=(double)Polynomial[i-1]/i;
		}
		return integralequation;
	}
	
	public static int trace (int [][] Matrix) throws MatrixException
	{
		if (Matrix.length!=Matrix[0].length)
		{
			throw new MatrixException("This is NOT a sqaure matrix!");
		}
		int trace=0;
		for (int i=0;i<Matrix.length;i++)
		{
			trace+=Matrix[i][i];
		}
		return trace;
	}
	
	private static int [][] Diagonal(int [][] Matrix)//IN PROGRESS
	{
		int rows=Matrix.length;
		int column=Matrix[0].length;
		//USE LU DECOMPOSITION!!!
		int [][] U = null;
		while (!isUTM(U))
		{
			//Step 1: Create Identity Matrix.
			int [][] RowOperation = new int [rows][column];
			for (int i=0;i<rows;i++)
			{
				RowOperation[i][i]=1;
			}
			//Figure out what Row Operation I need to do.
			
			//Write it to my ID Matrix
			
			//Append it to my LinkedList that will contain all elementary matrices.
		}
		//Matrix A = L*U
		//L = Inverse of all Matrices in Linked List.
		
		//I will return both LTM and UTM for purposes of both Determinant and Eigenvalues.
		return U;
	}
	
	public static boolean isUTM(int [][] Matrix)
	{
		for (int i=0;i<Matrix.length;i++)
		{
			for (int j=0;j<Matrix[0].length;j++)
			{
				if (Matrix[i][j]!=0)
				{
					return false;
				}
			}
		}
		return true;
	}
}