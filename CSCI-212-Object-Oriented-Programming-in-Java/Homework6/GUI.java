import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame
{
	
		private final static int SIZE=4;
		private JButton [][] sudokuButtons = new JButton [SIZE][SIZE];
		
		//I/O
		private JTextField inputField;
		private JTextField outputField;
	    

	    //Radio Buttons
	    private JRadioButton checkIfValidRadio;
	    private JRadioButton enterRadio;
	    private JRadioButton clearRadio;
	    
	    private SudokuBoard theBoard;

	    public GUI(SudokuBoard Game, int height, int width) 
	    {
	        super("Sudoku");
	        theBoard = Game;
	        setSize(height, width);
	        setLayout(new BorderLayout());
	        initComponents();
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setVisible(true);
	    }

	    private void initComponents() 
	    {
	        initOutputField();
	        initRadioButtons();
	        JPanel top = new JPanel(new BorderLayout());
	        JPanel topOptions = new JPanel(new GridBagLayout());
	        initTopPanel(top, topOptions);
	        JPanel buttonPane = new JPanel(new GridLayout(SIZE, SIZE));
	        initSudokuBtns(buttonPane);
	        initInputField();
	        add(buttonPane, BorderLayout.CENTER);
	        add(top, BorderLayout.NORTH);
	        add(inputField, BorderLayout.SOUTH);
	    }

	    private void initSudokuBtns(JPanel buttonPane) 
	    {
	        for(int i = 0; i < SIZE; i++) 
	        {
	            for(int j = 0; j < SIZE; j++) 
	            {
	                sudokuButtons[i][j] = new JButton();
	                String boardValue = Integer.toString(SudokuBoard.getSquare(i,j).getValue());
	                if(boardValue == "0") { boardValue = ""; }
	                else { sudokuButtons[i][j].setText(boardValue); }
	                sudokuButtons[i][j].setActionCommand(i + "," + j);
	                sudokuButtons[i][j].addActionListener(new ButtonListener());
	                buttonPane.add(sudokuButtons[i][j]);
	            }
	        }
	    }

	    private void resetButtonText() 
	    {
	        for(int i = 0; i < SIZE; i++) 
	        {
	            for(int j = 0; j < SIZE; j++) 
	            {
	                String boardValue = Integer.toString(SudokuBoard.getSquare(i,j).getValue());
	                if(boardValue == "0") { boardValue = ""; }
	                sudokuButtons[i][j].setText(boardValue);
	            }
	        }
	    }

	    private void initTopPanel(JPanel top, JPanel topOptions) 
	    {
	        top.add(outputField, BorderLayout.NORTH);
	        top.add(topOptions,BorderLayout.SOUTH);
	        top.setSize(getSize());
	        topOptions.add(checkIfValidRadio);
	        topOptions.add(enterRadio);
	        topOptions.add(clearRadio);
	    }

	    private void initRadioButtons() 
	    {
	        ButtonGroup radioOptionGroup= new ButtonGroup();
	        checkIfValidRadio = new JRadioButton("Validate Move", true);
	        enterRadio = new JRadioButton("Enter Move");
	        clearRadio = new JRadioButton("Clear Board");

	        radioOptionGroup.add(checkIfValidRadio);
	        radioOptionGroup.add(enterRadio);
	        radioOptionGroup.add(clearRadio);
	        clearRadio.addActionListener(new ClearRadioListener());
	    }

	    private void initOutputField() 
	    {
	        outputField = new JTextField(20);
	        outputField.setEditable(false);
	        outputField.setText("Output:");
	        outputField.setHorizontalAlignment((int)CENTER_ALIGNMENT);
	    }

	    private void initInputField() 
	    {
	        inputField = new JTextField(20);
	        inputField.setEditable(true);
	        inputField.setHorizontalAlignment((int)CENTER_ALIGNMENT);
	    }

	    public class ClearRadioListener implements ActionListener 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	            int userChoice = JOptionPane.showConfirmDialog(null, "Would you like to reset the board?",
	                    "Please Choose An Option", JOptionPane.YES_NO_OPTION);
	            if(userChoice == JOptionPane.YES_OPTION) 
	            {
	                theBoard.reset();
	                resetButtonText();
	            }
	            checkIfValidRadio.setSelected(true);
	        }
	    }

	    public class ButtonListener implements ActionListener 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	            int valueFromInput;
	            try 
	            {
	                valueFromInput = Integer.parseInt(inputField.getText());
	            } 
	            catch(NumberFormatException nfe) 
	            {
	                outputField.setText("Why are you putting non integer values?");
	                return;
	            }
	            String [] buttonLocation = e.getActionCommand().split(",");
	            int row = Integer.parseInt(buttonLocation[0]);
	            int column = Integer.parseInt(buttonLocation[1]);
	            if(checkIfValidRadio.isSelected()) 
	            {
	                if(theBoard.isValid(row, column, valueFromInput)) 
	                {
	                    outputField.setText("The move is valid.");
	                }
	                else 
	                {
	                    outputField.setText("The move entered is not a valid move.");
	                }
	            } 
	            else 
	            {
	                try 
	                {
	                    theBoard.enterMove(row, column, valueFromInput);
	                    sudokuButtons[row][column].setText(Integer.toString(valueFromInput));
	                } 
	                catch(SudokuException se) 
	                {
	                    outputField.setText("Cannot place a " + valueFromInput + " in that location.");
	                }
	            }

	        }
	    }
}