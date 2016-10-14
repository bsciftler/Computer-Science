public class MyInput 
{
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*10);
	}
	
	public static void main (String [] args)
	{

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
		lion.setElement(getRandom(), getRandom());
		LLSparseVec liger=tiger.multiplication(lion);
		
		int [] Val=lion.getAllValues();
		int [] IDX=lion.getAllIndices();
		
		for (int i=0;i<lion.numElements();i++)
		{
			System.out.println("Index: " + IDX[i]);
			System.out.println("Value: " + Val[i]);
		}

		System.out.println("TIGER LL");
		tiger.info();
		tiger.print();
		System.out.println("LION LL");
		lion.info();
		lion.print();
		System.out.println("Fusion");
		liger.info();
		liger.print();

	
		//Sparse Matrix Test 	
		
		/*
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
		
	liger = (LLSparseM) tiger.substraction(lion);
	System.out.println("Peanut Butter and Jelly Sandwich");
	//liger.info();
	//liger.printAllNodesByRow();
	liger.printMatrix();
	*/
	}
}