package net.consensys.mikuli.crypto;

import java.util.List;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.MPIN;
import org.apache.milagro.amcl.BLS381.PAIR;

public class Sig {

	static public class Signature {
		private final ECP2 ecp2Point;

		private Signature(ECP2 signature) {
			this.ecp2Point = signature;
		}
	}

	static public class SignatureAndPublicKey {
		private final Signature signature;
		private final PublicKey publicKey;

		private SignatureAndPublicKey(Signature signature, PublicKey pubKey) {
			this.signature = signature;
			this.publicKey = pubKey;
		}

		public PublicKey getPublicKey() {
			return publicKey;
		}
		
		public Signature getSignature() {
			return signature;
		}

	}

	public static SignatureAndPublicKey sign(KeyPair keyPair, byte[] message) {

		ECP2 hashinGroup2 = hashFunction(message);
		ECP2 sig = keyPair.getPrivateKey().sign(hashinGroup2);

		return new SignatureAndPublicKey(new Signature(sig), keyPair.getPublicKey());
	}

	public static boolean verify(PublicKey pubKey, Signature sig, byte[] message) {

		ECP g1Generator = SystemParameters.g1Generator;
		ECP2 hashinGroup2 = hashFunction(message);
		FP12 e1 = pair(pubKey.getECPpoint(), hashinGroup2);
		FP12 e2 = pair(g1Generator, sig.ecp2Point);

		return e1.equals(e2);
	}

	public static SignatureAndPublicKey aggregate(List<SignatureAndPublicKey> sigs) {
		PublicKey pubKey = new PublicKey(new ECP());
		Signature sig = new Signature(new ECP2());

		for (SignatureAndPublicKey s : sigs) {
			sig.ecp2Point.add(s.signature.ecp2Point);
			pubKey.getECPpoint().add(s.publicKey.getECPpoint());
		}
		sig.ecp2Point.affine();
		pubKey.getECPpoint().affine();

		SignatureAndPublicKey agg = new SignatureAndPublicKey(sig, pubKey);

		return agg;
	}

	private static ECP2 hashFunction(byte[] message) {
		byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
		return ECP2.mapit(hashByte);
	}

	private static FP12 pair(ECP p, ECP2 q) {
		FP12 e = PAIR.ate(q, p);
		return PAIR.fexp(e);
	}
}
