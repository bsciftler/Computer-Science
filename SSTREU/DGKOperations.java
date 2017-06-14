import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//Conversion from NLT to BigInteger was done using this site
//http://www.shoup.net/ntl/doc/ZZ.txt
/*
 * 	Most likely errors I made
 * 	- Random Number Generation:
 * I am not sure the difference between randombnd and random_zz
 * Also I dont understand how random number generation works with big integers.
 * (See bottom)
 * 	- I have ommitted some methods
 * 	- Potentially did Hashmapping wrong
 * 	- The IsSuperiorMethod is difficult to understand, so i probably made mistakes there
 * 
 * 	See the very bottom of the code to find the NLT methods translated to Java
 */
public class DGKOperations
{
	private static DGKPublicKey pubkey;
	private static DGKPrivateKey privkey;
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
	    System.out.println("No Exceptions in data found");
	    
	    BigInteger p, rp;
	    BigInteger q, rq;
	    BigInteger g, h ;
	    BigInteger n, r ;
	    long u;
	    BigInteger vp,vq, vpvq,tmp;

	    while(true)
	    {
	        // Generate some of the required prime number
	    	/*
	    	 * 	Here Line 54 it is a bit in C++ Code
	    	 * 	But I remember in DGK's original Paper they said
	    	 * 	something about finding the next prime from l+2.
	    	 * 	So I will go with that...
	    	 */
	    	//System.out.println("Stuck at Next Prime");
	    	BigInteger Lplustwo = BigInteger.valueOf((long) l).add(new BigInteger("2"));
	    	System.out.println(Lplustwo.toString());
	    	BigInteger zU  = NextPrime(Lplustwo);      
	    	u = zU.longValue();
	    	System.out.println(u +" " +Lplustwo.toString());
	        vp = new BigInteger(t, certainty, rnd);//(160,40,random)
	        vq = new BigInteger(t, certainty, rnd);//(160,40,random)
	        vpvq = vp.multiply(vq);
	        tmp = BigInteger.valueOf(u).multiply(vp);

	        System.out.println("Completed generating vp, vq");
	        
	        int needed_bits = k/2 - (tmp.bitLength());
	        //if k = 1,024
	        //needed is 512 - u-bit - vp bits

	        // Generate rp until p is prime such that uvp divde p-1
	        do
	        {
	        	//rp can be a random number?
	        	rp = new BigInteger(needed_bits, certainty, rnd);//(512,40,random)
		        rp = rp.setBit(needed_bits - 1 );
		        /*
	from NTL:
	long SetBit(ZZ& x, long p);
	returns original value of p-th bit of |a|, and replaces p-th bit of
	a by 1 if it was zero; low order bit is bit 0; error if p < 0;
	the sign of x is maintained
		         */
		        
		        // p = rp * u * vp + 1
		        // u | p - 1
		        // vp | p - 1
		        p = rp.multiply(tmp).add(BigInteger.ONE);
		        System.out.println("p is not prime");
	        }
	        while(!p.isProbablePrime(certainty));
	        //while(!p.isProbablePrime(90));
	        
	        //Thus we ensure that p is a prime, with p-1 divisible by prime numbers vp and u
	        //I can implement AKS for 100% certainty if need be
	        
	        tmp = BigInteger.valueOf(u).multiply(vq);
	        needed_bits = k/2 - (tmp.bitLength());
	        do
	        {
	        	// Same method for q than for p
		        rq = new BigInteger(needed_bits, certainty, rnd);//(512,40,random)
		        rq = rq.setBit(needed_bits -1);
		        q = rq.multiply(tmp).add(BigInteger.ONE);
		        System.out.println("q is not prime");
	        }
	        while(!q.isProbablePrime(certainty));    
	       //Thus we ensure that q is a prime, with p-1 divides the prime numbers vq and u
	       
	        if(!POSMOD(rq,BigInteger.valueOf(u)).equals(BigInteger.ZERO) && !POSMOD(rp,BigInteger.valueOf(u)).equals(BigInteger.ZERO))
	        {
	            break;//done!!
	        }
	    }
	    System.out.println("While Loop 1: initiational computations completed.");
	    n = p.multiply(q);
	    tmp = rp.multiply(rq).multiply(BigInteger.valueOf(u));
	    System.out.println("n, p and q sucessfully generated!");

