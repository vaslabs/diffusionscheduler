/**
 * 
 *Provides an implementation of a function so it can be
 *used in various ode's. Instead of developing a symbolic
 *solver this is the easiest way to protect variations for each function.
 * @author nicolav0
 * 
 */
public abstract class AbstractFunction
{
	protected double result = 0;

	/**
	 * 
	 * @param elems
	 * @param params
	 * @return result
	 * Defines the actual function and calculates the results
	 */
	protected abstract double calculate(int elems, double... params);

	/**
	 * 
	 * @param elems
	 * @param params
	 * @return a new calculated result
	 * Call this method to run a calculation of the function and get a new result
	 */
	public double getResult(int elems, double... params)
	{
		result = calculate(elems, params);
		return result;
	}

	/**
	 * 
	 * @param elems
	 * @param params
	 * @return a new calculated result
	 * Overload to ensure that the use of Double instead of double does 
	 * not affect the functionality
	 */
	public double getResult(int elems, Double... params)
	{
		int i = 0;
		double[] values = new double[params.length];
		for (Double d : params)
		{
			values[i++] = d.doubleValue();
		}
		result = calculate(elems, values);
		return result;
	}
	
	/**
	 * get the last calculated result, useful to check the previous
	 * value if there is one before running another calculation
	 * @return previous result
	 */
	public double getResult()
	{
		return result;
	}
	
	
}
