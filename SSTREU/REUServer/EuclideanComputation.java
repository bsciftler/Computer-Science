import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class EuclideanComputation
{
	private final static int VECTORSIZE = 10;

	//Unsecure localization: Data from Phone
	String [] MACAddress;
	Integer [] RSS;
	
	//Insecure localization: Data from SQLServer: LookupTable
	ArrayList<Integer []> SQLData;
	//Secure localization: Data from SQLServer: LookupTable
	ArrayList<BigInteger []> S1Array;
	
	
	//Secure localization: Data from Phone
	BigInteger [] S2;
	BigInteger [] S3;
	
	
	
	//Paillier Public Key
	PublicKey pk;
	
	//Store the computed distances...
	BigInteger [] encryptedDistance;
	int [] distance;
	static int rows;
	
	private boolean secureCompute;
	private boolean DMA=true;
	double location [] = new double [2];
	/*
	 * a Good SQL Command
	 *
select * from Localization
Where MACAddress = 'c8:b3:73:1f:fc:6e'
Order by XCoordinate ASC;
	 */
	
	HashMap<Integer, Integer> distanceKey = new HashMap<Integer, Integer>();
	HashMap<BigInteger, Integer> EncDistanceKey = new HashMap<BigInteger, Integer>();
	
	public EuclideanComputation(String [] MAC, Integer [] RSSVal)
	{
		MACAddress = MAC;
		RSS = RSSVal;
		secureCompute = false;
	}
	
	public EuclideanComputation(SecureTriple input, PublicKey n)
	{
		pk = n;
		//Each index will have:
		//1- MAC Address
		//2- S2
		//3- S3
		MACAddress = input.getFirst();
		S2 = input.getSecond();
		S3 = input.getThird();
		secureCompute = true;
	}
	
//================================PlainText==============================================	
	//Extract all the Values SQL Database LookupTable

	private void SQLAccess()
	{
		/*
		 * 	 I need both Vector and the First Primary Key...
		 * 	The Index will trace me back to the Correct XY Coordinate
		 */
		final String QueryStart = "select * from ";
		if (secureCompute == true)
		{
			String cmd = QueryStart + "reuplainlut;";
			SQLRead getLUTData = new SQLRead(cmd,secureCompute);
			Thread SQL = new Thread(getLUTData);
			SQL.start();
			S1Array = getLUTData.getS1();
			SQLData = getLUTData.getRSS();
			rows = S1Array.size();
			distance = new int [rows];
		}
		else
		{
			String cmd = QueryStart + "reuencryptedlut;";
			SQLRead getLUTData = new SQLRead(cmd, secureCompute);
			Thread SQL = new Thread(getLUTData);
			SQL.start();
			SQLData = getLUTData.getRSS();
			rows = SQLData.size();
			distance = new int [rows];
		}
	}
	
	
//========================================Plain Text Computation===============================	
	//DMA Algorithm (Compute Distance Part 1)
	private void computeDistance()
	{
		if (DMA==true)//If I have a null use RSS = -120
		{
			for (int i=0;i<rows;i++)
			{
				for (int j = 0; j < VECTORSIZE;j++)
				{
					distance[i] += (SQLData.get(i)[j] * SQLData.get(i)[j]) - (RSS[j]*RSS[j]);
				}
				//Use MultiMap as I can MAYBE have repeated distances?
				//Could happen if this location has 
				//-120, ..., -120?
				distanceKey.put(distance[i], i);
			}
		}
		else//If I hav a null, SKIP THE ROW
		{
			
		}
	}
	//Find the smallest distance using merge sort, since it has the X, Y
	public double [] findCoordinate()
	{
		if (secureCompute==true)
		{
			this.SQLAccess();
			this.EncryptedcomputeDistance();
			MergeSort mms = new MergeSort();
			mms.sort(distance);	
		}
		else
		{
			this.SQLAccess();
			this.computeDistance();
			//Compare Paillier
		}
		
		//Return that to Client Thread
		return location;
	}
	
//=====================Encrypted Paillier================================================
	//Extract all the Values from SQL Server
	
	
	//DMA Algorithm (Compute Distance Part 1)
	private void EncryptedcomputeDistance()
	{
		if (DMA==true)//If null use RSS = -120 encrypted in Paillier
		{
			BigInteger temp = BigInteger.ZERO;
			for (int i=0;i<rows;i++)
			{
				for (int j = 0; j < VECTORSIZE;j++)
				{
					if (temp.equals(BigInteger.ZERO))
					{
						//Create the temp
						temp = Paillier.multiply(S2[j], SQLData.get(i)[j], pk);//S2 = -2v'*v
						temp = Paillier.add(temp, S1Array.get(i)[j], pk);//S1 +  S2
						temp = Paillier.add(temp, S3[j], pk);//Add the S3
					}
					//Keep adding more the other values
					temp = Paillier.add(temp, Paillier.multiply(S2[j], SQLData.get(i)[j], pk), pk);
					temp = Paillier.add(temp, S1Array.get(i)[j], pk);
					temp = Paillier.add(temp, S3[j], pk);
				}
				encryptedDistance[i] = temp;
				//Reset temp
				temp = BigInteger.ZERO;
			}
		}
		else
		{
			
		}
	}
}
