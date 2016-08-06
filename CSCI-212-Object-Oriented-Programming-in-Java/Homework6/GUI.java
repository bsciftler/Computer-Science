import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Part b) You must use a completed version from Homework 6 part a in order to complete this assignment.
The Due date is Tuesday 12/10 before midnight. The cutoff date is Sunday 12/13 before midnight.
There cannot be any extensions due to it being tshe end of semester.
Create a GUI class and create a handler class.
The GUI class should have a TextField on top for the system to output messages to the user. This field should not be editable by the user.
Below that should be a row of 3 RadioButtons. The labels for each should read: “check if valid”, “enter” and “clear”.
Only one of these buttons can be selected at a time.
Below that should be a representation of the 4x4 board as buttons (16 buttons: 4 rows of 4 buttons). 
The text of each of these buttons should represent the value of the SudokuSquares object in the board of the SudokuBoard object. 
A square with a 0 value should be left blank, instead of displaying a 0. Buttons representing square that are locked 
(ie. the squares with a non-blank value at the beginning of a game) should be set so that the user cannot click on them 
(in other words they should be disabled).
Below that should be a TextField where the user can enter a number for the value to be assigned to a square. 
If any value is entered here other than a number from 1-4, an appropriate message should be output via the TextField at the top of the GUI
The handler object should be set up to react to the user clicking on one of the middle 16 squares.
There should also be  a button labeled “reset”. When this button is pressed, a JOptionPane Confirm box should be generated. 
If the user than clicks yes, the board should be reset to its initial values.
 */
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