package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import design.Block;
import design.Property;
import design.State;
import runtime.IdentifiedState;
import timeSeries.TimeSeriesDatabase;

public class Evaluation {

	public static Evaluation instance = null;

	private static int counter = 0;
	private List<IdentifiedState> realStates = new ArrayList<IdentifiedState>();

	private String realDataFilename;

	private static final DateTimeFormatter ISO8601_FORMATTER = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd'T'HH:mm:ss").appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
			.appendPattern("X").toFormatter();

	public Evaluation() {
		Evaluation.instance = this;
	}

	public Evaluation(String realDataFileName) {
		this.realDataFilename = realDataFileName;
		Evaluation.instance = this;
	}

	/**
	 * Evaluates the given solution map
	 * @param roboticArm .. Model with list of expected States
	 * @param propertyMap .. Solution Map 
	 * @param printRes .. flag for verbose printing
	 * @param statesToIgnore .. states that should not be evaluated
	 * @return List<EvaluationResult> .. Results from Evaluation in List mapped per State
	 */
	public List<EvaluationResult> evaluate(Block roboticArm, Map<String, PropertyBoundaries> propertyMap, boolean printRes, List<String> statesToIgnore) {
		ArrayList<IdentifiedState> recStates = new ArrayList<IdentifiedState>();
		recStates = this.testRecognition(roboticArm, propertyMap, statesToIgnore);
		List<EvaluationResult> allResults = new ArrayList<EvaluationResult>();
		if(printRes) {
			PrintWriter pw;
			String dir = "modelTest/";
			String filename = "result" + counter + ".csv";
			String path = dir + filename;
			try {
				File f = new File(dir, filename);
				f.createNewFile();
				pw = new PrintWriter(new File(path));
				String row = "";
				Collections.sort(recStates);
				int countProcess = 0;
				for (IdentifiedState identifiedState : recStates) {
					if (identifiedState.getName().equals("DriveDown")) {
						countProcess++;
					}
					row = countProcess + ";" + identifiedState.getName() + ";" + identifiedState.getTs().toString() + "\n";
					pw.write(row);
				}
				pw.close();
				counter++;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// Calculate Precision and Recall
		allResults.addAll(this.calculatePrecisionRecall(recStates));

		return allResults;
	}

	public void setUpRealDataStream(String filename, List<AxisStream> axisList) {
		this.realDataFilename = filename;
		this.setUpRealDataStream(axisList);
	}

	/**
	 * Finds all states in database that fit the currently evaluating solution
	 * @param b.. Model of Block with states
	 * @param propertyMap .. currently evaluating solution map
	 * @param statesToIgnore .. states that are not evaluated
	 * @return ArrayList<IdentifiedState> .. States that have been identified in the database
	 */
	public ArrayList<IdentifiedState> testRecognition(Block b, Map<String, PropertyBoundaries> propertyMap, List<String> statesToIgnore) {
		TimeSeriesDatabase db = TimeSeriesDatabase.instance;
		if (db == null)
			return new ArrayList<IdentifiedState>();
		ArrayList<IdentifiedState> result = new ArrayList<IdentifiedState>();
		for (State s : b.getAssignedState()) {
			if(statesToIgnore.size() == 0 || !statesToIgnore.contains(s.getName())) {
				result.addAll(db.recognizeState(s.getName(), s.getAssignedProperties(), propertyMap));
			}
		}
		return result;
	}

	public List<IdentifiedState> getRealStates() {
		return realStates;
	}

	public void setRealStates(List<IdentifiedState> realStates) {
		this.realStates = realStates;
	}

	/**
	 * Calculate Evaluation Results for all States
	 * @param recognizedStates .. list of recognized states in Database
	 * @return
	 */
	public List<EvaluationResult> calculatePrecisionRecall(List<IdentifiedState> recognizedStates) {
		// all states based on their name
		HashMap<String, List<IdentifiedState>> statesReal = new HashMap<String, List<IdentifiedState>>();
		for (IdentifiedState s : realStates) {
			if (statesReal.containsKey(s.getName())) {
				List<IdentifiedState> existing = statesReal.get(s.getName());
				existing.add(s);
				statesReal.put(s.getName(), existing);
			} else {
				List<IdentifiedState> existing = new ArrayList<IdentifiedState>();
				existing.add(s);
				statesReal.put(s.getName(), existing);
			}
		}
		HashMap<String, List<IdentifiedState>> statesRecognized = new HashMap<String, List<IdentifiedState>>();
		for (IdentifiedState s : recognizedStates) {
			if (statesRecognized.containsKey(s.getName())) {
				List<IdentifiedState> existing = statesRecognized.get(s.getName());
				existing.add(s);
				statesRecognized.put(s.getName(), existing);
			} else {
				List<IdentifiedState> existing = new ArrayList<IdentifiedState>();
				existing.add(s);
				statesRecognized.put(s.getName(), existing);
			}
		}
		List<EvaluationResult> result = new ArrayList<EvaluationResult>();
		for (Entry<String, List<IdentifiedState>> entry : statesRecognized.entrySet()) {
			String statename = entry.getKey();
			List<IdentifiedState> val = entry.getValue();
			result.add(calculatePrecisionRecall(statename, val, statesReal));

		}
		return result;

	}

	/**
	 * Calculates the Evaluation Result for a single State
	 * @param statename .. State to evaluate
	 * @param recognizedStates .. List of states that have been recognized
	 * @param statesReal .. List of states that should have been recognized
	 * @return
	 */
	private EvaluationResult calculatePrecisionRecall(String statename, List<IdentifiedState> recognizedStates,
			HashMap<String, List<IdentifiedState>> statesReal) {
		int intersection = 0;
		boolean noMatch = false;
		// look
		for (IdentifiedState s : recognizedStates) {
			if (statesReal.get(statename).contains(s)) {
				intersection++;
			} else {

			}
			if (s.getTimestamp().equals("")) {
				noMatch = true;
			}
		}
		// calculate precision,recall,F-Measure
		double precision;
		if (noMatch) {
			precision = calculatePrecision(intersection, 0);
		} else {
			precision = calculatePrecision(intersection, recognizedStates.size());
		}
		double recall = calculateRecall(intersection, statesReal.get(statename).size());
		double fMeasure = calculateFMeasure(precision, recall);
		EvaluationResult result = new EvaluationResult(statename, precision, recall, fMeasure);
		return result;
	}


	
	/**
	 * Reads data from csv file and writes it into the database
	 * @param axisList
	 */
	public void setUpRealDataStream(List<AxisStream> axisList) {
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(this.realDataFilename));
			while ((line = br.readLine()) != null) {

				String[] information = line.split(cvsSplitBy);
				String[] split = information[0].split("\\.");
				String timestamp = split[2] + "-" + split[1] + "-" + split[0] + "T" + information[1] + "Z";
				
				List<Property> properties = new ArrayList<Property>();
				
				/*for( AxisStream axStream : axisList) {
					properties.add(new Property(axStream.getAxisName(), Double.parseDouble(information[axStream.getSplitNr()])));
				}*/

				if (axisList.contains(AxisStream.BP)) {
					properties.add(new Property(AxisStream.BP.getAxisName(), Double.parseDouble(information[AxisStream.BP.getSplitNr()])));
				}
				if (axisList.contains(AxisStream.MAP)) {
					properties.add(new Property(AxisStream.MAP.getAxisName(), Double.parseDouble(information[AxisStream.MAP.getSplitNr()])));
				}
				if (axisList.contains(AxisStream.GP)) {
					properties.add(new Property(AxisStream.GP.getAxisName(), Double.parseDouble(information[AxisStream.GP.getSplitNr()])));
				}
				if (axisList.contains(AxisStream.SAP)) {
					properties.add(new Property(AxisStream.SAP.getAxisName(), Double.parseDouble(information[AxisStream.SAP.getSplitNr()])));
				}
				if (axisList.contains(AxisStream.WP)) {
					properties.add(new Property(AxisStream.WP.getAxisName(), Double.parseDouble(information[AxisStream.WP.getSplitNr()])));
				}
				
				String statename = information[7];

				IdentifiedState s = new IdentifiedState();
				s.setName(statename);
				s.setProperties(properties);
				Instant instant = Instant.from(ISO8601_FORMATTER.parse(timestamp));
				LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.from(ISO8601_FORMATTER.parse(timestamp)));
				s.setTimestamp(ldt.toString());
				realStates.add(s);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private double calculatePrecision(double intersection, double retrieved) {
		return intersection / retrieved;
	}

	private double calculateRecall(double intersection, double relevant) {
		return intersection / relevant;
	}

	private double calculateFMeasure(double precision, double recall) {
		if(precision == 0.0 && recall == 0.0) return 0.0;
		return 2 * (precision * recall) / (precision + recall);
	}
}