package net.consensys.mikuli.benchmarks.bls12_381;

import java.util.ArrayList;
import java.util.List;

import org.apache.milagro.amcl.RAND;

import net.consensys.mikuli.benchmarks.Benchmark;

/*
4000 Iterations of point multiplication in G2 took 15798 ms
One iteration took 3.9495 ms

4000 Iterations of point multiplication in G1 took 5758 ms
One iteration took 1.4395 ms

50000 Iterations of point addition in G2 took 762 ms
One iteration took 0.01524 ms

50000 Iterations of point addition in G1 took 243 ms
One iteration took 0.00486 ms

500 Iterations of point pairings in G2 and G1 took 6138 ms
One iteration took 12.276 ms
 */

public class Start {

	public static void main(String[] args) {

		RAND rng = new RAND();
		rng.sirand(123);

		List<Benchmark> benchmarks = new ArrayList<Benchmark>();

		benchmarks.add(new PointMultiplicationInG2Benchmark(4000, rng));
		benchmarks.add(new PointMultiplicationInG1Benchmark(4000, rng));
		benchmarks.add(new PointAdditionInG2Benchmark(50000, rng));
		benchmarks.add(new PointAdditionInG1Benchmark(50000, rng));
		benchmarks.add(new PairingBenchmark(500, rng));

		for (Benchmark bench : benchmarks) {
			bench.benchmark();
		}
	}
}
