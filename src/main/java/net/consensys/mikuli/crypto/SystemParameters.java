package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.ROM;
import net.consensys.mikuli.crypto.group.G2Point;

public class SystemParameters {
  static public final G2Point g2Generator = new G2Point(ECP2.generator());
  static public final BIG curveOrder = new BIG(ROM.CURVE_Order);
}
