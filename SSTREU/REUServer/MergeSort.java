import java.awt.List;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Set;

/*
 * 	Code Taken from: 
 * 	http://www.java2novice.com/java-sorting-algorithms/merge-sort/
 * 	Now that I learned how to compare Paillier Encrypted Values:
 * 	Following this guide, I can run the C++ in Java...
 * 	https://www.ibm.com/developerworks/java/tutorials/j-jni/j-jni.html
 */

public class MergeSort
{
	private int [] array;
	private int [] tempMergArr;
	private int length;
	private BigInteger [] encryptedArr;
	private BigInteger [] encryptedTempMergArr;

	public static void main(String a[])
	{
		System.loadLibrary("NumberList");
		int[] inputArr = {45,23,11,89,77,98,4,28,65,43};
		MergeSort mms = new MergeSort();
		mms.sort(inputArr);
		for(int i:inputArr)
		{
	    	System.out.print(i);
	    	System.out.print(" ");
	    }		
	}
	
	public void sort(BigInteger [] Paillier)
	{
		this.encryptedArr = Paillier;
		this.length = Paillier.length;
		this.encryptedTempMergArr = new BigInteger [length];
		//doMergeSort FIXUP FOR BIG INTEGERS
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
			//But for now, just do simple C++ code to test the wrapper...
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