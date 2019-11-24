package main;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HarmonySearch {

	private HarmonyMemory harmonyMemory;
	private HarmonyParameters harmonyParameters;

	HarmonySearch(HarmonyMemory memory, HarmonyParameters params) {
		harmonyMemory = memory;
		harmonyParameters = params;
	}

	public void search(int maxIter) {
		Random rand = new Random();
		System.out.println("Initial harmony");
		harmonyMemory.print();

		double[] newSolution = new double[2];
		for (int i = 0; i < maxIter; i++) {
			for (int j = 0; j < harmonyMemory.getMemory()[0].length; j++) {
				if (rand.nextDouble() < harmonyParameters.getR_accept()) {
					newSolution[j] = harmonyMemory.getMemory()[rand.nextInt(harmonyMemory.getMemory().length)][j];
					if (rand.nextDouble() < harmonyParameters.getR_pa()) {
						newSolution[j] = newSolution[j]
								+ ThreadLocalRandom.current().nextDouble(0, 1) * harmonyParameters.getBand();
					}
				} else {
					newSolution[j] = ThreadLocalRandom.current().nextDouble(0, harmonyParameters.getBand());
				}
			}

			harmonyMemory.evalSolution(newSolution, harmonyMemory.getMemory(), false);
		}
		System.out.println("\n\nAfter:");

		harmonyMemory.print();
	}

}
