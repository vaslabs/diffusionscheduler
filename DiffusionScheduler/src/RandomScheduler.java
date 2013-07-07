import java.util.Random ;

/**
 * scheduler that chooses processes randomly for
 * execution
 * @author nicolav0
 *
 */
public class RandomScheduler extends Scheduler {

private Random randGen ;

    public RandomScheduler() {
	randGen = new Random() ;
    }
    
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
		    int next = Math.abs(randGen.nextInt()) % noProcs ;
		    Process nextRunner = processes.get(next) ;
		    double old_size = nextRunner.size;
		    
		    Process.ProcessMessage pm = nextRunner.execute(0.3);
		    double time_used = old_size - nextRunner.size;
		    for (int i = 0; i < noProcs; i++)
		    {
		    	if (i != next)
		    		waits[i]+=time_used;
		    	liveTime[i] += time_used;
		    }
		    //System.out.println("Process: " + nextRunner.id + " Message: " + pm);
		    if (pm == Process.ProcessMessage.FINISHED)
		    	processes.remove(next);
		    calls++;
		    totalTime+=time_used;
		}
	    
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

    }
}
