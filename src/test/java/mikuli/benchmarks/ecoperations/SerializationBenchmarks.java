package mikuli.benchmarks.ecoperations;

import java.util.concurrent.TimeUnit;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
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
public class SerializationBenchmarks {

  private ECP p;
  byte[] serializedCompressedPoint;
  byte[] serializedPoint;

  @Setup
  public void prepare() {
    RAND rng = new RAND();
    rng.sirand(123);
    p = Utils.createRandomPointInG1(rng);
    int pointSize = BIG.MODBYTES;

    serializedCompressedPoint = new byte[pointSize + 1];
    p.toBytes(serializedCompressedPoint, true);

    serializedPoint = new byte[2 * pointSize + 1];
    p.toBytes(serializedPoint, false);
  }

  @Benchmark
  public ECP pointDeSerializationNoCompression() {
    ECP point = ECP.fromBytes(serializedPoint);
    return point;
  }

  @Benchmark
  public ECP pointDeSerializationWithCompression() {
    ECP point = ECP.fromBytes(serializedCompressedPoint);
    return point;
  }
}
