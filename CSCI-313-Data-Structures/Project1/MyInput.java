public class MyInput 
{
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*5);
	}
	
	public static void main (String [] args)
	{
	/*
		LLSparseVec tiger=new LLSparseVec(10);
		LLSparseVec lion = new LLSparseVec(10);
		LLSparseVec liger=null;
		try 
		{
			tiger.setElement(19, 20);
			tiger.setElement(12, 13 );
			tiger.setElement(7, 20);
			tiger.setElement(13, 35);
			tiger.setElement(17, 50);
			//tiger.setElement(getRandom(), getRandom());
			
			
			lion.setElement(100, 62);
			//lion.setElement(19, 20);
			//lion.setElement(53,30);
			//lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			lion.setElement(getRandom(), getRandom());
			//lion.setElement(getRandom(), getRandom());
			//lion.setElement(getRandom(), getRandom());
			//lion.setElement(getRandom(), getRandom());
			
			liger=tiger.multiplication(lion);
		}
		catch (VectorException e)
		{
			e.printStackTrace();
		}
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
	
		//Sparse Matrix Test 	
		LLSparseM tiger= new LLSparseM(5,5);
		tiger.setElement(1, 1, 4);
		tiger.setElement(2, 2, 4);
		tiger.setElement(3, 3, 15);
		tiger.setElement(1, 4, 14);
		tiger.setElement(4, 5, 42);
		
		LLSparseM lion= new LLSparseM(5,5);
		lion.setElement(1, 1, 4);//Row,Column,Value
		lion.setElement(1, 2, 30);
		lion.setElement(1, 3, 15);
		lion.setElement(3, 2, 10);
		lion.setElement(3, 5, 50);
		lion.setElement(4, 1, 55);
		lion.setElement(5, 1, 34);
//int [] RowID=lion.getRowIndices();
//int [] ColID=lion.getColIndices();
//int [] OneRowColID=lion.getOneRowColIndices(2);
//int [] OneRowVal=lion.getOneRowValues(3);
//int [] OneColRowID=lion.getOneColRowIndices(1);
//int [] OneColVal=lion.getOneColValues(1);
		System.out.println("Peanut Butter");
		//tiger.info();
		//tiger.printAllNodesByRow();
		tiger.printMatrix();
		System.out.println("Jelly");
		//lion.info();
		//lion.printAllNodesByRow();
		lion.printMatrix();
		//lion.printAllNodesByColumn();
		//tiger.printAllNodesByColumn();
		LLSparseM liger=null;
/* 
 * Progress Report:
 * Looking good! I am almost done!!
 */
		
	liger = (LLSparseM) tiger.substraction(lion);
	System.out.println("Peanut Butter and Jelly Sandwich");
	//liger.info();
	//liger.printAllNodesByRow();
	liger.printMatrix();
	}
}