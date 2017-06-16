import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Paillier
{

    // k2 controls the error probability of the primality testing algorithm
    // (specifically, with probability at most 2^(-k2) a NON prime is chosen).
    private static int k2 = 40;
    private static int kappa = 100;

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
        System.out.println("Key P: " + p);
        System.out.println("Key Q: " + q);
        
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
    
    //Step 2: According to Shamet
    public static BigInteger computeZ(BigInteger CipherTextA, BigInteger CipherTextB, PrivateKey sk)
    {
    	BigInteger Two = new BigInteger("2");
    	BigInteger Z = Two.pow(sk.k1).multiply(CipherTextA).multiply(CipherTextB);
    	return Z;
    }
    
    //Step 3: According to Shamet
    public static BigInteger computeD(BigInteger Z, PublicKey pk)
    {
    	//Generate Random and encrypt it
    	BigInteger random = new BigInteger((pk.k1+kappa+1), k2, rnd);
    	BigInteger encryptedRandom = Paillier.encrypt(random, pk);
    	//D = [Z + Random]
    	BigInteger D=Paillier.add(Z, encryptedRandom, pk);
    	//Rehash D
    	D=Paillier.add(D, Paillier.encrypt(BigInteger.ZERO, pk), pk);
    	return D;
    }
    
    //Step 5: According to Shamet
    public static BigInteger computeZPrime(BigInteger D,PublicKey pk)
    {
    	//Generate Random and encrypt it
    	BigInteger random = new BigInteger((pk.k1+kappa+1), k2, rnd);
    	BigInteger encryptedRandom = Paillier.encrypt(random, pk);
    	
    	BigInteger modulus = new BigInteger("2");
    	modulus.pow(pk.k1);
    	//z=d(mod 2^l)
    	BigInteger Zprime=D.mod(modulus);
    	//( rrandom(mod 2^L) )^-1
    	
    	Zprime=Zprime.multiply(encryptedRandom.modInverse(modulus));
    	return Zprime;
    	 /*
    	 Bob must obtain an encryption [Lambda] by a binary value
    	 containing the result by the comparison of two private inputs:
    	 dprime = d (mod 2^l) held by Alice (user)
    	 rprime = r (mod 2^l) held by Bob (Database) 
    	 */
    }
    
    
    
    //Step 9: According to Shamet
    public static int chooseS()
    {
    	//select a random number from 1 to 100.
    	int draw = (int)Math.ceil((Math.random()*100));
    	int S;
    	if (draw%2==0)
    	{
    		S=1;
    	}
    	else
    	{
    		S=-1;
    	}
    	return S;
    }
    //Step 10
    //public static BigInteger DGKCompare()
    //{
    	//BigInteger compareCipher;
    	//for (int i=0;i<10;i++)
    	//{
    		//compareCipher.multiply(val);
    	//}
    	//return compareCipher;
    //}
    //Step 12 and Step 13
    public static BigInteger computeEi(BigInteger CipherText, PublicKey pk)
    {
    	//r_i belongs to z_u.  In this case u = 7
    	BigInteger random = new BigInteger(7, k2, rnd);
    	CipherText.pow(random.intValue());
    	
    	//Step 13: Re-randomize and permates the encryptions [[e_i]]
    	//and send them to Alice (user)
    	
    	return CipherText;
    }
    
    //Step 15
    public boolean LambdaSign(int S)
    {
    	if (S==1)
    	{
    		return true;
    		/*
    		 * 	[Lambda] = [-Lambda Prime]
    		 */
    	}
    	else
    	{
    		return false;
    		/*
    		 * 	[Lambda] = [Lambda Prime]
    		 */
    	}
    }
    
    //Step 16
    
    /*
    is z(mod 2^L) = Z prime * [Lambda]^(2l)
     */
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
    	BigInteger EncC = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    	ArrayList<BigInteger> rand = GenerateRandom();
    	//ArrayList.get(0) holds r
    	//ArrayList.get(1) holds r'
    	EncC = Paillier.multiply(EncC, rand.get(0), pk);
    	EncC = Paillier.subtract(EncC, rand.get(1), pk);
    	//Currently, Enc(c) = Enc(r(x_0 - x_1) - r')
    	//Section 2.1.1 Step 1 Complete!
    	
    	//Step 2: party x_1 sends Enc(c), Enc(1), Enc(0)
    	//Since this is done at the server...I will just encrypt a 1 and 0
    	BigInteger EncOne = Paillier.encrypt(BigInteger.ONE, pk);
    	BigInteger EncZero = Paillier.encrypt(BigInteger.ZERO, pk);
    	
    	//Flip a coin for b_i 
    	Random randomNum = new Random();
    	int b_i = randomNum.nextInt(2);
    	
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
    	
    	ArrayList<BigInteger> moreRandom = GenerateRandom();
    	//r is in index 0, and r' is in index 1
    	//part 1: a_3 = E(C)^[-1^b_i*r_i]
    	BigInteger a_3= Paillier.multiply(EncC, new BigInteger("-1").pow(b_i).multiply(moreRandom.get(0)), pk);
    	//part 2: part 1 (Encrpyted) * E( (-1)^[b_i-1]*r' )
    	BigInteger temp;
    	if (b_i == 1)//Then b_1-1 = 0, meaning 1
    	{
    		temp = new BigInteger("1");
    	}
    	else
    	{
    		temp = new BigInteger("-1");
    	}
    	temp = temp.multiply(moreRandom.get(1));
    	temp = Paillier.encrypt(temp, pk);
    	a_3= Paillier.add(EncC, temp, pk);
    	
    	//Step 4:
    	//1 = True
    	//0 = False
    	
    	/*
    	 * 	To compute which value is the smallest I must do
    	 *  Enc(x_1 + [x_0 <= x_1](x_0 - x_1) )
    	 */
    	BigInteger small = null;
    	if (a_3.signum()==-1)
    	{
    		//if a_3 is negative then a_1 holds the boolean 
    		//of x_0 <= x_1, Compute smallest using above formula
    		BigInteger decrypta_1 = Paillier.decrypt(a_1, sk);
    		System.out.println("a_3 decrypted is: "+decrypta_1);
    		small = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    		small = Paillier.multiply(small, decrypta_1, pk);
    		System.out.println(Paillier.decrypt(small, sk));
    		small = Paillier.add(ciphertextx1, small, pk);
    		System.out.println(Paillier.decrypt(small, sk));
    	}
    	else
    	{
    		//if a_3 is positive then a_2 holds the boolean
    		//of x_0 <= x_1
    		BigInteger decrypta_2 = Paillier.decrypt(a_2, sk);
    		System.out.println("a_2 decrypted is: "+decrypta_2);
    		small = Paillier.subtract(ciphertextx0, ciphertextx1, pk);
    		small = Paillier.multiply(small, decrypta_2, pk);
    		System.out.println(Paillier.decrypt(small, sk));
    		small = Paillier.add(ciphertextx1, small, pk);
    		System.out.println(Paillier.decrypt(small, sk));
    	}
    	return small;
    }
    
    public static BigInteger reRandomize(BigInteger ciphertext, PublicKey pk)
    {
    	return Paillier.add(ciphertext, BigInteger.ZERO, pk);
    }
    
    public static ArrayList<BigInteger> GenerateRandom()
    {
    	ArrayList<BigInteger> random = new ArrayList<BigInteger>();
    	//ArrayList.get(0) holds r
    	BigInteger r;
    	do
    	{
    		r = new BigInteger(100,rnd);
    		random.add(r);
    	}
    	while(r.bitLength()==100);
    	//ArrayList.get(1) holds r'
    	//r' < r
    	do
    	{
    		r = new BigInteger(30, rnd);
    		random.add(r);
    	}
    	while(r.bitLength()==30);
    	
    	return random;
    }
    
    public static void main (String [] args)
    {
    	/*
    	 FROM LOCALIZE ACTIVITY
sk = new PrivateKey(1024);
pk = new PublicKey();
Paillier.keyGen(sk, pk);

PALLIER IS ON LOCALIZED EUCLIDEAN DETAIL but commented out
    	 */
    	
    	PrivateKey sk = new PrivateKey(1024);
    	PublicKey pk = new PublicKey();
    	Paillier.keyGen(sk,pk);
    	
    	//BigInteger Cipher = Paillier.encrypt(messageTwo, pk);
    	//System.out.println("Cipher Text of Message: " + Cipher);
    	//System.out.println(Paillier.decrypt(Cipher,sk));
    	
    	/*
    	 * 	Read the Array:
    	 * 	It should contain
    	 * 	1;2;3...;98;201
    	 */
    	String [] messages=null;
    	String filelocation= "C:\\Users\\Andrew\\Desktop\\REU.txt";
    	
    	try
    	{
    	 	BufferedReader br = new BufferedReader(
    				new InputStreamReader(
    				new FileInputStream(filelocation)));
    	 	String line = br.readLine();
    	 	messages=line.split(";");
    	 	br.close();
    	}//end of try
    	catch (IOException ioe)
    	{
    		System.out.println("Error at reading array");
    	}
    	
    	BigInteger boklan [] = new BigInteger[messages.length];
    	
    	/*
    	 * 	First Test...
    	 * 	Test enc(1) to enc (201)
    	 * 	Do I always get a positive signum?
    	 */
    	
    	for (int i=0;i<messages.length;i++)
    	{
    		boklan [i] = Paillier.encrypt(new BigInteger(messages[i]),pk);
    	}
    	
    	/*
    	 * 	Writer...
    	 * 	Make CSV
    	 * 
    	 * 	Be sure to print 
    	 * 	- Lambda
    	 * 	- N, p, q
    	 * 	3 Columns
    	 * 	Primary Key, Message, Cipher
    	 */
    	String FileOut="C:\\Users\\Andrew\\Desktop\\REU2.txt";
    	try 
		{
    		PrintWriter pw = new PrintWriter(
						 new BufferedWriter(
						 new OutputStreamWriter(
						 new FileOutputStream(FileOut))));
			pw.println("Value of N: " + pk.n);
			pw.println("Value of Mod: " + pk.modulous);
			pw.println("Value of LAMDA: " + sk.lambda);
			
			//Print the Rows...
			for (int i=0;i<boklan.length;i++)
			{
				pw.println(messages[i]+","+boklan[i]);
			}
			pw.flush();
			pw.close();
		}
    	catch (IOException ioe)
    	{
    		System.out.println("CRAP!");
    	}
    	
    	BigInteger testa = Paillier.encrypt(new BigInteger("50"),pk);
    	BigInteger testb = Paillier.encrypt(new BigInteger("10"), pk);
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
    	
    	BigInteger enc = Paillier.encrypt(new BigInteger("100"), pk);//Positive
    	BigInteger enc2 = Paillier.encrypt(new BigInteger("100"), pk);//Positive
    	enc2=Paillier.multiply(enc2, -1, pk);
    	BigInteger answer = Paillier.add(enc, enc2, pk);
    
    	//TEST ANSWER SIGNUM
    	
    	//answer = Paillier.add(answer, encryptedN, pk);
    	
    /*
     * 	CompareTo enc(0), sometimes correct, sometimes NOT...	
     */
    	System.out.println("Paillier answer: " + Paillier.decrypt(answer, sk));

    }//END OF MAIN
}//END OF CLASS