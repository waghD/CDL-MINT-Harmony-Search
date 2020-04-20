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

	public HarmonySearch(HarmonyMemory memory, HarmonyParameters params) {
		harmonyMemory = memory;
		harmonyParameters = params;
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
	 * @return HarmonyResult Result Object containing Information about this Harmony Search run
	 */
	public HarmonyResult execHarmonySearch() {
		Random rand = new Random();
		HarmonyResult hs = new HarmonyResult();

		if (harmonyParameters.getPrintNewSolutions() || harmonyParameters.getPrintMemorySwaps())
			Printer.printHeader("ALGORITHM START");
		long startIterTime = System.currentTimeMillis();
		for (int i = 0; i < harmonyParameters.getNrOfIterations(); i++) {
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
							solVec[j] = solVec[j] + (nextRandDoubleRandSign(0, 1) * harmonyParameters.getBand());
						}
					} else {

						solVec[j] = nextRandDouble(harmonyParameters.getLowerSearchBorder(), harmonyParameters.getUpperSearchBorder());

						solVec[j] = nextRandDouble(harmonyParameters.getLowerSearchBorder(), harmonyParameters.getUpperSearchBorder());
					}
				}

				newSolution.get(curPropertyName).setLower(solVec[0]);

				newSolution.get(curPropertyName).setUpper(solVec[1]);

			}
			if (harmonyParameters.getPrintNewSolutions()) {
				Printer.printHeader("New solution (" + (i + 1) + ". iteration)");
				newSolution.forEach((propertyName, boundaries) -> System.out.println(propertyName + ": " + boundaries));

			}
			//pre swap
			double bestAbsOffset = harmonyMemory.getBestAbsOffset();
			double bestAvgFMeasure = harmonyMemory.getBestAvgFMeasure();
			
			boolean foundOptimum = harmonyMemory.evalSolution(newSolution);
			boolean isBest = harmonyMemory.isSolutionBest(newSolution);

			//after swap
			double bestAbsOffsetAfterSwap = harmonyMemory.getBestAbsOffset();
			double bestAvgFMeasureAfterSwap = harmonyMemory.getBestAvgFMeasure();
			
			if (foundOptimum || isBest) {
				hs.setRuntimeTilOptimumFound((System.currentTimeMillis() - startIterTime) / 1000.0);
				if(bestAvgFMeasureAfterSwap > bestAvgFMeasure) {
					hs.setNrOfIterationsForBestFMeasure(i + 1);
					hs.setAbsOffsetBestInitial(harmonyMemory.getBestAbsOffset());
					hs.setAbsOffsetBestMinimized(harmonyMemory.getBestAbsOffset());

				} else {
					hs.setAbsOffsetBestMinimized(harmonyMemory.getBestAbsOffset());
				}
				hs.setOptimumFound(foundOptimum);
				hs.setAvgBestPrecision(harmonyMemory.getBestAvgPrecision());
				hs.setAvgBestRecall(harmonyMemory.getBestAvgRecall());
				hs.setAvgBestFMeasure(harmonyMemory.getBestAvgFMeasure());
				if (harmonyParameters.getStopOnOptimum()) {
					break;
				}
			}
			System.out.println(i+1 + ";" + harmonyMemory.getBestIndex() + ";" + harmonyMemory.getBestAvgFMeasure() + ";" + harmonyMemory.getBestAbsOffset());
			

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
}
