/**
 * Function that implements the diffusion equation
 * in a ring of cells
 * @author nicolav0
 *
 */
public class RingDiffusionFunction extends AbstractFunction
{
	public final double DC;
	
	public RingDiffusionFunction(double diffusion_constant)
	{
		DC = diffusion_constant;
	}

	
	@Override
	public double calculate(int index, double... params)
	{
		//first parameter is an int


		double current = params[index];
		int[] adjacent_cells = ring_diffusion(params, index);		
		for (int j=0; j < adjacent_cells.length; j++)			
			current += params[adjacent_cells[j]];
		current -= adjacent_cells.length*params[index];
		current *= DC;
		
		return current;

	}//calculate

	private int[] ring_diffusion(double[] params, int index)
	{
		int[] adj = new int[2];
		adj[0] = index + 1;
		adj[1] = index - 1;
		if (adj[0] >= params.length)
			adj[0] = 0;
		if (adj[1]<0)
			adj[1] = params.length - 1;
		return adj;		
	}//ring_diffusion

}
