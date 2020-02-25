package harmony;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import design.Property;
import main.AxisStream;
import main.Evaluation;
import main.EvaluationResult;
import main.PropertyBoundaries;
import main.TestData;
import output.Columns;
import output.Printer;

public class HarmonyMemory {

	private List<Map<String, PropertyBoundaries>> solutions;
	private List<EvaluationResult>[] fitness;
	private double[] overallOffsets;

	private List<AxisStream> axisStream;

	HarmonyMemory(List<Map<String, PropertyBoundaries>> initialMemory, List<EvaluationResult>[] initialFitness, List<AxisStream> axisStream) {
		solutions = initialMemory;
		fitness = initialFitness;
		this.axisStream = axisStream;
	}

	public List<Map<String, PropertyBoundaries>> getSolutions() {
		return this.solutions;
	}
	public HarmonyMemory(HarmonyParameters params, List<String> statesToEvaluateList, double spaceMin, double spaceMax) {
		axisStream = params.getAxisStreams();
		solutions = this.generateDefaultMemory(axisStream, params.getMemorySize(), spaceMin, spaceMax);
		Evaluation eval = Evaluation.instance;
		overallOffsets = new double[params.getMemorySize()];
		fitness = new List[params.getMemorySize()];
		int count = 0;
		for (Map<String, PropertyBoundaries> solution : solutions) {
			fitness[count] = eval.evaluate(TestData.setUpDataStream(axisStream), solution, false, statesToEvaluateList);
			double curOverallOffset = 0;
			for (Entry<String, PropertyBoundaries> pair : solution.entrySet()) {
			    curOverallOffset += pair.getValue().getLower() + pair.getValue().getUpper();
			}
			overallOffsets[count] = curOverallOffset;
			count++;
		}
	}

	List<Map<String, PropertyBoundaries>> getMemory() {
		return solutions;
	}

	void setMemory(List<Map<String, PropertyBoundaries>> newMemory) {
		solutions = newMemory;
	}

	/**
	 * Evaluates whether new solution is better than worst solution in memory. In
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
	 * @return boolean .. true when optimal solution (precision and recall both =
	 *         1.0) found
	 */
	public boolean evalSolution(Map<String, PropertyBoundaries> newSolution, boolean printMemorySwaps, List<String> statesToEvaluateList) {

		boolean foundOptimum = false;
		Evaluation eval = Evaluation.instance;

		int worstResultIdx = findWorstEvalResult();
		List<EvaluationResult> worstResult = this.fitness[worstResultIdx];
		double worstSolutionOffset = this.overallOffsets[worstResultIdx];
		
		List<EvaluationResult> newResult = eval.evaluate(TestData.setUpDataStream(this.axisStream), newSolution, false, statesToEvaluateList);

		double newSolutionOffset = 0;
		for (Entry<String, PropertyBoundaries> pair : newSolution.entrySet()) {
			newSolutionOffset += pair.getValue().getLower() + pair.getValue().getUpper();
		}
		
		if (cmpListEvalResults(newResult, newSolutionOffset, worstResult, worstSolutionOffset, false) > 0) {
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
			fitness[worstResultIdx] = newResult;
			overallOffsets[worstResultIdx] = newSolutionOffset;
			
			foundOptimum = true;
			for (int i = 0; i < newResult.size(); i++) {
				EvaluationResult stateResult = newResult.get(i);
				if (stateResult.getPrecision() < 1.0 || stateResult.getRecall() < 1.0) {
					foundOptimum = false;
					break;
				}
			}
		}
		return foundOptimum;
	}
	
	public boolean isSolutionBest(Map<String, PropertyBoundaries> testSolution) {
		int bestIndex = this.findBestEvalResult();
		Map<String, PropertyBoundaries> bestSolution = this.solutions.get(bestIndex);
		return bestSolution.equals(testSolution);
	}
	
	
	
	public double getBestAvgFMeasure() {
		int bestIndex = this.findBestEvalResult();
		List<EvaluationResult> best = this.fitness[bestIndex];
		double fMeasure = 0.0;
		for (EvaluationResult evalResult : best) {
			fMeasure += evalResult.getfMeasure();
		}
		fMeasure /= best.size();
		return fMeasure;
	}
	
	
	public double getBestAvgPrecision() {
		int bestIndex = this.findBestEvalResult();
		List<EvaluationResult> best = this.fitness[bestIndex];
		double avgPrec = 0.0;
		for (EvaluationResult evalResult : best) {
			avgPrec += evalResult.getPrecision();
		}
		avgPrec /= best.size();
		return avgPrec;
	}
	
