
public class RollDie {
	private static int N1=0;
	private static int N2=0;
	private static int N3=0;
	private static int N4=0;
	private static int N5=0;
	private static int N6=0;
	private static int [] Roll;

public static void Rolling(int Rolls)
{	
		Roll= new int [Rolls];
		for (int i=0;i<Roll.length;i++)
		{
			Roll[i]=(int)Math.ceil((Math.random()*6));
			System.out.println("Roll Result: "+ Roll[i]);
		}
		
	for (int i=0;i<Roll.length;i++)
	{
		if (Roll[i]==1)
		{
			N1=N1+1;
		}
		else if (Roll[i]==2)
		{
			N2=N2+1;
		}
		else if (Roll[i]==3)
		{
			N3=N3+1;
		}
		else if (Roll[i]==4)
		{
			N4=N4+1;
		}
		else if (Roll[i]==5)
		{
			N5=N5+1;
		}
		else 
		{
			N6=N6+1;
		}
	}
}
	public static int N1(){return N1;}
	public static int N2(){return N2;}
	public static int N3(){return N3;}
	public static int N4(){return N4;}
	public static int N5(){return N5;}
	public static int N6(){return N6;}
} //End of Class
