/**
 * Implementation of the famous RoundRobinScheduler
 * @author nicolav0
 *
 */
public class RoundRobinScheduler extends Scheduler
{

	private int calls = 0;
	private double[] waits;
	private double totalTime = 0;
	private double[] liveTime;
  //run
  public synchronized void run()
  {
	waits = new double[processes.size()];
  	liveTime = new double[processes.size()]; 
    while (true)
    {
      int numberOfProcesses = processes.size();
      if (numberOfProcesses == 0) break;        
      for (int counter = 0; counter < numberOfProcesses; counter++)
      {
        int newNumberOfProcesses = processes.size();

        int next = counter + (newNumberOfProcesses - numberOfProcesses);
        Process nextRunner = processes.get(next);
        double old_time = nextRunner.size;
        Process.ProcessMessage pm = nextRunner.execute(0.3);
        double time_used = old_time - nextRunner.size;
	    //System.out.println("Process: " + nextRunner.id + " Message: " + pm);
	    if (pm == Process.ProcessMessage.FINISHED || pm == Process.ProcessMessage.ERROR)
	    	processes.remove(next);
        calls++;
        for (int i = 0; i < newNumberOfProcesses; i++)
	    {
	    	if (i != next)
	    	{
	    		waits[i]+=time_used;
	    	}
	    	liveTime[i] += time_used;
	    }
        totalTime+=time_used;
      }//for    
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
