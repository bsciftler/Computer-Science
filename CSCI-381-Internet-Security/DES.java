import java.util.ArrayList;

public class DES
{
/*
 * 	Goal:
 * 1- Create a 56-Bit key Generator
 * 2- Make 24-bit Keys
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
		Keys = KeyGeneration2();
	}
	
	public ArrayList<String> KeyGeneration2 ()
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
			//System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			
			//Step 2: Shift the Half Keys according to DES...
			//Left Key: Get rightmost bit of KL and move to to the Left
			halfKeys[0]=shiftLeft(halfKeys[0]);
			//Right Key: Get the Left most bit of KR and move it to the Right.
			halfKeys[1]=shiftRight(halfKeys[1]);
			//System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			
			//Step 3:Create 28-bit Key...
			//Step 3A: Build the new key from which I keep using my rounds for...
			KeyGenerator=halfKeys[0]+halfKeys[1];
			
			//Step 3B: From the Generated Key, Get first 16-Bits and Last 16-Bits.
			tempRound= halfSize/2*3;
			//System.out.println("TEMP: " + tempRound + " halfSize: " + (halfSize*2-1) + " Key Generated: " + KeyGenerator);
			keyBuilder+=KeyGenerator.substring(0,halfSize/2-2);
			keyBuilder+=KeyGenerator.substring(tempRound+2,halfSize*2-1);
			keyBuilder+=KeyGenerator.charAt(halfSize*2-1);
			//System.out.println("Key Built: " + keyBuilder);
			keyMaster.add(keyBuilder);
			System.out.println("Key Number " + counter + " " + keyMaster.get(counter-1) + " Key size is " + keyBuilder.length());
			++counter;
			keyBuilder="";//Reset
		}
		KeyGenerator=memory; //Restore Original Key Generator.
		return keyMaster;
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
			//System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			
			//Step 2: Shift the Half Keys according to DES...
			//Left Key: Get rightmost bit of KL and move to to the Left
			halfKeys[0]=shiftLeft(halfKeys[0]);
			//Right Key: Get the Left most bit of KR and move it to the Right.
			halfKeys[1]=shiftRight(halfKeys[1]);
			//System.out.println("KL: "+ halfKeys[0] + " KR: " + halfKeys[1]);
			
			//Step 3:Create 28-bit Key...
			//Step 3A: Build the new key from which I keep using my rounds for...
			KeyGenerator=halfKeys[0]+halfKeys[1];
			
			//Step 3B: From the Generated Key, Get first 16-Bits and Last 16-Bits.
			tempRound= halfSize/2*3;
			//System.out.println("TEMP: " + tempRound + " halfSize: " + (halfSize*2-1) + " Key Generated: " + KeyGenerator);
			keyBuilder+=KeyGenerator.substring(0,halfSize/2);
			keyBuilder+=KeyGenerator.substring(tempRound,halfSize*2-1);
			keyBuilder+=KeyGenerator.charAt(halfSize*2-1);
			//System.out.println("Key Built: " + keyBuilder);
			keyMaster.add(keyBuilder);
			System.out.println("Key Number " + counter + " " + keyMaster.get(counter-1) + " Key size is " + keyBuilder.length());
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
		int counter=0;
		String [] halfMessage = new String[2];
		//Split the message into left and right part
		//Left Key
		halfMessage[0]=message.substring(0,messageSize/2);
		//Right Key
		halfMessage[1]=message.substring(messageSize/2,messageSize);
		//System.out.println("CURRENT MESSAGE: (ENCRYPT)" + halfMessage[0]+halfMessage[1] + " " + halfMessage[1].length());
		String function="";
		String xorfunction="";
		String temp="";
		while (counter < rounds)
		{
			//F(x,y) where x=Message, Y=Key 1, 2, ... 
			function=Function(halfMessage[1],Keys.get(counter));//14,28
			System.out.println("FUNTION RESULT (ENCRYPT):  "+function);
			
			//XOR(F(x,y),Unused Message Half)
			xorfunction=XOR(halfMessage[0],function);
			System.out.println("XOR RESULT (ENCRYPT): " + xorfunction);
			
			//Update the Message halves.
			temp=halfMessage[1];
			halfMessage[0]=temp;
			halfMessage[1]=xorfunction;
			
			//System.out.println("CURRENT MESSAGE: " + halfMessage[0]+halfMessage[1]);
			//Increment Counter and clear temporary values
			++counter;
			function="";
			xorfunction="";
			temp="";
		}
		cipher+=halfMessage[0];
		cipher+=halfMessage[1];
		return cipher;
	}
	
	public String decrypt(String cipherText)
	{	
		int messageSize=cipherText.length();
		String message="";
		int counter=Keys.size()-1;
		String [] halfMessage = new String[2];
		//Split the message into left and right part
		//Left Key
		halfMessage[0]=cipherText.substring(0,messageSize/2);
		//Right Key
		halfMessage[1]=cipherText.substring(messageSize/2,messageSize);
		System.out.println("CURRENT MESSAGE (DECRYPT): " + halfMessage[0]+halfMessage[1]);
		String function="";
		String xorfunction="";
		while (counter >= 0)
		{
			function=Function(halfMessage[0],Keys.get(counter));
			xorfunction=XOR(halfMessage[1],function);
			halfMessage[1]=halfMessage[0];
			halfMessage[0]=xorfunction;
			--counter;
			function="";
			xorfunction="";
			System.out.println("Current Decrpytion: " +halfMessage[0]+halfMessage[1]);
		}
		message+=halfMessage[0];
		message+=halfMessage[1];
		return message;
	}
	
	public String XOR (String left, String right)
	{
		String output="";
		if (left.length()!=right.length())
		{
			System.err.println("ERROR at XOR Function: Strings NOT same size!");
			System.exit(0);
		}
		int traverse=0;
		
		while (traverse<left.length())
		{
			/*
			 * Exclusive OR Logic:
			 * 0 & 0 = 0
			 * 1 & 1 = 0
			 * 1 & 0 = 1
			 * 0 & 1 = 1
			 */
			if (left.charAt(traverse) == right.charAt(traverse))
			{
				output+="0";
			}
			else
			{
				output+="1";
			}
			++traverse;
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
		 *  f(1,0)= 0
		 *  f(1,1)= 1
		 */
		while (traverse < left.length())
		{
			if (left.charAt(traverse)== right.charAt(traverse))
			{
				output+="1";
			}
			else
			{
				output+="0";
			}
			++traverse;
		}
		return output;
	}

	public static void main (String [] args)
	{
		String Key = BinaryStringGenerator(56);
		//System.out.println(Keyman +  " " + Keyman.length());
		DES Hacking = new DES(Key,5);
		String M = BinaryStringGenerator(48);
		System.out.println("Key: " + Key);
		System.out.println("Message: " + M);
		String E= Hacking.encrypt(M);
		System.out.println("MESSAGE CIPHER " + E);
		String Test=Hacking.decrypt(E);
		if (Test.equals(M))
		{
			System.out.println("Success!");
		}
	}
	
	public static String BinaryStringGenerator(int length)
	{
		String output="";
		int random=0;
		for (int i=0;i<length;i++)
		{
			random=(int)(Math.random()*100);
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
