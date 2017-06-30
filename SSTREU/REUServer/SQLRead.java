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
	//private final String myDriver = "org.gjt.mm.mysql.Driver";
	private final String URL = "jdbc:mysql://localhost/";
	private String username = "root";
	private String password = "SSTREU2017";
	String DB = "FIU";
	private final String myUrl = URL + DB;
	String Query;
	ArrayList<REUSQLTUPLE> QueryResult = new ArrayList<REUSQLTUPLE>();
	
	
	public SQLRead(String Q)
	{
		Query = Q;
	}
	/*
	select * from Localization
	Where MACAddress = 'c8:b3:73:1f:fc:6e'
	Order by XCoordinate ASC;
	*/
	
	public ArrayList<REUSQLTUPLE> getAnswer() {return QueryResult;}
	
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
			double Xcoordinate;
			double Ycoordinate;
			int RSS;
			while(rs.next())
			{
				Xcoordinate = rs.getDouble("Xcoordinate");
				Ycoordinate = rs.getDouble("Ycoordinate");
				RSS = rs.getInt("RSS");
				QueryResult.add(new REUSQLTUPLE(Xcoordinate, Ycoordinate, RSS));
			}
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
	}
	
}
