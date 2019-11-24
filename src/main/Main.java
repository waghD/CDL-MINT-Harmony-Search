package main;

import java.util.List;

import timeSeries.TimeSeriesDatabase;

public class Main {

	public static void main(String[] args) {

		setUpDatabase("./lib/Daten_156.csv", false, 0);
		
		StreamCount streamCount = StreamCount.FIVE;

		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataStream("./lib/realStates_156.csv", streamCount);

		int nrOfIter = 100;
		// Initialize harmony memory
		
		
		for (double adj_val = 0.1; adj_val <= 0.5; adj_val += 0.2) {
			System.out.println(
					"---------------------------------------------\n---------------------------------------------");
			System.out.printf("Adjustment value: %.2f\n", adj_val);
			System.out.println(
					"---------------------------------------------\n---------------------------------------------");

			double[][] initialMemory = { { 0.1, 0.5 }, { 0.002, 0.1 }, { 0.3, 0.4 }, { 0.3, 0.4 } };
			List<EvaluationResult>[] initialResults = new List[4];
			for (int i = 0; i < initialMemory.length; i++) {
				initialResults[i] = eval.evaluate(TestData.setUpDataStream(streamCount), initialMemory[i][0],
						initialMemory[i][1]);
			}
			HarmonyMemory harmonyMemory = new HarmonyMemory(initialMemory, initialResults, streamCount);
			HarmonyParameters hpa = new HarmonyParameters(adj_val, 0.03, 0.9);
			HarmonySearch hs = new HarmonySearch(harmonyMemory, hpa);
			hs.search(nrOfIter);
		}
	}

	private static void setUpDatabase(String filenameData, boolean longRun, int timespan) {
		TimeSeriesDatabase db = new TimeSeriesDatabase();
		db.setUpData(filenameData, longRun, timespan);
	}

}
