package harmony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.Evaluation;
import main.EvaluationResult;
import main.PropertyBoundaries;
import main.StreamCount;
import main.TestData;
import output.Columns;
import output.Printer;

public class HarmonyMemory {

	private List<Map<String, PropertyBoundaries>> solutions;
	private List<EvaluationResult>[] fitness;
	private StreamCount streamCount;

	HarmonyMemory(List<Map<String, PropertyBoundaries>> initialMemory, List<EvaluationResult>[] initialFitness, StreamCount streamCount) {
		solutions = initialMemory;
		fitness = initialFitness;
		this.streamCount = streamCount;
	}

	public HarmonyMemory(HarmonyParameters params) {
		streamCount = params.getStreamCount();
		solutions = this.generateDefaultMemory(streamCount, params.getMemorySize());
		Evaluation eval = Evaluation.instance;

		fitness = new List[params.getMemorySize()];
		int count = 0;
		for (Map<String, PropertyBoundaries> solution : solutions) {
			fitness[count] = eval.evaluate(TestData.setUpDataStream(streamCount), solution);
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
	public boolean evalSolution(Map<String, PropertyBoundaries> newSolution, boolean printMemorySwaps) {

		boolean foundOptimum = false;
		Evaluation eval = Evaluation.instance;

		int worstResultIdx = findWorstEvalResult();
		List<EvaluationResult> worstResult = this.fitness[worstResultIdx];

		List<EvaluationResult> newResult = eval.evaluate(TestData.setUpDataStream(this.streamCount), newSolution);

		if (cmpListEvalResults(newResult, worstResult) > 0) {
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

			System.out.println();
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
		for (List<EvaluationResult> evalResultList : this.fitness) {
			if (cmpListEvalResults(worstEvalResult, evalResultList) > 0) {
				worstEvalResult = evalResultList;
				worstIndex = index;
			}
			index++;
		}
		return worstIndex;
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
	private int cmpListEvalResults(List<EvaluationResult> evalList1, List<EvaluationResult> evalList2) {
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
			curMap.forEach((propertyName, boundaries) -> System.out.printf("%s: %.3f, %.3f\n", propertyName,
					boundaries.getLower(), boundaries.getUpper()));

			List<EvaluationResult> solutionEvalResults = fitness[i];
			for (EvaluationResult solEvalResult : solutionEvalResults) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall());
			}
			System.out.println();
		}
	}

	private List<Map<String, PropertyBoundaries>> generateDefaultMemory(StreamCount streamCount, int memorySize) {
		
		// Initialize solution map with expected abs. sensor deviations
		Map<String, PropertyBoundaries> defaultSolutions = new HashMap<String, PropertyBoundaries>();
		switch (streamCount) {
		case FIVE:
			defaultSolutions.put("sap", new PropertyBoundaries(0.1, 0.1));
			defaultSolutions.put("wp", new PropertyBoundaries(0.1, 0.1));
		case THREE:
			defaultSolutions.put("map", new PropertyBoundaries(0.1, 0.1));
			defaultSolutions.put("bp", new PropertyBoundaries(0.1, 0.1));
		case SINGLE:
		default:
			defaultSolutions.put("gp", new PropertyBoundaries(0.1, 0.1));
		}
		List<Map<String, PropertyBoundaries>> initialMemory = new ArrayList<Map<String, PropertyBoundaries>>();

		// Initialize harmony memory with equal solution maps
		for (int memorySolutions = 0; memorySolutions < memorySize; memorySolutions++) {
			initialMemory.add(defaultSolutions);
		}
		return initialMemory;
	}
}
