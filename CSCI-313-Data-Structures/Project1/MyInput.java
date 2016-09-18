
public class MyInput 
{
	public static void main (String [] args)
	{
		LLSparseM lion= new LLSparseM(1,10);//Row/Column
		lion.setElement(1, 2, 4);//Row,Column,Value
		lion.setElement(1, 2, 30);//WHY IS IT NOT BEING REPLACED???
		System.out.println(lion.getRowHead().getRowID());//ROW HEAD, ROW ID
		System.out.println(lion.getColumnHead().getColumn());//COLUMN HEAD, COLUMN ID
		System.out.println(lion.getColumnHead().getNextRow().getValue());
		lion.print();
	}
}
