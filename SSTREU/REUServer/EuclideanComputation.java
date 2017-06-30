import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;


public class EuclideanComputation
{
	private final String QueryStart = "select * from Localization Where MACAddress = ";
	
	//Unsecure localization: Data from Phone
	String [] MACAddress;
	Integer [] RSS;
	
	//Unsecure localization: Data from SQLServer: LookupTable
	int [] SQLRSS;
	
	//Secure localization: Data from Phone
	BigInteger [] S2;
	BigInteger [] S3;
	
	//Secure localization: Data from SQLServer: LookupTable
	BigInteger [] S1;
	
	//Paillier Public Key
	Paillier.PublicKey pk;
	
	//Store the computed distances...
	BigInteger [] encryptedDistance;
	int [] distance;
	
	/*
	 * a Good SQL Command
	 *
select * from Localization
Where MACAddress = 'c8:b3:73:1f:fc:6e'
Order by XCoordinate ASC;
	 */
	
	public EuclideanComputation(String [] MAC, Integer [] RSSVal)
	{
		MACAddress = MAC;
		RSS = RSSVal;
	}
	
	public EuclideanComputation(ArrayList<SecureTriple> input, Paillier.PublicKey n)
	{
		pk = n;
		//Each index will have:
		//1- MAC Address
		//2- S2
		//3- S3
		MACAddress = input.get(0).getFirst();
		S2 = input.get(1).getSecond();
		S3 = input.get(2).getThird();
	}
	
//================================PlainText==============================================	
	//Extract all the Values SQL Database

	private void SQLAccess()
	{
		String temp;
		for(int i=0;i<MACAddress.length;i++)
		{
			temp = MACAddress[i];
			SQLRead command = new SQLRead(QueryStart+temp);
			Thread t = new Thread(command);
			t.start();
			AllQueries.add(command.getAnswer());
			//I DO NEED JOIN DON'T I???
		}
	}
	
	//DMA Algorithm (Compute Distance Part 1)
	private void DMAcomputeDistance()
	{
		int d;
		for (int i = 0; i < AllQueries.size();i++)
		{
			for (int j = 0;j < AllQueries.get(i).size();j++)
			{
				d = RSS[0]*RSS[0] - AllQueries.get(i).get(i).getRSS()*AllQueries.get(i).get(i).getRSS();
			}
		}
	}
	
	//Algorithm (Compute Distance Part 2)
	private void computeDistance()
	{
		
	}
	
	//Find the smallest distance using merge sort, since it has the X, Y
	public double [] findCoordinate()
	{
		//Return that to Client Thread
		double [] location = new double[2];
		return location;
	}
	
	
	
//=====================Encrypted Paillier================================================
	//Extract all the Values from SQL Server
	
	
	//DMA Algorithm (Compute Distance Part 1)
	private void EDMAcomputeDistance()
	{
		
	}
	
	//Algorithm (Compute Distance Part 2)
	private void EcomputeDistance()
	{
		BigInteger S1;
		BigInteger S2;
		BigInteger S3;
		BigInteger d;
		//Get S1 + S2 + S3 
		for(int i=0;i<AllQueries.size();i++)
		{	
			//AllQueries contains an ArrayList.
			for (int j=0;j<AllQueries.get(i).size();j++)
			{
				S1 = Paillier.encrypt(new BigInteger(String.valueOf(AllQueries.get(i).get(j).getRSS())), pk);
				S2 = Paillier.multiply(encrypted.get(j).getSecond(), AllQueries.get(i).get(j).getRSS(), pk);
				d = Paillier.add(S1, S2, pk);
				d = Paillier.add(d, encrypted.get(j).getThird(), pk);
			}
		}
	}
	
	//Find the smallest distance using merge sort, since it has the X, Y
	//Return that to Client Thread
}
