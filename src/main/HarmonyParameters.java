package main;

public class HarmonyParameters {
	private double r_pa;
	private double band;
	private double r_accept;

	public HarmonyParameters(double r_pa, double band, double r_accept) {
		this.r_pa = r_pa;
		this.band = band;
		this.r_accept = r_accept;
	}

	public double getR_pa() {
		return r_pa;
	}

	public void setR_pa(double r_pa) {
		this.r_pa = r_pa;
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
