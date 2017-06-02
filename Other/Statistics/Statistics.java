import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Statistics
{
	//variables
	private Rational min;
	private Rational Q1;
	private Rational median;
	private Rational Q3;
	private Rational max;
	private Rational IQR;
	
	//Universal variables
	private int SIZE;
	private int SPLIT;
	private Rational [] input;
	private boolean computed=false;

	public Statistics (Rational [] newInput)
	{
		input=newInput;
		min=input[0];
		max=input[input.length-1];
		SIZE=input.length;
		SPLIT=SIZE/2;
	}
	
	public void ToukeyFiveNumberSummary()
	{
		if (computed==false)
		{
			median= median(input);
			Q1= findQ1(input);
			Q3= findQ3(input);
			IQR=Q3;
			IQR.subtract(Q1);
			IQR.multiply(new Rational(3,2));
			computed=true;
		}
		
		System.out.println("Minimum: "); min.print();
		System.out.println("Q1: "); Q1.print();
		System.out.println("Median: "); median.print();
		System.out.println("Q3: "); Q3.print();;
		System.out.println("Maximum: "); max.print();
		Rational temp1 = max;
		temp1.add(IQR);
		System.out.println("Modified Maximum: "); temp1.print() ;
		Rational temp2 = min;
		temp1.subtract(IQR);
		System.out.println("Modified Minimum: "); temp2.print();
	}
	
	private Rational findQ1(Rational [] input)
	{
		if (SPLIT%2==0)
		{
			Rational a=input[(SPLIT/2)-1];
			a.add(input[((SPLIT)/2)]);
			a.multiply(new Rational(1,2));
			return a;
		}
		else
		{
			return input[(((SPLIT+1)/2) - 1)];
		}
	}

	public Rational median(Rational [] input)
	{
		if (SIZE%2==0)
		{
			Rational a= input[(SIZE/2)-1];
			a.add(input[(SIZE/2)]);
			a.multiply(new Rational(1,2));
			return a;
		}
		else
		{	
			return input[(((SIZE+1)/2) - 1)];
		}
	}
	
	public Rational findQ3(Rational [] input)
	{
		if (SPLIT%2==0)
		{
			Rational a= input[((SIZE+SPLIT)/2)-1];
			a.add(input[((SIZE+SPLIT)/2)]);
			a.multiply(new Rational(1,2));
			return a;
		}
		else
		{
			return input[(((SPLIT+SIZE)/2) - 1)];
		}
	}
	
//===============ABSORB INPUT FROM TEXT FILE========================
	public static void main(String[] args) throws IOException
	{
		int [] input = readArray("Math242HW1.txt");
		Arrays.sort(input);
		Rational [] Rinput = intToRationalArray(input);
		Statistics Math242HW=new Statistics(Rinput);
		Math242HW.ToukeyFiveNumberSummary();
	}
	
	public static int [] readArray(String filename) throws FileNotFoundException
	{
		Scanner Read= new Scanner(new File(filename));
		String reader=Read.nextLine();//Read only 1 Line.
		String [] Stringinput = reader.split(" ");
		int [] input = new int [Stringinput.length];
		for (int i=0;i<input.length;i++)
		{
			input[i]=Integer.parseInt(Stringinput[i]);
		}
		Read.close();
		return input;
	}
	
	public static Rational [] intToRationalArray(int [] input)
	{
		Rational [] answer = new Rational [input.length];
		for (int i=0;i<input.length;i++)
		{
			answer[i]=new Rational(input[i]);
		}
		return answer;
	}
}