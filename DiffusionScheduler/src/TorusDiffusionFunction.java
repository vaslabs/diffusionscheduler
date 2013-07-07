import java.util.Arrays;


public class TorusDiffusionFunction extends RingDiffusionFunction{

	private int[][] matching;
	
	public TorusDiffusionFunction(double diffusion_constant)
	{
		super(diffusion_constant);
	}
	
	@Override
	public double calculate(int elems, double... params) {
		// TODO Auto-generated method stub
		double[][] torus = oneD2TwoD(params);
		double current = params[elems];
		double[] adjacent_values = getAdjacentValues(elems, torus);		
		for (int j=0; j < adjacent_values.length; j++)			
			current += adjacent_values[j];
		current -= params[elems];
		current *= DC;
		return 0;
	}
	
	private double[][] oneD2TwoD(double... array)
	{
		matching = new int[array.length][2];
		int sizeX = (int)Math.sqrt(array.length);
		double[][] twoD = new double[sizeX][];
		for (int i=0; i < sizeX; i++)
		{
			twoD[i] = new double[sizeX];

			for (int j=0; j<sizeX; j++)
			{
				int p = i*(sizeX - 1);
				twoD[i][j] = array[p];
				matching[p][0] = i;
				matching[p][1] = j;
			}//for
			
		}//for		
	
		int remained = array.length - sizeX*sizeX; 
		int l = twoD[sizeX-1].length;
		twoD[sizeX - 1] = Arrays.copyOf(twoD[sizeX - 1],  l + remained);
		
		for (int x=0; x < remained; x++)
			twoD[sizeX - 1][x+l] = x;
		
		return twoD;

	}

	private double[] getAdjacentValues(int elem, double[][] torus)
	{
		int left, right, top, bottom;
		int[] coordinates = matching[elem];
		left = coordinates[0] - 1;
		if (left < 0)
			left = torus.length - 1;
		right = coordinates[0] + 1;
		if (right >= torus.length)
			right = 0;
		top = coordinates[1] - 1;
		if (top < 0)
			top = torus[coordinates[1]].length - 1;
		bottom = coordinates[1] + 1;
		if (bottom >= torus[coordinates[1]].length)
			bottom = 0;
		double[] result =  {torus[coordinates[1]][top], torus[coordinates[1]][bottom], 
			torus[left][coordinates[0]], torus[right][coordinates[0]]};
		return result;
		
	}
}
