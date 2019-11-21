package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.util.HashMap;

import design.Block;
import design.Property;
import design.State;
import runtime.IdentifiedState;
import timeSeries.TimeSeriesDatabase;

public class Main {
	private static TimeSeriesDatabase db = null;

	public static void main(String[] args) {

		setUpDatabase("./lib/Daten_156.csv", false, 0);
		
		Random rand = new Random();
		int nrOfIter = 30;
		//Initialize harmony memory
		for(double adj_val = 0.1; adj_val <= 0.5; adj_val += 0.2) {
			System.out.println("---------------------------------------------\n---------------------------------------------");
			System.out.printf("Adjustment value: %.2f\n", adj_val);
			System.out.println("---------------------------------------------\n---------------------------------------------");

//			double[][] harmonyMemory = {
//					{0.02,0.02}
//			};
//			HarmonyParameters hpa = new HarmonyParameters(harmonyMemory, adj_val, 0.03, 0.9);
//			System.out.println("Initial harmony");
//			printGrid(harmonyMemory);
//			
//			double[] newSolution = new double[harmonyMemory[0].length];
//			for(int i = 0; i < nrOfIter; i++) {
//				for(int j = 0; j < hpa.getSolutions()[0].length; j++) {
//					if(rand.nextDouble() < hpa.getR_accept()) {
//						newSolution[j] = hpa.getSolutions()[rand.nextInt(hpa.getSolutions().length)][j];
//						if(rand.nextDouble() < hpa.getR_pa()) {
//							newSolution[j] = newSolution[j] + ThreadLocalRandom.current().nextDouble(0, 1) * hpa.getBand();
//						
//						}
//					} else {
//						newSolution[j] =  ThreadLocalRandom.current().nextDouble(0, hpa.getBand());
//					}
//				}
//				
//				hpa.setSolutions(evalSolution(newSolution, hpa.getSolutions(), "./lib/realStates_156.csv",  false));
//			}
//			System.out.println("After:");
//
//			printGrid(harmonyMemory);
			double[][] harmonyMemory = {
					{0.1,0.5},
					{0.002,0.1},
					{0.3,0.4},
					{0.3,0.4}
			};
			HarmonyParameters hpa = new HarmonyParameters(harmonyMemory, adj_val, 0.03, 0.9);
			System.out.println("Initial harmony");
			printGrid(harmonyMemory);
			
			double[] newSolution = new double[harmonyMemory[0].length];
			for(int i = 0; i < nrOfIter; i++) {
				for(int j = 0; j < hpa.getSolutions()[0].length; j++) {
					if(rand.nextDouble() < hpa.getR_accept()) {
						newSolution[j] = hpa.getSolutions()[rand.nextInt(hpa.getSolutions().length)][j];
						if(rand.nextDouble() < hpa.getR_pa()) {
							newSolution[j] = newSolution[j] + ThreadLocalRandom.current().nextDouble(0, 1) * hpa.getBand();
						}
					} else {
						newSolution[j] =  ThreadLocalRandom.current().nextDouble(0, hpa.getBand());
					}
				}
				
				hpa.setSolutions(evalSolution(newSolution, hpa.getSolutions(), "./lib/realStates_156.csv",  false));
			}
			System.out.println("After:");

			printGrid(harmonyMemory);
		}
		
		
		
	/*
		for(double deviationVal = 0.01; deviationVal < 0.1; deviationVal += 0.01) {
			System.out.println("single - symmetric deviation = " + deviationVal);
			singleStream("./lib/realStates_156.csv", deviationVal);
			System.out.println("three");
			threeStreams("./lib/realStates_156.csv", deviationVal);
			System.out.println("five");
			fiveStreams("./lib/realStates_156.csv", deviationVal);
		}*/
		

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
		// long run 15600
		db = null;
		setUpDatabase("./lib/Daten_1560.csv", true, 9);
		System.out.println("single");
		singleStream("./lib/realStates_1560.csv");
		System.out.println("three");
		threeStreams("./lib/realStates_1560.csv");
		System.out.println("five");
		fiveStreams("./lib/realStates_1560.csv");

		// long run 156000
		db = null;
		setUpDatabase("./lib/Daten_1560.csv", true, 99);
		System.out.println("single");
		singleStream("./lib/realStates_1560.csv");
		System.out.println("three");
		threeStreams("./lib/realStates_1560.csv");
		System.out.println("five");
		fiveStreams("./lib/realStates_1560.csv");

		// long run 1560000
		db = null;
		setUpDatabase("./lib/Daten_1560.csv", true, 900);
		System.out.println("single");
		singleStream("./lib/realStates_1560.csv");
		System.out.println("three");
		threeStreams("./lib/realStates_1560.csv");
		System.out.println("five");
		fiveStreams("./lib/realStates_1560.csv");
		*/

	}
	
