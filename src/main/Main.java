		package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import harmony.HarmonyMemory;
import harmony.HarmonyParameters;
import harmony.HarmonyResult;
import harmony.HarmonySearch;

import java.util.HashMap;
import output.Printer;
import timeSeries.TimeSeriesDatabase;

public class Main {

	/**
	 * If true, harmony search prints newly generated solutions within each
	 * iteration and potential swaps in memory if better solution was found than
	 * worst in memory
	 */
	private static final boolean PRINT_NEW_SOLUTIONS = false;
	private static final boolean PRINT_MEMORY_SWAPS = false;

	public static void main(String[] args) {

		setUpDatabase("./lib/Daten_2_156.csv", false, 0);

		StreamCount streamCount = StreamCount.FIVE;

		Evaluation eval = new Evaluation();

		// SetUp all information about the states in the files
		
		//Test 
		AxisStream[] axisArr = {AxisStream.BP, AxisStream.GP, AxisStream.MAP, AxisStream.SAP, AxisStream.WP};
		//AxisStream[] axisArr = { AxisStream.GP};
		List<AxisStream> axisList = new ArrayList<AxisStream>(Arrays.asList(axisArr));
		
		eval.setUpRealDataStream("./lib/realStates_2_156.csv", axisList);
		
		HarmonyParameters hpa = new HarmonyParameters(0.3, 0.2, 0.9, 10, streamCount);
		HarmonyResult hr = runHarmonySearch(hpa, 150, false);

	}

	/**
	 * Execute harmony search with given parameters and prints some information
	 * (influenced by constants in main).
	 * 
	 * @param hpa .. harmony parameters (bandwidth, acceptance rate, adjustment date, initial memory)
	 * @param nrOfIterations .. maximum number of iterations (new solutions to test)
	 * @param stopIfOptimumFound .. stop at iteration where optimum is foud, if optimumt is found
	 * @return HarmonyResult
	 */
	static HarmonyResult runHarmonySearch(HarmonyParameters hpa, int nrOfIterations, boolean stopIfOptimumFound) {
		// Initialize HarmonyMemory with HarmonyParameters
		HarmonyMemory memory = new HarmonyMemory(hpa);

		Printer.printHeader("Harmony parameters:");
		System.out.println(hpa);
		Printer.printHeader("INITIAL MEMORY");
		memory.print();
		HarmonySearch search = new HarmonySearch(memory, hpa);

		// Execute harmony search: If parameter "stopIfOptimumFound" is true,
		// max number of iterations is still respected (here: max 300 iterations)
		HarmonyResult hs = search.execHarmonySearch(nrOfIterations, stopIfOptimumFound, PRINT_NEW_SOLUTIONS, PRINT_MEMORY_SWAPS);
		Printer.printHeader("MEMORY RESULTS");
		memory.print();
		Printer.printHeader("HARMONY RESULTS");
		System.out.println(hs);

		return hs;
	}

	private static void setUpDatabase(String filenameData, boolean longRun, int timespan) {
		TimeSeriesDatabase db = new TimeSeriesDatabase();
		db.setUpData(filenameData, longRun, timespan);
	}
}
