package mikuli.benchmarks.ecoperations;

import java.util.Collection;
import org.junit.Test;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


public class BenchmarkTest {

  private static final Options opt = new OptionsBuilder()
      .include(SerializationBenchmarks.class.getSimpleName())
      .include(G1Benchmarks.class.getSimpleName())
      .include(G2Benchmarks.class.getSimpleName())
      .include(PairingBenchmarks.class.getSimpleName())
      .include(GTBenchmarks.class.getSimpleName())
      .build();

  @Test
  public void runJmhBenchmark() throws RunnerException {
    System.out.println("BENCHS");
    Collection<RunResult> runResults = new Runner(opt).run();
  }
}
