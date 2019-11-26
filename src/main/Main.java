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

		int memorySize = 3;

		Evaluation eval = new Evaluation();

		// SetUp all information about the states in the files
		eval.setUpRealDataStream("./lib/realStates_156.csv", streamCount);
		
		runHarmonySearch(memorySize, streamCount);

		/**
		 * Testing harmony search with different sizes of the memory and print number of
		 * iterations required for finding optimum for each harmony size
		 */
//		List<Integer> iterTestValues = new ArrayList<Integer>(Arrays.asList(1, 2, 5, 8, 10));
//		List<Integer> optimumFoundList = new ArrayList<Integer>();
//
//		for (int i : iterTestValues) {
//			int optimumFoundAtIter = runHarmonySearch(i, streamCount);
//			optimumFoundList.add(optimumFoundAtIter);
//		}
//		System.out.println(optimumFoundList);
//		System.out.println("Average: " + optimumFoundList.stream().mapToDouble(a -> a).average());

	}

	static int runHarmonySearch(int memorySize,  StreamCount streamCount) {
		// Initialize solution map with expected abs. sensor deviations
		Map<String, PropertyBoundaries> defaultSolutions = new HashMap<String, PropertyBoundaries>();
		defaultSolutions.put("bp", new PropertyBoundaries(0.1, 0.1));
		defaultSolutions.put("map", new PropertyBoundaries(0.1, 0.1));
		defaultSolutions.put("gp", new PropertyBoundaries(0.1, 0.1));
		defaultSolutions.put("sap", new PropertyBoundaries(0.1, 0.1));
		defaultSolutions.put("wp", new PropertyBoundaries(0.1, 0.1));

		/**
		 * Basic harmony search test: 3 solutions in memory, harmony parameters:
		 * acceptance rate 0.9, adjustment rate 0.3, bandwidth 0.03
		 */
		List<Map<String, PropertyBoundaries>> initialMemory = new ArrayList<Map<String, PropertyBoundaries>>();

		// Initialize harmony memory with 3 (equal) solution maps
		for (int memorySolutions = 0; memorySolutions < memorySize; memorySolutions++) {
			initialMemory.add(defaultSolutions);
		}
		// Initialize harmony parameters
		HarmonyParameters hpa = new HarmonyParameters(0.3, 0.03, 0.9);

		// Evaluate initialMemory
		
		Evaluation eval = Evaluation.instance;

		List<EvaluationResult>[] initialResults = new List[memorySize];
		List<EvaluationResult> defaultResult = eval.evaluate(TestData.setUpDataStream(streamCount), defaultSolutions);
		for (int i = 0; i < initialResults.length; i++) {
			initialResults[i] = defaultResult;
		}

		HarmonyMemory memory = new HarmonyMemory(initialMemory, initialResults, streamCount);

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
