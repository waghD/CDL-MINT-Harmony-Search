package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
		setUpDatabase("./lib/Daten_12_156.csv", false, 0);
		Evaluation eval = new Evaluation("./lib/realStates_12_156.csv");
		// SetUp all information about the states in the files

		// Test
		AxisStream[] axisArr = {AxisStream.GP, AxisStream.MAP, AxisStream.BP, AxisStream.SAP, AxisStream.WP};
		List<AxisStream> axisList = new ArrayList<AxisStream>(Arrays.asList(axisArr));

		eval.setUpRealDataStream(axisList);

		PrintStream out = null;
		List<Double> accList = new ArrayList<Double>(Arrays.asList(0.7));	
		List<Double> adjList = new ArrayList<Double>(Arrays.asList(0.3));
		List<Integer> sizeList = new ArrayList<Integer>(Arrays.asList(10));
		DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        
		for (double acc : accList) {
			for (double adj : adjList) {
				for (int size : sizeList) {
					/*try {
						out = new PrintStream(new FileOutputStream("output/156/rAccept_" + String.valueOf(acc) + "_band_0.1_rAdj_" + String.valueOf(adj) + "_memSize_" + String.valueOf(size) + ".txt", true),
								true);
						System.setOut(out);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/

					// states to use for evaluation
					// use to single out state to check if precision recall > 0.00 can be reached at
					// all
					// Set empty array to use all states!
					List<String> statesToEvaluateList = new ArrayList<String>(Arrays.asList());
					List<HarmonyResult> resultList = new ArrayList<HarmonyResult>();

					HarmonyParameters hpa = new HarmonyParameters(adj, 0.15, acc, size, axisList);

					Printer.printHeader("Harmony parameters:");
					System.out.println(hpa);

					for (int i = 0; i < 1; i++) {
						resultList.add(runHarmonySearch(hpa, 5000, true, statesToEvaluateList, 0, 0.3));
					}

					List<Integer> iterationsList = new ArrayList<Integer>();
					List<Double> timeTilOptList = new ArrayList<Double>();

					for (HarmonyResult res : resultList) {
						// System.out.println("Iterations: " + res.getNrOfIterationsForOptimum() + " ("
						// + res.getRuntimeTilOptimumFound() + "s)" );
						System.out.println(
								"Iterations: " + res.getNrOfIterationsForOptimum() + " (" + res.getRuntimeTilOptimumFound() + "s)");
						iterationsList.add(res.getNrOfIterationsForOptimum());
						timeTilOptList.add(res.getRuntimeTilOptimumFound());
					}

					System.out.println(Printer.div);
					double avgIterations = iterationsList.stream().mapToInt(x -> x).average().orElse(-1);
					double avgTimeTilOpt = timeTilOptList.stream().mapToDouble(x -> x).average().orElse(-1);
					System.out.println("Iterations (avg): " + avgIterations);
					System.out.println("Time til Optimum found (avg): " + avgTimeTilOpt);

					/*out.close();
					File theFile = new File("output/156/rAccept_" + String.valueOf(acc) + "_band_0.1_rAdj_" + String.valueOf(adj) + "_memSize_" + String.valueOf(size) + ".txt");
					theFile.renameTo(new File("output/156/rAccept_" + String.valueOf(acc) + "_band_0.1_rAdj_" + String.valueOf(adj) + "_memSize_" + String.valueOf(size) + "__avgTime_" + df.format(avgTimeTilOpt) + "_avgIter_" + avgIterations + ".txt"));
					*/// HarmonyResult hr = runHarmonySearch(hpa, 100, false, statesToEvaluateList);
				}
			}
		}
		
	}

	/**
	 * Execute harmony search with given parameters and prints some information
	 * (influenced by constants in main).
	 * 
	 * @param hpa                .. harmony parameters (bandwidth, acceptance rate,
	 *                           adjustment date, initial memory)
	 * @param nrOfIterations     .. maximum number of iterations (new solutions to
	 *                           test)
	 * @param stopIfOptimumFound .. stop at iteration where optimum is foud, if
	 *                           optimumt is found
	 * @param minSpace .. minimum value of search space
	 * 
	 * @param maxSpace .. maximum value of search space
	 * 
	 * @return HarmonyResult
	 */
	static HarmonyResult runHarmonySearch(HarmonyParameters hpa, int nrOfIterations, boolean stopIfOptimumFound,
			List<String> statesToEvaluateList, double spaceMin, double spaceMax) {
		// Initialize HarmonyMemory with HarmonyParameters
		HarmonyMemory memory = new HarmonyMemory(hpa, statesToEvaluateList, spaceMin, spaceMax);

		
		 Printer.printHeader("INITIAL MEMORY"); memory.print();
		 
		HarmonySearch search = new HarmonySearch(memory, hpa, spaceMin, spaceMax);

		// Execute harmony search: If parameter "stopIfOptimumFound" is true,
		// max number of iterations is still respected (here: max 300 iterations)
		HarmonyResult hs = search.execHarmonySearch(nrOfIterations, stopIfOptimumFound, statesToEvaluateList,
				PRINT_NEW_SOLUTIONS, PRINT_MEMORY_SWAPS);
		
		 Printer.printHeader("MEMORY RESULTS"); memory.print();
		 
		Printer.printHeader("HARMONY RESULTS");
		
		System.out.println(hs);
		Printer.printHeader("BEST");
		memory.print(memory.findBestEvalResult());

		return hs;
	}

	private static void setUpDatabase(String filenameData, boolean longRun, int timespan) {
		TimeSeriesDatabase db = new TimeSeriesDatabase();
		db.setUpData(filenameData, longRun, timespan);
	}
}
