package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.PAIR;

import net.consensys.mikuli.benchmarks.Benchmark;

public class PointMultiplicationInGT extends Benchmark {

	private final FP12 p;
	private final FP12 m;

	public PointMultiplicationInGT(int iterations, RAND rng) {
		super(iterations, rng);
		this.p = Utils.createRandomPointInFP12(rng);
		this.m = Utils.createRandomPointInFP12(rng);

	}

	@Override
	protected void operation(int i) {
		p.mul(m);
	}

	@Override
	protected void printReport(long duration) {
		createMessage("GT", "multiplication", duration);
	}

}
