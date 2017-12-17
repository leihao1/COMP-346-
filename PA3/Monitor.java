

import java.util.ArrayList;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca  
 * 
 * HAO LEI 
 * COMP 346 PA3
 */
public class Monitor  
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	// ...
	
	//An array of chopsticks, 1 means available ,0 means not available
	private static int [] chopsticks;
	
	//check whether a philosopher can talk now
	private static boolean can_talk;
	
	//only those people in the waitlist can eat
	//when a philosopher finish eating , he/she will removed from waitlist
	//until everyone have been already eaten ,which means waitlist is empty 
	private ArrayList<Integer> waitlist = new ArrayList<>();

	// ...
	
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		// ...
		
		//number of chopsticks equals to the number of philosophers
		chopsticks = new int [piNumberOfPhilosophers];
		
		// all the chopsticks are available at first
		for (int i=0; i<chopsticks.length; i++){
			chopsticks[i] = 1;
		}
		
		//anyone can talk if the want to at begining
		can_talk = true;
		
		//anyone can eat at beginning (they are in the list)
		for(int i =0; i < chopsticks.length;i++) {
			waitlist.add(i, i+1);
		}
		
		
		// ...
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 * @throws InterruptedException 
	 */
	public synchronized void pickUp(final int piTID) throws InterruptedException
	{
		// ...
		
		//Philosopher i can pick up chopsticks[i-1] (to the left) and chopsticks[i] (to the right)
		int idx = piTID -1 ;
//		if (chopsticks.length <2) {
//			System.out.println("Waiting For Another Philosopher");
//			wait();
//		}
//		else {
		while(true){
			
			//if he/she in the waitlist then he/she can eat
			if (waitlist.contains(piTID)) {
				
				//check left and right chopsticks are both available
				if ( chopsticks.length >1 && chopsticks[idx] == 1 && chopsticks[(idx+1) % chopsticks.length] == 1 ) {
					
					//use those chopsticks
					chopsticks[idx] = 0;
					chopsticks[(idx+1) % chopsticks.length] =0;
					
					//System.out.println("Philosopher "+ piTID + " picks up chopsticks " + idx+", " + (idx+1) % chopsticks.length);
					
					//change eating turn to next philosopher 
					for(int i = 0;i < waitlist.size();i++) {
	              		   if (waitlist.get(i) == piTID) {
	              			   waitlist.remove(i);
	              		   }
	                     }
					break;
				
				} else {
					//System.out.println("Philosopher " + piTID + " is waiting for the chopsticks " + idx +", " + (idx+1) % chopsticks.length);
					wait();
				}
				
				
			//if there is nobody in the list , the waitlist will be reset
			} else if (waitlist.isEmpty()) {
				
				for(int i =0; i < chopsticks.length;i++) {
					waitlist.add(i, i+1);
				}
				continue;
				
			//you have been eaten ,but somebody still waiting to eat,so you have to wait()	
			} else {
				wait();
			}
		}
//		}
		//...
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
		
		int idx = piTID - 1;
		
		//make sure chopsticks at left and right both picked  
		if ( chopsticks [idx] == 0 && chopsticks [(idx+1) % chopsticks.length] == 0) {
			
			//drop those chopsticks
			chopsticks[idx] = 1;
			chopsticks[(idx+1) % chopsticks.length] = 1;
			
			//System.out.println("Philosopher "+ piTID + " put down chopsticks " + idx +", " + (idx+1)% chopsticks.length);

			notifyAll();
			
		} else {
			System.err.println("Chopsticks are already put down " + idx + ", " + (idx+1)% chopsticks.length);
		}
		
		// ...
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 * @throws InterruptedException 
	 */
	public synchronized void requestTalk(final int piTID) throws InterruptedException
	{
		// ...
		
		while(true) {
			
			//check nobody is talking and this philosopher is not eating (you can not talking and eating at same time)
			if (can_talk ) {
				
				//everyone else can not talk now
				can_talk = false;
				
				break;
				
			} else {
				wait();
			}
		}
		
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk(final int piTID)
	{
		// ...
		
		//others can talk now
		can_talk = true;
		
		notifyAll();
		
		// ...
	}
}

// EOF
