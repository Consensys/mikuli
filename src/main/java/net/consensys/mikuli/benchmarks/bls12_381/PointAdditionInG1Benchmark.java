package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ROM;

import net.consensys.mikuli.benchmarks.Benchmark;

public class PointAdditionInG1Benchmark extends Benchmark {

	private final ECP p;
	private final ECP point = ECP.generator();

	public PointAdditionInG1Benchmark(int iterations, RAND rng) {
		super(iterations, rng);	
		this.p = Utils.createRandomPointInG1(rng);
	}

	@Override
	protected void operation(int i) {
		point.add(p);
	}

	@Override
	protected void printReport(long duration) {
		createMessage("G1", "addition", duration);
	}
}
