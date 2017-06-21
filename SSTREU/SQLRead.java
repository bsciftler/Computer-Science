import java.sql.*;
import java.util.ArrayList;

/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * 
 * Created by Alvin Alexander, http://devdaily.com
 */
public class SQLRead
{
	private ArrayList<double []> LittleFinger = new ArrayList<double []>();
	private final String myDriver = "org.gjt.mm.mysql.Driver";
	private final String URL = "jdbc:mysql://10.0.2.15:3306/";
	private String username;
	private String password;
	private final String myUrl;
	
	public SQLRead(String u, String p, String DBName) 
	{
		username=u;
		password=p;
		myUrl = URL + DBName;
	}
	
	public ArrayList<double []> getLittle()
	{
		return LittleFinger;
	}
	
	public void GenerateQueue(String Query)
	{
		//Database is FIU
		//Username is root
		//Password is SSTREU2017
		try
		{
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java result set
			ResultSet rs = st.executeQuery(Query);
			while(rs.next())
			{
				double Xcoordinate = rs.getDouble("X-coordinate");
				double Ycoordinate = rs.getDouble("Y-coordinate");
				int RSS = rs.getInt("RSS");
				double [] a = {Xcoordinate,Ycoordinate,(double)RSS};
				LittleFinger.add(a);
			}
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException
	{
		String user = "root";
		String pass = "";
		String DB = "FIU";
		SQLRead Project = new SQLRead(user,pass,DB);
		Project.GenerateQueue("SELECT * FROM FingerprintDatabase;");
		
		ArrayList<double []> Tiny= Project.getLittle();
		for(int i=0;i<Tiny.size();i++)
		{
			printArray(Tiny.get(i));
		}
	
	}
	public static void printArray(double [] x)
	{
		for (int i=0;i<x.length;i++)
		{
			System.out.print(x[i] + " ");
		}
		System.out.println();
	}
}
