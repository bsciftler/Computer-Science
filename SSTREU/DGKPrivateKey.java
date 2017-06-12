import java.math.BigInteger;

public class DGKPrivateKey
{
	private BigInteger p;
	private BigInteger q;
	private BigInteger vp;
	private BigInteger vq;
	private long u;
	
    public DGKPrivateKey (BigInteger P, BigInteger Q, BigInteger VP, 
    		BigInteger VQ, BigInteger LUT, long U)
    {
        p=P;
        q=Q;
        vp=VP;
        vq=VQ;
        
        u=U;
    }
}
