/*
 * 	Code Taken from: 
 * 	http://www.java2novice.com/java-sorting-algorithms/merge-sort/
 * 	Now that I learned how to compare Pallier Encrypted Values:
 * 	Following this guide, I can run the C++ in Java...
 * 	https://www.ibm.com/developerworks/java/tutorials/j-jni/j-jni.html
 */

public class MyMergeSort
{
	private int [] array;
	private int [] tempMergArr;
	private int length;

	public native void compare(int x, int y);

	public static void main(String a[])
	{
		System.loadLibrary("NumberList");
		int[] inputArr = {45,23,11,89,77,98,4,28,65,43};
		MyMergeSort mms = new MyMergeSort();
		mms.sort(inputArr);
		for(int i:inputArr)
		{
	    	System.out.print(i);
	    	System.out.print(" ");
	    }
	}
	
	public void sort(int inputArr[])
	{
		this.array = inputArr;
		this.length = inputArr.length;
		this.tempMergArr = new int[length];
		doMergeSort(0, length - 1);
	}

	private void doMergeSort(int lowerIndex, int higherIndex)
	{
		if (lowerIndex < higherIndex)
		{
			int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
			// Below step sorts the left side of the array
			doMergeSort(lowerIndex, middle);
			// Below step sorts the right side of the array
			doMergeSort(middle + 1, higherIndex);
			// Now merge both sides
			mergeParts(lowerIndex, middle, higherIndex);
		}
	}

	private void mergeParts(int lowerIndex, int middle, int higherIndex)
	{
		for (int i = lowerIndex; i <= higherIndex; i++)
		{
			tempMergArr[i] = array[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		while (i <= middle && j <= higherIndex)
		{
			//Implement the DGK, enc (x) <= enc(y)
			if (tempMergArr[i] <= tempMergArr[j])
			{
				array[k] = tempMergArr[i];
				i++;
			} 
			else
			{
				array[k] = tempMergArr[j];
				j++;
			}
			k++;
		}
		while (i <= middle)
		{
			array[k] = tempMergArr[i];
			k++;
			i++;
		}
	}
}