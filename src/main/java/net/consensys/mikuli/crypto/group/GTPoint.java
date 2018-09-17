package net.consensys.mikuli.crypto.group;

import org.apache.milagro.amcl.BLS381.FP12;

public class GTPoint {

  private final FP12 point;

  GTPoint(FP12 point) {
    this.point = point;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((point == null) ? 0 : point.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GTPoint other = (GTPoint) obj;
    if (point == null) {
      if (other.point != null)
        return false;
    } else if (!point.equals(other.point))
      return false;
    return true;
  }

}
