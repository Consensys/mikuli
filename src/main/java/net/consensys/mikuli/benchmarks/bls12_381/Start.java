package net.consensys.mikuli.benchmarks.bls12_381;

import java.util.ArrayList;
import java.util.List;

import org.apache.milagro.amcl.RAND;

import net.consensys.mikuli.benchmarks.Benchmark;

/*
4000 Iterations of point multiplication in G2 took 23439 ms
One iteration took 5.85975 ms

4000 Iterations of point multiplication in G1 took 7228 ms
One iteration took 1.807 ms

50000 Iterations of point addition in G2 took 1077 ms
One iteration took 0.02154 ms

50000 Iterations of point addition in G1 took 269 ms
One iteration took 0.00538 ms

500 Iterations of point pairings in G2 and G1 took 6682 ms
One iteration took 13.364 ms

5000 Iterations of point multiplication in GT took 225 ms
One iteration took 0.045 ms
*/

/*
 * 6000 Iterations of point multiplication in G2 took 22816 ms
One iteration took 3.8026666666666666 ms

6000 Iterations of point multiplication in G1 took 8085 ms
One iteration took 1.3475 ms

70000 Iterations of point addition in G2 took 1055 ms
One iteration took 0.01507142857142857 ms

70000 Iterations of point addition in G1 took 336 ms
One iteration took 0.0048 ms

700 Iterations of point pairings in G2 and G1 took 7936 ms
One iteration took 11.337142857142856 ms

8000 Iterations of point multiplication in GT took 311 ms
One iteration took 0.038875 ms
 */
public class Start {

	public static void main(String[] args) {

		RAND rng = new RAND();
		rng.sirand(123);

		List<Benchmark> benchmarks = new ArrayList<Benchmark>();

		benchmarks.add(new PointMultiplicationInG2Benchmark(6000, rng));
		benchmarks.add(new PointMultiplicationInG1Benchmark(6000, rng));
		benchmarks.add(new PointAdditionInG2Benchmark(50000, rng));
		benchmarks.add(new PointAdditionInG1Benchmark(50000, rng));
		benchmarks.add(new PairingBenchmark(500, rng));
		benchmarks.add(new PointMultiplicationInGT(8000, rng));

		for (Benchmark bench : benchmarks) {
			bench.benchmark();
		}
	}
}
