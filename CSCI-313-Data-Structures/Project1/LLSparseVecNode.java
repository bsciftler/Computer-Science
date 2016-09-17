
public class LLSparseVecNode
{
	private int ID;
	private int value;
	private LLSparseVecNode next;
	private LLSparseVecNode previous;
	
	public LLSparseVecNode (int newID, int newValue, LLSparseVecNode n, LLSparseVecNode p)
	{
		value=newValue;
		value=newValue;
		next=n;
		previous=p;
	}
	
	public LLSparseVecNode(int newID, int newValue, LLSparseVecNode n)
	{
		this(newID,newValue,n,null);
	}
	
	public LLSparseVecNode (int newID, int newValue)
	{
		this(newID,newValue,null);
	}
	
	//Get Methods
	public int getID(){return ID;}
	public int getValue() {return value;}
	public LLSparseVecNode getNext() {return next;}
	public LLSparseVecNode getPrevious() {return previous;}
	
	//Set Methods
	public void setID(int id){ID=id;};
	public void setValue(int val){value=val;}
	public void setPrevious (LLSparseVecNode P) {previous=P;}
	public void setNext(LLSparseVecNode N){next=N;}	
}
