import java.util.*;

public class BinaryTreeClass
{
	//Private Node Class
	private class Node 
	{
		private int e;
		private Node parent;
		private Node leftChild;
		private Node rightChild;
		public Node (int x, Node Parent, Node LeftChild, Node RightChild)
		{
			e=x;
			parent=Parent;
			leftChild=LeftChild;
			rightChild=RightChild;
		}
		public int getValue () {return e;}
		public Node getParent() {return parent;}
		public Node getLeft() {return leftChild;}
		public Node getRight() {return rightChild;}
	}
	/*
	 * Chao Chen ask:
	 * 
	 * 1) Fix Height
	 * 2) Fix In-Order/Pre-Order traversal
	 * 3) Hints on how to Generate ALL Trees
	 * I assume it will be a Linked List of Trees?
	 */
	
	
	//Tree Functions...
	private Node root;
	private int size=0;
	
	
	public boolean isEmpty() {return (size==0);}
	public Node getRoot() {return root;}
	
	public BinaryTreeClass()
	{
		
	}
	/*
	 *
	 * 
	 */
	
	
	
	public int numOfChildren(Node p)
	{
		int answer=0;
		if (p.getLeft()!=null)
		{
			++answer;
		}
		if (p.getRight()!=null)
		{
			++answer;
		}
		return answer;
	}
	
	public void insert(int x)
	{
		if (isEmpty())
		{
			root=new Node(x,null,null,null);
			++size;
			return;
		}
		
		++size;
	}
	/*
	 * Use Properties of In-Order and In-order Traversal to prune the trees.
	 * This how you can deduce the Root.
	 * Lets say I get 
	 * 1, 2, 3, 4, 5
	 * I can Recursively build my trees with this.
	 * So Let a[0], then a[1], etc. be my root, etc.
	 * The recursion can also test the tree.
	 * If I am to this properly, I should use the index instead of the value.
	 */
	
	public LinkedList<Node> children(Node p)
	{
		LinkedList <Node> kids = new LinkedList <Node>();
		if (p.getLeft()!=null)
		{
			kids.add(p.getLeft());
		}
		if (p.getRight()!=null)
		{
			kids.add(p.getRight());
		}
		return kids;
	}
	
	public LinkedList<ArrayList<Integer>> Problem3(ArrayList<Integer> input_array, int tolerance)
	{
		LinkedList<ArrayList<Integer> > returnList = new LinkedList<>();	
		//Step 1: Build all Possible Trees from that input Array
		
		//Step 2: Check which Trees Pass the tests
		//Step 2A: Tolerance Test
		
		//Step 2B: Same Traversal as what is given?
		
		//Step 3: Print Proper Traversal
		return returnList;
	}
	
	//Print Order
	private void preorderSubtree(Node p, LinkedList<Node> snapshot)
	{
		if (p==null)
		{
			return;
		}
		snapshot.add(p);
		preorderSubtree(p.getLeft(), snapshot);
		preorderSubtree(p.getRight(), snapshot);
	}
	
	//Tolerance Test//Snapshot is my answer
	
	private void inorderSubTree(Node p, LinkedList<Node> snapshot)
	{
		if (p==null)
		{
			return;
		}
		inorderSubTree(p.getLeft(),snapshot);
		snapshot.add(p);
		inorderSubTree(p.getRight(),snapshot);
		return;
	}
	
	public int height(Node p)
	{
		int h=0;
		for (Node i: children(p))
		{
			h=Math.max(h,1+height(i));
		}
		return h;
	}

}
