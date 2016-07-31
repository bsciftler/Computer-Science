//October 7
//Two Pointers!
public class Node2 {
			//Dynamic Data Structure
					private int data;
					private Node2 next; //Remember it matches class name
					private Node2 previous;
			//We use instance variables NOT static 
					public Node2(int d)
					{
						data = d; //Accept all integer values
					}
					public Node2(int d, Node2 n)
					{
						data = d;
						next = n;
					}
					
					public  Node2 (int d, Node2 n, Node2 p)
					{
						data=d;
						next=n;
						previous=p;
					}
					
					public int getData(){return data;}
					public Node2 getNext(){return next;}
					public Node2 getPrevious() {return previous;}
					public void setNext(Node2 n){next=n;}
					public void setPrevious(Node2 p) {previous = p; };

				}