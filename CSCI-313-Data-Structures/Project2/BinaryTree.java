
public class BinaryTree
{
	private TreeNode root;
	private int numOfElements=0;
	private boolean modified=false;
	private int height=0;
	
	public BinaryTree (int x)
	{
		root=new TreeNode(null,x,null,null);
		++numOfElements;
	}
	public TreeNode getRoot(){return root;}
	public boolean isRoot(TreeNode X) {return (X==root); }
	public boolean isEmpty() { return (numOfElements==0);}
	
	
	public void insert(TreeNode Parent, int x, String DIRECTION)
	{
		if (numOfElements >= 1)
		{
			if (DIRECTION.equalsIgnoreCase("Left"))
			{
				Parent.setLeft(new TreeNode(Parent,x));
				++numOfElements;
				
			}
			else if (DIRECTION.equalsIgnoreCase("Right"))
			{
				Parent.setRight(new TreeNode(Parent,x));
				++numOfElements;
			}
			else
			{
				System.out.println("INVALID DIRECTION IN INSERT METHOD");
			}
			modified=true;
			return;
		}
	}
	public int getHeight()
	{
		if (modified==false)
		{
			return height;
		}
		else//It has been modified...update your value.
		{
			modified=false;
			return height;
		}
	}
	
	public void getAllAncestors(TreeNode M)
	{
		TreeNode current=M;
		while (current.getParent()!=null)//(Root)
		{
			System.out.println(current.getValue());
			current=current.getParent();
		}
	}
}
