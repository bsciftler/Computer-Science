import javax.swing.JOptionPane;

public class MyInput 
{
	public static int getRandom()
	{
		return (int)Math.ceil(Math.random()*30);
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
		
		LLSparseM lion= new LLSparseM(5,5);//Row/Column
		//lion.setElement(1, 5, 4);//Row,Column,Value
		//Test SET AND CLEAR
		lion.setElement(2, 1, 30);
		lion.setElement(3, 2, 10);
		//lion.setElement(4, 1, 55);
		//lion.setElement(5, 1, 34);
		//lion.setElement(2, 2, 15);
		lion.setElement(3, 5, 50);
		
JOptionPane.showMessageDialog(null, "The Sparse Matrix Lion " + lion.nrows() +" X "+lion.ncols() + " with " + lion.numElements() + " non zero elements ");
JOptionPane.showMessageDialog(null, "Row: 3 " + "Column: 1 " + "Element: "+ lion.getElement(2, 1));
//int [] RowID=lion.getRowIndices();
//int [] ColID=lion.getColIndices();
//int [] OneRowColID=lion.getOneRowColIndices(2);
//int [] OneRowVal=lion.getOneRowValues(3);
//int [] OneColRowID=lion.getOneColRowIndices(1);
//int [] OneColVal=lion.getOneColValues(1);
System.out.println("Row Head RowID: "+lion.getRowHead().getRowID());//ROW HEAD, ROW ID
System.out.println("Column Head Column ID: "+lion.getColumnHead().getColumnID());//COLUMN HEAD, COLUMN ID
System.out.println("Active Rows: " + lion.getnumofRows() + " Active Columns " + lion.getnumofColumns());
		//System.out.println("Second Column ID: "+lion.getColumnHead().getNextColumn().getColumnID());//ROW HEAD, ROW ID
		//System.out.println("Column Travel:");
		//System.out.println("Node 1: " + lion.getColumnHead().getNextElement().getValue());
		//System.out.println("Node 2: " + lion.getColumnHead().getNextColumn().getNextElement().getValue());
		//System.out.println("Node 3: " + lion.getColumnHead().getNextColumn().getNextColumn().getNextElement().getValue());
		//System.out.println("Row Travel:");
		//System.out.println("Node 1: " + lion.getRowHead().getNextElement().getValue());
		//System.out.println("Node 2: " + lion.getRowHead().getNextElement().getNextColumn().getValue());
		//System.out.println("Node 3: " + lion.getRowHead().getNextElement().getNextColumn().getNextColumn().getValue());
//System.out.println("Node 3: " + lion.getRowHead().getNextElement().getNextColumn().getNextColumn().getNextColumn().getValue());	
		System.out.println("Print by Row:");
		lion.printAllNodes();
		lion.print();
		System.out.println("Print by Column");
		lion.PrintAllColNodes();
	}
}