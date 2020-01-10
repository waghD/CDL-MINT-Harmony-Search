package harmony;

public class HarmonyResult {
	private int nrOfIterationsForOptimum = 0;
	private double runtimeAllIterations = 0;
	private double runtimeTilOptimumFound = 0;
	
	public int getNrOfIterationsForOptimum() {
		return nrOfIterationsForOptimum;
	}
	public void setNrOfIterationsForOptimum(int nrOfIterationsForOptimum) {
		this.nrOfIterationsForOptimum = nrOfIterationsForOptimum;
	}
	public double getRuntimeIterations() {
		return runtimeAllIterations;
	}
	public void setRuntimeIterations(double runtimeIterations) {
		this.runtimeAllIterations = runtimeIterations;
	}
	
	public double getRuntimeTilOptimumFound() {
		return runtimeTilOptimumFound;
	}
	public void setRuntimeTilOptimumFound(double runtimeTilOptimumFound) {
		this.runtimeTilOptimumFound = runtimeTilOptimumFound;
	}
	
	public String toString() {
		return "Number of iterations til optimum found: " + this.nrOfIterationsForOptimum
				+ "\nRuntime of all iterations [s]: " + this.runtimeAllIterations
				+ "\nRuntime til optimum found [s]: " + (this.runtimeTilOptimumFound > 0.0 ? this.runtimeTilOptimumFound: "No optimum found");
		
	}
	
	
	
	
}