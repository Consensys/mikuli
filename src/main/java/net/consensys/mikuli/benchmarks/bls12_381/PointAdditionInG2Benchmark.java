package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP2;
import org.apache.milagro.amcl.BLS381.ROM;
import net.consensys.mikuli.benchmarks.Benchmark;

public class PointAdditionInG2Benchmark extends Benchmark {

	private final ECP2 p;
	private final ECP2 point = ECP2.generator();

	public PointAdditionInG2Benchmark(int iterations, RAND rng) {
		super(iterations, rng);
		this.p = Utils.createRandomPointInG2(rng);
	}

	@Override
	protected void operation(int i) {
		point.add(p);
	}

	@Override
	protected void printReport(long duration) {
		createMessage("G2", "addition", duration);
	}
}
