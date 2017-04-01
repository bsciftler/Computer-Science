import java.util.ArrayList;

public class DES
{
/*
 * 	Goal:
 * 1- Create a 56-Bit key Generator
 * 2- Make 28-bit Keys
 * 3- Both Message and Ciphertext will be 48 bits long
 * 4- Use 5 rounds of DES
 */
	
	
	private String KeyGenerator;
	private int rounds;
	private int halfSize;
	private ArrayList<String> Keys;
	
	public DES (String message, int R, int KeyGenSize)
	{
		KeyGenerator=BinaryStringGenerator(KeyGenSize);
		halfSize=KeyGenSize;
		rounds=R;
		KeyGeneration(rounds);
	}
	
	private void KeyGeneration ()
	{
		String [] halfKeys = new String[2];
		int counter=0;
		while (counter < rounds)
		{
			//SubString, [x,y)
			//halfKeys[0] is my left partition
			//halfKeys[1] is my right partiton
			halfKeys[0]=KeyGenerator.substring(0,halfSize);
			halfKeys[1]=KeyGenerator.substring(halfSize,(halfSize*2+1));
			
			//Keys.add(e);
			++counter;
		}
		
	}
	
	//Start Video at 13 minutes
	//https://www.youtube.com/watch?v=UgFoqxKY7cY
	
	public String encrypt(String message)
	{
		String cipher="";
		int counter=0;
		String [] halfMessage = new String[2];
		while (counter < rounds)
		{
			//Split the message into left and right part
			
			//F(x,y) where x=Message, Y=Key 1, 2, ... 
			
			//XOR(F(x,y),Unused Message Half)
			
			//Increment Counter
			++counter;
		}
		cipher+=halfMessage[0];
		cipher+=halfMessage[1];
		return cipher;
	}
	
	public String decrypt(String cipherText)
	{	
		return message;
	}
	
	public String XOR (String a, String b)
	{
		String output="";
		if (a.length()!=b.length())
		{
			System.err.println("ERROR at XOR Function: Strings NOT same size!");
			System.exit(0);
		}
		int traverse=0;
		
		while (traverse<a.length())
		{
			/*
			 * Exclusive OR Logic:
			 * 0 & 0 = 0
			 * 1 & 1 = 0
			 * 1 & 0 = 1
			 * 0 & 1 = 1
			 */
			if (a.charAt(traverse)==1 && b.charAt(traverse)==1)
			{
				output+="0";
			}
			else if (a.charAt(traverse)==0 && b.charAt(traverse)==0)
			{
				output+="0";
			}
			else
			{
				output+="1";
			}
		}
		return output;
	}
	
	public String Function(String left, String right)
	{
		if (left.length()!=right.length())
		{
			System.err.println("ERROR at XOR Function: Strings NOT same size!");
			System.exit(0);
		}
		String output = "";
		int traverse=0;
		/*	f(L,R)
		 * 	f(0,0)= 1
		 *  f(0,1)= 0
		 *  f(1,0)= 1
		 *  f(1,1)= 0
		 */
		while (traverse < left.length())
		{
			if (left.charAt(traverse)==0 && right.charAt(traverse)==0)
			{
				output+="1";
			}
			else if (left.charAt(traverse)==1 && right.charAt(traverse)==0)
			{
				output+="1";
			}
			
			else
			{
				output+="0";
			}
		}
		return output;
	}

	
	public static void main (String [] args)
	{
		String input=BinaryStringGenerator(48);
		DES Hackingcompetition = new DES (input, 5, 56);
		//I was instructed that input be a 48-bit message of 0's and 1's.
	}
	
	public static String BinaryStringGenerator(int length)
	{
		String output="";
		int random=0;
		for (int i=0;i<length;i++)
		{
			random=(int)Math.ceil(Math.random())*100;
			if (random%2==0)
			{
				output+="1";
			}
			else
			{
				output+="0";
			}
		}
		return output;
	}
}
