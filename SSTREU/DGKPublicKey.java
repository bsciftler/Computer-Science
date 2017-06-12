import java.math.BigInteger;
import java.util.HashMap;

public class DGKPublicKey
{
	// k1 is the security parameter. It is the number of bits in n.
    public int k1;
    public static BigInteger n;
	public static BigInteger g;
	public static BigInteger h;
	public static long u;
	public static BigInteger bigU;
	public static int l;
	public static int t;
	public static int k;
	//public BigInteger u;
	public HashMap gLUT;
	public HashMap hLUT;
	
	public DGKPublicKey(BigInteger N, BigInteger G, BigInteger H, long U, 
			HashMap GLUT, HashMap HLUT, int L, int T, int K)
	{
		n=N;
		g=G;
		h=H;
		bigU=BigInteger.valueOf(u);
		u=U;
		gLUT=GLUT;
		hLUT=HLUT;
		l=L;
		t=T;
		k=K;
	}
	public HashMap<Long,BigInteger> getGLUT()
	{
		return gLUT;
	}
	public HashMap<Long,BigInteger> getHLUT()
	{
		return hLUT;
	}
}
