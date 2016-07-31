import javax.swing.*; //Import everything
//Read Checked vs Unchecked exceptions!

public class Abstract{

	public static void main(String[] args) 
	{
		//Base
		JFrame frame = new JFrame();
		JMenuBar menuBar= new JMenuBar();
		JMenu fileMenu= new JMenu("File");
		JMenuItem item= new JMenuItem("open");
		//What is the user doing
		Listen handler= new Listen(); //Use Class Name
		
		item.addActionListener(handler);
		fileMenu.add(item);
		menuBar.add(fileMenu);
		
		frame.setJMenuBar(menuBar);
		
	//Frame details
		//Name for frame
		frame.setTitle("Test Frame");
		//How big is this?
		frame.setSize(300, 300);
		//Where is it on my screen?
		frame.setLocation(300, 300);
		//ALWAYS LAST STEP
		frame.setVisible(true);	
	}
	
	@SuppressWarnings("finally")
	public static boolean checkNumber(String someString)
	{
		boolean result = false;
		try
		{
			
		}
		catch(Exception e)
		{
			//If catch throws another exception. That exception that is thrown.
			//It will stop the finally block.
		}
		finally
		{
			return result; //Exception or not
		}
	}

}