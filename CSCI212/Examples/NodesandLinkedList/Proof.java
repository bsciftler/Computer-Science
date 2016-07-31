public class Proof {
	public static void main (String [] args){	
	Node2 n= new Node2(5);
	LinkedList2 Numbers = new LinkedList2(n);
	Numbers.insert(2);
	Numbers.insert(17);
	Numbers.insert(9);
	Numbers.insert(24);
	Node2 f= new Node2(24);

	//Numbers.print();
	//Numbers.delete(9);
	Numbers.print();
	System.out.println("The Sum of the numbers is: " + Numbers.getSum() + " and the product is: " + Numbers.getProduct());
	System.out.print("Here is the Max: " + Numbers.findMin(n).getData() + " and Here is the min: " + Numbers.findMax().getData());
	
	}
}