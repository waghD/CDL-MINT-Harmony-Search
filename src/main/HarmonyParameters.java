package main;

public class HarmonyParameters {
	private double r_pa;
	private double band;
	private double r_accept;
	private double[][] memory;

	
	public HarmonyParameters(double[][] solutions, double r_pa, double band,double r_accept) {
		this.r_pa = r_pa;
		this.memory = solutions;
		this.band = band;
		this.r_accept = r_accept;
	}

	public double getR_pa() {
		return r_pa;
	}

	public void setR_pa(double r_pa) {
		this.r_pa = r_pa;
	}

	public double[][] getSolutions() {
		return memory;
	}

	public void setSolutions(double[][] solutions) {
		this.memory = solutions;
	}

	public double getBand() {
		return band;
	}

	public void setBand(double band) {
		this.band = band;
	}

	public double getR_accept() {
		return r_accept;
	}

	public void setR_accept(double r_accept) {
		this.r_accept = r_accept;
	}
	
	

}
