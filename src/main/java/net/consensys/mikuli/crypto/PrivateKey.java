package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;

public final class PrivateKey {

	private final BIG privateKey;

	public PrivateKey(BIG privKey) {
		this.privateKey = privKey;
	}

	protected ECP sign(ECP p) {
		ECP ecp = new ECP();
		ecp.copy(p);
		return ecp.mul(privateKey);
	}
}
