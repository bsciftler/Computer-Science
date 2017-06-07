import java.math.BigInteger;
import java.util.Random;

public class DGK
{
	private static Random rnd = new Random();
	
    // k2 controls the error probability of the primality testing algorithm
    // (specifically, with probability at most 2^(-k2) a NON prime is chosen).
	private static int k2 = 40;
	int k; //bit size of RSA-like key
	int t=160; //size of prime v which divides (p-1) and (q-1)
	int l=16; //size of exponent
	
	public DGK()
	{
		/*
		 Following the Paper
		 k=1,204
		 t=160
		 l=16
		 */
	}
	
    public void keyGen(DGKPublicKey pk, DGKPrivateKey sk)
    {
    	k=sk.k1;
    	int bit_length = sk.k1 / 2;//should be getting 512 here...
    	BigInteger p = new BigInteger(bit_length, k2, rnd);//(512,40,random)
    	BigInteger q = new BigInteger(bit_length, k2, rnd);//(512,40,random) 
    	
    	//Set Public Key Values: n
    	pk.n=p.multiply(q);
    	
    	//Set Private Key Values: p, q
    	sk.p=p;
    	sk.q=q;
    	/*
    	 * 	Abstract Algebra
    	 * 
    	 *	a^{LCM(p-1)(q-1)} = 1 (mod pq)
    	 *	LCM(p-1)(q-1) is the order of every element in group pq.
    	 *	LCM | any other order...
    	 *	LCM * GCD = (p-1)(q-1)
    	 */
    	BigInteger GCD = p.gcd(q);
    	BigInteger LCM = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    	LCM=LCM.divide(GCD);
    	
    	//Since I am told to use z_n* in the paper...n=p*q
    	//Generation of g and h must wait
    	
    	/*
    	Properties of u: Add to Public Key
    	u | (p-1)
    	u | (q-1)
    	u is smallest prime starting from L + 2 (Page 5 Section 2)
    	 */
    	pk.u=findingU(p.subtract(BigInteger.ONE),q.subtract(BigInteger.ONE),l);
    	
//--------------------------------------------------------------------------------   	
    	//Set Private Key Values: v_p, v_q
    	/*
    	 * 	v_p and v_q are t-bit long primes
    	 *  v_p and v_q | (p-1)
    	 *  v_p and v_q | (q-1)
    	 */
    	BigInteger v_p=new BigInteger(t, k2, rnd);//(t=160,40,random)
    	BigInteger v_q=new BigInteger(t, k2, rnd);//(t=160,40,random)
    	while (!v_p.mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ZERO) && v_p.mod(q.subtract(BigInteger.ONE)).equals(BigInteger.ZERO))
    	{
    		v_p=new BigInteger(t, k2, rnd);//(t=160,40,random)
    	}
    	while (!v_q.mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ZERO) && v_q.mod(q.subtract(BigInteger.ONE)).equals(BigInteger.ZERO))
    	{
    		v_q=new BigInteger(t, k2, rnd);//(t=160,40,random)
    	}
    	sk.vp=v_p;
    	sk.vq=v_q;
//--------------Finish getting h and g for PublicKey-------------------
    	/*
    	 * 	g^(u*vp*vq)=1(mod n)
    	 * 	h^(vp*vq)=1(mod n)
    	 * 
    	 * 	Where does LCM(p-1,q-1) factor in here??
    	 * 	Idea:
    	 *	Let LCM(p-1)(q-1) = v_p and v_q
    	 *	then u*LCM(p-1)(q-1) with g will return identity...
    	 */
    	pk.g=findBase(v_p.multiply(v_q).multiply(pk.u),pk.n);
    	pk.h=findBase(v_p.multiply(v_q),pk.n);
    }
    
    public BigInteger findBase(BigInteger expo, BigInteger Mod)
    {
    	BigInteger Base= new BigInteger("2");
    	while (true)
    	{
    		if(Base.modPow(expo, Mod).equals(BigInteger.ONE))
    		{
    			return Base;
    		}
    		Base=Base.add(BigInteger.ONE);
    	}
    }
    
    public BigInteger findOrder(BigInteger base, BigInteger Mod)
    {
    	BigInteger Order= new BigInteger("2");
    	while (true)
    	{
    		if(base.modPow(base, Mod).equals(BigInteger.ONE))
    		{
    			return Order;
    		}
    		Order=Order.add(BigInteger.ONE);
    	}
    }
	
    public BigInteger findingU (BigInteger p, BigInteger q, int L)
	{
    	//Peel off the common factor of 2...
    	//Assuming L will always be > 2.
		while (p.mod(new BigInteger("2")).equals(BigInteger.ZERO))
		{
			p=p.divide(new BigInteger("2"));
		}
		while (q.mod(new BigInteger("2")).equals(BigInteger.ZERO))
		{
			q=q.divide(new BigInteger("2"));
		}
		
		BigInteger factor = new BigInteger("3");
		while (true)//Prime Factorization is unique
		{
			//Test if it is both common factor of
			//(p-1) and (q-1), but also if it is 
			//the closest one to (L+2)
			if (p.mod(factor).equals(BigInteger.ZERO)
				&& q.mod(factor).equals(BigInteger.ZERO))
			{
				if(factor.subtract(new BigInteger(Integer.toString(L+2))).signum()==1)
				{
					return factor;
				}
				else
				{
					p=p.divide(factor);
					q=q.divide(factor);
				}
			}
			else
			{
				factor=factor.add(new BigInteger("2"));
			}
		}
	}
    
    //CipherText = g^message*h^random(mod n)
    public BigInteger encrypt(BigInteger message, DGKPublicKey pk)
    {
    	//random is a 2t-bit integer, in my case t=160
    	BigInteger random = new BigInteger((2*t), k2, rnd);
    	//CipherText = g^message*h^random(mod n)
    	BigInteger CipherText=pk.g.modPow(message, pk.n);
    	CipherText=CipherText.multiply(pk.h.modPow(random, pk.n));
    	return CipherText;
    }
    
    public BigInteger decrypt (BigInteger CipherText, DGKPrivateKey sk)
    {
    	/*
    	 * 	According to the paper, revised copy in 2009
    	 * 	c^v (mod n) = g^(vm) (mod n)
    	 * 	since 
    	 * 	h^r (mod n) becomes h^(rv)(mod n) BUT h^v(mod n) = 1!!!
    	 	Therefore (h^r)^v(mod n) = 1.
    	 */
    	BigInteger plainText=CipherText.modPow(sk.vp.multiply(sk.vq), sk.p.multiply(sk.q));
    	//g^(vm) (mod n)
    	//From Abstract Algebra I know that g^(uv) = 1 (mod n)
    	//But the numbers I can reach is not that many because of u being tiny.
    	//Build an ArrayList
    	//g^(1*m*v) (mod n), g^(2*m*v)(mod n), ... g^((u-1)*m*v)(mod n), the next value would be 1...)
    	return plainText;
    }
}
