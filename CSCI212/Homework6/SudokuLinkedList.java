
public class SudokuLinkedList {
	private SudokuSquareNode head;
	private SudokuSquareNode currentNode;
	
	public SudokuLinkedList(SudokuSquare x)
	{
		head = new SudokuSquareNode(x);
		currentNode=head;
	}
	public SudokuLinkedList(SudokuSquareNode n)
	{
		head = n;
		currentNode=head;
	}
	
	public void append(SudokuSquare x)
	{
		SudokuSquareNode current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext(); 
		}
		current.setNext(new SudokuSquareNode(x));
	}
	
	public SudokuSquareNode getNext()
	{
		if (currentNode== null)
		{
			return null;
		}
		else 
		{
			SudokuSquareNode current=currentNode;
			currentNode=currentNode.getNext();
			return current;
		}
	}
}