import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildLookupTable
{
	/*
	 * 	For this Class to Build the Lookup Table...
	 * 	it assumes 
	 * 	1 - The Table called REULookUpTable is made
	 * 	2 - it has the structure of X, Y, 10 MAC Addresses
	 *  3 - Training Data is ready and loaded into a MYSQLServer
	 *  Final assumption, you are using the correct column names!
	 */
	final static int NUMBEROFMACS = 10;
	static String [] CommonMac = new String[NUMBEROFMACS];
	static String [] ColumnNames = {"ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", 
			"SEVEN", "EIGHT", "NINE", "TEN"};
	
	static HashMap<String, String> columnMapping = new HashMap<String, String>();
	static ArrayList<Double> Xmap = new ArrayList<Double>();
	static ArrayList<Double> Ymap = new ArrayList<Double>();
	
	static int rows;
	static int columns;
	int [][] Pinsert;
	String [][] Einsert;
	

	private final static String myDriver = "org.gjt.mm.mysql.Driver";
	private final static String URL = "jdbc:mysql://localhost/";
	private static String username = "root";
	private static String password = "SSTREU2017";
	private final static String DB = "FIU";
	private final static String myUrl = URL + DB;
	public PublicKey pk;
	private final BigInteger NULL;
	
	public BuildLookupTable(PublicKey in)
	{
		pk = in;
		NULL = Paillier.encrypt(new BigInteger("-120"), pk);
	}
	/*
	 * 	Do a SQL Statement to find what are the 10 most prominent MAC Addresses...
	 	SQL:
	SELECT MACADDRESS, Count(MACADDRESS) as count 
	from FIU.FingerPrint
	group by MACADDRESS
	ORDER BY count DESC
	LIMIT 10;
	 */
	public void getCommonMAC(String Query)
	{
		try
		{
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java result set
			ResultSet rs = st.executeQuery(Query);
			int i=0;
			while (rs.next())
			{
				/*
				 * Note to self: DO NOT USE FOR LOOPS!! 
				 */
				CommonMac[i]=rs.getString("MACADDRESS");
				++i;
			}
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
	/*
	 * 	Get all distinct pairs of x, y coordinates.
	 */
	public void getXY(String Query)
	{
		try
		{
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java result set
			ResultSet rs = st.executeQuery(Query);
			while (rs.next())
			{
				Xmap.add(rs.getDouble("Xcoordinate"));
				Ymap.add(rs.getDouble("YCoordinate"));
			}
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
	/*	CREATE TWO TABLES....
	 	Create a Table for Plain Text RSS Values
	 	The INDEX IS IMPORTANT. I WILL NEED IT AS THE COMPUTATIONS WILL
	 	BE ATTACHED TO AN INDEX.
	 	THE INDEX WILL TELL ME WHICH XY COORDINATE
	
	
	  	Create a Table for Encrypted RSS Values
	  	The INDEX IS IMPORTANT. I WILL NEED IT AS THE COMPUTATIONS WILL
	 	BE ATTACHED TO AN INDEX.
	 	THE INDEX WILL TELL ME WHICH XY COORDINATE
	 */

	public void createTables()
	{
		try
		{
			Class.forName(myDriver);
			System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			Statement stmt = conn.createStatement();
//===================BUILD PLAIN RSS TABLE================================
			String sql =
					"CREATE TABLE REUPlainLUT " +
					"(ID INTEGER not NULL, " +
					" Xcoordinate DOUBLE not NULL, " + 
					" Ycoordinate DOUBLE not NULL, ";
			String add = "";
			for (int i=0; i < NUMBEROFMACS;i++)
			{
				add+= ColumnNames[i];
				add+=" INTEGER,";
			}
			sql+=add;
			sql +=" PRIMARY KEY (ID));"; 
			System.out.println(sql);
			stmt.executeUpdate(sql);
//=====================Build Encrypted RSS Table==========================================		
			String sqlTwo =
					"CREATE TABLE REUEncryptedLUT " +
					"(ID INTEGER not NULL, " +
					" Xcoordinate DOUBLE not NULL, " + 
					" Ycoordinate DOUBLE not NULL, ";
			String addTwo = "";
			for (int i=0; i < NUMBEROFMACS;i++)
			{
				addTwo+=ColumnNames[i];
				addTwo+=" TEXT,";
			}
			sqlTwo +=addTwo;
			sqlTwo +=" PRIMARY KEY (ID));"; 
			System.out.println(sqlTwo);
			stmt.executeUpdate(sqlTwo);

		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
	
	/*
	 	Acquire all the RSS and Encrypted Data needed for the Lookup Table
	 */
	public void GetDataforLUT()
	{
		try
		{
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);
		
			//SQL Statement for PlainText Values
			/*
	 		select RSS FROM FingerPrint
			WHERE Xcoordinate = 227.761 
			AND YCoordinate = 1095.73 AND MACADDRESS = '84:1b:5e:4b:80:e2';
			 */
			
			//SQL Statement for Encrypted Values
			/*
	 		select EncryptedRSSSquared FROM FingerPrint
			WHERE Xcoordinate = 227.761 
			AND YCoordinate = 1095.73 AND MACADDRESS = '84:1b:5e:4b:80:e2';
			*/

			String getRSS;
			String getEncryptedRSS;
			// execute the queries, and get a java result set
			ResultSet RSS; 
			ResultSet EncryptedRSS;
			
			rows = Xmap.size();
			columns = NUMBEROFMACS;
			
			Pinsert = new int [rows][columns];
			Einsert = new String [rows][columns];
			
			// create the java statement
			Statement Plainst; 
			Statement EncSt;
			for (int x=0;x<Xmap.size();x++)
			{
				for (int currentCol = 0; currentCol < NUMBEROFMACS; currentCol++)
				{
					getRSS = "SELECT RSS FROM FingerPrint"
							+ " WHERE Xcoordinate = "
							+ Xmap.get(x)
							+ " AND Ycoordinate = "
							+ Ymap.get(x)
							+ " AND MACADDRESS = '"
							+ CommonMac[currentCol]
							+ "';";

					getEncryptedRSS = "SELECT EncryptedRSSSquared FROM FingerPrint"
							+ " WHERE Xcoordinate = "
							+ Xmap.get(x)
							+ " AND Ycoordinate = "
							+ Ymap.get(x)
							+ " AND MACADDRESS = '"
							+ CommonMac[currentCol]
							+ "';";
					System.out.println("RSS SQL Statement: ");
					System.out.println(getRSS);
					System.out.println("Encrypted RSS SQL Statement: "); 
					System.out.println(getEncryptedRSS);
					
					Plainst = conn.createStatement();
					RSS = Plainst.executeQuery(getRSS);
					EncSt = conn.createStatement();
					EncryptedRSS =  EncSt.executeQuery(getEncryptedRSS);
					while (RSS.next())
					{
						Pinsert [x][currentCol] = RSS.getInt("RSS");
					}
					//CHECK IF I GOT A NULL!
					if (Pinsert[x][currentCol]==0)
					{
						Pinsert[x][currentCol] = -120;
					}
					
					while (EncryptedRSS.next())
					{
						Einsert [x][currentCol] = EncryptedRSS.getString("EncryptedRSSSquared");
					}
					if (Einsert[x][currentCol]==null)
					{
						Einsert[x][currentCol] = NULL.toString();
					}
					//This is crucial to "Refresh" the data
					Plainst.close();
					EncSt.close();
				}
			}
			//This For Loop is for debugging purposes...
			String tuple = "";
			for (int i = 0;i<Xmap.size();i++)
			{
				for (int j=0;j<NUMBEROFMACS;j++)
				{
					tuple += Pinsert[i][j] + " ";
					//tuple += Einsert[i][j] + " ";
				}
				System.out.println("Tuple at Index " + i + ": " + tuple);
				tuple = "";
			}
			System.out.println("The look up table is a " + Xmap.size() + " X " + NUMBEROFMACS + " 2D-Array" );
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
	
	/*
	 	Insert the data acquired from GetDataforLUT(), and 
	 	insert it into the tables that were just created 
	 */
	public void UpdateTables()
	{
		try
		{
			//Create a mysql database connection
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			String append = "";
			for (int i=0;i<NUMBEROFMACS;i++)
			{
				append += " ?,";
			}
			append = append.substring(0, append.length() - 1);//Remove the Extra , at the end!!
			append += ");";
			//The Insert Statement for Plain Text
			String PlainQuery = "insert into REUPlainLUT"
			+ " values (?, ?, ?," + append;
			//Using default(Index, X Coordinate, Y Coordinate)
			//Followed by the number of RSS values needed
			
			//The Insert Statement for Encrypted Table
			String EncryptQuery = "insert into REUEncryptedLUT"
			+ " values (?, ?, ?," + append;
			
			PreparedStatement preparedStmtOne;
			PreparedStatement preparedStmtTwo; 
			
			//Create the Insert Statement
			for (int i = 0; i < Xmap.size(); i++)
			{
				preparedStmtOne = conn.prepareStatement(PlainQuery);
				preparedStmtTwo = conn.prepareStatement(EncryptQuery);
				//Fill up the PlainText Table Part 1
				preparedStmtOne.setInt (1, (i + 1));//Oh...(1, 1)
				preparedStmtOne.setDouble(2, Xmap.get(i));
				preparedStmtOne.setDouble(3, Ymap.get(i));
				
				preparedStmtTwo.setInt (1, (i + 1));//Oh...(1, 1)
				preparedStmtTwo.setDouble(2, Xmap.get(i));
				preparedStmtTwo.setDouble(3, Ymap.get(i));
				
				for (int j = 0; j < NUMBEROFMACS;j++)
				{
					switch(j)
					{
						case 0:
							preparedStmtOne.setInt(4, Pinsert[i][j]);
							preparedStmtTwo.setString(4, Einsert[i][j]);
							break;
						case 1:
							preparedStmtOne.setInt(5, Pinsert[i][j]);
							preparedStmtTwo.setString(5, Einsert[i][j]);
							break;
						case 2:
							preparedStmtOne.setInt(6, Pinsert[i][j]);
							preparedStmtTwo.setString(6, Einsert[i][j]);
							break;
						case 3:
							preparedStmtOne.setInt(7, Pinsert[i][j]);
							preparedStmtTwo.setString(7, Einsert[i][j]);
							break;
						case 4:
							preparedStmtOne.setInt(8, Pinsert[i][j]);
							preparedStmtTwo.setString(8, Einsert[i][j]);
							break;
						case 5:
							preparedStmtOne.setInt(9, Pinsert[i][j]);
							preparedStmtTwo.setString(9, Einsert[i][j]);
							break;
						case 6:
							preparedStmtOne.setInt(10, Pinsert[i][j]);
							preparedStmtTwo.setString(10, Einsert[i][j]);
							break;
						case 7:
							preparedStmtOne.setInt(11, Pinsert[i][j]);
							preparedStmtTwo.setString(11, Einsert[i][j]);
							break;
						case 8:
							preparedStmtOne.setInt(12, Pinsert[i][j]);
							preparedStmtTwo.setString(12, Einsert[i][j]);
							break;
						case 9:
							preparedStmtOne.setInt(13, Pinsert[i][j]);
							preparedStmtTwo.setString(13, Einsert[i][j]);
							break;
						default:
							System.out.println("ERROR");
							break;
					}
				}
				System.out.println(preparedStmtOne.toString());
				System.out.println(preparedStmtTwo.toString());
				preparedStmtOne.execute();
				preparedStmtTwo.execute();
				
				//Close statements, so I can reuse it again!
				preparedStmtOne.close();
				preparedStmtTwo.close();
			}
			//DONT FORGET TO COMMIT!!!
			Statement commit = conn.createStatement();
			commit.executeQuery("commit;");
			conn.close();
		}
		catch(SQLException se)
		{
			System.err.println("SQL EXCEPTION SPOTTED!!!");
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		String Q1 = "SELECT MACADDRESS, Count(MACADDRESS) as count from FIU.FingerPrint group by MACADDRESS ORDER BY count DESC LIMIT 10;";
		System.out.println(Q1);
		
		PublicKey keyused = new PublicKey();
		BuildLookupTable build = new BuildLookupTable(keyused);
		build.getCommonMAC(Q1);
		for (int i=0; i < NUMBEROFMACS;i++)
		{
			columnMapping.put(ColumnNames[i], CommonMac[i]);
			System.out.println("MAC " + (i+1) + ": " + CommonMac[i]);
		}
		/*
		 * 	Get XY
		 *  Order by is not needed but it makes it look a bit nicer!
		 */
		
		//This order by ascending x is crucial to double check my work (See how I get Data for Lookup Table Method)
		String Q2 = "select distinct Xcoordinate, Ycoordinate from FingerPrint Order By Xcoordinate ASC;";
		build.getXY(Q2);
		for (int i=0; i < Xmap.size();i++)
		{
			System.out.println("X: " + Xmap.get(i) + " Y: " + Ymap.get(i));
		}
		/*
		 * Build the Tables: (It was already made)
		 */
		//build.createTables();
		//build.GetDataforLUT();
		//build.UpdateTables();
		
		//Print the Database to a CSV File
		String Q3 = "SELECT * from FIU.REUPlainLUT";
		String Q4 = "SELECT * from FIU.REUEncryptedLUT";
		String PlainCSV = "C:\\Users\\Andrew\\Desktop\\LookUpTable1.csv";
		String EncryptedCSV = "C:\\Users\\Andrew\\Desktop\\LookUpTable2.csv";
		try
		{
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, username, password);

			// create the java statement
			Statement stOne = conn.createStatement();
			Statement stTwo = conn.createStatement();
			// execute the query, and get a java result set
			ResultSet P = stOne.executeQuery(Q3);
			ResultSet E = stTwo.executeQuery(Q4);

			PrintWriter WriteP = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(EncryptedCSV))));

			PrintWriter WriteE = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(PlainCSV))));

			String tuple = "";
			while(P.next())
			{
				tuple += P.getDouble("Xcoordinate") + ",";
				tuple += P.getDouble("Ycoordinate")  + ",";;
				tuple += P.getInt("ONE")  + ",";
				tuple += P.getInt("TWO")  + ",";
				tuple += P.getInt("THREE")  + ",";
				tuple += P.getInt("FOUR")  + ",";
				tuple += P.getInt("FIVE")  + ",";
				tuple += P.getInt("SIX")  + ",";
				tuple += P.getInt("SEVEN")  + ",";
				tuple += P.getInt("EIGHT") + ",";
				tuple += P.getInt("NINE")  + ",";
				tuple += P.getInt("TEN") + ",";
				WriteP.println(tuple);
				tuple = "";
			}
			while(E.next())
			{
				tuple += E.getDouble("Xcoordinate") + ",";
				tuple += E.getDouble("Ycoordinate")  + ",";;
				tuple += E.getString("ONE")  + ",";
				tuple += E.getString("TWO")  + ",";
				tuple += E.getString("THREE")  + ",";
				tuple += E.getString("FOUR")  + ",";
				tuple += E.getString("FIVE")  + ",";
				tuple += E.getString("SIX")  + ",";
				tuple += E.getString("SEVEN")  + ",";
				tuple += E.getString("EIGHT") + ",";
				tuple += E.getString("NINE")  + ",";
				tuple += E.getString("TEN") + ",";
				WriteE.println(tuple);
				tuple = "";
			}
			
			WriteP.close();
			WriteE.close();
			System.out.println("Don't forget to put the Header Info on the CSV Files!");
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch(SQLException sql)
		{
			sql.printStackTrace();
		}
		catch(ClassNotFoundException cnf)
		{
			cnf.printStackTrace();
		}
	}
}

