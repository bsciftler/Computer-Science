import javax.swing.JOptionPane;

public class LinearInput 
{
	public static void main (String [] args)
	{
		/*
		int [] Polynomial=new int [4];
		Polynomial[0]=40;
		Polynomial[1]=10;
		Polynomial[2]=20;
		Polynomial[3]=10;
		int [] diff=LinearAlgebra.derivative(Polynomial);
		double [] integrate=LinearAlgebra.integral(Polynomial);
		for (int i=0;i<5;i++)
		{
			//System.out.println("Diff: " + diff[i]);
			System.out.println("Integate " + integrate[i]);
		}
		*/
		JOptionPane.showMessageDialog(null, "Hello, Welcome to the Matrix Multiplication Calculator");
		int AR;//Matrix A Rows
		int AC;//Matrix A Columns
		int BR;//Matrix B Rows
		int BC;//Matrix B Columns
		try
		{
			AR=Integer.parseInt(JOptionPane.showInputDialog("Please insert number of Rows of Matrix A"));
			AC=Integer.parseInt(JOptionPane.showInputDialog("Please insert number of Columns of Matrix A"));
			BR=Integer.parseInt(JOptionPane.showInputDialog("Please insert number of Rows of Matrix B"));
			BC=Integer.parseInt(JOptionPane.showInputDialog("Please insert number of Columns of Matrix B"));
		}
		catch (NumberFormatException NFE)
		{
			JOptionPane.showMessageDialog(null, "You have input a invalid value, please try again.");
			return;
		}
	
		final int FinAR=AR;
		final int FinAC=AC;
		final int FinBR=BR;
		final int FinBC=BC;

		int MatrixA[][];
		int MatrixB[][];
		MatrixA= new int [FinAR][FinAC];
		MatrixB= new int [FinBR][FinBC];
		//Fill up Matrix A
		for (int a=0;a<FinAR;a++)//Row
		{
			for (int b=0;b<FinAC;b++)//Column
			{
				int temp = 0;
				try
				{
					temp=Integer.parseInt(JOptionPane.showInputDialog("Enter a value for Matrix A at ["+ a + "] ["+ b+"]"));
				}
				catch (NumberFormatException NFE)
				{
					JOptionPane.showMessageDialog(null, "You have input a invalid value, please try again.");
					return;
				}
				MatrixA[a][b]=temp;
			}
		}
		
		//Fill up Matrix B
		for (int c=0;c<BR;c++)
		{
			for(int d=0;d<BR;d++)
			{
				int temp = 0;
				try
				{
					temp=Integer.parseInt(JOptionPane.showInputDialog("Enter a value for Matrix B at [" + c + "] ["+ d+"]"));
				}
				catch (NumberFormatException NFE)
				{
					JOptionPane.showMessageDialog(null, "You have input a invalid value, please try again.");
					return;
				}
				MatrixB[c][d]=temp;
			}
		}
		
		if(LinearAlgebra.triangular(MatrixA))
		{
			JOptionPane.showMessageDialog(null,"UTM/LTM Matrix");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"NOT UTM/LTM Matrix");
		}
		if(LinearAlgebra.triangular(MatrixB))
		{
			JOptionPane.showMessageDialog(null,"UTM/LTM Matrix");
		}
		else
		{
			JOptionPane.showMessageDialog(null,"NOT UTM/LTM Matrix");
		}
		
		int ProductSolution [][]=null;
		try 
		{
			ProductSolution=LinearAlgebra.Multiply(MatrixA,MatrixB);
			//Print Multiplication result
			System.out.println("Solution of Matrix A * Matrix B");
			for (int i=0;i<AR;i++)
			{
				for (int j=0;j<BC;j++)
				{
					System.out.print(ProductSolution[i][j] + " ");
				}
				System.out.println(" ");
			}
		} 
		catch (MatrixException M)
		{
			JOptionPane.showMessageDialog(null, "Number of Columns in Matrix A NOT EQUAL to Number of Rows in Matrix B");
		}
	
		System.out.println("Solution of Matrix A + Matrix B");

		int SumSolution [][]=null;
		try 
		{
			SumSolution=LinearAlgebra.Addition(MatrixA, MatrixB);
			//Print Sum of Matrices
			for (int i=0;i<AR;i++)
			{
				for (int j=0;j<BC;j++)
				{
					System.out.print(SumSolution[i][j] + " ");
				}
				System.out.println(" ");
			}
		} 
		catch (MatrixException M) 
		{
			JOptionPane.showMessageDialog(null, "The two matrices are not square matrices of the same size!");
		}
		
	
	}//END OF MAIN
}
