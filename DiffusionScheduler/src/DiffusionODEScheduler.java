import java.util.ArrayList;
import java.util.List;

/**
 * This is a diffusion inspired scheduler, that uses
 * the model of reaction diffusion to solve the process scheduling
 * problem as a terms of cell organisation
 * @author nicolav0
 *
 */
public class DiffusionODEScheduler extends Scheduler
{
	RingDiffusionFunction diffusion;
	private int calls = 0;
	private double[] waits;
	private double totalTime = 0;
	private double[] liveTime;
	
	public DiffusionODEScheduler(RingDiffusionFunction d)
	{
		diffusion = d;
	}
	
	public DiffusionODEScheduler()
	{
		diffusion = new RingDiffusionFunction(1);
	}
	
	//run
	public void run()
	{
		waits = new double[processes.size()];
    	liveTime = new double[processes.size()];
		//initialise diffusion
		//calculate the initial concentrations at random
    	diffusion = new RingDiffusionFunction(15);
		List<Double> concentrations = new ArrayList<Double>();
		List<Double> timeUsed = new ArrayList<Double>();
		for (int i=0; i < this.processes.size(); i++)
			concentrations.add(0.3);
		int calls = 0;
		double RC = 1;
		while (true)
		{
			  
		    int numberOfProcesses = processes.size();
		    if (numberOfProcesses == 0) break;        
		    
		    for (int counter = 0; counter < processes.size(); counter++)
		    {
		        int newNumberOfProcesses = processes.size();

		        int next = counter + (newNumberOfProcesses - numberOfProcesses);
		        Process nextRunner = processes.get(next);
		        double old_size = nextRunner.size;
		        Process.ProcessMessage pm = nextRunner.execute(concentrations.get(next));
			    //System.out.println("Process: " + nextRunner.id + " Message: " + pm);
			    double time_used = old_size - nextRunner.size;
			    if (pm == Process.ProcessMessage.FINISHED || pm == Process.ProcessMessage.ERROR)
			    {
			    	concentrations.remove(next);
			    	if (next < timeUsed.size()) timeUsed.remove(next);
			    	processes.remove(next);
			    	
			    }
			    else
			    {   	
			    	timeUsed.add(next, time_used);	
			    }
			    totalTime += time_used;
			    for (int i = 0; i < newNumberOfProcesses; i++)
			    {
			    	if (i != next)
			    	{
			    		waits[i]+=time_used;
			    	}
			    	liveTime[i] += time_used;
			    }
		        calls++;
		     }//for    
		    
		      double[] newConcentrations = new double[concentrations.size()];
		      for (int i = 0; i < concentrations.size(); i++)
		      {
		    	  if (i < timeUsed.size())
		    		  newConcentrations[i] = 0;
		    	  else
		    		  newConcentrations[i] = concentrations.get(i);
		      }
		      //euler method for concentrations
		      double[] holdArray = new double[newConcentrations.length];
		      
		    	  

		      //update results
		      for (int i = 0; i < holdArray.length; i++)
		      {
		    	  holdArray[i] = diffusion.DC*diffusion.calculate(i, newConcentrations);
		    	  concentrations.set(i, concentrations.get(i)+0.001*(newConcentrations[i]+holdArray[i]));
		    	  if (concentrations.get(i) < 0.0)
		    		  concentrations.set(i, 0.0);
		      }
		     
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


}
