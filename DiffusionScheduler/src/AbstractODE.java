/**
 * class that provides a framework for various ODE solvers
 * to be implemented.
 * @author nicolav0
 *
 */
public abstract class AbstractODE implements ODE
{
	protected double[][] result;
	AbstractFunction f;
	/**
	 * each solver requires a function that will integrate
	 * @param f
	 */
	public AbstractODE(AbstractFunction f)
	{	
		this.f = f;
		result = null;	
	}
	
	/**
	 * @param steps
	 * @param initialConditions
	 * @param h
	 * Call this to integrate your equation. It needs the implementation of solve()
	 */
	public void integrate(int steps, double[] initialConditions, double h)
	{
		result = new double[steps+1][initialConditions.length];
		result=solve(steps, initialConditions, h);
	}

	/**
	 * implement this so you can call integrate
	 */
	public abstract double[][] solve(int steps, double[] initialConditions, double h);
	
	public double[] getResult(int time)
	{
		return result[time];
	}

	/**
	 * get the results in an array of double time x results
	 */
	public double[][] getResults()
	{
		return result;
	}

	/**
	 * Human readable representation of the solution
	 */
	public String toString()
	{
		String output = "Result\n========\n";
		for (int i=0; i < result.length; i++)
		{
			output+="Step " + i + ": ";
			for (int j=0; j < result[i].length; j++)
				output+=result[i][j] + " ";
			output+="\n";
		}	
		return output;	
	}
}
