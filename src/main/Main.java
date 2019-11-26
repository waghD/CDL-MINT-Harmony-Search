package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import design.Block;
import design.Property;
import design.State;
import output.Columns;
import output.Printer;
import runtime.IdentifiedState;
import timeSeries.TimeSeriesDatabase;

public class Main {

	/**
	 * If true, harmony search prints newly generated solutions
	 * within each iteration and potential swaps in memory
	* if better solution was found than worst in memory
	 */
	private static final boolean PRINT_NEW_SOLUTIONS = true;
	private static final boolean PRINT_MEMORY_SWAPS = true;

	private static TimeSeriesDatabase db = null;
	private static int counter = 0;

	public static void main(String[] args) {

		setUpDatabase("./lib/Daten_156.csv", false, 0);


		//Initialize solution map with expected abs. sensor deviations
		Map<String, PropertyBoundaries> solutionMap = new HashMap<String, PropertyBoundaries>();
		solutionMap.put("bp", new PropertyBoundaries(0.1, 0.1));
		solutionMap.put("map", new PropertyBoundaries(0.1, 0.1));
		solutionMap.put("gp", new PropertyBoundaries(0.1, 0.1));
		solutionMap.put("sap", new PropertyBoundaries(0.1, 0.1));
		solutionMap.put("wp", new PropertyBoundaries(0.1, 0.1));

		/**
		 * Basic harmony search test: 3 solutions in memory, 
		 * harmony parameters: acceptance rate 0.9, adjustment rate 0.3, bandwidth 0.03
		 */
		List<Map<String, PropertyBoundaries>> harmonyMemory = new ArrayList<Map<String, PropertyBoundaries>>();

		//Initialize harmony memory with 5 (equal) solution maps
		for(int memorySolutions = 0; memorySolutions < 3; memorySolutions++) {
			harmonyMemory.add(solutionMap);
		}
		//Initialize harmony parameters
		HarmonyParameters hpa = new HarmonyParameters(harmonyMemory, 0.3, 0.03, 0.9);

		Printer.printHeader("Harmony parameters:");
		System.out.println(hpa);
		Printer.printHeader("INITIAL MEMORY");
		printHarmonyMemory(harmonyMemory);
		
		//Execute harmony search: If parameter "stopIfOptimumFound" is true, 
		//max number of iterations is still respected (here: max 300 iterations)
		int optimumFoundAtIter = execHarmonySearch(hpa, 300, true, PRINT_NEW_SOLUTIONS, PRINT_MEMORY_SWAPS);
		Printer.printHeader("MEMORY RESULTS");
		printHarmonyMemory(harmonyMemory);
		System.out.println("Optimum found at " + optimumFoundAtIter + ". iteration.");
	
		
		/**
		 * Testing harmony search with different sizes of the memory
		 * and print number of iterations required for finding optimum for 
		 * each harmony size
		 */
		/*List<Integer> iterTestValues = new ArrayList<Integer>(Arrays.asList(1,2,5,8,10));
		List<Integer> optimumFoundList = new ArrayList<Integer>();
		
		for(int i = 0; i < iterTestValues.size(); i++) {
			List<Map<String, PropertyBoundaries>> harmonyMemory = new ArrayList<Map<String, PropertyBoundaries>>();

			for(int memorySolutions = 0; memorySolutions < iterTestValues.get(i); memorySolutions++) {
				harmonyMemory.add(solutionMap);
			}
			HarmonyParameters hpa = new HarmonyParameters(harmonyMemory, 0.3, 0.03, 0.9);

			Printer.printHeader("Harmony parameters:");
			System.out.println(hpa);
			Printer.printHeader("INITIAL MEMORY");
			printGrid(harmonyMemory);
			int optimumFoundAtIter = execHarmonySearch(hpa, 300, true, PRINT_NEW_SOLUTIONS, PRINT_MEMORY_SWAPS);
			optimumFoundList.add(optimumFoundAtIter);
			Printer.printHeader("MEMORY RESULTS");
			printGrid(harmonyMemory);
			System.out.println("Optimum found at " + optimumFoundAtIter + ". iteration.");
		}
		System.out.println(optimumFoundList);
		System.out.println("Average: " + optimumFoundList.stream().mapToDouble(a -> a).average());*/
		
	
		
		
		// }

		/*
		 * for(double deviationVal = 0.01; deviationVal < 0.1; deviationVal += 0.01) {
		 * System.out.println("single - symmetric deviation = " + deviationVal);
		 * singleStream("./lib/realStates_156.csv", deviationVal);
		 * System.out.println("three"); threeStreams("./lib/realStates_156.csv",
		 * deviationVal); System.out.println("five");
		 * fiveStreams("./lib/realStates_156.csv", deviationVal); }
		 */

		db = null;
//		System.out.println("1560 rows");
//		setUpDatabase("./lib/Daten_1560.csv", false, 0);
//		System.out.println("single");
//		singleStream("./lib/realStates_1560.csv");
//		System.out.println("three");
//		threeStreams("./lib/realStates_1560.csv");
//		System.out.println("five");
//		fiveStreams("./lib/realStates_1560.csv");

		/*
		 * // long run 15600 db = null; setUpDatabase("./lib/Daten_1560.csv", true, 9);
		 * System.out.println("single"); singleStream("./lib/realStates_1560.csv");
		 * System.out.println("three"); threeStreams("./lib/realStates_1560.csv");
		 * System.out.println("five"); fiveStreams("./lib/realStates_1560.csv");
		 * 
		 * // long run 156000 db = null; setUpDatabase("./lib/Daten_1560.csv", true,
		 * 99); System.out.println("single"); singleStream("./lib/realStates_1560.csv");
		 * System.out.println("three"); threeStreams("./lib/realStates_1560.csv");
		 * System.out.println("five"); fiveStreams("./lib/realStates_1560.csv");
		 * 
		 * // long run 1560000 db = null; setUpDatabase("./lib/Daten_1560.csv", true,
		 * 900); System.out.println("single");
		 * singleStream("./lib/realStates_1560.csv"); System.out.println("three");
		 * threeStreams("./lib/realStates_1560.csv"); System.out.println("five");
		 * fiveStreams("./lib/realStates_1560.csv");
		 */

	}

