import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

public class Listen implements ActionListener{
	
	public void actionPerformed(ActionEvent e)
	{
	//JOptionPane.showMessageDialog(null, "You have pressed " + 
	//e.getActionCommand());
	JFileChooser chooser= new JFileChooser();
	int status= chooser.showOpenDialog(null); //Choose a File
	}
}
