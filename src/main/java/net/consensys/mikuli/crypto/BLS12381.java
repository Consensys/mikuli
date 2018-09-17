package net.consensys.mikuli.crypto;

import java.util.List;

import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.FP12;
import org.apache.milagro.amcl.BLS381.MPIN;
import org.apache.milagro.amcl.BLS381.PAIR;

public final class BLS12381 {

	private static final int pointSize = BIG.MODBYTES;

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
		 * The signature is hash point in G1 multiplied by the private key.
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
		FP12 e1 = pair(hashInGroup1, pubKey.getECP2point());
		FP12 e2 = pair(sig.getEcpPoint(), g2Generator);

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
	 * @return SignatureAndPublicKey
	 */
	public static SignatureAndPublicKey aggregate(List<SignatureAndPublicKey> sigAndPubKeyList) {
		ECP sumInG1 = new ECP();
		ECP2 sumInG2 = new ECP2();

		for (SignatureAndPublicKey sigAndPubKey : sigAndPubKeyList) {
			sumInG1.add(sigAndPubKey.getSignature().getEcpPoint());
			sumInG2.add(sigAndPubKey.getPublicKey().getECP2point());
		}

		/* For efficiency milagro performs calculations in Jacobian coordinates */
		sumInG1.affine();
		sumInG2.affine();

		Signature sig = new Signature(sumInG1);
		PublicKey pubKey = new PublicKey(sumInG2);

		SignatureAndPublicKey aggregated = new SignatureAndPublicKey(sig, pubKey);
		return aggregated;
	}

	/**
	 * Signature deserialization
	 * 
	 * @param bytes
	 * @return Signature
	 */
	public static Signature decodeSignature(byte[] bytes) {
		ECP point = ECP.fromBytes(bytes);
		return new Signature(point);
	}

	/**
	 * Signature serialization
	 * 
	 * @param signature The Signature
	 * @return byte array representation of the signature
	 */
	public static byte[] encodeSignature(Signature signature) {
		// Size of the byte array representing compressed ECP point for BLS12-381 is
		// 49 bytes in milagro
		// size of the point = 48 bytes
		// meta information (parity bit, curve type etc) = 1 byte
		byte[] bytes = new byte[pointSize + 1];
		signature.getEcpPoint().toBytes(bytes, true);
		return bytes;
	}

	/**
	 * PublicKey deserialization
	 * 
	 * @param bytes
	 * @return PublicKey
	 */
	public static PublicKey decodePublicKey(byte[] bytes) {
		ECP2 point = ECP2.fromBytes(bytes);
		return new PublicKey(point);
	}

	/**
	 * Public key serialization
	 * 
	 * @param publicKey The Public key
	 * @return byte array representation of the public key
	 */
	public static byte[] encodePublicKey(PublicKey publicKey) {
		byte[] bytes = new byte[4 * pointSize];
		publicKey.getECP2point().toBytes(bytes);
		return bytes;
	}

	private static ECP hashFunction(byte[] message) {
		byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
		return ECP.mapit(hashByte);
	}

	private static FP12 pair(ECP p, ECP2 q) {
		FP12 e = PAIR.ate(q, p);
		return PAIR.fexp(e);
	}
}
