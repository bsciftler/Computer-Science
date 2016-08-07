
public class LinearAlgebra 
{
	public static int [][] Multiply(int [][] MatrixA, int [][] MatrixB, int AR, int ACBR, int BC)
	{
		int Answer [][];
		Answer= new int [AR][BC];

		for (int i=0;i<AR;i++) //Matrix A shift
		{
			for (int k=0;k<BC;k++) //Matrix B shift
			{
				for (int j=0;j<ACBR;j++)
				{
					Answer[i][k]+=MatrixA[i][j]*MatrixB[j][k];
				}
			}
		}
	return Answer;
	}
	
	//I will soon place...
	
	//Addition
	
	//Determinant
	
	//Eigenvalue?
}
