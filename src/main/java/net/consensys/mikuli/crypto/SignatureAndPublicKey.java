package net.consensys.mikuli.crypto;

import net.consensys.mikuli.crypto.SignatureService.Signature;

public final class SignatureAndPublicKey {
	private final Signature signature;
	private final PublicKey publicKey;

	SignatureAndPublicKey(Signature signature, PublicKey pubKey) {
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