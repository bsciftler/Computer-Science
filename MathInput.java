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
			if (input[i] > 40 && input[i] < 60)
			{
				System.out.println(input[i]);
				frequency++;
			}
		}
		System.out.println("Frequency is: " + frequency);
		System.out.println((int)(Math.ceil((double)7/2)));
		int [] TEST={1,2,3,4,5,6,7,8,9
				,10,11,12,13,14,15,16};
		System.out.println("Median: "+Statistics.median(TEST));
		System.out.println("Q1: "+Statistics.findQ1(TEST));
		System.out.println("Q3: "+Statistics.findQ3(TEST));
		//System.out.println("IQR: "+Statistics.findIQR(TEST));
	}
}
