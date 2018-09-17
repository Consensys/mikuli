package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.MPIN;

public class Signature {

	private final ECP2 signature;

	public Signature(ECP2 signature) {
		this.signature = signature;
	}

	public static Signature sign(PrivateKey privateKey, byte[] message) {

		ECP2 hashOnGroup2 = hashFunction(message);
		ECP2 sig = privateKey.sign(hashOnGroup2);

		return new Signature(sig);
	}

	private static ECP2 hashFunction(byte[] message) {
		byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
		return ECP2.mapit(hashByte);
	}
}
