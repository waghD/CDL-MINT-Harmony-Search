package main;

import java.util.List;

public class HarmonyMemory {

	private double[][] memory;
	private List<EvaluationResult>[] fitness;
	private StreamCount streamCount;

	HarmonyMemory(double[][] initialMemory, List<EvaluationResult>[] initialFitness, StreamCount streamCount) {
		memory = initialMemory;
		fitness = initialFitness;
		this.streamCount = streamCount;
	}

	double[][] getMemory() {
		return memory;
	}

	void setMemory(double[][] newMemory) {
		memory = newMemory;
	}

	public double[][] evalSolution(double[] solution, double[][] solutions, boolean printActions) {
		Evaluation eval = Evaluation.instance;

		int worstIndex = findWorstEvalResult();
		List<EvaluationResult> worstResult = this.fitness[worstIndex];

		List<EvaluationResult> newResult = eval.evaluate(TestData.setUpDataStream(this.streamCount), solution[0],
				solution[1]);

		if (printActions)
			System.out.println("New solution: (" + solution[0] + "," + solution[1] + ") => " + newResult);
		if (cmpListEvalResults(newResult, worstResult) > 0) {
			solutions[worstIndex][0] = solution[0];
			solutions[worstIndex][1] = solution[1];
			fitness[worstIndex] = newResult;
			if (printActions)
				System.out.print("\nExchanged " + newResult + " for\n " + worstResult);
		}
		this.memory = solutions;
		return solutions;
	}

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
	 * @param evalList1
	 * @param evalList2
	 * @return 1 if evalList1 carries better results, -1 if evalList2 carries better
	 *         results, 0 if results are equal in both lists
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

	public void print() {
		for (int i = 0; i < memory.length; i++) {
			for (int j = 0; j < memory[i].length; j++) {
				System.out.printf("\n%.8f ", memory[i][j]);
			}
			for (EvaluationResult solEvalResult : fitness[i]) {
				System.out.printf("\n=> %s: Precision: %.2f  Recall: %.2f", solEvalResult.getState(),
						solEvalResult.getPrecision(), solEvalResult.getRecall());
			}
		}
	}
}
