import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class DGKOperations
{
	private DGKPublicKey pubkey;
	private DGKPrivateKey privkey;
	private static Random rnd = new Random();
	private static int certainty = 40;
	//Probability of getting prime is 1-(1/2)^40
	
	public DGKOperations(int l, int t, int k)
	{
		GenerateKeys(l,t,k);
	}
	
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
	    	u = zU.longValue();
	        vp = new BigInteger(t, certainty, rnd);//(512,40,random)
	        vq = new BigInteger(t, certainty, rnd);//(512,40,random)
	        vpvq = vp.multiply(vq);
	        tmp = BigInteger.valueOf(u).multiply(vp);

	        //OR BITCOUNT?
	        int needed_bits = k/2 - (tmp.bitLength());


	        // Generate rp until p is prime such that uvp divde p-1
	        do
	        {
	            rp = NTL::RandomBits_ZZ(needed_bits);
	            NTL::SetBit(rp,needed_bits -1);
	            p = rp.multiply(mp).add(BigInteger.ONE);
	        }
	        while(!ProbPrime(p,10));  // Thus we ensure that p is a prime, with p-1 dividable by prime numbers vp and u

	        tmp = u * vq;
	        needed_bits = k/2 - (tmp.bitLength());

	// Same method for q than for p

	        do
	        {
	            rq = NTL::RandomBits_ZZ(needed_bits);
	            NTL::SetBit(rq,needed_bits -1);
	            q = rq.multiply(tmp).add(BigInteger.ONE);
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
	        h = r.modPow(temp,n);
	        
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
	        
	        if (h.modPow(u, n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(u.muliply(vq), n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(u.multiply(vp), n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (h.gcd(n).equals(BigInteger.ONE))
	        {
	            break;
	        }
	    }

	    BigInteger rprq = rp.multiply(rq);

	    while(true)
	    {
	        r = NTL::RandomBnd(n);
	        g = r.modPow(rprq,n);

	        if (g == ZZ(1))
	        {
	            continue;
	        }

	        if (g.gcd(n) != BigInteger.ONE)
	        {
	            continue;
	        } // Then h can still be of order u,vp, vq , or a combination of them different that uvpvq

	        if (g.modPow(u,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (g.modPow(u.mulitply(u),n) == BigInteger.ONE)
	        {

	            continue;
	        }
	        if (g.modPow(u*u*vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (g.modPow(u*u*vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (g.modPow(vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (g.modPow(vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (g.ModPow(u*vq,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (g.modPow(u*vp,n) == BigInteger.ONE)
	        {
	            continue;
	        }

	        if (g.modPow(vpvq,n) == BigInteger.ONE)
	        {
	            continue;
	        }
	        if (POSMOD(g,p).modPow(vp,p) == BigInteger.ONE)
	        {
	            continue; // Temporary fix
	        }
	        if ((POSMOD(g,p).modPow(u,p) == BigInteger.ONE)
	        {
	            continue;// Temporary fix
	        }
	        if (POSMOD(g,q).modPow(vq,q) == BigInteger.ONE)
	        {
	            continue;// Temporary fix
	        }
	        if ((POSMOD(g,q).modPow(u,q) == BigInteger.ONE)
	        {
	            continue;// Temporary fix
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
	    BigInteger gvp = POSMOD(g,p).modPow(vp,p);
	    for (int i=0; i<u; ++i)
	    {
	        BigInteger decipher = gvp.modPow(POSMOD(BigIntger.valueof((long) i),p),p);
	        lut[decipher] = i;
	    }

	    for (int i=0; i<2*t; ++i)
	    {
	        BigInteger e = new BigInteger("2").modPow(BigInteger.valueOf((long)(i)),n);
	        BigInteger out = h.modPow(e,n);
	        hLUT[i] = out;
	    }

	    for (int i=0; i<u; ++i)
	    {
	        BigInteger out = g.modPow(BigInteger.valueOf((long)i),n);
	        gLUT[i] = out;
	    }
	    
	    pubkey =  new DGKPublicKey(n,g,h, u, gLUT,hLUT,l,t,k);
	    privkey = new DGKPrivateKey(p,q,vp,vq,lut,u);
	}//End of Generate Key Method

	public static BigInteger encrypt(DGKPublicKey pubKey, long plaintext)
	{
	    int t = pubKey.t;

	    BigInteger n = pubKey.n;
	    BigInteger h = pubKey.h;
	    BigInteger u = pubKey.bigU;
	    int U=u.intValue();
	    //Through error is plaintext not in Zu
	    BigInteger ciphertext,r;

	    if (0 > plaintext || plaintext >= U )
	    {
	        throw new IllegalArgumentException("Encryption Invalid Parameter : the plaintext is not in Zu");
	    }
	    r = NTL::RandomBnd(t*2);
	    SetBit(r,t*2-1);
	    BigInteger firstpart = pubKey.getGLUT(plaintext);
	    BigInteger secondpart = BigInteger.ZERO;
	    if (h.equals(BigInteger.ZERO))
	    {
	        secondpart = BigInteger.ZERO;
	    }
	    secondpart = BigInteger.ONE;
	    for(int i = 0; i < r.bitCount(); ++i)
	    {
	        if(bit(r,i) == 1)
	        {
	            secondpart = secondpart * pubKey.getHLUT()[i];
	        }
	    }

	    // ZZ secondpart = NTL::PowerMod(h,r,n) ;

	    ciphertext = POSMOD(firstpart.multiply(secondpart), n);
	    return ciphertext;
	}
	
	public static long decrypt(DGKPublicKey pubKey, DGKPrivateKey privKey, BigInteger ciphertext)
	{
	    BigInteger vp = privKey.GetVP();
	    BigInteger p = privKey.GetP();
	    BigInteger n = pubKey.n;
	    long u = pubKey.u;
	
	    if (ciphertext.signum()==-1 || n <= ciphertext )
	    {
	    	throw new IllegalArgumentException("Decryption Invalid Parameter : the ciphertext is not in Zn");
	    }

	    BigInteger decipher = POSMOD(ciphertext,p.modPow(vp,p);
	    long plain = privKey.GetLUT(decipher);
	    return plain;
	}
	
	public static BigInteger DGKAdd(DGKPublicKey pubKey, BigInteger a, BigInteger b)
	{
	    BigInteger n= DGKPublicKey.n;
	    if (a.signum()==-1 || n <= a || b.signum()==-1 || n <= b )
	    {
	        throw new IllegalArgumentException("DGKAdd Invalid Parameter : at least one of the ciphertext is not in Zn");
	    }
	    BigInteger result = a.multiply(b).mod(n);
	    //Originally called MulMod...Method not found...
	    //Assum a*b(mod n)
	    return result;
	}

	public static BigInteger DGKMultiply(DGKPublicKey pubKey, BigInteger cipher, long plaintext)
	{
	    BigInteger n = DGKPublicKey.n;
	    long u = DGKPublicKey.u;
	    if (cipher.signum()==-1 || n <= cipher )
	    {
	    	 throw new IllegalArgumentException("DGKMultiply Invalid Parameter :  the ciphertext is not in Zn");
	    }
	    if (plaintext < 0 || u <= plaintext )
	    {
	    	 throw new IllegalArgumentException("DGKMultiply Invalid Parameter :  the plaintext is not in Zu");
	    }
	    BigInteger result = cipher.modPow(BigInteger.valueOf(plaintext),n);
	    return result;
	}
	
 
	public static ArrayList<Long> generateLUT(DGKPublicKey pubKey, DGKPrivateKey privKey)
	{
    	ArrayList<Long> LUT = new ArrayList<Long>();
    	BigInteger n = pubKey.n;
    	BigInteger g = pubKey.g;
    	BigInteger h = pubKey.h;
    	BigInteger u = pubKey.bigU;
    	BigInteger p = privKey.GetP();
    	BigInteger vp = privKey.GetVP() ;
    	BigInteger gvp = POSMOD(g,p).modPow(vp,p);
    	for (int i=0; i<u.intValue(); ++i)
    	{
        	BigInteger decipher = PowerMod(gvp,POSMOD(ZZ(i),p),p);
        	LUT[decipher] = i;
    	}
    	return LUT;
	}
	
	public static BigInteger isGreaterthan(DGKPublicKey pubKey,DGKPrivateKey privKey, BigInteger x, BigInteger y)
	{
	    // A & B
	    int l = pubKey.l - 2 ; // see constraints in the veugen paper
	    int N = (int) pubKey.u;
	    int u = (int) pubKey.u;
	    int powL = pow(2,l);

	    // A
	    long r = NTL::RandomBnd(N);//

	    //r = 5; //TODO remove this heresy
	    BigInteger encR = encrypt(pubKey,r);
	    long alpha = POSMOD(BigInteger.valueOf(r),BigInteger.valueOf(powL)).longValue();
	    BigInteger alphaZZ = BigInteger.valueOf(alpha);
	    BigInteger z = 
	    			DGKAdd(pubKey,
	    			encrypt(pubKey,powL),
	    			DGKAdd(pubKey, encR,
	    			DGKAdd(pubKey, x, DGKMultiply(pubKey,y,u-1))));
	    // B
	    long plainZ = decrypt(pubKey,privKey,z);

	    long beta = POSMOD(BigInteger.valueOf(plainZ),BigInteger.valueOf((long)powL)).longValue();
	    BigInteger encBetaMayOverflow = encrypt(pubKey,(beta >= (powL - POSMOD(BigInteger.valueOf(N),BigInteger.valueOf(powL)).intValue())));
	    BigInteger d ;
	    if ( plainZ < (N-1)/2)
	    {
	        d = encrypt(pubKey,1);
	    }
	    else
	    {
	        d = encrypt(pubKey,0);
	    }
	    long betaTab [] = new long [l];
	    BigInteger encBetaTab [] = new BigInteger [l];

	    for(int i = 0 ; i < l ; i++)
	    {
	        betaTab[i] = bit(beta,i);
	        encBetaTab[i] = encrypt(pubKey, betaTab[i]);
	    }
	    //A
	    if (r < (N-1)/2)
	    {
	        d = encrypt(pubKey,0);
	    }
	    BigInteger encAlphaXORBetaTab [] = new BigInteger[l];
	    BigInteger W [] = new BigInteger [l] ;
	    BigInteger c [] = new BigInteger [l+1];


	    long alphaHat = POSMOD(BigInteger.valueOf(r-N), BigInteger.valueOf(powL)).longValue();
	    BigInteger xorBitsSum = encrypt(pubKey,0);
	    BigInteger alphaHatZZ = BigInteger.valueOf(alphaHat);
	    for(int i = 0 ; i < l ; i++)
	    {
	        if(bit(alpha,i) == 0)
	        {
	            encAlphaXORBetaTab[i] = encBetaTab[i];
	        }
	        else
	        {
	            encAlphaXORBetaTab[i] = DGKAdd(pubKey,  encrypt(pubKey,1), DGKMultiply(pubKey,encBetaTab[i],u-1));
	        }
	        if(bit(alphaZZ,i) == bit(alphaHatZZ,i))
	        {
	            W[i] = encAlphaXORBetaTab[i];
	            //  xorBitsSum = DGKAdd(pubKey,xorBitsSum,encAlphaXORBetaTab[i] );
	        }
	        else
	        {
	            W[i] = DGKAdd(pubKey,  encAlphaXORBetaTab[i], DGKMultiply(pubKey,d,u-1));
	            // W[i] = encAlphaXORBetaTab[i];
	            // xorBitsSum = DGKAdd(pubKey,xorBitsSum,DGKAdd(pubKey,encrypt(pubKey,1),DGKMultiply(pubKey,encAlphaXORBetaTab[i],u-1)) );
	        }

	        W[i] = DGKMultiply(pubKey, W[i],pow(2,i));
	        xorBitsSum = DGKAdd(pubKey,xorBitsSum,DGKMultiply(pubKey,W[i],2 ));
	    }
	    long da = RandomBnd(2);
	    long s = 1 -2*da;
	    BigInteger wProduct = encrypt(pubKey,0);
	    for(int i = 0 ; i < l ; i++)
	    {
	        long alphaexp = POSMOD( bit(alphaHatZZ,l-1-i) -  bit(alphaZZ,l-1-i), u);
	        c[l-1-i] =  DGKAdd(pubKey,
	                           wProduct,
	                           DGKAdd(pubKey, DGKAdd(pubKey,DGKMultiply(pubKey,encBetaTab[l-1-i],u-1),encrypt(pubKey, POSMOD(s + bit(alphaZZ, l-1-i), N) )), DGKMultiply(pubKey,d,alphaexp)));

	        BigInteger rBlind = NTL::RandomBits_ZZ(pubKey.getT() * 2);
	        NTL::SetBit(rBlind,pubKey.getT() * 2 -1);
	        //TODO BLIND
	        wProduct = DGKAdd(pubKey,wProduct,DGKMultiply(pubKey, W[l-1-i],3));

	    }
	    c[l] = DGKAdd(pubKey, encrypt(pubKey,da),xorBitsSum);
	    // A shuffle C

	    // B
	    BigInteger db =  encrypt(pubKey,0);
	    for(int i = 0 ; i < l+1 ; i++)
	    {
	        if(decrypt(pubKey, privKey,c[i]) == 0)
	        {
	            db= encrypt(pubKey,1);
	            break;
	        }
	    }
	    BigInteger divZ = encrypt(pubKey,plainZ/powL);

	    //A
	    BigIntber betaInfAlpha;
	    if (da == 1)
	    {
	        betaInfAlpha = db;
	    }
	    else
	    {
	        betaInfAlpha = DGKAdd(pubKey,encrypt(pubKey,1),DGKMultiply(pubKey, db, u -1));
	    }

	    BigInteger overflow = DGKMultiply(pubKey,d,1+N/powL);
	    BigInteger doubleBucketGap ;
	    if (beta >= powL - POSMOD(N,powL))
	    {
	        doubleBucketGap = encrypt(pubKey,0);
	    }
	    else
	    {
	        doubleBucketGap = DGKMultiply(pubKey, d, u -1);

	    }
	    overflow = DGKAdd(pubKey,overflow,doubleBucketGap);
	    BigInteger result = DGKAdd(pubKey,
	                       DGKAdd(pubKey,
	                              divZ,
	                              DGKMultiply(pubKey,DGKAdd(pubKey, encrypt(pubKey,r/powL),betaInfAlpha), u-1))
	                       , overflow);
	    long lastpush = 0;

	    lastpush = decrypt(pubKey,privKey,d) *(
	                   (1 -decrypt(pubKey,privKey,betaInfAlpha))*(1 - (alpha < POSMOD(N,powL)))* (beta >= (powL - POSMOD(N,powL)))
	                   +(decrypt(pubKey,privKey,betaInfAlpha))*(0 - (alpha < POSMOD(N,powL)))* (1-(beta >= (powL - POSMOD(N,powL))))
	               );

	    ZZ effectOfAlphaBetaOverflow = std::get<0>((CipherMultiplication(pubKey,privKey,betaInfAlpha,encBetaMayOverflow )));
	    effectOfAlphaBetaOverflow = DGKMultiply(pubKey,effectOfAlphaBetaOverflow, POSMOD(2 * (alpha < POSMOD(N,powL)) - 1,N));
	    effectOfAlphaBetaOverflow = DGKAdd(pubKey,effectOfAlphaBetaOverflow,DGKMultiply(pubKey,DGKAdd(pubKey, encBetaMayOverflow, betaInfAlpha),POSMOD(N-(alpha < POSMOD(N,powL)),N)));
	    effectOfAlphaBetaOverflow = DGKAdd(pubKey, effectOfAlphaBetaOverflow,encBetaMayOverflow );
	    effectOfAlphaBetaOverflow = std::get<0>(CipherMultiplication(pubKey,privKey,effectOfAlphaBetaOverflow,d));
	    // encBetaMayOverflow
	    result = DGKAdd(pubKey,result,DGKMultiply(pubKey,effectOfAlphaBetaOverflow,u-1));

	    return result;
	}
/*
 * 	COMPUTATIONAL METHODS USED	
 */
	public static BigInteger POSMOD(BigInteger x, BigInteger n)
	{
		return ((x.mod(n).add(n)).mod(n));
	}
	
	public static void main (String args [])
	{
		int l=10, t=160, k=1024;
		DGKOperations FIU = new DGKOperations(l,t,k);
	}
}//END OF CLASS
