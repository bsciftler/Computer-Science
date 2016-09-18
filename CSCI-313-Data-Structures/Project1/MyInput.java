
public class MyInput 
{
	public static void main (String [] args)
	{
		LLSparseM lion= new LLSparseM(1,10);//Row/Column
		lion.setElement(1, 2, 4);//Row,Column,Value
		System.out.println(lion.getRowHead().getRowID());
		System.out.println(lion.getColumnHead().getColumn());
		System.out.println(lion.getColumnHead().getNextRow().getValue());
		lion.print();
	}
}
