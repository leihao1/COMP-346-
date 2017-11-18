package task4;
import java.util.ArrayList;

// Source code for semaphore class:

class Semaphore 
{
         private int value;
         private   ArrayList<Integer> waitlist = new ArrayList<>(); //waitlist shows the threads that waiting for semaphore
         

         public Semaphore(int value)
         {
        	 //**************************
        	 //semaphore can not be initialized to a negative value
        	 if (value >= 0){
                  this.value = value;
        	 }else {
        		 
        		 throw new IllegalArgumentException("Semaphore can not be initialized to a negative value");
        	 }
        	 //*****************************
         }
         public Semaphore()
         {	
                 this(0);
         }
         public synchronized void Wait(int w)
         {
        	 //**********************
        	 
        	 this.value--;
        	
        	 if (this.value < 0)
             {  
                 try
                 {
                	 this.waitlist.add(w); //add threads to waitlist when they call wait and can not get semaphore
                	 
                	 // shows semaphore name , thread ID that  are waiting in the waiting list 
                	
                	 System.out.println (StackManager.consumer.waitlist.size()+" threads waiting on Semaphore 'consumer'. TID: "+ StackManager.consumer.waitlist);
                     System.out.println (StackManager.mutex.waitlist.size()+" threads waiting on Semaphore 'mutex'. TID: "+StackManager.mutex.waitlist );
                	 
                	 wait();
                	 
                	 //remove threads from waitlist when they are notified and get semaphore
                	 for(int i = 0;i < waitlist.size();i++) {
              		   if (waitlist.get(i) == w) {
              			   waitlist.remove(i);
              			   i--;
              		   }
                     }
                	 
                	 System.out.println (StackManager.consumer.waitlist.size()+" threads waiting on Semaphore 'consumer'. TID: "+ StackManager.consumer.waitlist);
            	     System.out.println (StackManager.mutex.waitlist.size()+" threads waiting on Semaphore 'mutex'. TID: "+StackManager.mutex.waitlist );
                	 
                	 
                 } catch(InterruptedException e) 
                 {
                	 System.out.println ("Semaphore::Wait() - caught InterruptedException: " + e.getMessage() );
                     e.printStackTrace();
                 }
             }
        	 
         }
        
       //***************************************
           public synchronized void Signal()
           {
                   ++this.value;
                   notify();
                   
           }
           public synchronized void P(int p)
           {
                   this.Wait(p);
           }
          public synchronized void V()
          {
                   this.Signal();
          }
} 