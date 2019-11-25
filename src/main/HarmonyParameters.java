package main;

import java.util.List;
import java.util.Map;

public class HarmonyParameters {
	private double r_pa;
	private double band;
	private double r_accept;
	private List<Map<String,PropertyBoundaries>> memory;

	
	public HarmonyParameters(List<Map<String,PropertyBoundaries>> solutions, double r_pa, double band,double r_accept) {
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

	public List<Map<String,PropertyBoundaries>> getSolutions() {
		return memory;
	}

	public void setSolutions(List<Map<String,PropertyBoundaries>> solutions) {
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
	
	
	public String toString() {
		return String.format("Acceptance rate (r_accept): %.2f\n Parameter Adjustment Rate (r_pa): %.2f\n" + 
				"bandwith (band) = %.2f (= new value between 0 and %.3f)\n" +
				"Memory size (solutions) = %d", this.r_accept, this.r_pa, this.band, this.band, memory.size());
	}
	
	

}
