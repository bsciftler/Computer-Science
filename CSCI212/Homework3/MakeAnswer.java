
public class MakeAnswer {

	
	private int amtPpl;
	private int average;
	
	public MakeAnswer( int ppl, int avg)
	{

		ppl = amtPpl;
		average = avg;
	
	}
	
	public void calcAnswer(Node current)
	{

		int a[] = new int[amtPpl];
		int difference = 0;
		int sumDifference =0;
		
		for(int i=0; i<a.length; i++)
		{
			
			if(current.getData() > average)
			{
				a[i] = current.getData();
			}
			
			current = current.getNext();
		}

		
		/*for(int i = 0; i<a.length; i++)
		{
			if(a[i] > 0)
			{
				System.out.print(a[i] + " " ) ;
			}
		}*/
		
		for(int i = 0; i<a.length; i++)
		{
			if(a[i]>0)
			{
				difference = a[i] - average;
				sumDifference += difference; 
			}
		}
		
		System.out.println("The answer is: " + sumDifference);
		//for(int i = current.getData(); current!=null; current = current.getNext())
		
		
		
	}
	
	
}