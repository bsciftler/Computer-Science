import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;

public class PrivateKey implements Serializable
{

    public PrivateKey(int n)
    {
        k1 = n;
    }

    // k1 is the security parameter. It is the number of bits in n.
    public int k1;
    public BigInteger n;
    public BigInteger modulous;//modulus is n^2

    
    public BigInteger lambda; //lambda=lcm(p-1,q-1) is necessary for decryption
    public BigInteger mu; 

    private static final long serialVersionUID = 211310247747384568L;

    private void readObject(ObjectInputStream aInputStream)
    throws ClassNotFoundException,IOException
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
}