package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ROM;

public class KeyPairFactory {

	static public KeyPair createKeyPair() {
		ECP g1Generator = ECP.generator();
		RAND rng = new RAND();

		BIG curveOrder = new BIG(ROM.CURVE_Order);
		BIG secret = BIG.randomnum(curveOrder, rng);

		PrivateKey privateKey = new PrivateKey(secret);
		PublicKey publicKey = new PublicKey(g1Generator.mul(secret));
		return new KeyPair(privateKey, publicKey);
	}
}
