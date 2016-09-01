public class Statistics 
{
	public static double median(int [] input)
	{
		int SIZE=input.length;
		if (SIZE%2==0)
		{
			return (double)(input[(SIZE/2)-1] + input[(SIZE/2)])*.5;
		}
		else
		{	
			return (double)input[(((SIZE+1)/2) - 1)];
		}
	}
	public static double findQ1(int [] input)
	{
		int SIZE=input.length;
		int SPLIT=SIZE/2; //This is the new "length"
		if (SPLIT%2==0)
		{
			return (double)(input[(SPLIT/2)-1] + input[((SPLIT)/2)])*.5;
		}
		else
		{
			return (double)input[(((SPLIT+1)/2) - 1)];
		}
	}
	
	public static double findQ3(int [] input)
	{
		int SIZE=input.length;
		int SPLIT=SIZE/2; //This is the new "0"
		if (SPLIT%2==0)
		{
			return (double)(input[((SIZE+SPLIT)/2)-1] + input[((SIZE+SPLIT)/2)])*.5;
		}
		else
		{
			return (double)input[(((SPLIT+SIZE)/2) - 1)];
		}
	}
	public static double findIQR(int [] input)
	{
		return Statistics.findQ3(input)-Statistics.findQ1(input);
	}
}