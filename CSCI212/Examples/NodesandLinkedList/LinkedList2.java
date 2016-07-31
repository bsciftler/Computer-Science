public class LinkedList2 {
	static private Node2 head;
	
	public LinkedList2(int x){
		head = new Node2(x);
	}
	public LinkedList2(Node2 n){
		head = n;
	}
	
	public void print()
	{
		Node2 current = head;
		while (current!=null)
		{
			System.out.print(current.getData()+" ");
			current = current.getNext();
		}
		System.out.println();
	}
	
	public void insert(int x)
	{
		if(x<=head.getData())
		{
			head = new Node2(x, head); // case 1
			head.getNext().setPrevious(head);
			return;
		}
		Node2 current = head;
		while(current.getNext()!=null)
		{
			current = current.getNext();
			if(x>current.getData())
			{
				continue;
			}
			else 
			{
				current.getPrevious().setNext(new Node2(x,current, current.getPrevious())); // case 2
				current.setPrevious(current.getPrevious().getNext());
				return;
			}
		}// while
		current.setNext(new Node2(x, null, current)); //  case 3
	}
	
	public Node2 find(int x)
	{
		Node2 current = head;
		while (current!=null)
		{
			if(current.getData()==x)
			{
				return current;
			} 
			else if(current.getData()<x )
			{
				current = current.getNext();
			} 
			else 
			{
				current = null;
			}
		}
		return current;
	}
	
	public void append(int x){
		Node2 current = head;
		while(current.getNext()!=null){
			current = current.getNext();
		}
		current.setNext(new Node2(x, null, current));
	}

	public boolean delete(int x){
		Node2 found = find(x);
		if (found == null){
			return false;
		}
		if(found.getPrevious()!=null){
			found.getPrevious().setNext(found.getNext());
		} else {
			head = head.getNext();
		}
		if(found.getNext()!=null){
			found.getNext().setPrevious(found.getPrevious());
		}
		return true;
	}
	
	//public boolean replace(int x, int y){
	//	Node2 found = findUnsorted(x);
	//	if (found == null){
	//		return false;
	//	} 
	//	found.getPrevious().setNext(new Node2(y,found.getNext(), found.getPrevious()));
		//found.getNext().setPrevious(found.getPrevious().getNext());
	//}
	
	public Node2 findUnsorted(int x){
		Node2 current = head;
		while (current!=null){
			if(current.getData()==x){
				return current;
			} else {
				current = current.getNext();
			}
		}
		return current;
	}

//All Homework Addons
	public int size()
	{
		int size=0;
		Node2 current=head;
		while (current!=null)
		{
			size++;
			current=current.getNext();
		}
		return size;
	}

	public Node2 findMin (Node2 n)
	{
		//Create current equal to input.
		Node2 current=n;	
		Node2 compare=current.getNext();
		while (compare!=null)
		{
			if (compare.getData()<=current.getData())
			{
				current=compare;
			}
			compare=compare.getNext();
		}
		//current contains smallest.
		return current;
	}
	
	public Node2 findMax()
	{	
		Node2 max=head;//start from head.
		Node2 compare=max.getNext(); //Compare with head.
			while (compare!=null)
			{
				if (compare.getData()>=max.getData())
				{
					max=compare;
				}
				compare=compare.getNext();
			}
			return max;
	}
	
	public void move (Node2 a, Node2 n)
	{
		if (a.getPrevious()!=null)
		{
			a.getPrevious().setNext(a.getNext());
		}
		if (a.getNext()!=null)
		{
			a.getNext().setPrevious(a.getPrevious());
		}

		a.setPrevious(n.getPrevious());
		if (head != n) //Same as checking if b.getprevious is null.
		{
			n.getPrevious().setNext(a);
		}
		else
		{
			head=a; //b.getprevious is where head would be. If my goal is move a behind B. Well A must be the new head.
		}
		
		a.setNext(n);
		n.setPrevious(a); 
	}
	
	public int getProduct()
	{
		int product=1;
		Node2 current=head;;
		while (current!=null)
		{
			product*=current.getData();
			current=current.getNext();
		}
		return product;
	}
	
	public int getSum()
	{
		Node2 current=head;
		int sum=0;
		while(current!=null)
		{
			sum+=current.getData();
			current=current.getNext();
		}
		return sum;
	}
	
}//End of Class

		
