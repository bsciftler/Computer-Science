import java.math.BigInteger;

public class DGKPrivateKey
{
	BigInteger p;
	BigInteger q;
	BigInteger vp;
	BigInteger vq;
	int k1;
	
    public DGKPrivateKey(int n)
    {
        // k1 is the security parameter. It is the number of bits in n.
    	k1 = n;
    }
}
