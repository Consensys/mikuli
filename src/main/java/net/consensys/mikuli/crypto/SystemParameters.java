package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.ROM;

public class SystemParameters {
	static public final ECP2 g2Generator = ECP2.generator();
	static public final BIG curveOrder = new BIG(ROM.CURVE_Order);
}
