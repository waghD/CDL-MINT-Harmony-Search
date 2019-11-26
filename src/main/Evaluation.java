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
import timeSeries.PropertyBoundaries;
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
	}

	public List<EvaluationResult> evaluate(Block roboticArm, Map<String, PropertyBoundaries> propertyMap) {
		ArrayList<IdentifiedState> recStates = new ArrayList<IdentifiedState>();
		recStates = this.testRecognition(roboticArm, propertyMap);
		List<EvaluationResult> allResults = new ArrayList<EvaluationResult>();
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
		// Calculate Precision and Recall
		allResults.addAll(this.calculatePrecisionRecall(recStates));

		return allResults;
	}

	public void setUpRealDataStream(StreamCount streamCount) {
		if (this.realDataFilename == null)
			return;
		String filename = this.realDataFilename;
		switch (streamCount) {
		case FIVE:
			this.setUpRealDataFiveStreams(filename);
			break;
		case THREE:
			this.setUpRealDataThreeStreams(filename);
			break;
		case SINGLE:
		default:
			this.setUpRealDataSingleStream(filename);
			break;
		}
	}

	public void setUpRealDataStream(String filename, StreamCount streamCount) {
		this.realDataFilename = filename;
		switch (streamCount) {
		case FIVE:
			this.setUpRealDataFiveStreams(filename);
			break;
		case THREE:
			this.setUpRealDataThreeStreams(filename);
			break;
		case SINGLE:
		default:
			this.setUpRealDataSingleStream(filename);
			break;
		}
	}

	public ArrayList<IdentifiedState> testRecognition(Block b, Map<String,PropertyBoundaries> propertyMap) {
		TimeSeriesDatabase db = TimeSeriesDatabase.instance;
		if (db == null) return new ArrayList<IdentifiedState>();
		ArrayList<IdentifiedState> result = new ArrayList<IdentifiedState>();
		for (State s : b.getAssignedState()) {
			result.addAll(db.recognizeState(s.getName(), s.getAssignedProperties(), propertyMap));
		}
		return result;
	}

	public List<IdentifiedState> getRealStates() {
		return realStates;
	}

	public void setRealStates(List<IdentifiedState> realStates) {
		this.realStates = realStates;
	}

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

	private EvaluationResult calculatePrecisionRecall(String statename, List<IdentifiedState> recognizedStates,
			HashMap<String, List<IdentifiedState>> statesReal) {
		int intersection = 0;
		boolean noMatch = false;
		// look
		for (IdentifiedState s : recognizedStates) {
			if (statesReal.get(statename).contains(s)) {
				intersection++;
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

	private void setUpRealDataSingleStream(String filename) {
		String csvFile = filename;
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] information = line.split(cvsSplitBy);
				String[] split = information[0].split("\\.");
				String timestamp = split[2] + "-" + split[1] + "-" + split[0] + "T" + information[1] + "Z";
				double gp = Double.parseDouble(information[6]);
				String statename = information[7];

				IdentifiedState s = new IdentifiedState();
				s.setName(statename);
				Property p1 = new Property("gp", gp);

				List<Property> properties = new ArrayList<Property>();
				properties.add(p1);
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

	private void setUpRealDataThreeStreams(String filename) {
		String csvFile = filename;
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] information = line.split(cvsSplitBy);
				String[] split = information[0].split("\\.");
				String timestamp = split[2] + "-" + split[1] + "-" + split[0] + "T" + information[1] + "Z";
				double bp = Double.parseDouble(information[2]);
				double map = Double.parseDouble(information[3]);
				double gp = Double.parseDouble(information[6]);
				String statename = information[7];

				IdentifiedState s = new IdentifiedState();
				s.setName(statename);
				Property p1 = new Property("bp", bp);
				Property p2 = new Property("map", map);
				Property p3 = new Property("gp", gp);

				List<Property> properties = new ArrayList<Property>();
				properties.add(p1);
				properties.add(p2);
				properties.add(p3);
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

	private void setUpRealDataFiveStreams(String filename) {
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine()) != null) {

				String[] information = line.split(cvsSplitBy);
				String[] split = information[0].split("\\.");
				String timestamp = split[2] + "-" + split[1] + "-" + split[0] + "T" + information[1] + "Z";
				double bp = Double.parseDouble(information[2]);
				double map = Double.parseDouble(information[3]);
				double sap = Double.parseDouble(information[4]);
				double wp = Double.parseDouble(information[5]);
				double gp = Double.parseDouble(information[6]);
				String statename = information[7];

				IdentifiedState s = new IdentifiedState();
				s.setName(statename);
				Property p1 = new Property("bp", bp);
				Property p2 = new Property("map", map);
				Property p3 = new Property("gp", gp);
				Property p4 = new Property("sap", sap);
				Property p5 = new Property("wp", wp);
				List<Property> properties = new ArrayList<Property>();
				properties.add(p1);
				properties.add(p2);
				properties.add(p3);
				properties.add(p4);
				properties.add(p5);
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
		return 2 * (precision * recall) / (precision + recall);
	}
}
