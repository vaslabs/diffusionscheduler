/**
 * interface for implementing ODE solvers
 * @author nicolav0
 *
 */
public interface ODE
{
	
	void integrate(int steps, double[] initialConditions, double h);
	
	double[] getResult(int time);

	
	double[][] getResults();
	

	public String toString();	
	
}
