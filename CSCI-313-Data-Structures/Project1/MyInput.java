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
	
		LLSparseM tiger= new LLSparseM(5,5);
		tiger.setElement(1, 1, 4);
		tiger.setElement(2, 2, 4);
		tiger.setElement(3, 3, 15);
		tiger.setElement(1, 4, 14);
		tiger.setElement(3, 2, 42);
		
		LLSparseM lion= new LLSparseM(5,5);
		lion.setElement(1, 1, 4);//Row,Column,Value
		lion.setElement(1, 2, 30);
		lion.setElement(1, 3, 15);
		lion.setElement(3, 2, 10);
		lion.setElement(3, 4, 50);
		lion.setElement(4, 1, 55);
		lion.setElement(0, 1, 34);
		/* 
		 * 	int[] ridx_list = lion.getRowIndices();
		System.out.println("NUM_ROWS "+ridx_list.length);
		for(int ridx : ridx_list)
		{
			System.out.println("ROW "+ridx);
			int[] one_row_cidx_list = lion.getOneRowColIndices(ridx);
			int[] one_row_vals_list = lion.getOneRowValues(ridx);
			for(int i = 0; i < one_row_cidx_list.length; ++i)
				System.out.println("RIDX "+ridx+" CIDX " + one_row_cidx_list[i] + " VAL " + one_row_vals_list[i]);
		}
		System.out.println("Row test: CHECK!");
		 */
		// print the matrix in rows
	
		//Column Test YOU FAILED HERE BIG TIME ANDREW!!!
		lion.printAllNodesByColumn();
		int[] cidx_list = lion.getColIndices();
		System.out.println("NUM_COLS "+cidx_list.length);
		for(int cidx : cidx_list)
		{
			System.out.println("COL "+cidx);
			int[] one_col_ridx_list = lion.getOneColRowIndices(cidx);
			int[] one_col_vals_list = lion.getOneColValues(cidx);
			for(int i = 0; i < one_col_ridx_list.length; ++i)
				System.out.println("RIDX "+one_col_ridx_list[i]+" CIDX " + cidx + " VAL " + one_col_vals_list[i]);
		}
	}
}