/**
 * A rough implementation of the Priority scheduler that assigns randomly probabilities to
 * processes and them executes them accordingly
 * @author nicolav0
 *
 */
public class PriorityScheduler extends Scheduler {

	//statistics
	private int calls = 0;
	private double[] waits;
	private double totalTime = 0;
	private double[] liveTime;

	
  public void run() {
	waits = new double[processes.size()];
  	liveTime = new double[processes.size()];
    while (true) {
      int noProcs = processes.size() ;
      if (noProcs == 0) break ;
      Process nextRunner = processes.get(0) ;
      int priority = nextRunner.getCount();
      int next = 0;
      for (int i = 1; i < noProcs; i++)
      {
        Process candidateProc = processes.get(i);
        int candidatePriority = candidateProc.getCount();
        if (candidatePriority > priority)
        {
          priority = candidatePriority;
          nextRunner = candidateProc;
          next = i;
        }//if
        
      }//for
      double old_size = nextRunner.size;
      Process.ProcessMessage pm = nextRunner.execute(0.3);
      double time_used = old_size - nextRunner.size;

      for (int i = 0; i < noProcs; i++)
      {
    	  if (i != next)
    		waits[i]+=time_used;
    	  liveTime[i] += time_used;
      }//for
	    //System.out.println("Process: " + nextRunner.id + " Message: " + pm);
	  if (pm == Process.ProcessMessage.FINISHED)
		  processes.remove(next);
	  calls++;
	  totalTime+=time_used;
    }//while
    
    System.out.println("Loops: " + calls);
    System.out.println("Total time: " + totalTime);
    System.out.println("Total inactive time for each process\n-----------------\n");
    double sum = 0;
    for (int i=0; i < waits.length; i++)
    {
    	sum+=waits[i];
    	//System.out.println("Process: " + i + " inactive steps: " + waits[i]);
    }
    System.out.println("Average: " + sum/waits.length);
    System.out.println("Total time for each process to be executed\n-----------------\n");
    sum = 0;
    for (int i=0; i < waits.length; i++)
    {
    	sum+=liveTime[i];
    	//System.out.println("Process: " + i + " total time: " + liveTime[i]);
    }
    System.out.println("Average: " + sum/waits.length);
  }//run
}//class
