import java.io.*;
import java.util.StringTokenizer;

public class IOExample {

	public static void main(String[] args) {
		try 
		{

		PrintWriter pw = new PrintWriter(
						 new BufferedWriter(
						 new OutputStreamWriter(
						 new FileOutputStream("garbage.txt"))));
		for(int i =1;i<21; i++)
		{
			for(int j=1;j<21;j++)
			{
				pw.print(i*j+";");
			}
			pw.println();
		}
		pw.flush();
		pw.close();
		
		BufferedReader br = new BufferedReader(
							new InputStreamReader(
							new FileInputStream("garbage.txt")));
		String line = br.readLine();
		while (line!=null)
		{
			
			StringTokenizer st = new StringTokenizer(line, ";");
			int sum =0;
			while (st.hasMoreTokens())
			{
				sum+= Integer.parseInt(st.nextToken());
			}
			System.out.println("The sum is: " + sum);
			line = br.readLine();
		}

		} //end of try
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}

	}
	//put 5
	public static int m1(int x)
	{
		if (x<2)
		{
			return 2;
		}
		else
		{
			//Two Paths for 5
			int y=3*m1(x-2); //3
			int z=5+m1(x/2); //2
			System.out.println(x + " " + y + " " + z);
			return y+z;
		}
	}

}