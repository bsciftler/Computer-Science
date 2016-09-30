public class MyInput 
{
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
		getRandom();
		//lion.setElement(getRandom(), getRandom());
		System.out.println("TIGER LL");
		tiger.print();
		System.out.println("LION LL");
		lion.print();
		System.out.println("Fusion");
		liger.print();
	*/
		
	//Sparse Matrix Test 	
		//NOTE THE REPLACE DOES NOT WORK!!
		
		LLSparseM lion= new LLSparseM(1,10);//Row/Column
		lion.setElement(1, 5, 4);//Row,Column,Value
		lion.setElement(1, 2, 30);
		//lion.setElement(2, 3, 10);
		System.out.println("Row Head RowID: "+lion.getRowHead().getRowID());//ROW HEAD, ROW ID
		System.out.println("Column Head Column ID: "+lion.getColumnHead().getColumnID());//COLUMN HEAD, COLUMN ID
		//System.out.println("Second RowID: "+lion.getRowHead().getNextRow().getRowID());//ROW HEAD, ROW ID
		System.out.println("Second Column ID: "+lion.getColumnHead().getNextColumn().getColumnID());//ROW HEAD, ROW ID
		System.out.println("Column Travel:");
		System.out.println("Node 1: " + lion.getColumnHead().getNextElement().getValue());
		System.out.println("Node 2: " + lion.getColumnHead().getNextColumn().getNextElement().getValue());
		System.out.println("Row Travel:");
		System.out.println("Node 1: " + lion.getRowHead().getNextElement().getValue());
		System.out.println("Node 2: " + lion.getRowHead().getNextElement().getNextColumn().getValue());
		System.out.println("Print by Row:");
		lion.printRow();
	}
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*30);
	}
}