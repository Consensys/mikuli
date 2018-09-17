package benchmarks;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.ROM;
import org.apache.milagro.amcl.RAND;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
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
