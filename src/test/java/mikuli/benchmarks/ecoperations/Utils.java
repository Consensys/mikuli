package mikuli.benchmarks.ecoperations;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.PAIR;
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
	
	static public FP12 createRandomPointInFP12(RAND rng) {
		ECP ecp = createRandomPointInG1(rng);
		ECP2 ecp2 = createRandomPointInG2(rng);
		
		FP12 e = PAIR.ate(ecp2, ecp);
		return PAIR.fexp(e);
	}
}