	/**
	 * Executes harmony search on given harmony parameters.
	 * 
	 * Base idea of this algorithm is that from given initial solutions ("harmonies"
	 * in the harmony memory) it tries to find better solutions by using random
	 * values (according to set bandwith, acceptance rate and adjustment rate) and
	 * on finding a solution that is better than the worst in our memory, replaces
	 * the worst in our memory with the new solution.
	 * 
	 * By chance, new solutions are generated: (1) by using an existing partial
	 * solution randomly from the memory (if rand < acceptance rate) (2) adjusting
	 * the new partial solution randomly within the bandwidth (additionally to (1),
	 * if rand < param. adjustment rate) (3) completely random within the bandwith
	 * (rand >= acceptance rate)
	 * 
	 * 
	 * @param hpa               .. harmony parameters (bandwidth, acceptance rate,
	 *                          adjustment rate, memory)
	 * @param nrOfIter          .. number of iterations (each yields a new solution)
	 * @param printNewSolutions .. print statements of newly generated solutions
	 * @param printMemorySwaps  .. print statements of memory swaps
	 * @return int .. number of iterations required to find optimum, 0 if no optimum was found
	 */
	public static int execHarmonySearch(HarmonyParameters hpa, int nrOfIter,boolean stopIfOptimumFound, boolean printNewSolutions,
			boolean printMemorySwaps) {
		Random rand = new Random();
		int foundOptimumAtIter = 0;
		if (printNewSolutions || printMemorySwaps)
			Printer.printHeader("ALGORITHM START");
		for (int i = 0; i < nrOfIter; i++) {
			// Init new solution map
			Map<String, PropertyBoundaries> newSolution = new HashMap<String, PropertyBoundaries>();
			// Get the property names (keys) of a solution map of a solution of the harmony
			// memory (and convert to list)
			Set<String> propertyNamesSet = hpa.getSolutions().get(0).keySet();
			List<String> propertyNamesList = new ArrayList<>(propertyNamesSet);

			for (String prop : propertyNamesList) {
				newSolution.put(prop, new PropertyBoundaries(0, 0));
			}
			// int nrOfProperties = hpa.getSolutions().get(0).size()
			for (int z = 0; z < propertyNamesList.size(); z++) {
				String curPropertyName = propertyNamesList.get(z);
				if (rand.nextDouble() < hpa.getR_accept()) {
					int nrOfSolutionsInMemory = hpa.getSolutions().size();
					newSolution.get(curPropertyName).setLower(hpa.getSolutions()
							.get(rand.nextInt(nrOfSolutionsInMemory)).get(curPropertyName).getLower());
					newSolution.get(curPropertyName).setUpper(hpa.getSolutions()
							.get(rand.nextInt(nrOfSolutionsInMemory)).get(curPropertyName).getUpper());

					if (rand.nextDouble() < hpa.getR_pa()) {
						newSolution.get(curPropertyName).setLower(newSolution.get(curPropertyName).getLower()
								+ ThreadLocalRandom.current().nextDouble(0, 1) * hpa.getBand());
						newSolution.get(curPropertyName).setUpper(newSolution.get(curPropertyName).getUpper()
								+ ThreadLocalRandom.current().nextDouble(0, 1) * hpa.getBand());

					}
				} else {
					newSolution.get(curPropertyName).setLower(ThreadLocalRandom.current().nextDouble(0, hpa.getBand()));
					newSolution.get(curPropertyName).setUpper(ThreadLocalRandom.current().nextDouble(0, hpa.getBand()));
				}

			}
			if(printNewSolutions) {
				Printer.printHeader("New solution (" + (i+1) + ". iteration)");
				newSolution.forEach((propertyName, boundaries) -> System.out.println(propertyName + ": " + boundaries));
				
			}
			boolean foundOptimum = evalSolution(newSolution, hpa.getSolutions(), "./lib/realStates_156.csv",
					printMemorySwaps);
			if(foundOptimum) {
				foundOptimumAtIter = i+1;
				if(stopIfOptimumFound) {
					break;
				}
			}
		}
		return foundOptimumAtIter;

	}

