package net.consensys.mikuli.crypto.group;

import org.apache.milagro.amcl.BLS381.BIG;

public class Scalar {

  private final BIG value;

  public Scalar(BIG value) {
    if (value == null) {
      throw new NullPointerException("value is null");
    }
    this.value = value;
  }

  BIG value() {
    return value;
  }
}
