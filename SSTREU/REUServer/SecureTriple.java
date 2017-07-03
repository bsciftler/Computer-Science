
import java.math.BigInteger;

/**
 * Created by Andrew on 6/28/2017.
 */

public class SecureTriple
{
    private String [] MACAddress;
    private BigInteger [] S2;
    private BigInteger [] S3;

    public SecureTriple(String [] first, BigInteger [] second, BigInteger [] third)
    {
        MACAddress = first;
        S2 = second;
        S3 = third;
    }

    public String [] getFirst() { return MACAddress; }
    public BigInteger [] getSecond() { return S2; }
    public BigInteger [] getThird() { return S3; }
}
