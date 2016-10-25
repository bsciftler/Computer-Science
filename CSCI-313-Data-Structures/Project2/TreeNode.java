
public class TreeNode
{
	private TreeNode parent;
	private TreeNode left;
	private TreeNode right;
	private int value;
	private int depth;
	
	public TreeNode(TreeNode previous,int newVal, TreeNode LEFT, TreeNode RIGHT)
	{
		parent=previous;
		value=newVal;
		left=LEFT;
		right=RIGHT;
	}
	public TreeNode (TreeNode previous, int newVal)
	{
		this(previous,newVal,null,null);
	}
	public TreeNode(TreeNode previous, int newVal, TreeNode Left)
	{
		this(previous,newVal,Left,null);
	}
	//Get Method
	public int getValue() {return value;}
	public TreeNode getLeft() {return left;}
	public TreeNode getRight() {return right;}
	public TreeNode getParent() {return parent;}
	
	//Set Method
	public void setValue(int VAL){value=VAL;}
	public void setLeft(TreeNode L){left=L;}
	public void setRight (TreeNode R){right=R;}
}
