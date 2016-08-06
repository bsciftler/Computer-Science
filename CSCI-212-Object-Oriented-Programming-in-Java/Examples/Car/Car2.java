//September 15
public class Car2 {
	public static void main (String[] args) {
		Car c1= new Car(39000);
		c1.drive(500); //Drive the car 500 miles
		int m= c1.getMiles(); //Calling to a Public Method
		System.out.println(m);
		Car c2= new Car(35000, "Blue");
		c1.setColor("pink");
		int carcount= Car.getCount();
		
		//Class is static, because it is calling class then method
		int totalmiles=Car.getTotalMiles();
		System.out.println("The total miles of ALL cars is: " + totalmiles);
	
	String s= c1.getColor();
	int len= s.length(); //Amount of characters in string.
	int len2= args.length; //This is no (), meaning no parameter/method.
	//len2 is a variable...
	//it needs to be declared public.
	}
}
