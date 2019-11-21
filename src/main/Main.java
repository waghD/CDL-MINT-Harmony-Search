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
	private static int counter =0;

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
	      List<EvaluationResult> solutionEvalResults = fiveStreams("./lib/realStates_156.csv", solutions[i][0], solutions[i][1]);
	      for(EvaluationResult solEvalResult : solutionEvalResults) {
	        	 System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(), solEvalResult.getPrecision(), solEvalResult.getRecall());
	      }
	   }
	}
	
	public static double[][] evalSolution(double[] solution, double[][] solutions, String realStatesFilePath, boolean printActions) {
		List<ArrayList<EvaluationResult>> evalResults = new ArrayList<ArrayList<EvaluationResult>>();
		for(int i = 0; i < solutions.length; i++) {
			evalResults.add((ArrayList<EvaluationResult>) fiveStreams(realStatesFilePath, solutions[i][0], solutions[i][1]));
			//System.out.println("(" + solutions[i][0] + "," + solutions[i][1] + ") => " + func(solutions[i][0], solutions[i][1]));
		}
	
		List<EvaluationResult> worstResult = findWorstEvalResult(evalResults);
		
		List<EvaluationResult> newResult = fiveStreams(realStatesFilePath, solution[0], solution[1]);
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
	 *
	 * 
	 * @param evalList1
	 * @param evalList2
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries beter results, 0 if results are equal in both lists
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

	private static  List<EvaluationResult>  fiveStreams(String filenameReal, double devLower, double devUpper) {
		// Test State Identification
		Evaluation eval = new Evaluation();
		// SetUp all information about the states in the files
		eval.setUpRealDataFiveStreams(filenameReal);

		return evaluate(eval, db, setUpDataFiveStreams(),  devLower, devUpper);

	}
	//List<Double> harmonics, double r_pa, double pitch_lower_bound, double pitch_upper_bound, double r_accept
	private static List<EvaluationResult> evaluateWithHarmony(Evaluation eval, TimeSeriesDatabase db, Block roboticArm) {
		ArrayList<IdentifiedState> recStates = new ArrayList<IdentifiedState>();
		int nrOfIterations = 20;
		Map<String, Boundaries<Double, Double>> harmonics = new HashMap<String, Boundaries<Double, Double>>();
		// Initialize start harmonics for each state
		for(State s : roboticArm.getAssignedState()) {
			harmonics.put(s.getName(), new Boundaries<Double,Double>(0.1,0.1));
		}
		HarmonyParameters harmonyParam = new HarmonyParameters(0.3, 0.5, 0.1, 1, 0.7);
		for(int i = 0; i < nrOfIterations; i++) {
			recStates = eval.testRecognitionWithHarmony(db, roboticArm, harmonics, harmonyParam);
			List<EvaluationResult> allResults = new ArrayList<EvaluationResult>();
			PrintWriter pw;
			String dir = "modelTest/";
			String filename = "result"+counter+".csv";
			String path = dir+filename;
			try {
				File f = new File(dir,filename);
				f.createNewFile();
				pw = new PrintWriter(new File(path));
				String row ="";
				Collections.sort(recStates);
				int countProcess =0;
				for (IdentifiedState identifiedState : recStates) {
					if(identifiedState.getName().equals("DriveDown")) {
						countProcess++;	
					}
					row = countProcess+";"+identifiedState.getName()+ ";"+identifiedState.getTs().toString()+"\n";
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
		}

		return allResults;
	}

	private static List<EvaluationResult> evaluate(Evaluation eval, TimeSeriesDatabase db, Block roboticArm, double devLower, double devUpper) {
		ArrayList<IdentifiedState> recStates = new ArrayList<IdentifiedState>();
			recStates = eval.testRecognition(db, roboticArm, devLower, devUpper);
			List<EvaluationResult> allResults = new ArrayList<EvaluationResult>();
			PrintWriter pw;
			String dir = "modelTest/";
			String filename = "result"+counter+".csv";
			String path = dir+filename;
			try {
				File f = new File(dir,filename);
				f.createNewFile();
				pw = new PrintWriter(new File(path));
				String row ="";
				Collections.sort(recStates);
				int countProcess =0;
				for (IdentifiedState identifiedState : recStates) {
					if(identifiedState.getName().equals("DriveDown")) {
						countProcess++;	
					}
					row = countProcess+";"+identifiedState.getName()+ ";"+identifiedState.getTs().toString()+"\n";
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
