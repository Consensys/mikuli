package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.ECP2;

public class PublicKey {

	private final ECP2 point;

	protected PublicKey(ECP2 point) {
		this.point = point;
	}
	
	protected ECP2 getECPpoint() {
		return point;
	}
}