	    while(true)
	    {
	        do
	        {
	            r = new BigInteger(n.bitLength(), certainty, rnd);//(bit size,40,random)
	            //System.out.println("bitlength of r: "+ r.bitLength());
	            //System.out.println("bitlength of n: " + n.bitLength());
	            if (r.bitLength()==n.bitLength())
	            {
	            	System.out.println("escape the loop!");
	            }
	        } 
	        while (!( r.bitLength()==n.bitLength()) );//Ensure it is n-bit Large number
	        
	        h = r.modPow(tmp,n);
	        
	        if (h == BigInteger.ONE)
	        {
	            continue;
	        }
	        
	        if (h.modPow(vp,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(vq,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(BigInteger.valueOf(u), n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(BigInteger.valueOf(u).multiply(vq), n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        
	        if (h.modPow(BigInteger.valueOf(u).multiply(vp), n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (h.gcd(n).equals(BigInteger.ONE))
	        {                                               
	            break;
	        }
	    }

	    BigInteger rprq = rp.multiply(rq);
	    System.out.println("h and g generated");
	    
	    while(true)
	    {
	    	do
	    	{
	    		r = new BigInteger(n.bitLength(), certainty, rnd);//(bit length,40,random)
	    	} 
	    	while (! (r.bitLength()==n.bitLength()) );
		        
	        g = r.modPow(rprq,n);

	        if (g.equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (!g.gcd(n).equals(BigInteger.ONE))
	        {
	            continue;
	        } // Then h can still be of order u, vp, vq , or a combination of them different that uvpvq

	        if (g.modPow(BigInteger.valueOf(u),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        if (g.modPow(BigInteger.valueOf(u).multiply(BigInteger.valueOf(u)),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        if (g.modPow(BigInteger.valueOf(u).multiply(BigInteger.valueOf(u)).multiply(vp),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        if (g.modPow(BigInteger.valueOf(u).multiply(BigInteger.valueOf(u)).multiply(vq),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (g.modPow(vp,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (g.modPow(vq,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (g.modPow(BigInteger.valueOf(u).multiply(vq),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (g.modPow(BigInteger.valueOf(u).multiply(vp),n).equals(BigInteger.ONE))
	        {
	            continue;
	        }

	        if (g.modPow(vpvq,n).equals(BigInteger.ONE))
	        {
	            continue;
	        }
	        if (POSMOD(g,p).modPow(vp,p).equals(BigInteger.ONE))
	        {
	            continue; // Temporary fix
	        }
	        
	        if ((POSMOD(g,p).modPow(BigInteger.valueOf(u),p).equals(BigInteger.ONE)))
	        {
	            continue;// Temporary fix
	        }
	        
	        if (POSMOD(g,q).modPow(vq,q).equals(BigInteger.ONE))
	        {
	            continue;// Temporary fix
	        }
	        
	        if ((POSMOD(g,q).modPow(BigInteger.valueOf(u),q).equals(BigInteger.ONE)))
	        {
	            continue;// Temporary fix
	            //((g%q)+q)%q^{u}(mod q) = 1;
	            //Test if g^{u} = 1 (mod q)
	        }
	        
	        break;
	    }
	    
	    System.out.println("Final variable tests completed");
	    /*
	        ZZ gvp = PowerMod(POSMOD(g,n),vp*vq,n);
	        for (int i=0; i<u; ++i){
	            ZZ decipher = PowerMod(gvp,POSMOD(ZZ(i),n),n);
	            lut[decipher] = i;
	        }
	    */
	    System.out.println("Generating hashmap");
	    
	    HashMap<BigInteger, Long> lut = new HashMap <BigInteger, Long>((int)u);
	    
	    BigInteger gvp = POSMOD(g,p).modPow(vp,p);
	    for (int i=0; i<u; ++i)
	    {
	        BigInteger decipher = gvp.modPow(POSMOD(BigInteger.valueOf((long) i),p),p);
	        //lut[decipher] = i;
	        lut.put(decipher,(long)i);
	    }

	    HashMap<Long, BigInteger> hLUT = new HashMap<Long, BigInteger>(2*t);
	    for (int i=0; i<2*t; ++i)
	    {
	        BigInteger e = new BigInteger("2").modPow(BigInteger.valueOf((long)(i)),n);
	        BigInteger out = h.modPow(e,n);
	        //hLUT[i] = out;
	        hLUT.put((long)i,out);
	    }
	    
	    HashMap<Long, BigInteger> gLUT = new HashMap<Long, BigInteger>((int)u);
	    for (int i=0; i<u; ++i)
	    {
	        BigInteger out = g.modPow(BigInteger.valueOf((long)i),n);
	        //gLUT[i] = out;
	        gLUT.put((long)i,out);
	    }
	    System.out.println("Printing values!!");
	    pubkey =  new DGKPublicKey(n,g,h, u, gLUT,hLUT,l,t,k);
	    privkey = new DGKPrivateKey(p,q,vp,vq,lut,u);
	    
	    try
	    {
	    	String filelocation= "C:\\Users\\Andrew\\Desktop\\DGKKeys.txt";
		
			PrintWriter pw = new PrintWriter(
							 new BufferedWriter(
							 new OutputStreamWriter(
							 new FileOutputStream(filelocation))));
			pw.print("Public Key");
			pw.println("N: " + n);
			pw.println("G: " + g);
			pw.println("H: " + h);
			pw.println("U: " + u);
			pw.println("Private Key");
			pw.println("p: "+p);
			pw.println("q: "+q);
			pw.println("vp: " + vp);
			pw.println("vq: " + vq);
			pw.println("U: " + u);
			pw.flush();
			pw.close();
	    }
	    catch (IOException ioe)
	    {
	    	System.out.println("Failure at printing values");
	    }
	    
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
		do
    	{
    		r = new BigInteger(2*t, certainty, rnd);//(512,40,random);
    	} 
    	while (! (r.bitLength()==(2*t)) );
	        
	    r = r.setBit(t*2-1);
	    BigInteger firstpart = pubKey.getGLUT().get(plaintext);
	    BigInteger secondpart = BigInteger.ZERO;
	    if (h.equals(BigInteger.ZERO))
	    {
	        secondpart = BigInteger.ZERO;
	    }
	    secondpart = BigInteger.ONE;
	    for(int i = 0; i < r.bitLength(); ++i)
	    {
	    	/*
	    	 * 
long bit(const ZZ& a, long k);
long bit(long a, long k); 
// returns bit k of |a|, position 0 being the low-order bit.
// If  k < 0 or k >= NumBits(a), returns 0.
	    	 */
	        if(bit(r,i) == 1)
	        {
	            secondpart = secondpart.multiply(pubKey.getHLUT().get(i));
	        }
	    }

	    //secondpart = h.modPow(r,n);

	    ciphertext = POSMOD(firstpart.multiply(secondpart), n);
	    return ciphertext;
	}
	
	public static long decrypt(DGKPublicKey pubKey, DGKPrivateKey privKey, BigInteger ciphertext)
	{
	    BigInteger vp = privKey.GetVP();
	    BigInteger p = privKey.GetP();
	    BigInteger n = pubKey.n;
	    long u = pubKey.u;
	
	    if (ciphertext.signum()==-1 || (isGreaterthan(ciphertext,n)))
	    {
	    	throw new IllegalArgumentException("Decryption Invalid Parameter : the ciphertext is not in Zn");
	    }

	    BigInteger decipher = POSMOD(ciphertext,p.modPow(vp,p));
	    long plain = privKey.GetLUT().get(decipher).longValue();
	    return plain;
	}
	
	public static BigInteger DGKAdd(DGKPublicKey pubKey, BigInteger a, BigInteger b)
	{
	    BigInteger n= DGKPublicKey.n;
	    if (a.signum()==-1 || (isLessthanorEqualto(n,a))|| b.signum()==-1 || isLessthanorEqualto(n,b))
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
	    if (cipher.signum()==-1 || (isLessthanorEqualto(n,cipher)) )
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
	
 
	public static HashMap<Long, BigInteger> generateLUT(DGKPublicKey pubKey, DGKPrivateKey privKey)
	{
    	BigInteger n = pubKey.n;
    	BigInteger g = pubKey.g;
    	BigInteger h = pubKey.h;
    	BigInteger u = pubKey.bigU;
    	BigInteger p = privKey.GetP();
    	BigInteger vp = privKey.GetVP() ;
    	BigInteger gvp = POSMOD(g,p).modPow(vp,p);
    	HashMap <Long, BigInteger>LUT = new HashMap <Long,BigInteger>(u.intValue());
    	for (int i=0; i<u.intValue(); ++i)
    	{
        	BigInteger decipher = gvp.modPow(POSMOD(BigInteger.valueOf((long)i),p),p);
        	//LUT[decipher] = i;
        	LUT.put((long)i, decipher);
    	}
    	return LUT;
	}
	
	public static BigInteger isSuperiorTo(DGKPublicKey pubKey,DGKPrivateKey privKey, BigInteger x, BigInteger y)
	{
	    // A & B
	    int l = pubKey.l - 2 ; // see constraints in the veugen paper
	    int N = (int) pubKey.u;
	    int u = (int) pubKey.u;
	    int powL = 2;

	    // A
	    long r = RandomBnd(N).longValue();//

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
	    BigInteger encBetaMayOverflow = 
	    	encrypt
	    	(pubKey,
	    		(overflow(beta,(powL - POSMOD(N,powL))))
	    	);
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

	        W[i] = DGKMultiply(pubKey, W[i],(long)Math.pow(2,i));
	        xorBitsSum = DGKAdd(pubKey,xorBitsSum,DGKMultiply(pubKey,W[i],2 ));
	    }
	    long da = RandomBnd(2).longValue();
	    long s = 1 -2*da;
	    BigInteger wProduct = encrypt(pubKey,0);
	    for(int i = 0 ; i < l ; i++)
	    {
	        long alphaexp = POSMOD( bit(alphaHatZZ,l-1-i) -  bit(alphaZZ,l-1-i), u);
	        c[l-1-i] =  DGKAdd(pubKey,
	                           wProduct,
	                           DGKAdd(pubKey, DGKAdd(pubKey,DGKMultiply(pubKey,encBetaTab[l-1-i],u-1),encrypt(pubKey, POSMOD(s + bit(alphaZZ, l-1-i), N) )), DGKMultiply(pubKey,d,alphaexp)));

	        BigInteger rBlind = RandomBits_ZZ(pubKey.t * 2);
	        rBlind = rBlind.setBit(pubKey.t * 2 -1);
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
	    BigInteger betaInfAlpha;
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
	    BigInteger result =
	    		DGKAdd(pubKey,
	    		DGKAdd(pubKey,divZ,
	    		DGKMultiply(pubKey,DGKAdd(pubKey, encrypt(pubKey,r/powL),betaInfAlpha), u-1))
	             , overflow);
	    long lastpush = decrypt(pubKey,privKey,d) *
	    (
	    	(1 -decrypt(pubKey,privKey,betaInfAlpha))*(1 - (overflowTwo(alpha,POSMOD(N,powL))))* (overflow(beta,(powL - POSMOD(N,powL))))
	    	+(decrypt(pubKey,privKey,betaInfAlpha))*(0 - (overflowTwo(alpha,POSMOD(N,powL)))* (1-(overflow(beta,(powL - POSMOD(N,powL))))
	    )));

	    BigInteger effectOfAlphaBetaOverflow = CipherMultiplication(pubKey,privKey,betaInfAlpha,encBetaMayOverflow).get(0);
	    effectOfAlphaBetaOverflow = DGKMultiply(pubKey,effectOfAlphaBetaOverflow, POSMOD(2 * (overflowTwo(alpha,POSMOD(N,powL))) - 1,N));
	    effectOfAlphaBetaOverflow = DGKAdd(pubKey,effectOfAlphaBetaOverflow,DGKMultiply(pubKey,DGKAdd(pubKey, encBetaMayOverflow, betaInfAlpha),POSMOD(N-overflowTwo(alpha,POSMOD(N,powL)),N)));
	    effectOfAlphaBetaOverflow = DGKAdd(pubKey, effectOfAlphaBetaOverflow,encBetaMayOverflow );
	    effectOfAlphaBetaOverflow = CipherMultiplication(pubKey,privKey,effectOfAlphaBetaOverflow,d).get(0);	    // encBetaMayOverflow
	    result = DGKAdd(pubKey,result,DGKMultiply(pubKey,effectOfAlphaBetaOverflow,u-1));

	    return result;
	}
	
	public static ArrayList<BigInteger> CipherMultiplication(DGKPublicKey pubKey,DGKPrivateKey privKey, BigInteger x, BigInteger y)
	{
	    long u = pubKey.u;
	    // Generate all the blinding/challenge values
	    long ca = RandomBnd(u).longValue();
	    long cm = (RandomBnd(u-1).add(BigInteger.ONE)).longValue();
	    long bx = RandomBnd(u).longValue();
	    long by = RandomBnd(u).longValue();
	    long p = (RandomBnd(u-1).add(BigInteger.ONE)).longValue();

	    long secrets[]= {ca, cm, bx, by,p};
	    BigInteger caEncrypted = encrypt(pubKey, ca);
	    BigInteger bxEncrypted = encrypt(pubKey, bx);
	    BigInteger byEncrypted = encrypt(pubKey, by);
	    BigInteger pEncrypted  = encrypt(pubKey, p);

	    BigInteger xBlinded  = DGKAdd(pubKey, x, bxEncrypted);
	    BigInteger yBlinded  = DGKAdd(pubKey, y, byEncrypted);
	    BigInteger challenge = DGKAdd(pubKey, xBlinded, caEncrypted);
	    challenge = challenge.multiply(DGKMultiply(pubKey, challenge,cm));

	    // Send blinded operands and challenge to the key owner
	    // Below Owner part
	    BigInteger product = 
	    	encrypt
	    	(
	    		pubKey,
	    		POSMOD
	    		(
	    			decrypt(pubKey,privKey,xBlinded)*(decrypt(pubKey,privKey,yBlinded))
	    			,u
	    		)
	    	);

	    BigInteger response = encrypt
	    (
	    	pubKey,
	    	POSMOD
	    	(
	    		decrypt(pubKey,privKey,challenge)*
	    		decrypt(pubKey,privKey,yBlinded)
	    	,u)
	    );
	    // The owner send product + response flow

	    //Unblind the challenge
	    BigInteger associated = 
	    	DGKMultiply
	    	(pubKey,
	    		DGKAdd
	    		(pubKey, response,
	    				DGKMultiply
	    				(pubKey,
	    					DGKAdd
	    						(pubKey,
	    							DGKMultiply(pubKey, product,
	    								POSMOD(cm,u)),
	    								DGKMultiply(pubKey, yBlinded,POSMOD(ca*cm,u))
	    						),u-1
	    				)
	    		), p
	    	);

	    // Un-blind the Result
	    BigInteger result = DGKAdd
	    (pubKey,product,
	    	DGKMultiply
	    		(pubKey,
	    			DGKAdd
	    			(pubKey,
	    				DGKAdd
	    				(pubKey,
	    						DGKMultiply(pubKey, x, by),
	    						DGKMultiply(pubKey, y, bx)),
	    						encrypt(pubKey, POSMOD(bx*by,u)
	    				)
	    			),
	    u-1		)
	    );
	    
	    ArrayList<BigInteger> answer= new ArrayList<BigInteger>();
	    answer.add(result);
	    answer.add(associated);
	    return answer;
	};

	
	/*
	 *  MAIN METHOD TO TEST 
	 */
	public static void main (String args [])
	{
		int l=16, t=160, k=1024;
		DGKOperations.GenerateKeys(l, t, k);
		
		//for (int i=0;i<h.size();i++)
		//{
			//System.out.println(h.get(i));
			//System.out.println("Mods: " + h.get(i).mod(new BigInteger("5")));
		//}
		
	
//		
//		String filelocation= "C:\\Users\\Andrew\\Desktop\\Practice.txt";
//		
//		/*
//		 * 	Test 1:
//		 * 	Successfully encrypt and decrypt all numbers from -100 to 100
//		 */
//		long [] Control = null;
//		String [] inControl= null;
//		ArrayList<Long> Experiment = new ArrayList<Long>();
//		try
//		{
//			BufferedReader br = new BufferedReader(
//					new InputStreamReader(
//					new FileInputStream(filelocation)));
//			String line = br.readLine();
//			inControl = line.split(";");
//			Control = new long [inControl.length];
//			for (int i=0;i<inControl.length;i++)
//			{
//				Control[i]=(long)Integer.parseInt(inControl[i]);
//			}
//			br.close();
//		}
//		catch (IOException ioe)
//		{
//			System.err.println("TRIAL 1 FAILED");
//		}
//		//Now Encrypt and Decrypt the data
//		
//		String toInt;
//		for (int i=0;i<100;i++)
//		{
//			toInt = inControl[i];
//			BigInteger temp = DGKOperations.encrypt(pubkey, (long)Integer.parseInt(toInt));
//			Experiment.add(DGKOperations.decrypt(pubkey, privkey, temp));
//		}
//		//Test it
//		for (int i=0;i<100;i++)
//		{
//			if (Experiment.get(i)!=Control[i])
//			{
//				System.out.println("FAILURE AT " + i);
//				return;
//			}
//		}
//		
//		
//		/*
//		 * 	Test 2: Successfully add 2 to all numbers and get the correct answer
//		 */
//		
//		/*
//		 * 	Test 3: Successfully multiply 3 to all numbers and get the correct answer
//		 */
//		
//		/*
//		 * 	Test 4: Actually compare it!
//		 */
	}
	
/*
 * 	COMPUTATIONAL METHODS USED	
 */
	public static BigInteger POSMOD(BigInteger x, BigInteger n)
	{
		return ((x.mod(n).add(n)).mod(n));
	}
	
	public static long POSMOD(long x, long n)
	{
		return ((x%n)+n)%n;
	}
	
	//Test if x > y
	public static boolean isGreaterthan(BigInteger x, BigInteger y)
	{
		BigInteger classify = x.subtract(y);
		if (classify.signum()==-1) // x - y > 0: True
		{
			return true;
		}
		else if (classify.signum()==0)
		{
			return false; //x = y, False
		}
		else
		{
			return false; // x - y < 0, x > y: False, implies y > x
		}
	}
	public static boolean isLessthanorEqualto (BigInteger x, BigInteger y)
	{
		if (isGreaterthan(x,y)==true)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static BigInteger NextPrime (BigInteger x)
	{
		//Find next Prime number after x
		//Example if x =18, return 19 (closest prime)

		if(x.mod(new BigInteger("2")).equals(BigInteger.ZERO))
		{
			x=x.add(BigInteger.ONE);
		}

		while (true)
		{
			if (isPrime(x))
			{
				return x;
			}
			//System.out.print(x.toString());
			x = x.add(new BigInteger("2"));
		}
	}
	
	public static boolean isPrime(BigInteger x)
	{
		BigInteger factor = new BigInteger("3");
		
		while (!factor.equals(x))
		{
			if (x.mod(factor).equals(BigInteger.ZERO))
			{
				return false;
			}
			factor=factor.add(new BigInteger("2"));
		}
		return true;
	}
	//long bit(const ZZ& a, long k);
	//long bit(long a, long k); 
	// returns bit k of |a|, position 0 being the low-order bit.
	// If  k < 0 or k >= NumBits(a), returns 0.
	public static long bit(BigInteger a, long k)
	{
		if (isGreaterthan(a,BigInteger.valueOf(k))|| (a.subtract(BigInteger.valueOf(k)).equals(BigInteger.ZERO)) )
		{
			return 0;
		}
		if (k <0)
		{
			return 0;
		}
		String bit= a.toString(2);//get it in Binary
		return bit.charAt((int)k);
	}
	
	public static long bit(long a, long k)
	{
		if (a >= k)
		{
			return 0;
		}
		if (k < 0)
		{
			return 0;
		}
		BigInteger Bit = BigInteger.valueOf(k);
		String bit= Bit.toString(2);//get it in Binary
		return bit.charAt((int)k);
	}
	/*
void RandomBnd(ZZ& x, const ZZ& n);
ZZ RandomBnd(const ZZ& n);
void RandomBnd(long& x, long n);
long RandomBnd(long n);
x = pseudo-random number in the range 0..n-1, or 0 if n <= 0
	 */
	public static BigInteger RandomBnd(int n)
	{
		BigInteger r;
		do 
		{
		    r = new BigInteger(n, rnd);
		} 
		while (r.compareTo(new BigInteger(Integer.toString(n))) >= 0);
		return r;
	}
	
	public static BigInteger RandomBnd(long n)
	{
		BigInteger r;
		do 
		{
		    r = new BigInteger((int)n, rnd);
		} 
		while (r.compareTo(BigInteger.valueOf(n)) >= 0);
		return r;
	}
	/*
	 *
void RandomBits(ZZ& x, long l);
ZZ RandomBits_ZZ(long l);
void RandomBits(long& x, long l);
long RandomBits_long(long l);
// x = pseudo-random number in the range 0..2^l-1.
// EXCEPTIONS: strong ES
	 */
	
	public static BigInteger RandomBits_ZZ(int x)
	{
		BigInteger r;
		do 
		{
		    r = new BigInteger((int)x, rnd);
		} 
		while (r.compareTo(BigInteger.valueOf(x)) >= 0);
		return r;
	}
	
	public static long overflow(long x, long y)
	{
		if (x >= y)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	public static long overflowTwo (long x, long y)
	{
		if (x < y)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	/*
	 * 	Polynomial running time 100% fool proof prime test.
	 * 	Paper is from 2002.
	 */
	public static boolean AKSTest(BigInteger p)
	{
		//(x-1)^p - (x^p - 1)
		//Test if p divides all the coefficients
		//excluding the first and last term of (x-1)^p
		//If it can divide all of them then p is a prime
	
		//Using Binomial Theorem, I obtain the coefficients of all
		//terms from the expansion (x-1)^p
		ArrayList<BigInteger> coeff = BinomialTheorem(p);
		
		coeff.remove(0); //Remove first term
		coeff.remove(coeff.remove(coeff.size()-1)); //Remove last term
		
		for (int i=0;i<coeff.size();i++)
		{
			//System.out.println(coeff.get(i));
			//System.out.println(coeff.get(i).mod(p));
			if (!coeff.get(i).mod(p).equals(BigInteger.ZERO))
			{
				return false;
			}
		}
		return true;
	}
	//AKS-Test, I can use binomial theorem
	public static ArrayList<BigInteger> BinomialTheorem (BigInteger x)
	{
		ArrayList<BigInteger> coeff = new ArrayList<BigInteger>();
		/*
		 * 	Binomial Theorem: Choose
		 * 	n	n	n	...	n
		 * 	0	1	2	...	n
		 */
		BigInteger start = BigInteger.ZERO;
		while (! (start.equals(x.add(BigInteger.ONE))) )
		{
			coeff.add(nCr(x,start));
			start = start.add(BigInteger.ONE);
		}
		return coeff;
	}
	public static BigInteger nCr (BigInteger n, BigInteger r)
	{
		BigInteger nCr=factorial(n);
		nCr=nCr.divide(factorial(r));
		nCr=nCr.divide(factorial(n.subtract(r)));
		//nCr = n!/r!(n-r)!
		//or (n * n-1 * ... r+1)/(n-r)!
		return nCr;
	}
	
	public static BigInteger factorial(BigInteger x)
	{
		BigInteger result = BigInteger.ONE;
		BigInteger n = x;
		while (!n.equals(BigInteger.ZERO))
		{
			result = result.multiply(n);
			n= n.subtract(BigInteger.ONE);
		}
		return result;
	}
	
}//END OF CLASS