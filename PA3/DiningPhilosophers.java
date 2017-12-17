

import java.util.Scanner;

/**
 * Class DiningPhilosophers 
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca   
 * 
 * HAO LEI 
 * COMP 346 PA3
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			// ...
			
			int iPhilosophers = set_Philosopher_Number();

			// ...
			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Get start time in milliseconds 
			long start = System.currentTimeMillis();
			
			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);
			
			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();
			
			// Get elapsed time in milliseconds 
			long elapsedTimeMillis = System.currentTimeMillis()-start;
			float elapsedTimeSec = elapsedTimeMillis/1000F;
			
			System.out.println("All philosophers have left. System terminates normally.");
			System.out.println("Elapsed time in seconds is "+elapsedTimeSec);
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
	
	// ... 
	
	//user can set the number of philosopher 
	//the number must be positive integer
	//other format of input will be regarded as illegal and the default number (4) will be used
	//when the command line argument is empty, default number (4) will be used
	//@return legal setting number or default number
	
	public static int  set_Philosopher_Number () {
		
		try{
			
			Scanner input = new Scanner(System.in);
			
			System.out.println("Please Set The Number Of Philosophers: ");
			String line = input.nextLine();
			input.close();
			
			int set = Integer.parseInt(line);	
			
			if(set <= 0){	
				System.out.println(set + " Is Not A Positive Decimal Integer");
				System.out.println("Useage: java DiningPhilosophers ["+DEFAULT_NUMBER_OF_PHILOSOPHERS+"]");
				System.out.println(" ");
				return DEFAULT_NUMBER_OF_PHILOSOPHERS;
			} else {
				System.out.println("There Will Be "+set+" Philosophers Eating On The Table");
				System.out.println(" ");
				return set;
			}
			
		} catch(NumberFormatException e){
		
			System.out.println("Empty Input or Illegal Input Format.");
			System.out.println("The Default Number Of Philosophers "+ DEFAULT_NUMBER_OF_PHILOSOPHERS+" will be used.");
			System.out.println(" ");
			return DEFAULT_NUMBER_OF_PHILOSOPHERS;
			}
		}
	
	// ...
	
}

// EOF
