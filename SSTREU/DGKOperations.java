import java.math.BigInteger;

public class DGKOperations
{
	private DGKPublicKey pubkey;
	private DGKPrivateKey privkey;
	
	public static void GenerateKeys(int l, int t, int k)
	{
		System.out.println("Generating Keys...");
	    // First check that all the parameters of the KeyPair are coherent throw an exception otherwise
	    if (0 > l || 32 <= l )
	    {
	        throw new IllegalArgumentException("DGK Keygen Invalid parameters : plaintext space must be less than 32 bits");
	    }

	    if (l > t ||  t > k )
	    {
	        throw new IllegalArgumentException("DGK Keygen Invalid parameters: we must have l < k < t");
	    }
	    if ( k/2 < t + l + 1 )
	    {
	        throw new IllegalArgumentException("DGK Keygen Invalid parameters: we must have k > k/2 < t + l ");
	    }

	    if ( t%2 != 0 )
	    {
	        throw new IllegalArgumentException("DGK Keygen Invalid parameters: t must be divisible by 2 ");
	    }
	    BigInteger p, rp;
	    BigInteger q, rq;
	    BigInteger g, h ;
	    BigInteger n, r ;
	    long u;
	    BigInteger vp,vq, vpvq,tmp;

	    while(true)
	    {
	        // Generate some of the required prime number
	        BigInteger zU  = NTL::NextPrime(NTL::power(ZZ(2),l) + ZZ(2),10);
	        conv(u,zU);
	        vp = NTL::RandomPrime_ZZ(t,10);
	        vq = NTL::RandomPrime_ZZ(t,10);
	        vpvq = vp.multiply(vq);
	        tmp = u.multiply(vp);

	        int needed_bits = k/2 - NTL::NumBits(tmp);


	        // Generate rp until p is prime such that uvp divde p-1
	        do
	        {
	            rp = NTL::RandomBits_ZZ(needed_bits);
	            NTL::SetBit(rp,needed_bits -1);
	            p = rp * tmp +1;
	        }
	        while(!ProbPrime(p,10));  // Thus we ensure that p is a prime, with p-1 dividable by prime numbers vp and u

	        tmp = u * vq;
	        needed_bits = k/2 - NTL::NumBits(tmp);

	// Same method for q than for p

	        do
	        {
	            rq = NTL::RandomBits_ZZ(needed_bits);
	            NTL::SetBit(rq,needed_bits -1);
	            q = rq * tmp +1;
	        }
	        while(!ProbPrime(q,10));  // Thus we ensure that q is a prime, with p-1 dividable by prime numbers vq and u
	        if(POSMOD(rq,u)!= 0 && POSMOD(rp,u)!= 0 )
	        {
	            break;
	        }
	    }
	    n = p.multiply(q);
	    tmp = rp.multiply(rq).multiply(u);
	    

	    while(true)
	    {
	        r = NTL::RandomBnd(n);
	        h = NTL::PowerMod(r,tmp,n);
	        if (h == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (h.modPow(vp,n).equals(BigInteger.ONE))
	        {

	            continue;
	        }
	        
	        if (h.modpow(vq,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        if (NTL::PowerMod(h,u,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(h,u*vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (NTL::PowerMod(h,u*vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::GCD(h,n) == BigInteger.ONE)
	        {
	            break;
	        }
	    }

	    BigInteger rprq = rp.multiply(rq);

	    while(true)
	    {
	        r = NTL::RandomBnd(n);
	        g = NTL::PowerMod(r,rprq,n);

	        if (g == ZZ(1))
	        {
	            continue;
	        }

	        if (g.gcd(n) != BigInteger.ONE)
	        {
	            continue;
	        } // Then h can still be of order u,vp, vq , or a combinaison of them different that uvpvq

	        if (NTL::PowerMod(g,u,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (NTL::PowerMod(g,u*u,n) == BigInteger.ONE)
	        {

	            continue;
	        }
	        if (NTL::PowerMod(g,u*u*vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (NTL::PowerMod(g,u*u*vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(g,vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(g,vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(g,u*vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(g,u*vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (NTL::PowerMod(g,vpvq,n) == BigInteger.ONE)
	        {

	            continue;
	        }
	        if (NTL::PowerMod(POSMOD(g,p),vp,p) == BigInteger.ONE)
	        {
	            // Temporary fix
	            continue;
	        }
	        if (NTL::PowerMod(POSMOD(g,p),u,p) == BigInteger.ONE)
	        {
	            // Temporary fix
	            continue;
	        }
	        if (NTL::PowerMod(POSMOD(g,q),vq,q) == BigInteger.ONE)
	        {
	            // Temporary fix
	            continue;
	        }
	        if (NTL::PowerMod(POSMOD(g,q),u,q) == BigInteger.ONE)
	        {
	            // Temporary fix
	            continue;
	        }
	        break;
	    }
	    
	    /*
	        ZZ gvp = PowerMod(POSMOD(g,n),vp*vq,n);
	        for (int i=0; i<u; ++i){
	            ZZ decipher = PowerMod(gvp,POSMOD(ZZ(i),n),n);
	            lut[decipher] = i;
	        }
	    */
	    BigInteger gvp = PowerMod(POSMOD(g,p),vp,p);
	    for (int i=0; i<u; ++i)
	    {
	        BigInteger decipher = PowerMod(gvp,POSMOD(ZZ(i),p),p);
	        lut[decipher] = i;
	    }

	    for (int i=0; i<2*t; ++i)
	    {
	        BigInteger e = PowerMod(ZZ(2),i,n);
	        BigInteger out = PowerMod(h,e,n);
	        hLUT[i] = out;
	    }

	    for (int i=0; i<u; ++i)
	    {
	        BigInteger out = PowerMod(g,i,n);
	        gLUT[i] = out;
	    }
	    
	    pubkey =  new DGKPublicKey(n,g,h, u, gLUT,hLUT,l,t,k);
	    privkey = new DGKPrivateKey(p,q,vp,vq,lut,u);
	}//End of Generate Key Method
}//END OF CLASS
/*
 * ZZ DGKOperations::encrypt(DGKPublicKey &pubKey, unsigned long plaintext)
	{
	    int t = pubKey.getT();
	    ZZ n, h, u;

	    n = pubKey.GetN();
	    h = pubKey.GetH();
	    u = pubKey.GetU();

	    // TODO Thro error is paintext not in Zu
	    ZZ ciphertext,r;

	    if (0 > plaintext || u <= plaintext )
	    {
	        throw std::runtime_error("Encryption Invalid Parameter : the plaintext is not in Zu");
	    }
	    r = NTL::RandomBnd(t*2);
	    SetBit(r,t*2-1);
	    ZZ firstpart = pubKey.getGLUT(plaintext);
	    ZZ secondpart = ZZ(0);
	    if (h== 0)
	    {
	        secondpart = ZZ(0) ;
	    }
	    secondpart = ZZ(1);
	    for(int i = 0; i < NTL::NumBits(r); ++i)
	    {
	        if(bit(r,i) == 1)
	        {
	            secondpart = secondpart * pubKey.getHLUT()[i];
	        }
	    }

	    // ZZ secondpart = NTL::PowerMod(h,r,n) ;

	    ciphertext = POSMOD(firstpart * secondpart, n);
	    return ciphertext;

	}
	
	public static long decrypt(DGKPublicKey pubKey, DGKPrivateKey privKey, BigInteger ciphertext)
	{
	    BigInteger vp = privKey.GetVP();
	    BigInteger p = privKey.GetP();
	    BigInteger n = n = pubKey.GetN();
	    long u = pubKey.GetU();
	
	    if (0 > ciphertext || n <= ciphertext )
	    {
	    	throw new IllegalArgumentException("Decryption Invalid Parameter : the ciphertext is not in Zn");
	    }

	    BigInteger decipher = PowerMod(POSMOD(ciphertext,p),vp,p);

	    long plain = privKey.GetLUT(decipher);

	    return plain;
	};
	
	public static BigInteger DGKAdd(DGKPublicKey pubKey, BigInteger a, BigInteger b)
	{
	    BigInteger n= DGKPublicKey.n();
	    if (a.signum()==1 || n <= a || 0 >b || n <= b )
	    {
	        throw new IllegalArgumentException("DGKAdd Invalid Parameter : at least one of the ciphertext is not in Zn");
	    }
	    BigInteger result = MulMod(a,b,n);
	    return result;
	}

	public static BigInteger DGKMultiply(DGKPublicKey pubKey, BigInteger cipher, long plaintext)
	{
	    BigInteger n = DGKPublicKey.n();
	    long u = DGKPublicKey.u();
	    if (0 > cipher || n <= cipher )
	    {
	    	 throw new IllegalArgumentException("DGKMultiply Invalid Parameter :  the ciphertext is not in Zn");
	    }
	    if (0 > plaintext || u <= plaintext )
	    {
	    	 throw new IllegalArgumentException("DGKMultiply Invalid Parameter :  the plaintext is not in Zu");
	    }
	    BigInteger result = PowerMod(cipher,plaintext,n);
	    return result;
 */
*/