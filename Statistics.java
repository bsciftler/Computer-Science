
public class Statistics 
{
	public static double median(int [] input)
	{
		int size=input.length;
		if (size%2==0)
		{
			return (double)(input[(size/2)-1] + input[((size)/2)])*.5;
		}
		else
		{	
			return (double)input[(((size+1)/2) - 1)];
		}
	}
	public static double findQ1(int [] input)
	{
		int min=(int)Math.ceil((double)input.length/2);
		System.out.println("MIN: " + min + " SIZE: " + input.length );
		
		int max=input.length;
		int size=max-min;
		if (size%2==0)
		{
			return (double)input[(((size+1)/2) - 1)];
		}
		else
		{	
			return (double)(input[(size/2)] + input[(size/2)+1])*.5;
		}
	}
	
	public static double findQ3(int [] input)
	{
		int min=(int)Math.ceil((double)input.length/2);
		int max=input.length;
		int size=max-min;
		if (size%2==0)
		{
			return (double)(input[size] + input[((size+1))])/2;
		}
		else
		{	
			return (double)input[((size+1) - 1)];
		}
	}
	public static double findIQR(int [] input)
	{
		return Statistics.findQ3(input)-Statistics.findQ1(input);
	}
}
