package main;

public class EvaluationResult {
	String state;
	double precision;
	double recall;
	double fMeasure;
	
	public EvaluationResult() {
		state="";
		precision=0;
		recall=0;
		fMeasure=0;
	}
	public EvaluationResult(String state, double precision, double recall, double fMeasure) {
		this.state=state;
		this.precision=precision;
		this.recall=recall;
		this.fMeasure=fMeasure;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getfMeasure() {
		return fMeasure;
	}
	public void setfMeasure(double fMeasure) {
		this.fMeasure = fMeasure;
	}
	


	
	public String toString() {
		return state+" precision:"+precision+" recall:"+recall+" F-Measure:"+fMeasure;
	}
}