	public static void printGrid(double[][] solutions)
	{
	   for(int i = 0; i < solutions.length; i++)
	   {
	      for(int j = 0; j < solutions[i].length; j++)
	      {
	         System.out.printf("\n%.8f ", solutions[i][j]);
	        
	      }
	      List<EvaluationResult> solutionEvalResults = evaluateStream("./lib/realStates_156.csv", solutions[i][0], solutions[i][1], StreamCount.FIVE);
	      for(EvaluationResult solEvalResult : solutionEvalResults) {
	        	 System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(), solEvalResult.getPrecision(), solEvalResult.getRecall());
	      }
	   }
	}
	
	public static double[][] evalSolution(double[] solution, double[][] solutions, String realStatesFilePath, boolean printActions) {
		List<ArrayList<EvaluationResult>> evalResults = new ArrayList<ArrayList<EvaluationResult>>();
		for(int i = 0; i < solutions.length; i++) {
			evalResults.add((ArrayList<EvaluationResult>) evaluateStream(realStatesFilePath, solutions[i][0], solutions[i][1], StreamCount.FIVE));
			//System.out.println("(" + solutions[i][0] + "," + solutions[i][1] + ") => " + func(solutions[i][0], solutions[i][1]));
		}
	
		List<EvaluationResult> worstResult = findWorstEvalResult(evalResults);
		
		List<EvaluationResult> newResult = evaluateStream(realStatesFilePath, solution[0], solution[1], StreamCount.FIVE);
		if(printActions)
			System.out.println("New solution: (" + solution[0] + "," + solution[1] + ") => " + newResult);
		if(cmpListEvalResults(newResult, worstResult) > 0) {
			int worstResultIdx = evalResults.indexOf(worstResult);
			solutions[worstResultIdx][0] = solution[0];
			solutions[worstResultIdx][1] = solution[1];
			if(printActions)
				System.out.print("\nExchanged " + newResult + " for\n " + worstResult);
		}
		return solutions;
	}
	
	private static List<EvaluationResult> findWorstEvalResult(List<ArrayList<EvaluationResult>> evalResultListofLists) {
		List<EvaluationResult> worstEvalResult = evalResultListofLists.get(0);
		for(ArrayList<EvaluationResult> evalResultList : evalResultListofLists) {
			if(cmpListEvalResults(worstEvalResult, evalResultList) > 0) {
				worstEvalResult = evalResultList;
			}
		}
		return worstEvalResult;
	}
	
	/**
	 * @param evalList1
	 * @param evalList2
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries better results, 0 if results are equal in both lists
	 */
	private static int cmpListEvalResults(List<EvaluationResult> evalList1, List<EvaluationResult> evalList2) {
		double avgPrecisionList1 = 0;
		double avgPrecisionList2 = 0;
		double avgRecallList1 = 0;
		double avgRecallList2 = 0;

		for(EvaluationResult evalResult : evalList1) {
			avgPrecisionList1 += evalResult.getPrecision();
			avgRecallList1 += evalResult.getRecall();
		}
		for(EvaluationResult evalResult : evalList2) {
			avgPrecisionList2 += evalResult.getPrecision();
			avgRecallList2 += evalResult.getRecall();
		}
		avgPrecisionList1 /= evalList1.size();
		avgRecallList1 /= evalList1.size();
		avgPrecisionList2 /= evalList2.size();
		avgRecallList2 /= evalList2.size();
		
		if(avgPrecisionList1 > avgPrecisionList2) {
			return 1;
		} else if(avgPrecisionList1 < avgPrecisionList2) {
			return -1;
		} else if(avgRecallList1 > avgRecallList2) {
			
			return 1;
		} else if(avgRecallList1 < avgRecallList2) {
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

	private static List<EvaluationResult> evaluateStream(String filenameReal, double devLower, double devUpper, StreamCount streamCount) {
		// Test State Identification
		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataStream(filenameReal, streamCount);

		return eval.evaluate(TestData.setUpDataStream(streamCount), devLower, devUpper);
	}

}
