package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import output.Printer;
import timeSeries.TimeSeriesDatabase;

public class Main {

	/**
	 * If true, harmony search prints newly generated solutions within each
	 * iteration and potential swaps in memory if better solution was found than
	 * worst in memory
	 */
	private static final boolean PRINT_NEW_SOLUTIONS = true;
	private static final boolean PRINT_MEMORY_SWAPS = true;

	public static void main(String[] args) {

		setUpDatabase("./lib/Daten_156.csv", false, 0);

		StreamCount streamCount = StreamCount.FIVE;

		Evaluation eval = new Evaluation();

		// SetUp all information about the states in the files
		eval.setUpRealDataStream("./lib/realStates_156.csv", streamCount);
		HarmonyParameters hpa = new HarmonyParameters(0.3, 0.03, 0.9, 3, streamCount);
		runHarmonySearch(hpa);

		/**
		 * Testing harmony search with different sizes of the memory and print number of
		 * iterations required for finding optimum for each harmony size
		 */
//		List<Integer> iterTestValues = new ArrayList<Integer>(Arrays.asList(1, 2, 5, 8, 10));
//		List<Integer> optimumFoundList = new ArrayList<Integer>();
//
//		for (int i : iterTestValues) {
//			hpa = new HarmonyParameters(0.3, 0.03, 0.9, i, streamCount);
//			int optimumFoundAtIter = runHarmonySearch(hpa);
//			optimumFoundList.add(optimumFoundAtIter);
//		}
//		System.out.println(optimumFoundList);
//		System.out.println("Average: " + optimumFoundList.stream().mapToDouble(a -> a).average());

	}

	static int runHarmonySearch(HarmonyParameters hpa) {
		// Initialize HarmonyMemory with HarmonyParameters
		HarmonyMemory memory = new HarmonyMemory(hpa);

		Printer.printHeader("Harmony parameters:");
		System.out.println(hpa);
		Printer.printHeader("INITIAL MEMORY");

		memory.print();

		HarmonySearch search = new HarmonySearch(memory, hpa);

		// Execute harmony search: If parameter "stopIfOptimumFound" is true,
		// max number of iterations is still respected (here: max 300 iterations)
		int optimumFoundAtIter = search.execHarmonySearch(300, true, PRINT_NEW_SOLUTIONS, PRINT_MEMORY_SWAPS);
		Printer.printHeader("MEMORY RESULTS");
		memory.print();
		System.out.println("Optimum found at " + optimumFoundAtIter + ". iteration.");
		return optimumFoundAtIter;
	}

	private static void setUpDatabase(String filenameData, boolean longRun, int timespan) {
		TimeSeriesDatabase db = new TimeSeriesDatabase();
		db.setUpData(filenameData, longRun, timespan);
	}
}
