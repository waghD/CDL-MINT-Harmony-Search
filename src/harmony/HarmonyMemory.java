package harmony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import main.AxisStream;
import main.Evaluation;
import main.EvaluationResult;
import main.PropertyBoundaries;
import main.TestData;
import output.Columns;
import output.Printer;

public class HarmonyMemory {

	// Parameters with which harmony search is run
	private HarmonyParameters harmonyParameters;
	
	// Solution maps, bUpper and bLower in PropertyBoundaries per sensor
	private List<Map<String, PropertyBoundaries>> solutions;
	
	// f score per solution
	private List<EvaluationResult>[] fitness;
	
	// offset sum per solution
	private double[] overallOffsets;
	
	// List of axis used by this Harmony Search run
	private List<AxisStream> axisStream;

	
	/**
	 * Initialize memory with initial memory solutions, f1 scores and used sensors
	 * 
	 * @param params .. initialized memory pre search phase
	 */
	public HarmonyMemory(HarmonyParameters params) {
		this.harmonyParameters = params;
		axisStream = params.getAxisStreams();
		solutions = this.generateDefaultMemory();
		Evaluation eval = Evaluation.instance;
		overallOffsets = new double[params.getMemorySize()];
		fitness = new List[params.getMemorySize()];
		int count = 0;
		for (Map<String, PropertyBoundaries> solution : solutions) {
			fitness[count] = eval.evaluate(TestData.setUpDataStream(axisStream), solution, false,
					params.getStatesToNotEvaluate());
			double curOverallOffset = 0;
			for (Entry<String, PropertyBoundaries> pair : solution.entrySet()) {
				curOverallOffset += pair.getValue().getLower() + pair.getValue().getUpper();
			}
			overallOffsets[count] = curOverallOffset;
			count++;
		}
	}

	public List<Map<String, PropertyBoundaries>> getSolutions() {
		return this.solutions;
	}

	public List<EvaluationResult>[] getFitness() {
		return this.fitness;
	}

