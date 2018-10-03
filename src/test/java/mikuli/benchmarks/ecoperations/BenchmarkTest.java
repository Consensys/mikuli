package mikuli.benchmarks.ecoperations;

import java.util.Collection;

import org.junit.Test;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/*
Benchmark                               Mode  Cnt   Score    Error  Units
G1Benchmarks.pointAdditionInECP         avgt   20   0.005 ±  0.001  ms/op
G1Benchmarks.pointMultiplicationInECP   avgt   20   1.426 ±  0.034  ms/op
G2Benchmarks.pointAdditionInECP2        avgt   20   0.016 ±  0.001  ms/op
G2Benchmarks.pointMultiplicationInECP2  avgt   20   3.990 ±  0.096  ms/op
GTBenchmarks.pointMultiplicationInGT    avgt   20   0.042 ±  0.001  ms/op
PairingBenchmarks.pairing               avgt   20  12.318 ±  0.337  ms/op
 */

public class BenchmarkTest {

	private static final Options opt = new OptionsBuilder()
			.include(SerializationBenchmarks.class.getSimpleName())
		/*	.include(G1Benchmarks.class.getSimpleName())
			.include(G2Benchmarks.class.getSimpleName())
			.include(PairingBenchmarks.class.getSimpleName())
			.include(GTBenchmarks.class.getSimpleName())*/
			.build();

	@Test
	public void runJmhBenchmark() throws RunnerException {
		Collection<RunResult> runResults = new Runner(opt).run();
	}
}
