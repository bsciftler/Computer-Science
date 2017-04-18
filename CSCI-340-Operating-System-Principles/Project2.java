/*
 * 	Group Members:
 * 	Andrew Quijano, CUNYID: (WITHDRAWN)
 * 	Pratik Malli, CUNYID:
 */

public class Project2 extends Thread
{
	private int priority=5;
	private INTOXICATIONLEVEL intoxication;
	private enum INTOXICATIONLEVEL {SOBER, FEELINGNICE, LIT, DRUNK};
	
	
	public Project2 ()
	{
		intoxication=INTOXICATIONLEVEL.SOBER;
	}
	
	public void run()
	{
		//Some Customer Threads
	}
	
	public void drink()
	{
		switch (intoxication)
		{
			case SOBER:
			case FEELINGNICE:
			case LIT:
			//If he/she drinks anymore...the bartender doesn't want to clean vomit.
			//Kill the thread = Send Customer home...	
			case DRUNK:
				System.exit(0);
			
		}
	}
	
	public void tip()
	{
		++priority;
	}
	
	public void hit()
	{
		--priority;
	}
	
	public void fineAF()
	{
		++priority;
	}
	
	public static void main (String args [])
	{
		
	}
}