	/**
	 * Prints the solutions (= total deviances per sensor) with resulting measures
	 * 
	 * @param solutions .. list of solution maps
	 */
	public static void printHarmonyMemory(List<Map<String, PropertyBoundaries>> solutions) {
		for (int i = 0; i < solutions.size(); i++) {
			Map<String, PropertyBoundaries> curMap = solutions.get(i);
			Iterator<Map.Entry<String, PropertyBoundaries>> it = curMap.entrySet().iterator();
			System.out.println(Printer.div + "\nSolution " + (i + 1) + " in memory\n" + Printer.div);
			curMap.forEach((propertyName, boundaries) -> System.out.printf("%s: %.3f, %.3f\n", propertyName,
					boundaries.getLower(), boundaries.getUpper()));

			List<EvaluationResult> solutionEvalResults = fiveStreams("./lib/realStates_156.csv", curMap);
			for (EvaluationResult solEvalResult : solutionEvalResults) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall());
			}
			System.out.println();
		}
	}

	/**
	 * Evaluates wether new solution is better than worst solution in memory. In
	 * case new solution is better than worst solution, replace worst with new
	 * solution.
	 * 
	 * Evaluation happens according to means (of states) of precision and recall
	 * 
	 * @param newSolution        .. the new solution map
	 * @param solutions          .. solution maps (harmony memory)
	 * @param realStatesFilePath .. file with real states for evaluation
	 * @param printNewSolutions  .. print statements of newly generated solutions
	 * @param printMemorySwaps   .. print statements of memory swaps
	 * @return boolean .. true when optimal solution (precision and recall both = 1.0) found
	 */
	public static boolean evalSolution(Map<String, PropertyBoundaries> newSolution,
			List<Map<String, PropertyBoundaries>> solutions, String realStatesFilePath,
			boolean printMemorySwaps) {
		boolean foundOptimum = false;
		List<ArrayList<EvaluationResult>> evalResults = new ArrayList<ArrayList<EvaluationResult>>();
		for (int i = 0; i < solutions.size(); i++) {
			evalResults.add((ArrayList<EvaluationResult>) fiveStreams(realStatesFilePath, solutions.get(i)));
			// System.out.println("(" + solutions[i][0] + "," + solutions[i][1] + ") => " +
			// func(solutions[i][0], solutions[i][1]));
		}

		List<EvaluationResult> worstResult = findWorstEvalResult(evalResults);

		List<EvaluationResult> newResult = fiveStreams(realStatesFilePath, newSolution);
	
		if (cmpListEvalResults(newResult, worstResult) > 0) {
			int worstResultIdx = evalResults.indexOf(worstResult);
			if (printMemorySwaps) {
				Printer.printHeader("SWAP: Solution " + (worstResultIdx + 1) + " (worst) -> New solution");
				Map<String, PropertyBoundaries> worstSolution = solutions.get(worstResultIdx);
				// Get all properties in map
				List<String> propertyNameList = new ArrayList<>(newSolution.keySet());
				for (int i = 0; i < propertyNameList.size(); i++) {
					String curProperty = propertyNameList.get(i);
					new Columns().addLine(
							curProperty + ": " + worstSolution.get(curProperty) + " -> " + newSolution.get(curProperty))
							.print();
				}
				System.out.print("\n" + worstResult + "\n->\n" + newResult);
			}
			solutions.set(worstResultIdx, newSolution);
			System.out.println();
			foundOptimum = true;
			for(int i = 0; i < newResult.size(); i++) {
				EvaluationResult stateResult = newResult.get(i);
				if(stateResult.precision < 1.0 || stateResult.recall < 1.0) {
					foundOptimum = false;
					break;
				}
			}
		}
		return foundOptimum;

	}

	/**
	 * Determines worst result in list of solution maps.
	 * 
	 * Worst solution is determined by worst precision/recall
	 * 
	 * @param evalResultListofLists
	 * @return
	 */
	private static List<EvaluationResult> findWorstEvalResult(List<ArrayList<EvaluationResult>> evalResultListofLists) {
		List<EvaluationResult> worstEvalResult = evalResultListofLists.get(0);
		for (ArrayList<EvaluationResult> evalResultList : evalResultListofLists) {
			if (cmpListEvalResults(worstEvalResult, evalResultList) > 0) {
				worstEvalResult = evalResultList;
			}
		}
		return worstEvalResult;
	}

	/**
	 * Compares two EvaluationResult objects and determines result from worse
	 * solution.
	 * 
	 * Worse solution is the one with worse mean (of states) precision/recall
	 * whereas precision has higher priority.
	 * 
	 * @param evalList1 .. first solution map
	 * @param evalList2 .. second solution map
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries beter
	 *         results, 0 if results (precision and recall) are equal in both lists
	 */
	private static int cmpListEvalResults(List<EvaluationResult> evalList1, List<EvaluationResult> evalList2) {
		double avgPrecisionList1 = 0;
		double avgPrecisionList2 = 0;
		double avgRecallList1 = 0;
		double avgRecallList2 = 0;

		for (EvaluationResult evalResult : evalList1) {
			avgPrecisionList1 += evalResult.getPrecision();
			avgRecallList1 += evalResult.getRecall();
		}
		for (EvaluationResult evalResult : evalList2) {
			avgPrecisionList2 += evalResult.getPrecision();
			avgRecallList2 += evalResult.getRecall();
		}
		avgPrecisionList1 /= evalList1.size();
		avgRecallList1 /= evalList1.size();
		avgPrecisionList2 /= evalList2.size();
		avgRecallList2 /= evalList2.size();

		if (avgPrecisionList1 > avgPrecisionList2) {
			return 1;
		} else if (avgPrecisionList1 < avgPrecisionList2) {
			return -1;
		} else if (avgRecallList1 > avgRecallList2) {

			return 1;
		} else if (avgRecallList1 < avgRecallList2) {
			return -1;
		} else {
			return 0;
		}
	}

	private static void setUpDatabase(String filenameData, boolean longRun, int timespan) {
		if (db == null) {
			// set up Database and Connection
			db = new TimeSeriesDatabase();
			// set up Data in TSDB
			db.setUpData(filenameData, longRun, timespan);
		}
	}

	private static List<EvaluationResult> singleStream(String filenameReal, double devLower, double devUpper) {
		// Test State Identification
		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataSingleStream(filenameReal);

		return evaluate(eval, db, setUpDataSingleStream(), devLower, devUpper);

	}

	private static void threeStreams(String filenameReal, double evalSymDevationVal) {
		// Test State Identification
		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataThreeStreams(filenameReal);

		System.out.println(evaluate(eval, db, setUpDataThreeStreams(), evalSymDevationVal));

	}

	private static List<EvaluationResult> fiveStreams(String filenameReal,
			Map<String, PropertyBoundaries> propertyMap) {
		// Test State Identification
		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataFiveStreams(filenameReal);

		return evaluate(eval, db, setUpDataFiveStreams(), propertyMap);

	}

	private static List<EvaluationResult> evaluate(Evaluation eval, TimeSeriesDatabase db, Block roboticArm,
			Map<String, PropertyBoundaries> propertyMap) {
		ArrayList<IdentifiedState> recStates = new ArrayList<IdentifiedState>();
		recStates = eval.testRecognition(db, roboticArm, propertyMap);
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
		allResults.addAll(eval.calculatePrecisionRecall(recStates));

		return allResults;
	}

	private static Block setUpDataSingleStream() {
		// setUp Information for state DriveDown
		Property p1 = new Property("gp", 1.5);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p2 = new Property("gp", -0.4);
		List<Property> props2 = new ArrayList<Property>();
		props2.add(p2);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}

	private static Block setUpDataThreeStreams() {
		// setUp Information for state DriveDown
		Property p1 = new Property("bp", 0);
		Property p2 = new Property("map", 1.5);
		Property p3 = new Property("gp", 1.5);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		props1.add(p2);
		props1.add(p3);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p4 = new Property("bp", 0);
		Property p5 = new Property("map", 1.5);
		Property p6 = new Property("gp", -0.4);

		List<Property> props2 = new ArrayList<Property>();
		props2.add(p4);
		props2.add(p5);
		props2.add(p6);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}

	private static Block setUpDataFiveStreams() {
		// setUp Information for state DriveDown
		Property p1 = new Property("bp", 0);
		Property p2 = new Property("map", 1.5);
		Property p3 = new Property("gp", 1.5);
		Property p4 = new Property("sap", -0.12);
		Property p5 = new Property("wp", 0);

		List<Property> props1 = new ArrayList<Property>();
		props1.add(p1);
		props1.add(p2);
		props1.add(p3);
		props1.add(p4);
		props1.add(p5);
		State s1 = new State("DriveDown", props1);

		// setUp Information for state PickUp
		Property p6 = new Property("bp", 0);
		Property p7 = new Property("map", 1.5);
		Property p8 = new Property("gp", -0.4);
		Property p9 = new Property("sap", -0.12);
		Property p10 = new Property("wp", 0);
		List<Property> props2 = new ArrayList<Property>();
		props2.add(p6);
		props2.add(p7);
		props2.add(p8);
		props2.add(p9);
		props2.add(p10);
		State s2 = new State("PickUp", props2);

		// List states
		List<State> states = new ArrayList<State>();
		states.add(s1);
		states.add(s2);

		// SetUp Block
		Block roboticArm = new Block("roboticArm", states);
		return roboticArm;
	}

}
