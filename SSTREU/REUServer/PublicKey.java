import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;

public class PublicKey implements Serializable
{

	// k1 is the security parameter. It is the number of bits in n.
	public int k1 = 1024;;

	// n=pq is a product of two large primes (such N is known as RSA modulous.
	public BigInteger n = new BigInteger("130087418715186676916493485177367961873303968549017242987201907595374679495382197277075827289556722600430235142740506104444238703939853702116524744714108730126829046810267222588901310415445515526990926223695373814379908355964624364257176685247961682329019489330107324364258894156298191740143393820771145009887");
	public BigInteger modulous = new BigInteger("16922736507980300887625661039946975711315547330074104004410135663644470197273394689510172542633882636387517759974316973099806729180583982154890489598251400038785503986238124602951100768129297927360037411201608592553559895209017557378442145417021400040521234174653891652757512943405379421524812677473798337535414265213685636698503381745651585406983649884512127658012350095147191640240353968621332693592336754887726135244092269772548206612602662542977310632216081901618246047350283992170609008708279558808845112023113196009577267988450389189911134621260894327898777105193858992575166573160390724837934022953621327752769");

	public int getk1() {return k1;}
	public BigInteger getMod() {return modulous;}
	public BigInteger getN() {return n;}
	
	private static final long serialVersionUID = -4979802656002515205L;

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException,
	IOException
	{
		// always perform the default de-serialization first
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException
	{
		// perform the default serialization for all non-transient, non-static
		// fields
		aOutputStream.defaultWriteObject();
	}

	public String toString()
	{
		return "k1 = " + k1 + ", n = " + n + ", modulous = " + modulous;
	}
}