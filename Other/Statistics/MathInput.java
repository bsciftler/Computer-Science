import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Arrays;

public class MathInput 
{
	public static void main(String[] args) throws IOException
	{
		Scanner Read= new Scanner(new File("Math242HW3.txt"));
		String reader=Read.nextLine();
		String [] Stringinput = reader.split(" ");
		double [] input = new double [Stringinput.length];
		for (int i=0;i<Stringinput.length;i++)
		{
			input[i]=Double.parseDouble(Stringinput[i]);
		}
		Arrays.sort(input);

		double LEAF=4.5;
		int frequency=0;
		for (int i=0;i<Stringinput.length;i++)
		{
			if (input[i] == LEAF)
			{
					frequency++;
			}
		}
		System.out.println("LEAF: " + LEAF + " and its Frequency is: " + frequency);
		double Q1=Statistics.findQ1(input);
		double Q3=Statistics.findQ3(input);
		double IQR=Statistics.findIQR(input);
		System.out.println("MIN:" + input[0]);
		System.out.println("Q1: "+ Q1);
		System.out.println("Median: "+ Statistics.median(input));
		System.out.println("Q3: "+ Q3);
		System.out.println("MAX: " + input[input.length-1]);
		System.out.println("IQR: " + IQR);
		System.out.println("LEFT FENCE: " + (Q1 - (1.5*IQR) ));
		System.out.println("RIGHT FENCE: " + (Q3 +  (1.5*IQR) ));
		Read.close();
	}
}