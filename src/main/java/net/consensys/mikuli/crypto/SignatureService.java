package net.consensys.mikuli.crypto;

import java.util.List;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.MPIN;
import org.apache.milagro.amcl.BLS381.PAIR;

public final class SignatureService {

	static public final class Signature {
		private final ECP2 ecp2Point;

		private Signature(ECP2 signature) {
			this.ecp2Point = signature;
		}
	}

	/**
	 * Generates a SignatureAndPublicKey.
	 *
	 * @param keyPair The public and private key pair.
	 * @param message The message to sign.
	 * @return The SignatureAndPublicKey.
	 */
	public static SignatureAndPublicKey sign(KeyPair keyPair, byte[] message) {
		ECP2 hashInGroup2 = hashFunction(message);
		/*
		 * The signature is hashInGroup2 multiplied by the private key.
		 */
		ECP2 sig = keyPair.getPrivateKey().sign(hashInGroup2);

		return new SignatureAndPublicKey(new Signature(sig), keyPair.getPublicKey());
	}

	/**
	 * Verifies the given BLS signature against the message bytes using the public
	 * key.
	 * 
	 * @param publicKey The public key.
	 * @param signature The signature.
	 * @param message The message data to verify.
	 * 
	 * @return True if the verification is successful.
	 */
	public static boolean verify(PublicKey pubKey, Signature sig, byte[] message) {
		ECP g1Generator = SystemParameters.g1Generator;
		ECP2 hashInGroup2 = hashFunction(message);
		FP12 e1 = pair(pubKey.getECPpoint(), hashInGroup2);
		FP12 e2 = pair(g1Generator, sig.ecp2Point);

		return e1.equals(e2);
	}

	/**
	 * Verifies the given BLS signature against the message bytes using the public
	 * key.
	 * 
	 * @param sigAndPubKey The signature and public key.
	 * @param message The message data to verify.
	 * 
	 * @return True if the verification is successful.
	 */
	public static boolean verify(SignatureAndPublicKey sigAndPubKey, byte[] message) {
		return verify(sigAndPubKey.getPublicKey(), sigAndPubKey.getSignature(), message);
	}

	/**
	 * Aggregates list of Signature and PublicKey pairs.
	 * 
	 * @param sigAndPubKeyList The list of Signatures and corresponding Public keys
	 *            to aggregate.
	 * @return Aggregated public key and signature.
	 */
	public static SignatureAndPublicKey aggregate(List<SignatureAndPublicKey> sigAndPubKeyList) {
		ECP sumInG1 = new ECP();
		ECP2 sumInG2 = new ECP2();

		for (SignatureAndPublicKey sigAndPubKey : sigAndPubKeyList) {
			sumInG1.add(sigAndPubKey.getPublicKey().getECPpoint());
			sumInG2.add(sigAndPubKey.getSignature().ecp2Point);
		}

		/* For efficiency milagro performs calculations in Jacobian coordinates */
		sumInG1.affine();
		sumInG2.affine();

		PublicKey pubKey = new PublicKey(sumInG1);
		Signature sig = new Signature(sumInG2);

		SignatureAndPublicKey aggregated = new SignatureAndPublicKey(sig, pubKey);
		return aggregated;
	}

	private static ECP2 hashFunction(byte[] message) {
		byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
		/* Fast Hashing to G2 - Fuentes-Castaneda, Knapp and Rodriguez-Henriquez */
		return ECP2.mapit(hashByte);
	}

	private static FP12 pair(ECP p, ECP2 q) {
		FP12 e = PAIR.ate(q, p);
		return PAIR.fexp(e);
	}
}
