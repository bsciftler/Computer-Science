import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Practice
{
	public static void main (String args [])
	{
		ArrayList <Integer> Practice = new ArrayList<Integer>();
		Practice.add(4);
		Practice.add(2);
		Practice.add(6);
		Practice.add(1);
		int max=0;
		for (int i=1;i<Practice.size();i++)
		{
			if (Practice.get(max) < Practice.get(i))
			{
				max=i;
			}
		}
		System.out.println("NUMBER SEARCHED FOR IS: " + Practice.get(max));
	}
}
