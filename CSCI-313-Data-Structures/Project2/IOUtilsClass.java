import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IOUtilsClass
{
	public static void printV(int[] tmpV)
	{
		for(int i = 0; i < tmpV.length; i++)
		{
			System.out.print(tmpV[i]); 
			if( i < tmpV.length-1 )
				System.out.print(' ');
		}
		System.out.println("");
	}
	
	public static void printV(ArrayList<Integer> tmpV)
	{
		for(int i = 0; i < tmpV.size(); i++)
		{
			System.out.print(tmpV.get(i)); 
			if( i < tmpV.size()-1 )
				System.out.print(' ');
		}
		System.out.println("");
	}

	public static void printV_with_prefix(ArrayList<Integer> tmpV, String prefix)
	{
		System.out.print(prefix + " ");
		for(int i = 0; i < tmpV.size(); i++)
		{
			System.out.print(tmpV.get(i)); 
			if( i < tmpV.size()-1 )
				System.out.print(' ');
		}
		System.out.println("");
	}

	public static void printV(ArrayList<Integer> tmpV, String fname) throws IOException
	{

		FileWriter fw = new FileWriter(fname);
		for (int i = 0; i < tmpV.size(); i++)
		{
			fw.write(Integer.toString(tmpV.get(i)));
			fw.write(" ");
		}
		fw.write("\n");
		fw.close();
	}
	
	public static void addLine(String line, String fname) throws IOException
	{
		FileWriter fw = new FileWriter(fname, true);
		fw.write(line);
		fw.write("\n"); 
		fw.close();
	}

	public static ArrayList<Integer> ParseP1P2(String file_name) throws IOException
	{
	    Scanner sc = new Scanner(new File(file_name));
	        if(!sc.hasNext())
	        {
	        	sc.close();
	        	throw new IOException();
	        }
	        
		    ArrayList<Integer> ret = new ArrayList<>();
		    
		    while(sc.hasNext())
		    	ret.add(sc.nextInt());
		    sc.close();
		    return ret;
	}

	public static ArrayList<Integer> ParseP3(String file_name, int[] tol) throws IOException
	{
	    Scanner sc = new Scanner(new File(file_name));
	        if(!sc.hasNext())
	        {
	        	sc.close();
	        	throw new IOException();
	        }
	        
		    ArrayList<Integer> ret = new ArrayList<>();
		    
		    while(sc.hasNext())
		    {
		    	String tmps = sc.next();
		    	if(!tmps.equals("TOL")) 
		    		ret.add(Integer.parseInt(tmps));
		    	else
		    	{
		    		tol[0] = sc.nextInt();
		    		break;
		    	}
		    }		    
		    sc.close();
		    return ret;
	}
}
