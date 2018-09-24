package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ROM;

import net.consensys.mikuli.benchmarks.Benchmark;

public class PointMultiplicationInG1Benchmark extends Benchmark {

	private final BIG big;
	private final ECP point = ECP.generator();

	public PointMultiplicationInG1Benchmark(int iterations, RAND rng) {
		super(iterations, rng);
		BIG random = BIG.randomnum(new BIG(ROM.CURVE_Order), rng);
		this.big = random;
	}

	@Override
	protected void operation(int i) {
		point.mul(big);
	}

	@Override
	protected void printReport(long duration) {
		createMessage("G1", "multiplication", duration);
	}
}
