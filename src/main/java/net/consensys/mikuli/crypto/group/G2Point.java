package net.consensys.mikuli.crypto.group;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;

public final class G2Point implements Group<G2Point> {
  private final ECP2 point;
  private static final int fpPointSize = BIG.MODBYTES;

  public G2Point(ECP2 point) {
    if (point == null) {
      throw new NullPointerException("ECP2 point is null");
    }
    this.point = point;
  }

  public G2Point add(G2Point other) {
    ECP2 sum = new ECP2();
    sum.add(point);
    sum.add(other.point);
    sum.affine();
    return new G2Point(sum);
  }

  public G2Point mul(Scalar scalar) {
    ECP2 newPoint = point.mul(scalar.value());
    return new G2Point(newPoint);
  }

  public byte[] toBytes() {
    byte[] bytes = new byte[4 * fpPointSize];
    point.toBytes(bytes);
    return bytes;
  }

  public static G2Point fromBytes(byte[] bytes) {
    return new G2Point(ECP2.fromBytes(bytes));
  }

  ECP2 ecp2Point() {
    return point;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    G2Point other = (G2Point) obj;
    if (point == null) {
      if (other.point != null)
        return false;
    } else if (!point.equals(other.point))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long xa = point.getX().getA().norm();
    long ya = point.getY().getA().norm();
    long xb = point.getX().getB().norm();
    long yb = point.getY().getB().norm();
    result = prime * result + (int) (xa ^ (xa >>> 32));
    result = prime * result + (int) (ya ^ (ya >>> 32));
    result = prime * result + (int) (xb ^ (xb >>> 32));
    result = prime * result + (int) (yb ^ (yb >>> 32));
    return result;
  }
}
