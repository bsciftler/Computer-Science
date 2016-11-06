
public class Input619
{
	public static void main(String[] args)
	{
		NumberTheory Homework= new NumberTheory(1373653);
		//Test bases 2, 3, 5, 7
		int [] basetest={2, 3, 5, 7};
		for (int i=0;i<4;i++)
		{
			System.out.println("Base: " + basetest[i]);
			Homework.primality(new String("Miller"),basetest[i]);
			System.out.println("====================================");
		}
	}
}
