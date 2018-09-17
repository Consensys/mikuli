package net.consensys.mikuli.crypto;

public final class KeyPair {

	private final PrivateKey privateKey;
	private final PublicKey publicKey;

	public KeyPair(PrivateKey privateKey, PublicKey publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}
}
