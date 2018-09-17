package net.consensys.mikuli.crypto.group;

import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.PAIR;

public class AtePairing {

  /**
   * 
   * @param p1 the point in Group1, not null
   * @param p2 the point in Group2, not null
   * @return GTPoint
   */
  public static GTPoint pair(G1Point p1, G2Point p2) {
    FP12 e = PAIR.ate(p2.ecp2Point(), p1.ecpPoint());
    return new GTPoint(PAIR.fexp(e));
  }
}
