package harmony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import main.EvaluationResult;
import main.PropertyBoundaries;
import output.Printer;

public class HarmonySearch {

	private HarmonyMemory harmonyMemory;
	private HarmonyParameters harmonyParameters;
	private double spaceMin; // minimum of search space
	private double spaceMax; // maxiumum of search space

	public HarmonySearch(HarmonyMemory memory, HarmonyParameters params, double spaceMin, double spaceMax) {
		harmonyMemory = memory;
		harmonyParameters = params;
		this.spaceMin = spaceMin;
		this.spaceMax = spaceMax;
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
	 * @param nrOfIter           .. number of iterations (each yields a new
	 *                           solution)
	 * @param stopIfOptimumFound .. if true, algorithm stops iteration loop when
	 *                           optimum is found
	 * @param printNewSolutions  .. print statements of newly generated solutions
	 * @param printMemorySwaps   .. print statements of memory swaps
	 * @return int .. number of iterations required to find optimum, 0 if no optimum
	 *         was found
	 */
	public HarmonyResult execHarmonySearch(int nrOfIter, boolean stopIfOptimumFound, List<String> statesToEvaluateList,
			boolean printNewSolutions, boolean printMemorySwaps, boolean minimizeBandwidth) {
		Random rand = new Random();
		HarmonyResult hs = new HarmonyResult();

		if (printNewSolutions || printMemorySwaps)
			Printer.printHeader("ALGORITHM START");
		long startIterTime = System.currentTimeMillis();
		for (int i = 0; i < nrOfIter; i++) {
			// Init new solution map
			Map<String, PropertyBoundaries> newSolution = new HashMap<String, PropertyBoundaries>();
			// Get the property names (keys) of a solution map of a solution of the harmony
			// memory (and convert to list)
			Set<String> propertyNamesSet = harmonyMemory.getMemory().get(0).keySet();
			List<String> propertyNamesList = new ArrayList<>(propertyNamesSet);

			for (String prop : propertyNamesList) {
				newSolution.put(prop, new PropertyBoundaries(0, 0));
			}
			// int nrOfProperties = hpa.getSolutions().get(0).size()
			for (int z = 0; z < propertyNamesList.size(); z++) {

				String curPropertyName = propertyNamesList.get(z);
				double[] solVec = new double[2]; // solution vector

				for (int j = 0; j < 2; j++) {
					if (rand.nextDouble() < harmonyParameters.getR_accept()) {
						int nrOfSolutionsInMemory = harmonyMemory.getMemory().size();

						solVec[j] = harmonyMemory.getMemory().get(rand.nextInt(nrOfSolutionsInMemory))
								.get(curPropertyName).getAsArray()[j];

						if (rand.nextDouble() < harmonyParameters.getR_pa()) {
							//solVec[j] = solVec[j] + (nextRandDoubleRandSign(0, 1) * harmonyParameters.getBand());
							if (rand.nextDouble() < 0.5) {
								solVec[j] = solVec[j] - ((solVec[j] - harmonyMemory.getMemoryMinAtPropertyAndDim(curPropertyName, j))*rand.nextDouble());
							} else {
								solVec[j] = solVec[j] + ((harmonyMemory.getMemoryMaxAtPropertyAndDim(curPropertyName, j) - solVec[j])*rand.nextDouble());
							}
						}
					} else {

						solVec[j] = nextRandDouble(spaceMin, spaceMax);

						solVec[j] = nextRandDouble(spaceMin, spaceMax);
					}
				}

				newSolution.get(curPropertyName).setLower(solVec[0]);

				newSolution.get(curPropertyName).setUpper(solVec[1]);

			}
			if (printNewSolutions) {
				Printer.printHeader("New solution (" + (i + 1) + ". iteration)");
				newSolution.forEach((propertyName, boundaries) -> System.out.println(propertyName + ": " + boundaries));

			}

			boolean foundOptimum = harmonyMemory.evalSolution(newSolution, printMemorySwaps, statesToEvaluateList, minimizeBandwidth);
			boolean isBest = harmonyMemory.isSolutionBest(newSolution, minimizeBandwidth);
			// only remember iteration where FIRST optimum was found
			if (foundOptimum || isBest) {
				hs.setRuntimeTilOptimumFound((System.currentTimeMillis() - startIterTime) / 1000.0);
				hs.setNrOfIterationsForOptimum(i + 1);
				hs.setOptimumFound(foundOptimum);
				hs.setAvgBestPrecision(harmonyMemory.getBestAvgPrecision(minimizeBandwidth));
				hs.setAvgBestRecall(harmonyMemory.getBestAvgRecall(minimizeBandwidth));
				hs.setAvgBestFMeasure(harmonyMemory.getBestAvgFMeasure(minimizeBandwidth));
				if (stopIfOptimumFound) {
					break;
				}
			}
			System.out.println(i+1 + ";" + harmonyMemory.getBestIndex(minimizeBandwidth) + ";" + harmonyMemory.getBestAvgFMeasure(minimizeBandwidth) + ";" + harmonyMemory.getBestAbsOffset(minimizeBandwidth));
			

		}
		
		hs.setRuntimeIterations((System.currentTimeMillis() - startIterTime) / 1000.0);
		return hs;
	}

	public double nextRandDouble(double lower, double upper) {
		Random rand = new Random();
		return rand.nextDouble() * (upper - lower) + lower;
	}

	public double nextRandDoubleRandSign(double lower, double upper) {
		Random rand = new Random();
		double rv = rand.nextDouble() * (upper - lower) + lower;
		return rand.nextDouble() >= 0.5 ? rv : (rv * -1);
	}

	public double getSpaceMin() {
		return spaceMin;
	}

	public void setSpaceMin(double spaceMin) {
		this.spaceMin = spaceMin;
	}

	public double getSpaceMax() {
		return spaceMax;
	}

	public void setSpaceMax(double spaceMax) {
		this.spaceMax = spaceMax;
	}
}
