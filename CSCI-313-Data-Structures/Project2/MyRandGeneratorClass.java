import java.util.*;

public class MyRandGeneratorClass
{
	Random rnd = null;

	// initializing the random class using current time
	// if always use the same seed (eg 100), you will get the same random sequence
	// this is useful when you want to observe a same bug again
	public MyRandGeneratorClass(long seed)
	{
		rnd = new Random(seed);
	}
	
	// initializing the random class using current time
	// this is useful when you want to generate complete random sequence
	// so you could thoroughly debug your code
	public MyRandGeneratorClass()
	{
		rnd = new Random(System.currentTimeMillis());
	}
	
	// generating a random integer
	// lb/ub -- lowerbound and upperbound
	public int GenerateRandInt(int lb, int ub) throws IllegalArgumentException
	{	
		if(lb>=ub) throw new IllegalArgumentException("Wrong input for random generator.");
		return rnd.nextInt(ub-lb+1) + lb ;
	}
		
	// generating a random integer sequence,
	// len -- length
	// lb/ub -- lowerbound and upperbound of each entry
	public ArrayList<Integer> GenerateRandIntSequence(int len, int lb, int ub) throws IllegalArgumentException
	{	
		if(lb>=ub) throw new IllegalArgumentException("Wrong input for random generator.");

		ArrayList<Integer> ret = new ArrayList<>();
		for(int i = 0; i < len; ++i)
		{
			ret.add( rnd.nextInt(ub-lb+1) + lb );
			// this generates a random number between lb and ub, think about why
		}
		return ret;
	}
	
	// generating a random integer sequence (without repeat)
	// len -- length
	// lb/ub -- lowerbound and upperbound of each entry
	public ArrayList<Integer> GenerateRandDistinctIntSequence(int len, int lb, int ub) throws IllegalArgumentException
	{
		
		if(lb>=ub) throw new IllegalArgumentException("Wrong input for random generator.");

		ArrayList<Integer> ret = new ArrayList<>();
		TreeSet<Integer> generatedSet = new TreeSet<>();
		while(ret.size() < len)
		{
			int tmpi = rnd.nextInt(ub-lb+1) + lb;
			if(generatedSet.add(tmpi))	// add only if it does not exist in the map
				ret.add( tmpi );
		}
		return ret;
	}
	
	// generating a random double sequence,
	// len -- length
	// lb/ub -- lowerbound and upperbound of each entry
	public ArrayList<Double> GenerateRandDoubleSequence(int len, double lb, double ub) throws IllegalArgumentException
	{
		
		if(lb>=ub) throw new IllegalArgumentException("Wrong input for random generator.");

		ArrayList<Double> ret = new ArrayList<>();
		for(int i = 0; i < len; ++i)
		{
			ret.add( rnd.nextDouble() * (ub-lb) + lb );
			// this generates a random double between lb and ub, think about why
		}
		return ret;
	}

	// generate a random binary number, 
	// with a specified probability (sparsity) of having one
	// think about how to extend this to multiple values
	// for example: generate numbers of 0, 1, or 2 using probability 0.2, 0.3, or 0.5
	// this is an interview question for data science
	public int GenerateBinary(double sparsity)
	{
		if(sparsity < 0.0) throw new IllegalArgumentException("Wrong input for random generator.");
		if(sparsity > 1.0) throw new IllegalArgumentException("Wrong input for random generator.");
		double mydb = rnd.nextDouble();
		if(mydb <= sparsity)
			return 1;
		else
			return 0;
	}
	
	// generating a binary sequence,
	// len -- length
	// sparsity -- ratio of 1's
	public ArrayList<Integer> GenerateBinarySequence(int len, double sparsity)
									 throws IllegalArgumentException
	{
		
		if(sparsity < 0.0) throw new IllegalArgumentException("Wrong input for random generator.");
		if(sparsity > 1.0) throw new IllegalArgumentException("Wrong input for random generator.");

		ArrayList<Integer> ret = new ArrayList<>();
		for(int i = 0; i < len; ++i)
			ret.add( GenerateBinary(sparsity) );
		return ret;
	}
}
