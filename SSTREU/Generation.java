import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Generation
{
	public static void main(String[] args)
	{
		String filelocation= "C:\\Users\\Andrew\\Desktop\\DBSimulation.txt";
		try 
		{

		PrintWriter pw = new PrintWriter(
						 new BufferedWriter(
						 new OutputStreamWriter(
						 new FileOutputStream(filelocation))));
		for(int i =1;i<=100; i++)
		{
			pw.print(i+","+tenAPGenerate());
			pw.println();
		}
		pw.flush();
		pw.close();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	
	public static int Random() //Generate numbers 1 to 100
	{
		return (int)Math.ceil((Math.random()*100));
	}
	
	public static String tenAPGenerate()
	{
		String gen="";
		for (int i=0;i<10;i++)
		{
			gen+=Integer.toString(Random())+",";
		}
		return gen;
	}
}
