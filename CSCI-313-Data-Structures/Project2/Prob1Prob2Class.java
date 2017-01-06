import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Prob1Prob2Class
{
	
	public static LinkedList< ArrayList<Integer> > Problem1(ArrayList<Integer> num_val_list)
	{
		LinkedList< ArrayList<Integer> > output_list = new LinkedList<ArrayList<Integer>>();
/*
 * 		Step 1: Get the highest possible combination.
 * 		Input Example: 1, 5, 5, 7, 5 
 * 		Start with: 0, 4, 4, 6, 5 and add it as a solution
 * 		Step 2:
 * 		Then decrease by 1 and keep adding solutions.
 * 		Until i hit 0, 0, 0, 0, 0 and I add it to the LinkedList and I am done.		
 */
		//Step 1:
		int currentIndex=num_val_list.size();
		int [] MemoryArray=new int [currentIndex];
		for (int i=0;i<currentIndex;i++)
		{
			/*
			 * 	Step 1.5: Create a "Memory Array"
				This is so the Program can memorize the original values.
			 	For Example: I have
			 	Original: 5, 6, 5
			 	Current: 5, 0, 0 
			 	I need to be able to do this...
			 	4, 6, 5
			 	But if I am consistently going to modify the input, I need a Memorized Array
			 	to remember the original values.
			 */
			int x=num_val_list.get(i);
			--x;
			num_val_list.set(i, x);
			MemoryArray[i]=x;
		}
		--currentIndex;//To get the RightMost Index and not get an IndexOutofBounds
		
		output_list.add(createCopy(num_val_list)); //Add my first successful case.		
		//Step 2: Recursive Method...
		Problem1Recursion(num_val_list,output_list,currentIndex,MemoryArray);
		//I am passing by reference so I will be okay as output_list will have all updated solutions.
		return output_list;	
	}
	
	public static ArrayList<Integer> createCopy (ArrayList<Integer> original)
	{
		ArrayList<Integer> COPY = new ArrayList<Integer>();
		for (int i=0;i<original.size();i++)
		{
			COPY.add(original.get(i));
		}
		return COPY;
	}
	
	private static boolean Problem1BaseCase(ArrayList<Integer> check)
	{
		for (int i=0;i<check.size();i++)
		{
			if (check.get(i)!=0)
			{
				return false;
			}
		}
		return true;
	}
	
	private static void Problem1Recursion
	(ArrayList<Integer> num_val_list, LinkedList<ArrayList<Integer>> output_list, int currentIndex, int[] MemoryArray) 
	{
		if (Problem1BaseCase(num_val_list))
		{
			return;
		}
	//If its not base case, add the value
	
		//Proceed to create the next output...
		if (num_val_list.get(currentIndex)==0)
		{
			//Find Closest non-zero
			while(num_val_list.get(currentIndex)==0)
			{
				--currentIndex;
			}
			//Decrease that value by 1...
			int x=num_val_list.get(currentIndex);
			--x;
			num_val_list.set(currentIndex, x);
				
			//Refill all values right of the value that got decreased
			for (int i=currentIndex+1;i<num_val_list.size();i++)
			{
				num_val_list.set(i, MemoryArray[i]);
			}
			output_list.add(createCopy(num_val_list));
			//Set CurrentIndex all the way to the Right and Print again
			currentIndex=num_val_list.size()-1;
			Problem1Recursion(num_val_list, output_list, currentIndex, MemoryArray);
		}
		else 
		{
			int x=num_val_list.get(currentIndex);
			--x;
			num_val_list.set(currentIndex, x);
			output_list.add(createCopy(num_val_list));
			Problem1Recursion(num_val_list,output_list,currentIndex, MemoryArray);
		}
	}

	public static LinkedList< ArrayList<Integer> > Problem2(ArrayList<Integer> input_array)
	{
		/*
		 * Algorithm:
		 * 1A- Find the Index with the smallest value in the ArrayList
		 * This is useful because I know I do NOT need to check any values to the left of the minimum 
		 * by definition
		 * 1B- Build all possible Sub-Arrays with the Minimum value.
		 * 2A- Go backward from the Minimum Index
		 * 2B- THERE CAN BE EXCEPTIONS DOUBLE CHECK FIRST!!!
		 * 2C- Assuming there is no exception, repeat Step 1B
		 * Once I get a "-1" Index after starting with the Minimum Index...I am done!
		 */
		LinkedList< ArrayList<Integer> > output_list = new LinkedList<>();
		//Sub Array Variables
		int Root=MinIndex(input_array); //This is the "First Element" in the SubArray
		int InputTravelIndex = Root;
		
		//Stack <Integer> subArray = new Stack<Integer>();//I am building Sub Array Max to Minimum (Right to Left).
		Queue subArray = new Queue();
		
		Problem2Recursion(output_list, input_array, InputTravelIndex, Root, subArray);
		//Prune any Extras...
		Problem2Prune(output_list);
		
		return output_list;
	}
	
	private static void Problem2Prune(LinkedList<ArrayList<Integer>> output_list)
	{
		for (int i=0;i<output_list.size();i++)
		{
			ArrayList<Integer> duplicate = output_list.get(i);
			for (int j=i+1;j<output_list.size();j++)
			{
				if (compareEquality(output_list.get(j), duplicate))
				{
					output_list.remove(j);
				}
			}
		}
		//Compare the VERY Last Two Elements
		if (compareEquality(output_list.get(output_list.size()-2), output_list.get(output_list.size()-1)))
		{
			output_list.remove(output_list.size()-1);
		}
	}
	
	private static boolean compareEquality(ArrayList<Integer> A, ArrayList<Integer> B)
	{
		if (A.size()!=B.size())
		{
			return false;
		}
		for (int i=0;i<A.size();i++)
		{
			if (A.get(i)!=B.get(i))
			{
				return false;
			}
		}
		return true;
	}
	
//USING QUEUE!!!!	
	private static void Problem2Recursion
	(LinkedList<ArrayList<Integer>> output_list, ArrayList<Integer> input_array, 
	int InputTravelIndex, int subArrayFirstElement, Queue subArray)
	{	
		if (subArrayFirstElement<=-1)
		{
			return;
		}
		
		if (InputTravelIndex <= 0)
		{
			return;
		}
		
		//Base Case: Add Solution and Prevent Index Out of Bounds Exception...
		if (InputTravelIndex == input_array.size())
		{
			Queue subArrayCLONE = subArray.createCopy();
			output_list.add(QueueToArrayList(subArrayCLONE));//Add Solution
			
			/*
			 * For BackTrack: I will go from Right to Left.
			 * This will Require a Stack instead of a Queue.
			 * Also, I will make a new variable called poison.
			 * The goal of poison is tell the program which maximum arrays have already been completed.
			 */
			
			int PoisonIndex = input_array.size()-1;
			InputTravelIndex=PoisonIndex;
			Stack <Integer> BackTrack = new Stack <Integer>();
			Problem2BackTrack(output_list,input_array, subArrayFirstElement, 
					InputTravelIndex, PoisonIndex, BackTrack);
			//BackTrack completed...Reset the Variables!
			subArray.clearAll();
			//First...Find a GOOD ROOT HERE!!!If you get -1 it is okay...
			--subArrayFirstElement;
			subArrayFirstElement=goodRoot(input_array,subArrayFirstElement);
			InputTravelIndex=subArrayFirstElement;
			Problem2Recursion(output_list,input_array,InputTravelIndex, subArrayFirstElement, subArray);
		}
		
		//Build the SubArray for the First Time
		if (subArray.isEmpty())
		{
			subArray.insert(input_array.get(InputTravelIndex));
			++InputTravelIndex;
			Problem2Recursion(output_list,input_array,InputTravelIndex, subArrayFirstElement, subArray);
		}
		
		else
		{
			try
			{			
				if (subArray.peekTail() < input_array.get(InputTravelIndex))
				{
					subArray.insert(input_array.get(InputTravelIndex));
					++InputTravelIndex;
					Problem2Recursion(output_list,input_array,InputTravelIndex, subArrayFirstElement, subArray);
				}
				else
				{
					++InputTravelIndex;
					Problem2Recursion(output_list,input_array,InputTravelIndex, subArrayFirstElement, subArray);
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOE)
			{
				/*
				 * So from the looks of it. I know that I get the exception
				 * when InputTravelIndex is the size of input_array.
				 * I tried my best to fix it, but yeah...
				 */
				//System.out.println("Value of InputTravelIndex " + InputTravelIndex);
				//System.out.println("EXCEPTION FOUND: QUEUE BUILDING");
			}
		}
	}
	
//BACKTRACK WITH STACK!!!!
	private static void Problem2BackTrack
	(LinkedList<ArrayList<Integer>> output_list, ArrayList<Integer> input_array, 
	int subArrayFirstElement, int InputTravelIndex, int PoisonIndex, Stack<Integer> BackTrack)
	{
		/*
		 * Now I will go Right to Left instead of Left to Right.
		 */
		//Base Case:
		if (PoisonIndex<=subArrayFirstElement)
		{
			//There are no more elements to add! 
			return;
		}
		if (InputTravelIndex >= input_array.size() || InputTravelIndex < 0)
		{
			return;
		}
		
		if (InputTravelIndex==subArrayFirstElement)
		{
			
			//Add my First Element
			BackTrack.push(input_array.get(subArrayFirstElement));
	
			Stack<Integer> newStack = new Stack<Integer>();
			newStack.addAll(BackTrack);
			ArrayList<Integer> Tentative = StackToArrayList(newStack);
			
			//Do I have a valid subArray??
			if (isValidMaxArray(input_array,Tentative.get(Tentative.size()-1)))
			{
				//Is this a Unique Solution???
				if(!output_list.contains(Tentative))
				{
					//Yes it is a unique entry
					BackTrack.clear();
					output_list.add(Tentative);		
				}
				else
				{
					//No the solution has already been found. Poison it!
					BackTrack.clear();
					--PoisonIndex;
					PoisonIndex=goodLastIndex(input_array, InputTravelIndex);
				}
			}
			//Time to Poison the Index again.
			else
			{
				//No the solution has already been found. Poison it!
				BackTrack.clear();
				--PoisonIndex;
				PoisonIndex=goodLastIndex(input_array, InputTravelIndex);
			}
			
			//Kill off any other random Recursive Stacks.
			if (BackTrack.isEmpty())
			{
				return;
			}
			//Start again.
			InputTravelIndex= PoisonIndex;
			Problem2BackTrack(output_list,input_array, subArrayFirstElement, InputTravelIndex, PoisonIndex,BackTrack);
		}
		if (BackTrack.isEmpty())
		{
			BackTrack.push(input_array.get(InputTravelIndex));
			--InputTravelIndex;
			Problem2BackTrack(output_list,input_array, subArrayFirstElement, InputTravelIndex, PoisonIndex,BackTrack);
		}
		else
		{
			if (BackTrack.peek() > input_array.get(InputTravelIndex))
			{
				//BE EXTRA CAREFUL WITH INAPPROPIATE MINIMUM VALUES!!!
				//There can be NO Number smaller than your "First Value!"
				if (input_array.get(InputTravelIndex) < input_array.get(subArrayFirstElement))
				{
					--InputTravelIndex;
					Problem2BackTrack(output_list,input_array, subArrayFirstElement, InputTravelIndex, PoisonIndex,BackTrack);
				}
				BackTrack.push(input_array.get(InputTravelIndex));
				--InputTravelIndex;
				Problem2BackTrack(output_list,input_array, subArrayFirstElement, InputTravelIndex, PoisonIndex,BackTrack);
			}
			else
			{
				--InputTravelIndex;
				Problem2BackTrack(output_list,input_array, subArrayFirstElement, InputTravelIndex, PoisonIndex,BackTrack);
			}
		}
	}
	
	private static boolean isValidMaxArray(ArrayList<Integer> input_array,int index)
	{
		for (int i=index+1;i<input_array.size();i++)
		{
			if (input_array.get(index) < input_array.get(i))
			{
				return false;
			}
		}
		return true;
	}
	
	private static ArrayList<Integer> StackToArrayList(Stack<Integer> subArray)
	{
		ArrayList<Integer> answer = new ArrayList<Integer>();
		while (!subArray.isEmpty())
		{
			answer.add(subArray.pop());
		}
		return answer;
	}
	
	private static ArrayList<Integer> QueueToArrayList(Queue subArray)
	{
		ArrayList<Integer> answer = new ArrayList<Integer>();
		while (!subArray.isEmpty())
		{
			answer.add(subArray.pull());
		}
		return answer;
	}
	
	private static int goodRoot(ArrayList<Integer> input_array, int oldRoot)
	{
		//This is my Step 2B Check (See Notes on Algorithm Description)
		if (oldRoot < 0)
		{
			return -1; //No more good starting values!!
		}
		
		for (int i=oldRoot;i>=0;i--)
		{
			try
			{
				if(input_array.get(i) < input_array.get(oldRoot))
				{
					//return goodRoot(input_array, oldRoot-1);
					return goodRoot(input_array,--oldRoot);
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOE)
			{
				System.out.println("EXCEPTION FOUND AT FINDING NEW ROOT");
			}
		}
		return oldRoot;
	}
	
	private static int goodLastIndex(ArrayList<Integer> input_array, int lastID)
	{
		//This is my Step 2B Check:
		//But this is for the Problem 2 BackTrack...
		if (lastID < 0)
		{
			return -1; //No more good starting values!!
		}
		
		for (int i=lastID;i>=0;i--)
		{
			try
			{
				if(input_array.get(i) > input_array.get(lastID))
				{
					//return goodRoot(input_array, oldRoot-1);
					return goodRoot(input_array,--lastID);
				}
			}
			catch (ArrayIndexOutOfBoundsException AIOE)
			{
				//System.out.println("EXCEPTION FOUND AT FINDING NEW ROOT");
			}
		}
		return lastID;
	}

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
}
