public class MyInput 
{
	public static void main (String [] args)
	{
		LLSparseVec tiger=new LLSparseVec(10);
		LLSparseVec lion = new LLSparseVec(10);
		LLSparseVec liger=null;
//UPLOAD
		//MYINPUT 
		//LLSPARSEVECNODE
		//LLSPARSEVEC	
		
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
		getRandom();
		//lion.setElement(getRandom(), getRandom());
		System.out.println("TIGER LL");
		tiger.print();
		System.out.println("LION LL");
		lion.print();
		System.out.println("Fusion");
		liger.print();
	/* SPARSE MATRIX TEST
	 * 	
		LLSparseM lion= new LLSparseM(1,10);//Row/Column
		lion.setElement(1, 2, 4);//Row,Column,Value
		lion.setElement(1, 2, 30);//WHY IS IT NOT BEING REPLACED???
		System.out.println(lion.getRowHead().getRowID());//ROW HEAD, ROW ID
		System.out.println(lion.getColumnHead().getColumn());//COLUMN HEAD, COLUMN ID
		System.out.println(lion.getColumnHead().getNextRow().getValue());
		lion.print();
	*/
	}
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*30);
	}
}