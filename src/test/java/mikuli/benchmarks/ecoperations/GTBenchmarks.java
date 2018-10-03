package mikuli.benchmarks.ecoperations;

import java.util.concurrent.TimeUnit;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.FP12;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
public class GTBenchmarks {

	private FP12 p;
	private FP12 m;

	@Setup
	public void prepare() {
		RAND rng = new RAND();
		rng.sirand(123);

		this.p = Utils.createRandomPointInFP12(rng);
		this.m = Utils.createRandomPointInFP12(rng);
	}

	@Benchmark
	public FP12 pointMultiplicationInGT() {
		p.mul(m);
		return p;
	}
}
