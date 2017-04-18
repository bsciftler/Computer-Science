/*
 * 	Group Members:
 * 	Andrew Quijano, CUNYID: (WITHDRAWN)
 * 	Pratik Malli, CUNYID:
 */

public class Project2 extends Thread
{
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
				System.out.println("This is going to be a fun night.");
				this.intoxication=INTOXICATIONLEVEL.FEELINGNICE;
			case FEELINGNICE:
				System.out.println("I am having fun!");
				this.intoxication=INTOXICATIONLEVEL.LIT;
			case LIT:
				System.out.println("My head is starting to spin...");
				this.intoxication=INTOXICATIONLEVEL.DRUNK;
			//If he/she drinks anymore...the bartender doesn't want to clean vomit.
			//Kill the thread = Send Customer home...	
			case DRUNK:
				System.out.println("Go home, you are drunk");
		}
	}
	
	public void tip(int money)
	{
		int increase=this.getPriority();
		this.setPriority(increase+=money);
	}
	
	public void rude()
	{
		int decrease=this.getPriority();
		this.setPriority(--decrease);
	}
	
	public void nice()
	{
		int increase=this.getPriority();
		this.setPriority(++increase);
	}
	
	public static void main (String args [])
	{
		
	}
}