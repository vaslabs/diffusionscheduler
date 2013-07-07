/**
 * implements the AbstractODE for diffusion
 * This is a class for experimentation and for providing
 * a framework to work
 * @author nicolav0
 *
 */
public class DiffusionODE extends AbstractODE
{


	double integrationStep = 0.01;

	public DiffusionODE(RingDiffusionFunction f)
	{
		super(f);
	}	


	public DiffusionODE(RingDiffusionFunction f, double h)
	{
		super(f);
		integrationStep = h;
	}

	@Override
	public double[][] solve(int steps, double[] initialConditions, double h)
	{
		double result[][] = new double[steps+1][initialConditions.length];

		result[0] = initialConditions;
		for (int i=1; i <= steps; i++)
			for (int element = 0; element < result[i].length; element++)			
				result[i][element] = result[i-1][element] + 
					integrationStep*f.getResult(element, result[i-1]);
		return result;
	}


	
	
	
}
