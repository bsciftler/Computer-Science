import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Created by Andrew Quijano
//CUNYID: 14193591

public class Project1b extends Thread
{
	String [] commands;
	Scanner inputReader;
	
	public Project1b(String [] input, Scanner reader) throws IOException
	{
		commands=input;
		inputReader=reader;
	}
	/*
	 * 	This will be the method that has the function for echo
	 *  Possible Errors that can happen and will be caught
	 *  1- No quote.
	 *  quan3591> echo hello world
	 *  2- Only one quote
	 *  quan3591> echo "hello world
	 *  If any of those two situations happen, the shell will inform the
	 *  user which error happened.
	 *  
	 *  If everything works it will work as intended
	 *  quan3591> echo "hello world"
	 *  hello world
	 */
	public void echo(String input)
	{
		int index1=0;
		int echoTracker = 0;
		while (echoTracker < input.length())
		{
			if (input.charAt(echoTracker)=='"')
			{
				++echoTracker; //DO NOT RECOUNT FIRST QUOTE!
				index1=echoTracker;
				
				//Find the other Quote!
				while (echoTracker < input.length())
				{
					if (input.charAt(echoTracker)=='"')
					{
						//[a,b)
						String print = input.substring(index1,echoTracker);
						System.out.println(print);
						return;
					}
					++echoTracker; //Keep going
				}
				//If second quote is not found, let the user know..
				System.out.println("quan3591> INVALID ECHO COMMAND! SECOND " + " QUOTE IS MISSING!");
				return;
			}
			++echoTracker;
		}
		//If I am here, I didn't find first ' " ' meaning that someone put echo wrong!
		System.out.println("quan3591> INVALID ECHO COMMAND! FIRST QUOTE IS MISSING!");
	}
	
	public void run()
	{
		/*
		 * Use ASCII clear command to clear the console.
		 */
		if (commands[0].equals("clr"))
		{	
			System.out.println("\033[2J\033[;H");
			System.out.flush();
		}
		/*
		 * Echo command...See method for echo
		 */
		else if (commands[0].equals("echo"))
		{
			String echoInput =Arrays.toString(commands);
			echo(echoInput);
		}
			
		/*
		 * Help command:
		 * Purpose of method: Open readme.txt and print information in it.
		 * Expected return: Print the readme.txt onto console.
		 * Possible Error: I/O Exception
		 * Why?:
		 * Because the readmne.txt is not in the same directory as project1a.java
		 */
		
		else if (commands[0].equals("help"))
		{
			try
			{
				BufferedReader br = new BufferedReader(
				new InputStreamReader(
				new FileInputStream("readme")));
				String helpPrint;
				while ( (helpPrint = br.readLine()) != null)
				{
					System.out.println(helpPrint);
				}
				br.close();
			}
			catch (IOException ioe)
			{
				System.err.println("readme is NOT in the same directory as Project1.java. Closing...");
				return;
			}
		}
			
			/*
			 * Pause Method.
			 * Cause the script to pause. Until the
			 * ENTER KEY NOT "enter" is pressed!
			 * I detect the enter key if the input string is null.
			 */
			else if (commands[0].equalsIgnoreCase("pause"))
			{
				while (true)
				{
					String input=inputReader.nextLine();
					if (input.length()==0)
					{
						break;
					}
				}
			}
			
			/*
			 * Exit method.
			 * When the string "exit" the input.
			 * Close the Program.
			 */
			else if (commands[0].equals("exit"))
			{
				inputReader.close();
				System.exit(0);
			}
			
			/*
			 * If the command is not found here, I will send it to the Operating System
			 * Venus Server (CentOS 7) will take care of the process.
			 * For example: ls does NOT exist in this script.
			 * But I can pass ls to the CentOS 7 Operating System and
			 * the Operating System will know how to process it.
			 *
			 */
			else
			{		
				try
				{
					//Start Process
					Process p = new ProcessBuilder(commands).start();					
					
					//Check for the following errors:
					//1- The file/directory does not exist is found
					//2- More than one shell argument made
					InputStream Errorcheck = p.getErrorStream();
					InputStreamReader esr = new InputStreamReader(Errorcheck);
					BufferedReader errorReader = new BufferedReader(esr);
					String error = errorReader.readLine();
					
					if (error==null)
					{
						//If no error was spotted, obtain the output and print to console. 
						InputStream is = p.getInputStream();
						InputStreamReader isr = new InputStreamReader(is);
						BufferedReader br = new BufferedReader(isr);

						String line;
						while ( (line = br.readLine()) != null)
						{
							System.out.println(line);
						}
						br.close();
					}
					
					//Error spotted
					else if (error.contains("No such file or directory"))
					{
						System.err.println("The file does NOT exist or can not be opened!");
						System.err.println("OR: Incorrect number of command line arguments to my shell");
						System.err.println("Closing...");
						System.exit(0);
					}
					//Error spotted
					else if (error.contains("invalid option"))
					{
						System.err.println("The command does not exist or can not be executed");
					}
				}
				catch (IOException InvalidCommand)
				{
					System.err.println("The command does not exist or can not be executed");
				}
				catch (Exception e)
				{
					System.err.println("ELSE: Anything less than immortality is a complete waste of time!");
				}
			}
	}

	public static void main(String [] args)throws IOException, InterruptedException
	{	
		Scanner inputReader = new Scanner(System.in);//Scan input
		while (true)
		{	
			System.out.print("quan3591> "); //Shell Prompt
			String input=inputReader.nextLine();	
			input = input.trim(); //Clear out all space bars before command.
			String [] multipleCommands = input.split(";");
		
			//Create String [] for each multiple command...
			ArrayList<String []> wholeCommand = new ArrayList<String []>();
		
			for (int i=0;i<multipleCommands.length;i++)
			{
				wholeCommand.add(multipleCommands[i].split(" "));
			}
		
		//Initialize Threads and run them!
			for (int i=0;i<wholeCommand.size();i++)
			{	
				Thread execute = new Thread(new Project1b(wholeCommand.get(i), inputReader), Integer.toString(i));
				execute.run();
				System.out.println("Thread # "+execute.getName() + " is running!");
			}
		}
		
	}//END OF MAIN

}//End of CLASS