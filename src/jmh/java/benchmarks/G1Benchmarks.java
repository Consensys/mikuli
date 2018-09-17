package benchmarks;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ROM;
import org.apache.milagro.amcl.RAND;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
public class G1Benchmarks {

  private ECP p;
  private ECP point = ECP.generator();
  private BIG random;

  @Setup
  public void prepare() {
    RAND rng = new RAND();
    rng.sirand(123);
    p = Utils.createRandomPointInG1(rng);

    BIG random = BIG.randomnum(new BIG(ROM.CURVE_Order), rng);
    this.random = random;
  }

  @Benchmark
  public ECP pointAdditionInG1() {
    point.add(p);
    return point;
  }

  @Benchmark
  public ECP pointMultiplicationInG1() {
    this.point = point.mul(random);
    return point;
  }
}
