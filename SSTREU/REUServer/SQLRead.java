import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * 
 * Created by Alvin Alexander, http://devdaily.com
 */
public class SQLRead extends Thread
{
	private final String myDriver = "org.gjt.mm.mysql.Driver";
	private final String URL = "jdbc:mysql://localhost/";
	private String username = "root";
	private String password = "SSTREU2017";
	String DB = "FIU";
	private final String myUrl = URL + DB;
	String Query;
	boolean isSecure;
	private final static int VECTORSIZE = 10;
	ArrayList<BigInteger []> S1Array;
	ArrayList<Integer []> RSSArray;
	
	public SQLRead(String Q, Boolean isSec)
	{
		Query = Q;
		isSecure=isSec;
	}
	
	public ArrayList<Integer []> getRSS() {return RSSArray;}
	public ArrayList<BigInteger []> getS1() {return S1Array;}
	
	public void run()
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
			
			//int index;
			
			if (isSecure==true)
			{
				while(rs.next())
				{
					Integer [] RSS = new Integer [VECTORSIZE];
					RSS[0] = rs.getInt("ONE");
					RSS[1] = rs.getInt("TWO");
					RSS[2] = rs.getInt("THREE");
					RSS[3] = rs.getInt("FOUR");
					RSS[4] = rs.getInt("FIVE");
					RSS[5] = rs.getInt("SIX");
					RSS[6] = rs.getInt("SEVEN");
					RSS[7] = rs.getInt("EIGHT");
					RSS[8] = rs.getInt("NINE");
					RSS[9] = rs.getInt("TEN");
					RSSArray.add(RSS);
				}
			}
			else
			{
				while(rs.next())
				{
					BigInteger [] S1 = new BigInteger [VECTORSIZE];
					S1[0] = new BigInteger(rs.getString("ONE"));
					S1[1] = new BigInteger(rs.getString("TWO"));
					S1[2] = new BigInteger(rs.getString("THREE"));
					S1[3] = new BigInteger(rs.getString("FOUR"));
					S1[4] = new BigInteger(rs.getString("FIVE"));
					S1[5] = new BigInteger(rs.getString("SIX"));
					S1[6] = new BigInteger(rs.getString("SEVEN"));
					S1[7] = new BigInteger(rs.getString("EIGHT"));
					S1[8] = new BigInteger(rs.getString("NINE"));
					S1[9] = new BigInteger(rs.getString("TEN"));
					S1Array.add(S1);
				}
				//I also need plain text to compute S2
				Statement partTwo = conn.createStatement();
				ResultSet S2 = partTwo.executeQuery("select * from reuplainlut;");
				while(S2.next())
				{
					Integer [] RSS = new Integer [VECTORSIZE];
					RSS[0] = rs.getInt("ONE");
					RSS[1] = rs.getInt("TWO");
					RSS[2] = rs.getInt("THREE");
					RSS[3] = rs.getInt("FOUR");
					RSS[4] = rs.getInt("FIVE");
					RSS[5] = rs.getInt("SIX");
					RSS[6] = rs.getInt("SEVEN");
					RSS[7] = rs.getInt("EIGHT");
					RSS[8] = rs.getInt("NINE");
					RSS[9] = rs.getInt("TEN");
					RSSArray.add(RSS);
				}
			}
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
	}
	
}
