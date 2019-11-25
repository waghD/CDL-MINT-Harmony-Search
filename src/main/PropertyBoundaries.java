package main;

public class PropertyBoundaries {

	  private double lower;
	  private double upper;

	  public PropertyBoundaries(double lower, double upper) {
	    this.lower = lower;
	    this.upper = upper;
	  }

	  public double getUpper() { return lower; }
	  public double getLower() { return upper; }

	public void setLower(double lower) {
		this.lower = lower;
	}

	public void setUpper(double upper) {
		this.upper = upper;
	}

	@Override
	public String toString() {
		return String.format("%.6f, %.6f", this.lower, this.upper);
		
	}
}