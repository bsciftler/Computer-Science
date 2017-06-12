import java.math.BigInteger;

public class DGKPublicKey
{
	// k1 is the security parameter. It is the number of bits in n.
    public int k1;
    public BigInteger n;
	public BigInteger g;
	public BigInteger h;
	public long u;
	//public BigInteger u;
	
	
	public DGKPublicKey(BigInteger N, BigInteger G, BigInteger H, long U, 
			BigInteger gLUT, BigInteger hLUT, int L, int T, int K)
	{
		n=N;
		g=G;
		h=H;
		u=U;
	}
}
