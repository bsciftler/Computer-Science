import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Paillier
{
    // k2 controls the error probability of the primality testing algorithm
    // (specifically, with probability at most 2^(-k2) a NON prime is chosen).
    private static int k2 = 40;

    private static Random rnd = new Random();

    public static void keyGen(PrivateKey sk, PublicKey pk)
    {
        // bit_length is set as half of k1 so that when pq is computed, the
        // result has k1 bits
    	
    	//Passing in PrivateKey(1,024)
        int bit_length = sk.k1 / 2;//should be getting 512 here...
        System.out.println("Bit length: " + bit_length);
        System.out.println("Random: " + rnd);
        System.out.println("End of Random");
        
        // Chooses a random prime of length k2. The probability that p is not
        // prime is at most 2^(-k2)
        BigInteger p = new BigInteger(bit_length, k2, rnd);//(512,40,random)
        BigInteger q = new BigInteger(bit_length, k2, rnd);//(512,40,random)
        //BigInteger (bit_length,uncertainty,random)...Create something size 512 bits
        //that is probably a prime is >= (1-.5^40)
        //System.out.println("Key P: " + p);
        //System.out.println("Key Q: " + q);
        
        //Modifications to the public key
        pk.k1 = sk.k1;
        pk.n = p.multiply(q); // n = pq
        pk.modulous = pk.n.multiply(pk.n); // modulous = n^2

        //Modifications to the Private key
        sk.lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        sk.mu = sk.lambda.modInverse(pk.n);
        sk.n = pk.n;
        sk.modulous = pk.modulous;
    }

    // Compute ciphertext = (mn+1)r^n (mod n^2) in two stages: (mn+1) and (r^n).
    public static BigInteger encrypt(BigInteger plaintext, PublicKey pk)
    {
        BigInteger randomness = new BigInteger(pk.k1, rnd); // creates
        // randomness of
        // length k1
        BigInteger tmp1 = plaintext.multiply(pk.n).add(BigInteger.ONE).mod(pk.modulous);
        BigInteger tmp2 = randomness.modPow(pk.n, pk.modulous);
        BigInteger ciphertext = tmp1.multiply(tmp2).mod(pk.modulous);

        return ciphertext;
    }
    
    public static BigInteger encrypt(BigInteger plaintext, PrivateKey sk)
    {
        BigInteger randomness = new BigInteger(sk.k1, rnd); // creates
        // randomness of
        // length k1
        BigInteger tmp1 = plaintext.multiply(sk.n).add(BigInteger.ONE).mod(sk.modulous);
        BigInteger tmp2 = randomness.modPow(sk.n, sk.modulous);
        BigInteger ciphertext = tmp1.multiply(tmp2).mod(sk.modulous);

        return ciphertext;
    }

    // Compute plaintext = L(cipherText^(lambda) mod n^2) * mu mod n
    public static BigInteger decrypt(BigInteger ciphertext, PrivateKey sk)
    {
        BigInteger plaintext = L(ciphertext.modPow(sk.lambda,
        		sk.modulous), sk.n).multiply(sk.mu).mod(sk.n);
        // L(u)=(u-1)/n
        return plaintext;
    }

    // On input two encrypted values, returns an encryption of the sum of the
    // values
    public static BigInteger add(BigInteger ciphertext1, BigInteger ciphertext2, PublicKey pk)
    {
        BigInteger ciphertext = ciphertext1.multiply(ciphertext2).mod(pk.modulous);
        //(Cipher1 * Cipher 2 (mod N)
        return ciphertext;
    }
    
    public static BigInteger subtract(BigInteger ciphertext1, BigInteger ciphertext2, PublicKey pk)
    {
    	ciphertext2= Paillier.multiply(ciphertext2, -1, pk);
    	BigInteger ciphertext = ciphertext1.multiply(ciphertext2).mod(pk.modulous);
    	return ciphertext;
    }

    // On input an encrypted value x and a scalar c, returns an encryption of
    // cx.
    public static BigInteger multiply(BigInteger ciphertext1, BigInteger scalar, PublicKey pk)
    {
    	//CITATION: https://crypto.stackexchange.com/questions/17862/paillier-can-add-and-multiply-why-is-it-only-partially-homomorphic
    	//NOTE: scalar is NOT encrypted
    	while (scalar.signum()==-1)
    	{
    		scalar = scalar.add(pk.modulous);
    	}
        BigInteger ciphertext = ciphertext1.modPow(scalar, pk.modulous);
        //Paillier.ciphertext = (mn+1)r^n (mod n^2)
        //cipher^(scalar)(mod N)
        return ciphertext;
    }

    public static BigInteger multiply(BigInteger ciphertext1, int scalar, PublicKey pk)
    {
    	//NOTE: SCALAR IS NOT ENCRYPTED!
    	//CITATION: https://crypto.stackexchange.com/questions/17862/paillier-can-add-and-multiply-why-is-it-only-partially-homomorphic
        return multiply(ciphertext1, BigInteger.valueOf(scalar), pk);
    }

    public static void display(BigInteger c, PrivateKey sk)
    {
        BigInteger tmp = decrypt(c, sk);
        byte[] content = tmp.toByteArray();
        System.out.print("Ciphertext contains " + content.length + " bytes of the following " +
                "plaintext:");
        for (int i = 0; i < content.length; i++)
            System.out.print((0xFF & content[i]) + " ");
        System.out.println();
    }

    // L(u)=(u-1)/n
    private static BigInteger L(BigInteger u, BigInteger n)
    {
        return u.subtract(BigInteger.ONE).divide(n);
    }
    
    public static BigInteger computeDistance(ArrayList<BigInteger> fingerPrint, ArrayList<BigInteger> incoming, PublicKey pk)
    {
    	/*
    	 * 	This computes distance for two vectors
    	 */
    	/*
    	 *  let y be new input...
    	 *  compare it with distance to x1, x2, x3, etc.
    	 */
    	BigInteger temp=null;
    	BigInteger distance=null;
    	if (fingerPrint.size()!=incoming.size())
    	{
    		System.err.println("VECTORS NOT SAME SIZE!!");
    	}
    	
    	for (int i=0;i<fingerPrint.size();i++)
    	{
    		temp = Paillier.subtract(fingerPrint.get(i),incoming.get(i),pk);
    		temp=temp.multiply(temp);
    		distance= distance.add(temp);
    	}
    	return distance;
    }
    
    /*
     * 	This method was created using this paper as my guide:
     * 	http://www.fkerschbaum.org/bps09a.pdf
     */
    public static BigInteger isSuperiorTo(BigInteger ciphertextx0, BigInteger ciphertextx1, PublicKey pk, PrivateKey sk)
    {
    	int rBit = 200;
    	int rPrimeBit = 100;
    	BigInteger EncC = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    	
    	EncC = Paillier.multiply(EncC, GenerateRandom(rBit), pk);
    	EncC = Paillier.subtract(EncC, Paillier.encrypt(GenerateRandom(rPrimeBit), pk), pk);
    	//Currently, Enc(c) = Enc(r(x_0 - x_1) - r')
    	//Section 2.1.1 Step 1 Complete!
    	
    	//Step 2: party x_1 sends Enc(c), Enc(1), Enc(0)
    	//Since this is done at the server...I will just encrypt a 1 and 0
    	BigInteger EncOne = Paillier.encrypt(BigInteger.ONE, pk);
    	BigInteger EncZero = Paillier.encrypt(BigInteger.ZERO, pk);
    	
    	//Flip a coin for b_i 
    	Random randomNum = new Random();
    	int b_i = randomNum.nextInt(2);
    	System.out.println("B_i value: " + b_i);//If 1 BAD, if 0 GOOD??s
    	
    	System.out.println(Paillier.decrypt(EncC, sk));
    	EncC    = Paillier.reRandomize(EncC, pk);
    	EncOne  = Paillier.reRandomize(EncOne, pk);
    	EncZero = Paillier.reRandomize(EncZero, pk);
    	System.out.println(Paillier.decrypt(EncC, sk));
    	//Step 3B: Compute a_1, a_2, a_3
    	BigInteger a_1=null;
    	BigInteger a_2=null;
    	if (b_i==1)
    	{
    		a_1=EncZero.multiply(Paillier.encrypt(BigInteger.ZERO, pk));
    		a_2=EncOne.multiply(Paillier.encrypt(BigInteger.ZERO, pk));
    	}
    	else if (b_i==0)
    	{
    		a_1=EncOne.multiply(Paillier.encrypt(BigInteger.ZERO, pk));
    		a_2=EncZero.multiply(Paillier.encrypt(BigInteger.ZERO, pk));
    	}
    	
    	//r is in index 0, and r' is in index 1
    	
    	//part 2: part 1 (Encrypted) * E( (-1)^[b_i-1]*r' )
    	BigInteger temp;
    	BigInteger a_3 = null;
    	if (b_i == 1)//Then b_1-1 = 0, meaning (-1)^0=1
    	{
    		temp = GenerateRandom(rPrimeBit);
    		
    		//part 1: a_3 = E(C)^[-1^b_i*r_i]
    		a_3= Paillier.multiply(EncC,GenerateRandom(rBit).multiply(new BigInteger("-1")), pk);
    		//System.out.println(Paillier.decrypt(EncC, sk));
    	}
    	else//(-1)^1
    	{
    		temp = GenerateRandom(rPrimeBit).multiply(new BigInteger("-1"));
    		//part 1: a_3 = E(C)^[-1^b_i*r_i]
    		a_3= Paillier.multiply(EncC, GenerateRandom(rBit), pk);
    		//System.out.println(Paillier.decrypt(EncC, sk));
    	}
    	temp = Paillier.encrypt(temp, pk);
    	System.out.println("temp at Line 222: "+temp);
    	System.out.println("a_3 at Line 223 : " + a_3);//Why is it 0 here?
    	
    	a_3= Paillier.add(a_3, temp, pk);
    	
    	//Step 4:
    	//1 = True
    	//0 = False
    	
    	/*
    	 * 	To compute which value is the smallest I must do
    	 *  Enc(x_1 + [x_0 <= x_1](x_0 - x_1) )
    	 */
    	BigInteger small = null;
    	System.out.println("Value of a_3: " + a_3);
    	BigInteger decrypta_3=Paillier.decrypt(a_3, sk);
    	
    	if (decrypta_3.signum()==-1)
    	{
    		//if a_3 is negative then a_1 holds the boolean 
    		//of x_0 <= x_1, Compute smallest using above formula
    		BigInteger decrypta_1 = Paillier.decrypt(a_1, sk);
    		BigInteger guess = Paillier.subtract(a_1, Paillier.encrypt(BigInteger.ONE, pk), pk);
    		System.out.println("sig: "+guess.signum());
    		
    		System.out.println("a_1 decrypted is: "+decrypta_1);
    		small = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    		small = Paillier.multiply(small, decrypta_1, pk);
    		System.out.println("value of small 1: "+Paillier.decrypt(small, sk));
    		small = Paillier.add(ciphertextx1, small, pk);
    		System.out.println("value of small 2: "+Paillier.decrypt(small, sk));
    	}
    	else
    	{
    		//if a_3 is positive then a_2 holds the boolean
    		//of x_0 <= x_1
    		BigInteger decrypta_2 = Paillier.decrypt(a_2, sk);
    		BigInteger guess = Paillier.subtract(a_1, Paillier.encrypt(BigInteger.ONE, pk), pk);
    		System.out.println("sig: "+guess.signum());
    		
    		System.out.println("a_2 decrypted is: "+decrypta_2);
    		small = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    		small = Paillier.multiply(small, decrypta_2, pk);
    		System.out.println("value of small 1: "+Paillier.decrypt(small, sk));
    		small = Paillier.add(ciphertextx1, small, pk);
    		System.out.println("value of small 2: "+Paillier.decrypt(small, sk));
    	}
    	return small;
    }
    
    public static BigInteger reRandomize(BigInteger ciphertext, PublicKey pk)
    {
    	return Paillier.add(ciphertext, Paillier.encrypt(BigInteger.ZERO,pk), pk);
    }
    
    public static BigInteger GenerateRandom(int bitSize)
    {
    	BigInteger r;
    	do
    	{
    		r = new BigInteger(bitSize, rnd);
    	}
    	while(r.bitLength()!=bitSize);
    	return r;
    }
    
    public static void main (String [] args)
    {
    	PrivateKey sk = new PrivateKey(1024);
       	PublicKey pk = new PublicKey();
       	Paillier.keyGen(sk,pk);
       	
    	BigInteger testa = Paillier.encrypt(new BigInteger("100"),pk);
    	BigInteger testb = Paillier.encrypt(new BigInteger("145"), pk);
    	//System.out.println("The smallest is: " + testa + " " + testb);
    	
    	//testa <= testb
    	BigInteger small = Paillier.isSuperiorTo(testa, testb, pk, sk);
    	small = Paillier.decrypt(small, sk);
    	System.out.println("The smallest is: " +small);
    	
    	/*
    	 * 
    	 	if (enc.equals(enc2))
    	{
    		System.out.println("Same Hash!");
    	}
    	//Question 2A:
    	if (Paillier.add(test2, enczero, pk).equals(test2))
    	{
    		System.out.println("Preserved x + 0");
    	}
    	//Question 2B:
    	BigInteger dummy = Paillier.add(test2,encone,pk);
    	BigInteger dummy2 = Paillier.multiply(encone, new BigInteger("-1"), pk);
    	if (Paillier.add(dummy, dummy2, pk).equals(test2))
    	{
    		System.out.println("Preserved x + 1 - 1");
    	}
    	//Question 3:
    	if(Paillier.multiply(test2, BigInteger.ZERO, pk).equals(Paillier.encrypt(new BigInteger("0"), pk)))
    	{
    		System.out.println("Question 3 is true.");
    	}
    	 */
    	
//    	BigInteger enc = Paillier.encrypt(new BigInteger("100"), pk);//Positive
//    	BigInteger enc2 = Paillier.encrypt(new BigInteger("100"), pk);//Positive
//    	enc2=Paillier.multiply(enc2, -1, pk);
//    	BigInteger answer = Paillier.add(enc, enc2, pk);
    
    	//TEST ANSWER SIGNUM
    	
    	//answer = Paillier.add(answer, encryptedN, pk);
    	
    /*
     * 	CompareTo enc(0), sometimes correct, sometimes NOT...	
     */
    	//System.out.println("Paillier answer: " + Paillier.decrypt(answer, sk));

    }//END OF MAIN
}//END OF CLASS