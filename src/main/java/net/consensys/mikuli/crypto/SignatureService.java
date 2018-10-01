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
		private final ECP ecp2Point;

		private Signature(ECP signature) {
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
		ECP hashInGroup1 = hashFunction(message);
		/*
		 * The signature is hashInGroup2 multiplied by the private key.
		 */
		ECP sig = keyPair.getPrivateKey().sign(hashInGroup1);

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
		ECP2 g2Generator = SystemParameters.g2Generator;
		ECP hashInGroup1 = hashFunction(message);
		FP12 e1 = pair(hashInGroup1, pubKey.getECPpoint());
		FP12 e2 = pair(sig.ecp2Point, g2Generator);

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
			sumInG1.add(sigAndPubKey.getSignature().ecp2Point);
			sumInG2.add(sigAndPubKey.getPublicKey().getECPpoint());
		}

		/* For efficiency milagro performs calculations in Jacobian coordinates */
		sumInG1.affine();
		sumInG2.affine();

		Signature sig = new Signature(sumInG1);
		PublicKey pubKey = new PublicKey(sumInG2);
	
		SignatureAndPublicKey aggregated = new SignatureAndPublicKey(sig, pubKey);
		return aggregated;
	}

	private static ECP hashFunction(byte[] message) {
		byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
		/* Fast Hashing to G2 - Fuentes-Castaneda, Knapp and Rodriguez-Henriquez */
		return ECP.mapit(hashByte);
	}

	private static FP12 pair(ECP p, ECP2 q) {
		FP12 e = PAIR.ate(q, p);
		return PAIR.fexp(e);
	}
}
