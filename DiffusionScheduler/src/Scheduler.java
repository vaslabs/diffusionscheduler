import java.util.ArrayList ;
import java.util.Arrays;
import java.util.List;
/** 
 * provides a framework for implementing various schedulers.
 * @author nicolav0
 *
 */
public abstract class Scheduler {

	protected static double sliceSize = 0.001;
	
	protected List<Process> processes;
	public Scheduler()
	{
		processes = new ArrayList<Process>();
	}
	
	public void addProcess(Process p)
	{
		processes.add(p);
	}
	
	public Process getProcess(int index)
	{
		return processes.get(index);
	}
	
	public abstract void run();
	
	
	
}
