import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Prob1Prob2Class
{
	public static LinkedList< ArrayList<Integer> > Problem1(ArrayList<Integer> num_val_list)
	{
		LinkedList< ArrayList<Integer> > output_list = new LinkedList<>();
		//Build 
		
		// dummy line, put input_arr into output, remove it when you code
		output_list.add(num_val_list);		
		return output_list;	
	}
	
	public static LinkedList< ArrayList<Integer> > Problem2(ArrayList<Integer> input_array)
	{
		LinkedList< ArrayList<Integer> > output_list = new LinkedList<>();
		//Find Maximum Number's Position
		int MaxPos=findMaxPosition(input_array);
		//Find Smallest Number's Position
		int MinPos=findMinPosition(input_array);
		/* 
		 * Notes: Think of these cases:
		 * 1- Get all possible maximum sub-arrays starting with minimum number.
			2- Think about these cases
			A) Start with Numbers Before Min
			B) Start with Numbers After Min (CONTRADICTION! BECAUSE IT IS MAXIMAL SUBARRAY!)
		 */
		//Start Finding the SubArrays that Satisfy Condition 1
		
		
		//What???? dummy line, put input_arr into output, remove it when you code
		output_list.add(input_array);
		return output_list;
	}
	
	private static ArrayList<Integer> buildSubArray (ArrayList<Integer> input)
	{
		ArrayList <Integer> SubArray = new ArrayList <Integer>();
		return SubArray;
	}
	
	private static int findMinPosition (ArrayList<Integer> input)
	{
		int min = 0; //Assume First number is the largest
		for (int i=1;i<input.size();i++)
		{
			if (input.get(min) < input.get(i))
			{
				min=i;
			}
		}
		return min;
	}
	
	private static int findMaxPosition (ArrayList<Integer> input)
	{
		int max = 0; //Assume First number is the largest
		for (int i=1;i<input.size();i++)
		{
			if (input.get(max) < input.get(i))
			{
				max=i;
			}
		}
		return max;
	}
}
