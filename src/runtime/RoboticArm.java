package runtime;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "roboticArm")
public class RoboticArm {
	@Column(name = "time")
    private Instant time;
	@Column(name = "bp")
	private double bp;
	@Column(name = "map")
	private double map;
	@Column(name = "gp")
	private double gp;
	
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public double getBp() {
		return bp;
	}
	public void setBp(double bp) {
		this.bp = bp;
	}
	public double getMap() {
		return map;
	}
	public void setMap(double map) {
		this.map = map;
	}
	public double getGp() {
		return gp;
	}
	public void setGp(double gp) {
		this.gp = gp;
	}
	
}