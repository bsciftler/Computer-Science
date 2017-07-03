
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread
{	
	final static int portNumber = 9254;
	ServerSocket SQLServer;
	Socket incomingClient;
	int compute;
	Scanner input;
	int counter = 1;
	
	private boolean secure;
	double [] location;
	EuclideanComputation comp;
	/*
	 * 	REU Variables
	 */
	PublicKey pk = new PublicKey();//Public Key
    SecureTriple transmission; //For Encrypted Paillier Transmission
    String [] MACAddress; //For unencrypted transmission Part 1
    Integer [] RSS;//For unencrpypted transmission Part 2
	
    /*
     *  Time
     */
    long startTime = System.nanoTime();
    static long Billion = 1000000000;
    
	public Server() throws IOException
	{
		SQLServer = new ServerSocket(portNumber);
	}
	

	public void run()
	{
		while (true)
		{
			try
			{
				/*
				 * 	Establish Connection to Client
				 * 	Acquire Data
				 */
				incomingClient = SQLServer.accept();//Accept an incoming Client Socket
				//input = new Scanner(incomingClient.getInputStream());//Read from the Client Socket
				//compute = input.nextInt();
				
				ObjectInputStream fromClient= new ObjectInputStream(incomingClient.getInputStream());
				
//				Get the Paillier Key
//				try
//				{
//					pk = (Paillier.PublicKey)fromClient.readObject();
//				}
//				catch(ClassNotFoundException cnf)
//				{
//					System.out.println("FAILURE GETTING PAILLIER KEY");
//					cnf.printStackTrace();
//				}
				
				//If I dont have a MAC Address I probably have secure transmission!
				//Is it encrypted/unencrypted
				try
				{
					MACAddress = (String [])fromClient.readObject();
					RSS = (Integer [])fromClient.readObject();
					System.out.println("UNSECURE DATA RECEIVED");
					secure=false;
				}
				catch(ClassNotFoundException cnf)
				{
//					If I fail here, then I have big trouble!
					try 
					{
						transmission = (SecureTriple) fromClient.readObject();
						System.out.println("SECURE DATA RECEIVED");
						secure = true;					
					}
					catch (ClassNotFoundException cnfTwo)
					{
						cnfTwo.printStackTrace();
					}
				}
				
				
		
				/*
				 	After getting the data, send it to the Euclidean Computation Class
				  	Do Required Computations...see Euclidean Computation Class
				 */
				
				if (secure==true)
				{
					comp = new EuclideanComputation(transmission, pk);
				}
				else
				{
					comp = new EuclideanComputation(MACAddress,RSS);
				}
				location = comp.findCoordinate();

				//compute = compute*=2;

				/*
				 * 	Return Data to Client
				 */
				
				ObjectOutputStream responseToClient = new ObjectOutputStream(incomingClient.getOutputStream());
				responseToClient.writeObject(location);
				//PrintStream responseToClient = new PrintStream(incomingClient.getOutputStream());
				//responseToClient.println(compute); //Give back the X, Y Coordinate
				
				
				this.closeClientConnection();//Close Connection of Socket
				long estimatedTime = System.nanoTime() - startTime;
				

				System.out.println(counter + ": Computation completed from Client at: " + incomingClient.getInetAddress() 
						+ " and it took " + (estimatedTime/Billion) + "seconds");
				++counter;
			}
			catch(IOException IOE)
			{
				IOE.printStackTrace();
			}
		}
	}

	public void closeClientConnection()
	{
		if (incomingClient!=null)
		{
			if (incomingClient.isConnected())
            {
                try 
                {
					incomingClient.close();
				}
                catch (IOException ioe)
                {
                	ioe.printStackTrace();
				}
            }
		}
	}
	
	public static void main(String args []) throws IOException
	{
		Thread com = new Thread(new Server());
		com.start();
	}
	
	/*
	 * 	To do this correctly:
	 * 	1- Run the Server Class
	 *  2- Run the Client Class
	 */
}