	public double getBestAvgRecall() {
		int bestIndex = this.findBestEvalResult();
		List<EvaluationResult> best = this.fitness[bestIndex];
		double avgRec = 0.0;
		for (EvaluationResult evalResult : best) {
			avgRec += evalResult.getRecall();
		}
		avgRec /= best.size();
		return avgRec;
	}

	/**
	 * Determines worst result in list of solution maps.
	 * 
	 * Worst solution is determined by worst precision/recall
	 * 
	 * @param evalResultListofLists
	 * @return
	 */
	private int findWorstEvalResult() {
		int worstIndex = 0;
		int index = 0;
		List<EvaluationResult> worstEvalResult = this.fitness[0];
		double worstSolOffset = this.overallOffsets[0];
		for (List<EvaluationResult> evalResultList : this.fitness) {
			if (cmpListEvalResults(worstEvalResult, worstSolOffset, evalResultList, this.overallOffsets[index], false) > 0) {
				worstEvalResult = evalResultList;
				worstIndex = index;
			}
			index++;
		}
		return worstIndex;
	}
	
	/**
	 * Determines worst result in list of solution maps.
	 * 
	 * Worst solution is determined by worst precision/recall
	 * 
	 * @param evalResultListofLists
	 * @return
	 */
	public int findBestEvalResult() {
		int bestIndex = 0;
		int index = 0;
		List<EvaluationResult> bestEvalResult = this.fitness[0];
		double bestSolOffset = this.overallOffsets[0];
		for (List<EvaluationResult> evalResultList : this.fitness) {
			if (cmpListEvalResults(evalResultList, bestSolOffset, bestEvalResult, this.overallOffsets[index], false) > 0) {
				bestEvalResult = evalResultList;
				bestIndex = index;
			}
			index++;
		}
		return bestIndex;
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
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries better
	 *         results, 0 if results (precision and recall) are equal in both lists
	 */
	private int cmpListEvalResults(List<EvaluationResult> evalList1, double solOffset1, List<EvaluationResult> evalList2, double solOffset2, boolean print) {
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

		if ((avgPrecisionList1 + avgRecallList1) > (avgPrecisionList2 + avgRecallList2)) {
			if(print)System.out.format("Swapped for better precision/recall! (%.4f > %.4f)\n", avgPrecisionList1+avgRecallList1, avgPrecisionList2+avgRecallList2);

			return 1;
		} else if ((avgPrecisionList1 + avgRecallList1) == (avgPrecisionList2 + avgRecallList2)) {
			if(solOffset1 < solOffset2) {
				if(print)System.out.format("Swapped for narrower range! (%.4f < %.4f)\n", solOffset1, solOffset2);
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
		/*if (avgPrecisionList1 > avgPrecisionList2) {
			return 1;
		} else if (avgPrecisionList1 < avgPrecisionList2) {
			return -1;
		} else if (avgRecallList1 > avgRecallList2) {

			return 1;
		} else if (avgRecallList1 < avgRecallList2) {
			return -1;
		} else {
			return 0;
		}*/
	}

	/**
	 * Prints the solutions (= total deviances per sensor) with resulting measures
	 * 
	 * @param solutions .. list of solution maps
	 */
	public void print() {
		for (int i = 0; i < solutions.size(); i++) {
			Map<String, PropertyBoundaries> curMap = solutions.get(i);
			Iterator<Map.Entry<String, PropertyBoundaries>> it = curMap.entrySet().iterator();
			System.out.println(Printer.div + "\nSolution " + (i + 1) + " in memory\n" + Printer.div);
			curMap.forEach((propertyName, boundaries) -> System.out.printf("%s: %.7f, %.7f\n", propertyName,
					boundaries.getLower(), boundaries.getUpper()));

			List<EvaluationResult> solutionEvalResults = fitness[i];
			for (EvaluationResult solEvalResult : solutionEvalResults) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f F-Measure: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall(), solEvalResult.getfMeasure());
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints the solutions (= total deviances per sensor) with resulting measures
	 * 
	 * @param solutions .. list of solution maps
	 */
	public void print() {
		for (int i = 0; i < solutions.size(); i++) {
			Map<String, PropertyBoundaries> curMap = solutions.get(i);
			Iterator<Map.Entry<String, PropertyBoundaries>> it = curMap.entrySet().iterator();
			System.out.println(Printer.div + "\nSolution " + (i + 1) + " in memory\n" + Printer.div);
			curMap.forEach((propertyName, boundaries) -> System.out.printf("%s: %.7f, %.7f\n", propertyName,
					boundaries.getLower(), boundaries.getUpper()));

			List<EvaluationResult> solutionEvalResults = fitness[i];
			List<Double> fmeasureList = new ArrayList<Double>();

			for (EvaluationResult solEvalResult : solutionEvalResults) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall());
				fmeasureList.add(solEvalResult.getfMeasure());

			}
			System.out.printf("\n\nf-measure: %.3f\n\n", fmeasureList.stream().mapToDouble(x -> x).average().orElse(-1));
		}
	}
	
	/**
	 * Prints the solutions (= total deviances per sensor) with resulting measures
	 * 
	 * @param solutions .. list of solution maps
	 */
	public void print(int idx) {
			Map<String, PropertyBoundaries> curMap = solutions.get(idx);
			Iterator<Map.Entry<String, PropertyBoundaries>> it = curMap.entrySet().iterator();
			System.out.println(Printer.div + "\nSolution " + (idx + 1) + " in memory\n" + Printer.div);
			curMap.forEach((propertyName, boundaries) -> System.out.printf("%s: %.4f, %.4f\n", propertyName,
					boundaries.getLower(), boundaries.getUpper()));

			List<EvaluationResult> solutionEvalResults = fitness[idx];
			List<Double> fmeasureList = new ArrayList<Double>();
			for (EvaluationResult solEvalResult : solutionEvalResults) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall());
				fmeasureList.add(solEvalResult.getfMeasure());
				
			}
			System.out.printf("\n\nf-measure: %.3f\nAbs. Offset Value: %.3f\n", fmeasureList.stream().mapToDouble(x -> x).average().orElse(-1), this.overallOffsets[idx]);
	
	}

