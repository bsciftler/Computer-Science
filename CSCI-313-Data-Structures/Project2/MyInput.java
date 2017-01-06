import java.util.ArrayList;
import java.util.LinkedList;

public class MyInput 
{
	public static void main (String [] args)
	{
		ArrayList<Integer> input_arr = new ArrayList<Integer>();
		input_arr.add(0);
		input_arr.add(-10);
		input_arr.add(10);
		input_arr.add(5);
		input_arr.add(20);

		ArrayList<Integer> ROAR = new ArrayList<Integer>();
		ROAR.add(5);
		ROAR.add(51);
		ROAR.add(47);
		ROAR.add(60);
		ROAR.add(4);
		ROAR.add(76);
		ROAR.add(-8);
		ROAR.add(21);
		ROAR.add(78);
		ROAR.add(-45);
		ROAR.add(64);
		ROAR.add(-98);
		ROAR.add(93);
		ROAR.add(-82);
		ROAR.add(29);
		ROAR.add(52);
		
		//System.out.println(ROAR.size());
		LinkedList< ArrayList<Integer> > ret = Prob1Prob2Class.Problem2(ROAR);
		
	    for (ArrayList<Integer> one_output : ret) 
	        IOUtilsClass.printV_with_prefix(one_output, "OUTPUT:");
		 
	}
	/*
	 * 		int min=4;
	//int min = MinIndex(ROAR)-1;
	if (Step2B(min,ROAR))
	{
		System.out.println("TRUE! Did it work???");
	}
	else
	{
		System.out.println("FALSE! Did it work???");
	}
	 */
	private static int MinIndex(ArrayList<Integer> input_array)
	{
		int minIndex=0;
		for (int i=0;i<input_array.size();i++)
		{
			if (input_array.get(minIndex) > input_array.get(i))
			{
				minIndex=i;
			}
		}
		return minIndex;
	}
	
	private static boolean Step2B(int Root, ArrayList<Integer> input_array)
	{
		//This is my Step 2B Check:
		for (int i=Root;i>-1;i--)
		{
			if(input_array.get(i) < input_array.get(Root))
			{
				return false;
			}
		}
		return true;
	}
}