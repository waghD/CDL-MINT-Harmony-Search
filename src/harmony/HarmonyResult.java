package harmony;

public class HarmonyResult {
	private boolean optimumFound;
	private int nrOfIterationsForOptimum = 0;
	private double runtimeAllIterations = 0;
	private double runtimeTilOptimumFound = 0;
	private double avgBestPrecision = 0;
	private double avgBestRecall = 0;
	private double avgBestFMeasure = 0;
	private double absoluteOffset = 0;
	
	public double getAbsoluteOffset() {
		return absoluteOffset;
	}
	
	public void setAbsoluteOffset(double offset) {
		this.absoluteOffset = offset;
	}
	public double getAvgBestPrecision() {
		return avgBestPrecision;
	}
	
	public void setAvgBestPrecision(double prec) {
		this.avgBestPrecision = prec;
	}
	
	public double getAvgBestFMeasure() {
		return avgBestFMeasure;
	}
	
	public void setAvgBestFMeasure(double fMeasure) {
		this.avgBestFMeasure = fMeasure;
	}
	
	public double getAvgBestRecall() {
		return avgBestRecall;
	}
	
	public void setAvgBestRecall(double rec) {
		this.avgBestRecall = rec;
	}
	
	public boolean getOptimumFound() {
		return optimumFound;
	}
	
	public void setOptimumFound(boolean opt) {
		this.optimumFound = opt;
	}
	
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