	public double[] getOverallOffsets() {
		return this.overallOffsets;
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
	 * @param newSolution .. the new solution map
	 * @return boolean .. true when optimal solution (precision and recall both =
	 *         1.0) found
	 */
	public boolean evalSolution(Map<String, PropertyBoundaries> newSolution) {

		boolean foundOptimum = false;
		Evaluation eval = Evaluation.instance;

		// get offset and result of worst solution in memory
		int worstResultIdx = findWorstEvalResult();
		List<EvaluationResult> worstResult = this.fitness[worstResultIdx];
		double worstSolutionOffset = this.overallOffsets[worstResultIdx];

		// evaluate new solution
		List<EvaluationResult> newResult = eval.evaluate(TestData.setUpDataStream(this.axisStream), newSolution, false,
				harmonyParameters.getStatesToNotEvaluate());

		// calculate absolute offset of new solution
		double newSolutionOffset = 0;
		for (Entry<String, PropertyBoundaries> pair : newSolution.entrySet()) {
			newSolutionOffset += pair.getValue().getLower() + pair.getValue().getUpper();
		}

		// compare new solution result to worst solution result
		if (cmpListEvalResults(newResult, newSolutionOffset, worstResult, worstSolutionOffset, false) > 0) {
			// new solution is better
			
			if (harmonyParameters.getPrintMemorySwaps()) {
				
				// print swap to console
				Printer.printHeader("SWAP: Solution " + (worstResultIdx) + " (worst) -> New solution");
				Map<String, PropertyBoundaries> worstSolution = solutions.get(worstResultIdx);
				// Get all properties in map
				List<String> propertyNameList = new ArrayList<>(newSolution.keySet());
				for (int i = 0; i < propertyNameList.size(); i++) {
					String curProperty = propertyNameList.get(i);
					new Columns().addLine(
							curProperty + ": " + worstSolution.get(curProperty) + " -> " + newSolution.get(curProperty))
							.print();
				}
				double worstFMeasure = 0;
				double worstPrec = 0;
				double worstRec = 0;
				for (EvaluationResult res : worstResult) {
					worstFMeasure += res.getfMeasure() / worstResult.size();
					worstPrec += res.getPrecision() / worstResult.size();
					worstRec += res.getRecall() / worstResult.size();
				}
				double newFMeasure = 0;
				double newPrec = 0;
				double newRec = 0;
				for (EvaluationResult res : newResult) {
					newFMeasure += res.getfMeasure() / newResult.size();
					newPrec += res.getPrecision() / newResult.size();
					newRec += res.getRecall() / newResult.size();
				}
				System.out.println("--------MEMORY STATE--------");
				for (int a = 0; a < this.fitness.length; a++) {
					double fScore = 0.0;
					for (EvaluationResult evalResult : this.fitness[a]) {
						fScore += evalResult.getfMeasure() / this.fitness[a].size();
					}
					System.out.println(a + ": " + fScore);
				}
				System.out.println("//--------MEMORY STATE--------");
				System.out.print("\n" + worstResult + "\n==> Fscore:" + worstFMeasure + ", Precision:" + worstPrec
						+ ", Recall:" + worstRec + "\n->\n" + newResult + "\n==> Fscore:" + newFMeasure + ", Precision:"
						+ newPrec + ", Recall:" + newRec);
			}

			// add new solution in place of worst solution
			solutions.set(worstResultIdx, newSolution);
			fitness[worstResultIdx] = newResult;
			overallOffsets[worstResultIdx] = newSolutionOffset;

			// check if optimum was found
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

	/**
	 * Tests if new solution is the currently best solution in whole memory.
	 * 
	 * @param testSolution .. solution
	 * @return true if solution is best in memory
	 */
	public boolean isSolutionBest(Map<String, PropertyBoundaries> testSolution) {
		int bestIndex = this.findBestEvalResult();
		Map<String, PropertyBoundaries> bestSolution = this.solutions.get(bestIndex);
		return bestSolution.equals(testSolution);
	}

	/**
	 * Get the memory index of best result
	 * 
	 * @return int Index
	 */
	public int getBestIndex() {
		return this.findBestEvalResult();
	}

	/**
	 * Get the average FMeasure of the best result in memory
	 * 
	 * @return Avg Fmeasure
	 */
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

	/**
	 * Get average Precision of the best result in memory
	 * 
	 * @return Avg Precision
	 */
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

	/**
	 * Get average Recall of the best result in memory
	 * 
	 * @return Avg Recall
	 */
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
	 * Get the summed up offset of all sensors of best result in memory
	 * 
	 * @return double Sum of offsets
	 */
	public double getBestAbsOffset() {
		int bestIndex = this.findBestEvalResult();
		return this.overallOffsets[bestIndex];
	}

	/**
	 * Determines worst result in list of solution maps. Worst solution is
	 * determined by worst precision/recall
	 * 
	 * @return index of worst result
	 */
	private int findWorstEvalResult() {
		int worstIndex = 0;
		int index = 0;
		List<EvaluationResult> worstEvalResult = this.fitness[0];
		double worstSolOffset = this.overallOffsets[0];
		for (List<EvaluationResult> evalResultList : this.fitness) {
			if (cmpListEvalResults(worstEvalResult, worstSolOffset, evalResultList, this.overallOffsets[index],
					false) > 0) {
				worstEvalResult = evalResultList;
				worstSolOffset = this.overallOffsets[index];
				worstIndex = index;
			}
			index++;
		}
		return worstIndex;
	}

	/**
	 * Determines best result in list of solution maps. Best solution is determined
	 * by highest f-measure
	 * 
	 * @return Index of best result
	 */
	public int findBestEvalResult() {
		int bestIndex = 0;
		int index = 0;
		List<EvaluationResult> bestEvalResult = this.fitness[0];
		double bestSolOffset = this.overallOffsets[0];
		for (List<EvaluationResult> evalResultList : this.fitness) {
			if (cmpListEvalResults(evalResultList, this.overallOffsets[index], bestEvalResult, bestSolOffset,
					false) > 0) {
				bestEvalResult = evalResultList;
				bestSolOffset = this.overallOffsets[index];
				bestIndex = index;
			}
			index++;
		}
		return bestIndex;
	}

	/**
	 * 
	 * @param evalList1 .. first solution map
	 * @param evalList2 .. second solution map
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries better
	 *         results, 0 if results (precision and recall) are equal in both lists
	 */

	/**
	 * Compares two EvaluationResult objects and determines result from worse
	 * solution.
	 * 
	 * Worse solution is the one with worse mean (of states) precision/recall
	 * whereas precision has higher priority.
	 * 
	 * @param evalList1  .. first solution map
	 * @param solOffset1 .. avg offset of first solution map
	 * @param evalList2  .. second solution map
	 * @param solOffset2 .. avg offset of second solution map
	 * @param print      .. flag for verbose printing
	 * @return
	 */
	private int cmpListEvalResults(List<EvaluationResult> evalList1, double solOffset1,
			List<EvaluationResult> evalList2, double solOffset2, boolean print) {
		double avgFMeasureList1 = 0;
		double avgFMeasureList2 = 0;

		for (EvaluationResult evalResult : evalList1) {
			avgFMeasureList1 += evalResult.getfMeasure();
		}
		for (EvaluationResult evalResult : evalList2) {
			avgFMeasureList2 += evalResult.getfMeasure();
		}
		avgFMeasureList1 /= evalList1.size();
		avgFMeasureList2 /= evalList2.size();

		if (avgFMeasureList1 > avgFMeasureList2) {
			if (print)
				System.out.format("Swapped for better f-measure! (%.4f > %.4f)\n", avgFMeasureList1, avgFMeasureList2);

			return 1;
		} else if (avgFMeasureList1 == avgFMeasureList2) {
			if (harmonyParameters.getMinimizeResult() && solOffset1 < solOffset2) {
				if (print)
					System.out.format("Swapped for narrower range! (%.4f < %.4f)\n", solOffset1, solOffset2);
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	/**
	 * Prints all the solutions (= total deviances per sensor) with resulting
	 * measures
	 */
	public void print() {
		for (int i = 0; i < solutions.size(); i++) {
			Map<String, PropertyBoundaries> curMap = solutions.get(i);
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
			System.out.printf("\n\nf-measure: %.3f\n\n",
					fmeasureList.stream().mapToDouble(x -> x).average().orElse(-1));
		}
	}

	/**
	 * Prints a single solution (= total deviances per sensor) with resulting
	 * measures
	 * 
	 * @param idx .. index of solution
	 */
	public void print(int idx) {
		Map<String, PropertyBoundaries> curMap = solutions.get(idx);
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
		System.out.printf("\n\nf-measure: %.3f\nAbs. Offset Value: %.3f\n",
				fmeasureList.stream().mapToDouble(x -> x).average().orElse(-1), this.overallOffsets[idx]);

	}

	/**
	 * Initializes the Memory with random Values
	 * 
	 * @return Initialized Memory
	 */
	private List<Map<String, PropertyBoundaries>> generateDefaultMemory() {
		List<Map<String, PropertyBoundaries>> initialMemory = new ArrayList<Map<String, PropertyBoundaries>>();

		// Initialize solution map with expected abs. sensor deviations
		for (int memorySolutions = 0; memorySolutions < harmonyParameters.getMemorySize(); memorySolutions++) {
			Map<String, PropertyBoundaries> defaultSolutions = new HashMap<String, PropertyBoundaries>();
			
			if (harmonyParameters.getAxisStreams().contains(AxisStream.BP)) {
				defaultSolutions.put(AxisStream.BP.getAxisName(),
						new PropertyBoundaries(
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder()),
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder())));
			}
			
			if (harmonyParameters.getAxisStreams().contains(AxisStream.GP)) {
				defaultSolutions.put(AxisStream.GP.getAxisName(),
						new PropertyBoundaries(
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder()),
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder())));
			}
			
			if (harmonyParameters.getAxisStreams().contains(AxisStream.MAP)) {
				defaultSolutions.put(AxisStream.MAP.getAxisName(),
						new PropertyBoundaries(
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder()),
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder())));
			}
			
			if (harmonyParameters.getAxisStreams().contains(AxisStream.SAP)) {
				defaultSolutions.put(AxisStream.SAP.getAxisName(),
						new PropertyBoundaries(
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder()),
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder())));
			}
			
			if (harmonyParameters.getAxisStreams().contains(AxisStream.WP)) {
				defaultSolutions.put(AxisStream.WP.getAxisName(),
						new PropertyBoundaries(
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder()),
								nextRandDouble(harmonyParameters.getLowerSearchBorder(),
										harmonyParameters.getUpperSearchBorder())));
			}

			initialMemory.add(defaultSolutions);
		}
		return initialMemory;
	}

	private double nextRandDouble(double lower, double upper) {
		Random rand = new Random();
		return rand.nextDouble() * (upper - lower) + lower;
	}
}