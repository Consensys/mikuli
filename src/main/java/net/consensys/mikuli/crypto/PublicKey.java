package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.ECP;

public class PublicKey {

	private final ECP point;

	protected PublicKey(ECP point) {
		this.point = point;
	}
	
	protected ECP getECPpoint() {
		return point;
	}
}
