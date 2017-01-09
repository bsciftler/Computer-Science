import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LinearInput 
{	
	public static void main (String [] args)
	{
		Matrix A=ParseMatrix(new String("MATRIXA.txt"));
		Matrix B=ParseMatrix(new String("MATRIXB.txt"));
		
	}//END OF MAIN
	
	public static Matrix ParseMatrix(String file_name)
	{
	    Scanner sc = null;
	    String tmps;
	    Matrix output= null;
	    try
	    {
	        sc = new Scanner(new File(file_name));
		    while (sc.hasNext())
		    {
		    	tmps = sc.next();
		    	if(tmps.equals("MATRIX"))
		    	{
		    		// initialize the matrix
		    		int nr = sc.nextInt();
		    		int nc = sc.nextInt();
		    		output = new Matrix(nr, nc);
		    	}
		    	else if(tmps.equals("END"))
		    	{
		    		// finished, return the matrix
		    		sc.close();
		    		return output;
		    	}
		    	else if(tmps.equals("SET"))
		    	{
		    		// set an element
		    		int ridx = sc.nextInt(); // row index
		    		int cidx = sc.nextInt(); // col index
		    		int val = sc.nextInt();  // value
		    		output.setElement(ridx, cidx, val);
		    	}
		    	else if(tmps.equals("CLEAR"))
		    	{
		    		// clear an element
		    		int ridx = sc.nextInt(); // row index
		    		int cidx = sc.nextInt(); // col index
		    		output.clearElement(ridx, cidx);		    		
		    	}		    	
		    }
		    sc.close();
		    return output;
	    } 
	    catch (Exception e)
	    {
	    	System.out.println("NULL: ERROR AT PARSE MATRIX!");
	    	return null;
	    }
	}
}