package net.consensys.mikuli.crypto;

public final class SignatureAndPublicKey {
	private final Signature signature;
	private final PublicKey publicKey;

	SignatureAndPublicKey(Signature signature, PublicKey pubKey) {
		if (signature == null || pubKey == null) {
			throw new NullPointerException("SignatureAndPublicKey was not properly initialized");
		}
		this.signature = signature;
		this.publicKey = pubKey;
	}

	public PublicKey publicKey() {
		return publicKey;
	}

	public Signature signature() {
		return signature;
	}

	public SignatureAndPublicKey combine(SignatureAndPublicKey sigAndPubKey) {
		Signature newSignature = signature.combine(sigAndPubKey.signature);
		PublicKey newPubKey = publicKey.combine(sigAndPubKey.publicKey);
		return new SignatureAndPublicKey(newSignature, newPubKey);
	}
}