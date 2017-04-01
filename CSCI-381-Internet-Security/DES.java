import java.util.ArrayList;

public class DES
{
/*
 * 	Goal:
 * 1- Create a 56-Bit key Generator
 * 2- Make 28-bit Keys
 * 3- Both Message and Cipher text will be 48 bits long
 * 4- Use 5 rounds of DES
 */
	
	private String KeyGenerator;
	private int halfSize;
	private int rounds;
	private ArrayList<String> Keys;
	
	public DES(String Key,int ROUNDS)
	{
		KeyGenerator=Key;
		rounds=ROUNDS;
		halfSize=Key.length()/2;
		Keys = KeyGeneration();
	}

	public ArrayList<String> KeyGeneration ()
	{
		ArrayList<String> keyMaster = new ArrayList<String>();
		String [] halfKeys = new String[2];
		int counter=1;
		String memory = KeyGenerator; //If for some reason I need to restore original Key Generator...
		int tempRound=0;
		String keyBuilder="";
		while (counter <= rounds)
		{
			//SubString, [x,y)
			
			//Step 1: Partition the Key Generator into two halves.
			//halfKeys[0] is my left partition
			//halfKeys[1] is my right partition
			halfKeys[0]=KeyGenerator.substring(0,halfSize);//[0,3]
			halfKeys[1]=KeyGenerator.substring(halfSize,(halfSize*2));//[4,7]
			System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			
			//Step 2: Shift the Half Keys according to DES...
			//Left Key: Get rightmost bit of KL and move to to the Left
			halfKeys[0]=shiftLeft(halfKeys[0]);
			//Right Key: Get the Left most bit of KR and move it to the Right.
			halfKeys[1]=shiftRight(halfKeys[1]);
			System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			//Step 3:Create 28-bit Key...
			//Step 3A: Build the new key from which I keep using my rounds for...
			KeyGenerator=halfKeys[0]+halfKeys[1];
			//Step 3B: From the Generated Key, Get first 16-Bits and Last 16-Bits.
			tempRound= halfSize/2*3;
			System.out.println("TEMP: " + tempRound + " halfSize: " + (halfSize*2-1) + " Key Generated: " + KeyGenerator);
			keyBuilder+=KeyGenerator.substring(0,halfSize/2);
			keyBuilder+=KeyGenerator.substring(tempRound,halfSize*2-1);
			keyBuilder+=KeyGenerator.charAt(halfSize*2-1);
			//System.out.println("Key Built: " + keyBuilder);
			keyMaster.add(keyBuilder);
			System.out.println("Key Number " + counter + " " + keyMaster.get(counter-1));
			++counter;
			keyBuilder="";//Reset
		}
		KeyGenerator=memory; //Restore Original Key Generator.
		return keyMaster;
	}
	
	public static String shiftLeft(String s)
	{
	    return s.charAt(s.length()-1)+s.substring(0, s.length()-1);
	}
	
	public static String shiftRight(String s)
	{
	    return s.substring(1, s.length()) + s.charAt(0);
	}
	
	public String encrypt(String message)
	{
		int messageSize=message.length();
		String cipher="";
		int counter=1;
		String [] halfMessage = new String[2];
		while (counter <= rounds)
		{
			//Split the message into left and right part
			halfMessage[0]=message.substring(0,messageSize/2);
			halfMessage[1]=message.substring(messageSize/2,messageSize)+message.charAt(messageSize-1);
			
			//F(x,y) where x=Message, Y=Key 1, 2, ... 
			
			//XOR(F(x,y),Unused Message Half)
			
			//Increment Counter
			++counter;
		}
		cipher+=halfMessage[0];
		cipher+=halfMessage[1];
		return cipher;
	}
	
	//public String decrypt(String cipherText)
	//{	
		//return message;
	//}
	
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
		DES Hacks = new DES("11011010",4);
		String E= Hacks.encrypt("10011011");
		System.out.println("MESSAGE CIPER " + E);
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