import javax.swing.JOptionPane;

/*
 * a) Write a class called Student
This class should have the following data members: 
Number Of Students, First Name, Last Name, Cunyfirst ID, GPA and their Venus Login. 
Remember to name them appropriately and use lower camel case. So for example, First Name = firstName. 
b) Write a constructor for your class that accepts all the values listed above and initialize your object appropriately. Your constructor needs to increment the static value Number of Students each time an instantiation occurred so that you can keep track of the students at any given time. 
c) Write a static method that will return the number of students.
d) Write a method called getGPA that will return the student’s GPA.
e) Write a method called isValidVenusLogin to check that what they entered is actually a valid Venus login. As you recall, the Venus login has to be in this format:
lafi1234 (First two characters of your last name, first two characters of your first name and your last 4 digits of your CUNYFirst ID)
Hint: Use the Java String API to achieve this.
f) Write a print method called displayAttributes() that will output all non-static attribute values. So, for example, you should have something like this:
System.out.println(“Student Name: ” + First Name + “ “ + Last Name);
System.out.println(“GPA: ” + GPA);
g) Create an array of type Student with n objects inside your main. (Use JOptionPane to ask user to tell you how many Student objects you need to instantiate.)
h) Iterate through the array and use proper constructor to instantiate and populate values for each of your student. 
Use Math.random() to select from a list of predetermined array values dynamically to select a name for each student. So, for example, you would have an array called FirstNames that has the values {“Bob”, “Mary”, “Peter”} and last Names = {“Peterson”, “Reich”, “Jackson”} etc. 
Randomly select the first name and last names for each student during your instantiation as well as for your GPA, CUNY ID, and Venus Login. 
i) Iterate through the array of objects and call the displayAttributes() Method that you have created in (f) to display each student’s attributes.
j) Iterate through the array of objects and call the isValidVenusLogin() method to display whether each student’s Venus Login is valid using this format:
Student <FirstName + LastName>’s Venus Login is <Valid/Invalid> .
If you did not make a mistake during the instantiation of each Student object, you should be getting all valid Venus login. 
k) Call the static method that you have created in c) to get and display the number of students  using this format: 
The number of students that exists now is <number of students>
 */
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
		} while(! isQuit);

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