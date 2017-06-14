import java.math.BigInteger;
import java.util.ArrayList;

public class DatabaseCommunication
{
	PublicKey pk;
	
    public BigInteger computemin(ArrayList<BigInteger> incomingLocation)
    {
    	BigInteger min = null;
    	//Query all x1, x2, x3 locations and put it in an arraylist...
    	ArrayList <BigInteger> fingerPrints = new ArrayList<BigInteger>();
    	
    	//Compute distance squared for all of them...
    	ArrayList <BigInteger> distanceSquared = new ArrayList<BigInteger>();
    	
    	for (int i=0;i<100;i++)
    	{
    		distanceSquared.add(Paillier.computeDistance(fingerPrints,incomingLocation,pk));
    	}
    	//Which is the smallest distance squared...
    	
    	return min;
    }
}
