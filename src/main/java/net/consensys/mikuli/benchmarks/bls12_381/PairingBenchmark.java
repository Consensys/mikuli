package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.PAIR;

import net.consensys.mikuli.benchmarks.Benchmark;

public class PairingBenchmark extends Benchmark {

	private final ECP[] pointsInG1;
	private final ECP2[] pointsInG2;

	public PairingBenchmark(int iterations, RAND rng) {
		super(iterations, rng);
		pointsInG1 = new ECP[iterations];
		pointsInG2 = new ECP2[iterations];

		for (int i = 0; i < iterations; i++) {
			pointsInG1[i] = Utils.createRandomPointInG1(rng);
			pointsInG2[i] = Utils.createRandomPointInG2(rng);
		}
	}

	@Override
	protected void operation(int i) {
		FP12 e = PAIR.ate(pointsInG2[i], pointsInG1[i]);
		PAIR.fexp(e);
	}

	@Override
	protected void printReport(long duration) {
		createMessage("G2 and G1", "pairings", duration);

	}
}
