import java.text.DecimalFormat;

public class Student
{
	private static int numOfStudents = 0;
	private String firstName;
	private String lastName;
	private String cunyID;
	private double GPA;
	private String venusLogin;

	public Student(String fName, String lName, String ID, double gradeAvg, String venusAcc)
	{
		firstName = fName;
		lastName = lName;
		cunyID  = ID;
		GPA = gradeAvg;
		venusLogin = venusAcc;
		numOfStudents++;
	}
	
	public void displayAttributes()
	{
		System.out.println("Student Name: " + firstName + " " + lastName);
		System.out.println("GPA: " + new DecimalFormat("##.##").format(GPA));
		System.out.println("Cuny ID: " + cunyID);
		System.out.println("Venus Username: " + venusLogin);
	}
	
	public static int getNumOfStudents() { return numOfStudents; }
	public double getGPA() { return GPA; }
	public String getName() { return firstName + " " + lastName; }
	
	//Check Venus ID
	public boolean isValidVenusLogin()
	{
		//Left is INCLUSIVE, Right is NOT inclusive.
		//Count element 0 and 1, NOT 2, etc. 
		return venusLogin.equalsIgnoreCase(lastName.substring(0,2) + firstName.substring(0,2) + cunyID.substring(4,8));
	}

}