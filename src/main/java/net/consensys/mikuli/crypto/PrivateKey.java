package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP2;

public class PrivateKey {

	private final BIG privateKey;

	public PrivateKey(BIG privKey) {
		this.privateKey = privKey;
	}

	protected ECP2 sign(ECP2 p) {
		ECP2 ecp2 = new ECP2();
		ecp2.copy(p);
		return ecp2.mul(privateKey);
	}
}
