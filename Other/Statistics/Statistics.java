import java.util.Arrays;
import java.util.Comparator;

public class Statistics <K>
{
	private int SIZE;
	private int sum;
	private int sampleVariance;
	private int sampleMean;
	
	//Toukey Five Number Summary
	private int min;
	private int Q1;
	private double median;
	private int Q3;
	private int max;
	
	//Modified Box and Whisker
	private int IQR;
	private int modMin;
	private int modMax;
	
	//PlaceHolder of Input
	private int [] info;
	
	public Statistics (int [] input)
	{
		SIZE=input.length;
		info=input;
		computeINTEGERINPUT();
	}
	
	public void StatisticalInformation()
	{
		System.out.println("Min: " + min);
		System.out.println("Modified Min: " + modMin);
		System.out.println("Q1: " + Q1);
		System.out.println("Median: " + median);
		System.out.println("Q3: " + Q3);
		System.out.println("modified Max: " + modMax);
		System.out.println("Max: " + max);
		System.out.println("Sample Mean: " + sampleMean);
		System.out.println("Sample Variance: " + sampleVariance);
	}
	
	private void computeINTEGERINPUT()
	{
		sum=summation();
		sampleVariance=sum/(SIZE-1);
		sampleMean=sum/SIZE;
		sort();
		min=info[0];
		Q1=findQ1();
		median=findMedian();
		Q3=findQ3();
		max=info[info.length-1];
		IQR=Q3-Q1;
		modMin=(int) (min-(1.5*IQR));
		modMax=(int) (max+(1.5*IQR));
	}
	
	public void sort()
	{
		if (SIZE <=70)
		{//I can use Insert Sort more effectively
			
		}
		else
		{//I will use Merge Sort
			//mergeSort(info, new Comparator <K>());
		}
	}
	/*
	 * public <K> void mergeSort(int [], Comparator<K> comparator)
	{
		int n=s2.length;
		if (n < 2)
		{
			return;
		}
		//divide
		int mid=n/2;
		K[]S1=Arrays.copyOfRange(s2,0,mid);
		K[]S2=Arrays.copyOfRange(s2, mid, n);
		//conquer with recursion
		mergeSort(S1,comparator);
		mergeSort(S2,comparator);
		
		//merge results
		merge(S1,S2,s2,comparator);
	}
	
	public static <K> void merge (K[] s1, K[] s2, int[] s, Comparator<K> comparator)
	{
		int i=0; int j=0;
		while (i+j<s.length)
		{
			if (j==s2.length || (i<s1.length && comparator.compare(s1[j],s2[j])< 0))
			{
				s[i+j]=s1[i++];
			}
			else
			{
				s[i+j]=s2[j++];
			}
		}
	}
	 */
	
	public int summation ()
	{
		int answer=0;
		for (int i=0;i<SIZE;i++)
		{
			answer+=info[i];
		}
		return answer;
	}
	
	public double findMedian()
	{	
		if (SIZE%2==0)
		{
			return (double)(info[(SIZE/2)-1] + info[(SIZE/2)])*.5;
		}
		else
		{	
			return info[(((SIZE+1)/2) - 1)];
		}
	}
	
	public int findQ1()
	{
		int SPLIT=SIZE/2; //This is the new "length"
		if (SPLIT%2==0)
		{
			return (int) ((info[(SPLIT/2)-1] + info[((SPLIT)/2)])*.5);
		}
		else
		{
			return info[(((SPLIT+1)/2) - 1)];
		}
	}
	
	public int findQ3()
	{
		int SPLIT=SIZE/2; //This is the new "0"
		if (SPLIT%2==0)
		{
			return (int) ((int) (info[((SIZE+SPLIT)/2)-1] + info[((SIZE+SPLIT)/2)])*.5);
		}
		else
		{
			return info[(((SPLIT+SIZE)/2) - 1)];
		}
	}
}