public class Main
{
	public static void main(String[] args)
	{
		AbstractODE ode = new DiffusionODE(new RingDiffusionFunction(0.1));
		double[] concentrations = new double[3];
		for (int i=0; i < concentrations.length; i++)
			concentrations[i] = Math.random();
		ode.integrate(3, concentrations, 1);
		System.out.println(ode.toString());
	}


}
