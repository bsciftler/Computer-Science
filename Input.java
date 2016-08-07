import javax.swing.JOptionPane;

public class Input
{
	public static void main (String [] args)
	{

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
if (AC!=BR)
{
	JOptionPane.showMessageDialog(null, "Sorry but the Number of columns in Matrix A must be equal to Number of Rows in Matrix B");
	return;
}
//I must convert to final integers once I completed making sure I have proper input.
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

		int Solution [][];
		Solution=LinearAlgebra.Multiply(MatrixA,MatrixB,AR,AC,BC);
		//Print Solution
		for (int i=0;i<AR;i++)
		{
			for (int j=0;j<BC;j++)
			{
				System.out.print(Solution[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
}