	private List<Map<String, PropertyBoundaries>> generateDefaultMemory(List<AxisStream> axisList, int memorySize, double spaceMin, double spaceMax) {
		Random rand = new Random();
		List<Map<String, PropertyBoundaries>> initialMemory = new ArrayList<Map<String, PropertyBoundaries>>();

		// Initialize solution map with expected abs. sensor deviations
		for (int memorySolutions = 0; memorySolutions < memorySize; memorySolutions++) {
			Map<String, PropertyBoundaries> defaultSolutions = new HashMap<String, PropertyBoundaries>();
			if (axisList.contains(AxisStream.BP)) {
				defaultSolutions.put(AxisStream.BP.getAxisName(), new PropertyBoundaries(nextRandDouble(spaceMin, spaceMax), nextRandDouble(spaceMin, spaceMax)));
			}
			if (axisList.contains(AxisStream.GP)) {
				defaultSolutions.put(AxisStream.GP.getAxisName(), new PropertyBoundaries(nextRandDouble(spaceMin, spaceMax), nextRandDouble(spaceMin, spaceMax)));
			}
			if (axisList.contains(AxisStream.MAP)) {
				defaultSolutions.put(AxisStream.MAP.getAxisName(), new PropertyBoundaries(nextRandDouble(spaceMin, spaceMax), nextRandDouble(spaceMin, spaceMax)));
			}
			if (axisList.contains(AxisStream.SAP)) {
				defaultSolutions.put(AxisStream.SAP.getAxisName(), new PropertyBoundaries(nextRandDouble(spaceMin, spaceMax), nextRandDouble(spaceMin, spaceMax)));
			}
			if (axisList.contains(AxisStream.WP)) {
				defaultSolutions.put(AxisStream.WP.getAxisName(), new PropertyBoundaries(nextRandDouble(spaceMin, spaceMax), nextRandDouble(spaceMin, spaceMax)));
			}
	
			// Initialize harmony memory with equal solution maps
			
			initialMemory.add(defaultSolutions);
		}
		return initialMemory;
	}
	
	 private double nextRandDouble(double lower, double upper) {
		 	Random rand = new Random();
		   	return rand.nextDouble() * (upper - lower) + lower;
	 }
}