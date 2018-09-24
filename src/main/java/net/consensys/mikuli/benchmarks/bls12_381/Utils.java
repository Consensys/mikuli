package net.consensys.mikuli.benchmarks.bls12_381;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP2;
import org.apache.milagro.amcl.BLS381.ROM;

public class Utils {

	
	static public ECP createRandomPointInG1(RAND rng) {
		ECP g1Generator = ECP.generator();
			
		BIG random = BIG.randomnum(new BIG(ROM.CURVE_Order), rng);
	
		return g1Generator.mul(random);
	}

	static public ECP2 createRandomPointInG2(RAND rng) {

		BIG random = BIG.randomnum(new BIG(ROM.CURVE_Order), rng);
		ECP2 g1Generator = ECP2.generator();

		return g1Generator.mul(random);
	}
}
