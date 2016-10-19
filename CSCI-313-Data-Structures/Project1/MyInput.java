public class MyInput 
{
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*2);
	}
	
	
	
	public static void main (String [] args)
	{
		/*
		LLSparseVec tiger=new LLSparseVec(10);
		LLSparseVec lion = new LLSparseVec(10);
	
			tiger.setElement(getRandom(), getRandom());
			tiger.setElement(getRandom(), getRandom());
			tiger.setElement(getRandom(), getRandom());
			tiger.setElement(getRandom(), getRandom());
			tiger.setElement(getRandom(), getRandom());
			tiger.setElement(getRandom(), getRandom());
			
			
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			
			LLSparseVec liger=tiger.multiplication(lion);
		
		int [] Val=lion.getAllValues();
		int [] IDX=lion.getAllIndices();
		int VecSize=lion.getLength();
		int VecValSize=lion.numElements();
		System.out.println("Sparse Vector Lion has Length: " + VecSize + " and " + VecValSize + " non zero elements");
		for (int i=0;i<VecValSize;i++)
		{
			System.out.println("Index: " + IDX[i]);
			System.out.println("Value: " + Val[i]);
		}
		
		System.out.println("TIGER LL");
		tiger.print();
		System.out.println("LION LL");
		lion.print();
		System.out.println("Fusion");
		liger.print();
		*/
		LLSparseM Bread=new LLSparseM(3,3);
		LLSparseM Butter=new LLSparseM(3,3);
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(getRandom(), getRandom(), getRandom());
		Bread.setElement(0, 0, 5);
		Bread.setElement(getRandom(), getRandom(), getRandom());
		System.out.println("BREAD");
		Bread.printAllNodesByRow();
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(0, 0, 5);
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(getRandom(), getRandom(), getRandom());
		Butter.setElement(getRandom(), getRandom(), getRandom());
		System.out.println("BUTTER");
		Butter.printAllNodesByRow();
		LLSparseM Yum=(LLSparseM) Bread.addition(Butter);
		System.out.println("Combined");
		Yum.printAllNodesByRow();
	}
}