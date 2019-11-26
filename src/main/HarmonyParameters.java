package main;

import java.util.List;
import java.util.Map;

public class HarmonyParameters {

	private double r_pa; //pitch adjustment rate
	private double band; //bandwidth
	private double r_accept; //acceptance rate
	private int memorySize;
	private StreamCount streamCount;

	/**
	 * Initializes harmony parameters
	 *
	 * @param solutions .. list of solution maps
	 * @param r_pa .. pitch adjustment rate
	 * @param band .. bandwidth
	 * @param r_accept .. acceptance rate
	 */
	public HarmonyParameters(double r_pa, double band,double r_accept, int memorySize, StreamCount streamCount) {
		this.r_pa = r_pa;
		this.band = band;
		this.r_accept = r_accept;
		this.memorySize = memorySize;
		this.streamCount = streamCount;
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
	
	public int getMemorySize() {
		return memorySize;
	}
	
	public StreamCount getStreamCount() {
		return streamCount;
	}
	
	public void setStreamCount(StreamCount streamCount) {
		this.streamCount = streamCount;
	}

	public String toString() {
		return String.format("Acceptance rate (r_accept): %.2f\nParameter Adjustment Rate (r_pa): %.2f\n" +
				"bandwith (band) = %.2f\n" +
				"Memory size (solutions) = %d", this.r_accept, this.r_pa, this.band, memorySize);
	}



}
