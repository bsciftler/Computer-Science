import javax.swing.JOptionPane;

public class Homework2
{
	public static void main(String [] args)
	{
		boolean isQuit = true;
		String studentSz;
		do
		{
			studentSz = JOptionPane.showInputDialog(null, "How many students would you like to create?");
			if(studentSz == null)
			{
				System.exit(1);
			}
			else if(! isValidInt(studentSz) )
			{
				JOptionPane.showMessageDialog(null,"Error, Please enter a valid number.");
				isQuit = false;
			}
			else
			{
				isQuit = true;
			}
		} 
		while(!isQuit);

		//Create Students
		Student [] studentBody = new Student[Integer.parseInt(studentSz)];
		String firstNames [] = new String[]{"Evan", "Andrew", "Heather", "Luis", "Bill", "Jack"};
		String lastNames [] = new String[]{"Phillips", "Clark", "Rodriguez", "Cage", "Jobs", "Stevenson"};
		for(int i = 0; i < studentBody.length; i++)
		{
			String fName = firstNames[randomInt(0, firstNames.length)];
			String lName = lastNames[randomInt(0, lastNames.length)];
			String cunyID = generateCunyID();
			double GPA = randomDouble(0,4.0);
			String venusAcc = createVenusAcc(fName, lName, cunyID);
			studentBody[i] = new Student(fName, lName, cunyID, GPA, venusAcc);
		}

		//Check Venus ID
		for(int i = 0; i < studentBody.length; i++)
		{
			studentBody[i].displayAttributes();
			System.out.print(studentBody[i].getName() + "'s Venus login is ");
			if(studentBody[i].isValidVenusLogin() )
			{
				System.out.print("VALID");
			} 
			else 
			{
				System.out.print("INVALID");
			}
			System.out.println(".\n");
		}

		System.out.println("There are currently " + Student.getNumOfStudents() + " students.");
	}
	public static boolean isValidInt(String check)
	{
		try
		{
			Integer.parseInt(check);
		} 
		catch(NumberFormatException badInt)
		{
			return false;
		}
		return true;
	}
	
	public static int randomInt(int min, int max)
	{
		return (int) (Math.random()*max) + min;
	}
	
	public static double randomDouble(double min, double max)
	{
		return Math.random()*max;
	}
	
	public static String generateCunyID()
	{
		String cunyID = "";
		for(int i = 1; i <= 8; i++)
		{
			cunyID += randomInt(0, 9);
		}
		return cunyID;
	}
	
	public static String createVenusAcc(String fName, String lName, String cunyID)
	{
		return ( lName.substring(0,2) + fName.substring(0,2) + cunyID.substring(4,8) ).toLowerCase();
	}
}