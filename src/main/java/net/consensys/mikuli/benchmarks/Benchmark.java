package net.consensys.mikuli.benchmarks;

import org.apache.milagro.amcl.RAND;

public abstract class Benchmark {

	protected final int iterations;
	protected final RAND rng;

	public Benchmark(int iterations, RAND rng) {
		this.iterations = iterations;
		this.rng = rng;
	}

	public void benchmark() {
		wormUP();
		long startTime = System.currentTimeMillis();
		iterations();
		long endTime = System.currentTimeMillis();
		printReport(endTime - startTime);
	}

	protected void wormUP() {
		iterations();
	}

	protected void iterations() {
		for (int x = 0; x < iterations; x++) {
			operation(x);
		}
	}

	protected abstract void operation(int i);

	protected abstract void printReport(long duration);

	protected void createMessage(String group, String operation, long duration) {

		String totalTimeMsg = String.format("%d Iterations of point %s in %s took %s ms", iterations, operation, group,
				duration);

		Double iterationTime = Double.valueOf(duration) / Double.valueOf(iterations);
		String iterationTimeMsg = String.format("One iteration took %s ms", iterationTime);

		System.out.println(totalTimeMsg);
		System.out.println(iterationTimeMsg);
		System.out.println();
	}
}
