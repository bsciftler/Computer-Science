import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class MathInput 
{
	public static void main(String[] args) throws IOException
	{
		Scanner Read= new Scanner(new File("Math242HW1.txt"));
		String reader=Read.nextLine();
		String [] Stringinput = reader.split(" ");
		int [] input = new int [Stringinput.length];
		for (int i=0;i<Stringinput.length;i++)
		{
			input[i]=Integer.parseInt(Stringinput[i]);
		}
		Arrays.sort(input);
		int frequency=0;
		for (int i=0;i<Stringinput.length;i++)
		{
			if (input[i] == 39)
			{
				System.out.println(input[i]);
				frequency++;
			}
		}
		System.out.println("Frequency is: " + frequency);
		int [] TEST={1,2,3,4,5,6,7,8,9};
		System.out.println("Median: "+Statistics.median(TEST));
		double Q1=Statistics.findQ1(TEST);
		double Q3=Statistics.findQ3(TEST);
		double IQR=Statistics.findIQR(TEST);
		System.out.println("Q1: "+ Q1);
		System.out.println("Q3: "+ Q3);
		System.out.println("IQR: " + IQR);
		System.out.println("LEFT FENCE: " + (Q1 - (1.5*IQR) ));
		System.out.println("RIGHT FENCE: " + (Q3 +  (1.5*IQR) ));
	}
}
	

