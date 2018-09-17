package mikuli.benchmarks.ecoperations;

import java.util.concurrent.TimeUnit;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.ROM;
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
@Fork(3)
public class G2Benchmarks {

	private ECP2 p;
	private ECP2 point = ECP2.generator();
	private BIG random;

	@Setup
	public void prepare() {
		RAND rng = new RAND();
		rng.sirand(123);
		p = Utils.createRandomPointInG2(rng);
		BIG random = BIG.randomnum(new BIG(ROM.CURVE_Order), rng);
		this.random = random;
	}

	@Benchmark
	public ECP2 pointAdditionInG2() {
		point.add(p);
		return point;
	}

	@Benchmark
	public ECP2 pointMultiplicationInG2() {
		this.point = point.mul(random);
		return point;
	}
}
