package timeSeries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import design.Property;
import harmony.HarmonyParameters;
import main.Boundaries;
import main.PropertyBoundaries;
import runtime.IdentifiedState;

public class TimeSeriesDatabase {

	public static TimeSeriesDatabase instance = null;

	private String dbName = "state_identification";
	private InfluxDB influxDB;

	private static final DateTimeFormatter ISO8601_FORMATTER = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd'T'HH:mm:ss").appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
			.appendPattern("X").toFormatter();

	public TimeSeriesDatabase() {
		this.influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
		this.influxDB.deleteDatabase(dbName);
		this.influxDB.createDatabase(dbName);
		this.influxDB.setDatabase(dbName);
		TimeSeriesDatabase.instance = this;
	}

	public void setUpData(String filename, boolean longRun, int timespan) {
		String csvFile = filename;
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		Date parsedDate=new Date();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] information = line.split(cvsSplitBy);
				String timestamp = information[0] + " " + information[1];

				double bp = Double.parseDouble(information[2]);
				double map = Double.parseDouble(information[3]);
				double sap = Double.parseDouble(information[4]);
				double wp = Double.parseDouble(information[5]);
				double gp = Double.parseDouble(information[6]);

				SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

				try {
					parsedDate = df.parse(timestamp);
					savePoint(parsedDate.getTime(), bp, map, sap, wp, gp);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		if(longRun) {
			setUpDataLong(filename, parsedDate, timespan);
		}

	}

	private void setUpDataLong(String filename, Date lastDate, int timespan) {
		String csvFile = filename;
		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br = null;
		Date currentDate=lastDate;
		for (int i = 0; i < timespan; i++) {
			try {

				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {

					String[] information = line.split(cvsSplitBy);
					
					double bp = Double.parseDouble(information[2]);
					double map = Double.parseDouble(information[3]);
					double sap = Double.parseDouble(information[4]);
					double wp = Double.parseDouble(information[5]);
					double gp = Double.parseDouble(information[6]);

					Calendar now = Calendar.getInstance();
					now.setTime(currentDate);
					now.add(Calendar.SECOND, 1);
					currentDate = now.getTime();
					savePoint(currentDate.getTime(), bp, map, sap, wp, gp);
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
	}

	private void savePoint(long timestamp, double bp, double map, double sap, double wp, double gp) {
		BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true").retentionPolicy("autogen")
				.consistency(InfluxDB.ConsistencyLevel.ALL).tag("BatchTag", "BatchTagValue").build();
		Point point1 = Point.measurement("roboticArm").time(timestamp, TimeUnit.MILLISECONDS).addField("bp", bp)
				.addField("map", map).addField("sap", sap).addField("wp", wp).addField("gp", gp).build();
		batchPoints.point(point1);

		// Write them to InfluxDB
		influxDB.write(batchPoints);

	}

	
	 public static void harmony(int nrOfIters, List<Double> harmonics, double r_pa, double pitch_lower_bound, double pitch_upper_bound, double r_accept) {
	    	for(int i = 0; i < nrOfIters; i++) {
	    		
	    }	
	 }
	 
	 public List<IdentifiedState> recognizeStateHarmonized(String statename, List<Property> propertyValues, 
			 Map<String, Boundaries<Double, Double>> harmonics, HarmonyParameters harmonyParams) {
			Query query = new Query(StateQuery.createQuery(statename), dbName);
			//final long startTime = System.nanoTime();
			QueryResult queryResult = influxDB.query(query);
			//final long duration = System.nanoTime() - startTime;
			//System.out.println("Time: " + duration);

			List<IdentifiedState> stateList = new ArrayList<IdentifiedState>();

			for (QueryResult.Result result : queryResult.getResults()) {
				if (result.getSeries() != null) {
					for (QueryResult.Series series : result.getSeries()) {

						for (List<Object> val : series.getValues()) {
							IdentifiedState s = new IdentifiedState();
							List<Property> properties = new ArrayList<Property>();
							s.setName(statename);
							for (int i = 0; i < val.size(); i++) {
								if (i == 0) {
									Instant instant = Instant.from(ISO8601_FORMATTER.parse(String.valueOf(val.get(i))));
									LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
									s.setTimestamp(ldt.toString());
								} else {
									Property p = new Property();
									p.setName(series.getColumns().get(i));
									p.setValue((double) val.get(i));
									properties.add(p);
								}
							}
							s.setProperties(properties);
							stateList.add(s);

						}
					}
				} else {
					IdentifiedState s = new IdentifiedState();
					List<Property> properties = propertyValues;
					s.setName(statename);
					s.setTimestamp("");
					s.setProperties(properties);
					stateList.add(s);

				}
			}
			return stateList;
		}
	
	public List<IdentifiedState> recognizeState(String statename, List<Property> propertyValues,  Map<String,PropertyBoundaries> propertyMap) {
		Query query = new Query(StateQuery.createQuery(statename, propertyValues, propertyMap), dbName);
		//final long startTime = System.nanoTime();
		QueryResult queryResult = influxDB.query(query);
		//final long duration = System.nanoTime() - startTime;
		//System.out.println("Time: " + duration);

		List<IdentifiedState> stateList = new ArrayList<IdentifiedState>();

		for (QueryResult.Result result : queryResult.getResults()) {
			if (result.getSeries() != null) {
				for (QueryResult.Series series : result.getSeries()) {

					for (List<Object> val : series.getValues()) {
						IdentifiedState s = new IdentifiedState();
						List<Property> properties = new ArrayList<Property>();
						s.setName(statename);
						for (int i = 0; i < val.size(); i++) {
							if (i == 0) {
								Instant instant = Instant.from(ISO8601_FORMATTER.parse(String.valueOf(val.get(i))));
								LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
								s.setTimestamp(ldt.toString());
							} else {
								Property p = new Property();
								p.setName(series.getColumns().get(i));
								p.setValue((double) val.get(i));
								properties.add(p);
							}
						}
						s.setProperties(properties);
						stateList.add(s);

					}
				}
			} else {
				IdentifiedState s = new IdentifiedState();
				List<Property> properties = propertyValues;
				s.setName(statename);
				s.setTimestamp("1990-01-01T00:00:00");
				s.setProperties(properties);
				stateList.add(s);

			}
		}
		return stateList;
	}

}
