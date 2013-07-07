
public class AbstractSystemODE implements ODE {

	AbstractFunction[] equations;
	public AbstractSystemODE(AbstractFunction... eqs) {
		equations = eqs;
		
		
	}

	protected double[][] solve(int steps, double[] initialConditions, double h) {
		double results[][] = new double[steps][equations.length];
		results[0] = initialConditions;
		for (int i=1; i <= steps; i++)
			for (int element = 0; element < results[i].length; element++)			
				results[i][element] = results[i-1][element] + 
					h*equations[element].getResult(element, results[i-1]);
		return results;
	}

	@Override
	public void integrate(int steps, double[] initialConditions, double h) {
		// TODO Auto-generated method stub
		solve(steps, initialConditions, h);
		
	}

	@Override
	public double[] getResult(int time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] getResults() {
		// TODO Auto-generated method stub
		return null;
	}

}
