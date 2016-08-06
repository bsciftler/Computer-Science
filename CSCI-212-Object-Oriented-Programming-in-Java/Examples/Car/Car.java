public class Car {

	private int numofdoors = 4;
	private String color ="Black";
	private int mileage;
	private String vin;
	private final static int WHEELS = 4;
	private static int count =0; 
	private static int totalmiles=0;
	
	public Car(int m){
		if(m>0)
		{
			mileage = m;
			totalmiles+=m;
		} 
		else 
		{
			mileage = 1000;
		}
		count++;
	}
	
	public Car(int m, String c){
		if(m>0)
		{
			mileage = m;
			totalmiles+=m;
		} 
		else 
		{
			mileage = 1000;
		}
		if(isValidColor(c)){
			color = c;
		}
		count++;
	}
	
	public Car(int m, String c, String v){
		if(m>0)
		{
			mileage = m;
			totalmiles+=m;
		} 
		else 
		{
			mileage = 1000;
		}
		
		if(!isValidColor(c))
		{
			color = c;
		}
		vin = v;
		count++;
	}
	
	public static int getTotalMiles(){return totalmiles;}
	public static int getCount(){return count;}
	public int getMiles(){ return mileage; }
	public String getColor() {return color;}
	
	public void setColor(String c)
	{ 
		if(isValidColor(c))
		{
			color = c;
		}
	}
	
	public void drive(int m)
	{
		if(m>0)
		{
			mileage+=m;
			addTotal(m);
		}
	}
	
	public static int addTotal(int m){return totalmiles+=m;}
	
	public static boolean year(int x)
	{
		if(x>2015)
		{
			return true;
		} 
		else if(x<2016)
		{
			return false;
		} 
			return false;
	}
	
	public static boolean isValidColor(String s)
	{
		if (s.equalsIgnoreCase("Black") ||
				s.equalsIgnoreCase("white") ||
				s.equalsIgnoreCase("red") ||
				s.equalsIgnoreCase("blue") ||
				s.equalsIgnoreCase("green") ||
				s.equalsIgnoreCase("silver") ||
				s.equalsIgnoreCase("gold") ||
				s.equalsIgnoreCase("pink") ||
				s.equalsIgnoreCase("orange") ||
				s.equalsIgnoreCase("tan") ||
				s.equalsIgnoreCase("fusia"))
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
}