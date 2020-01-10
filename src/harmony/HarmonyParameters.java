package harmony;

import java.util.List;
import java.util.Map;

import main.AxisStream;
import main.StreamCount;

public class HarmonyParameters {

	private double r_pa; //pitch adjustment rate
	private double band; //bandwidth
	private double r_accept; //acceptance rate
	private int memorySize;

	private List<AxisStream> axisStream;

	/**
	 * Initializes harmony parameters
	 *
	 * @param r_pa .. pitch adjustment rate
	 * @param band .. bandwidth
	 * @param r_accept .. acceptance rate
	 * @param memorySize .. size of memory
	 * @param streamCount .. number of stream (single, three, five)
	 */
	public HarmonyParameters(double r_pa, double band,double r_accept, int memorySize, List<AxisStream> axisStream) {
		this.r_pa = r_pa;
		this.band = band;
		this.r_accept = r_accept;
		this.memorySize = memorySize;
		this.axisStream = axisStream;
		
		
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
	
	public List<AxisStream> getAxisStreams() {
		return this.axisStream;
	}
	
	public void setAxisStreams(List<AxisStream> axisStream) {
		this.axisStream = axisStream;
	}

	public String toString() {
		return String.format("Acceptance rate (r_accept): %.2f\nParameter Adjustment Rate (r_pa): %.2f\n" +
				"bandwith (band) = %.2f\n" +
				"Memory size (solutions) = %d", this.r_accept, this.r_pa, this.band, memorySize);
	}



}
