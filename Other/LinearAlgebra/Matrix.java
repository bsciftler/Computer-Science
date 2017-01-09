
public class Matrix
{
	private int determinant=1;
	private boolean determinantFound=false;
	private int rows;
	private int columns;
	private int [][] MatrixA;
	
	public Matrix(int [][] Matrix)
	{
		MatrixA=Matrix;
		rows=Matrix.length;
		columns=Matrix[0].length;
	}
	
	public Matrix (int ROW, int COL)
	{
		rows=ROW;
		columns=COL;
		MatrixA=new int [ROW][COL];
	}
	
	public void clearElement (int row, int column)
	{
		MatrixA[row][column]=0;
	}
	
	public void setElement (int row, int column, int value)
	{
		MatrixA[row][column]=value;
	}
	
	public void print()
	{
		for (int i=0;i<rows;i++)
		{
			for (int j=0;j<columns;j++)
			{
				System.out.println(MatrixA[i][j]);
			}
			System.out.println(" ");
		}
	}
	
	public int [][] Multiply(int [][] MatrixB) throws MatrixException
	{
		if (columns!=MatrixB.length)
		{	
			throw new MatrixException("Number of Columns in Matrix A NOT EQUAL to Number of Rows in Matrix B");
		}
		
		int Answer [][]= new int [rows][MatrixB[0].length];//Matrix A Rows and Matrix B Columns

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
	
	public int Trace () throws MatrixException
	{
		if (rows!=columns)
		{
			throw new MatrixException("This is NOT a sqaure matrix!");
		}
		int answer=0;
		for (int i=0;i<rows;i++)
		{
			answer+=MatrixA[i][i];
		}
		return answer;
	}
	
	public int [][] Addition(int [][] MatrixB)throws MatrixException
	{
		if (columns!=MatrixB[0].length || MatrixB.length!=MatrixB[0].length //Are A and B Squares?
			|| rows!=MatrixB.length  //Do both A and B have the same number of rows?
			|| columns !=MatrixB[0].length)//Do both A and B have the same number of of columns?
		{
			throw new MatrixException("The two matrices are not square matrices that are not of the same size");
		}
		int Answer [][]=new int [rows][columns];
		for (int i=0;i<rows;i++)
		{
			for (int j=0;j<columns;j++)
			{
				Answer[i][j]=MatrixA[i][j]+MatrixB[i][j];
			}
		}
		return Answer;
	}
	
	public int determinant() throws MatrixException//IN PROGRESS
	{
		if (rows!=columns)
		{
			throw new MatrixException("This is NOT a square matrix!");
		}
		if (determinantFound==false)
		{//Compute the Determinant
			
			//if it is a 2x2 Matrix
			if (rows==2 && columns==2)
			{
				determinant=(MatrixA[0][0]*MatrixA[1][1])-(MatrixA[0][1]*MatrixA[1][0]);
				determinantFound=true;
				determinant();
			}
			
			//Step 1: Use LU decomposition...
			
			//Step 2: Det(LU)=Det(L)*Det(U)
			
			
			determinantFound=true;
			determinant();
		}
		//If already found, No need to Compute Determinant Again...
		return determinant;
	}
	
	private int [][] Diagonal()//IN PROGRESS
	{
		//USE LU DECOMPOSITION!!!
		int [][] U = null;
		while (!MatrixMath.isUTM(U))
		{
			//Step 1: Create Identity Matrix.
			int [][] RowOperation = new int [rows][columns];
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
}
