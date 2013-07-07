import java.util.ArrayList;

/**
 * A definition of a process that has various characteristics such as
 * process events, size in cycles, probability for IO blocking or crashing. 
 * @author nicolav0
 *
 */
public class Process {
	
	public enum ProcessEvent {RUN, IO, CRASH};
	public enum ProcessMessage {OK, PAUSED, FINISHED, ERROR}
	protected double initialSize;
	protected double size;
	private ArrayList<ProcessEvent> path;
	private double ioProbability;
	private double crashProbability;
	public final int id;
	private boolean isReset = false;
	private int count;
	public Process(int id)
	{
		size = Math.random()*3;
		path = new ArrayList<ProcessEvent>();
		ioProbability = Math.random() / 50; //at most 1/50 prob of expecting io
		crashProbability = Math.random()/100;// / 100; //at most 1/100 prob of crashing
		this.id = id;
		initialSize = size;
		isReset = false;
	}
	
	/**
	 * create a process with a priority count
	 * @param id
	 * @param count
	 */
	public Process(int id, int count)
	{
		this(id);
		this.count = count;
	}

	/**
	 * reset the current process for reuse
	 */
	public void reset()
	{
		size = initialSize;
	}
	
	/**
	 * execute the Process for time amount of cycles
	 * @param time
	 * @return
	 */
	public ProcessMessage execute(double time)
	{
		//System.out.println("Given a time slice " + time);
		int tStep = 0;
		for (double t=0; t <= time; t+=Scheduler.sliceSize)
		{
			if (isReset)
			{
				switch (path.get(tStep++))
				{
				case IO:
					path.add(ProcessEvent.IO);
					size-=t;
					return ProcessMessage.PAUSED;
				case CRASH:
					return ProcessMessage.ERROR;
				}
				
				if (size - t <= Math.exp(-10))
				{
					return ProcessMessage.FINISHED;
				}
			}
			double condition = Math.random();
			if (condition < ioProbability)
			{
				path.add(ProcessEvent.IO);
				size-=t;
				
				return ProcessMessage.PAUSED;
			}
			if (condition < crashProbability)
			{
				path.add(ProcessEvent.CRASH);
				return ProcessMessage.ERROR;
			}
			if (size - t <= Scheduler.sliceSize*0.1)
			{
				return ProcessMessage.FINISHED;
			}
		}
		
		size -= time;
		return ProcessMessage.OK;
		
	}

	/**
	 * get the priority of this process
	 * @return
	 */
	public int getCount() {
		
		return count;
	}

}
