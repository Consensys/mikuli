package net.consensys.mikuli.crypto;

public final class KeyPair {

  private final PrivateKey privateKey;
  private final PublicKey publicKey;

  KeyPair(PrivateKey privateKey, PublicKey publicKey) {
    if (privateKey == null || publicKey == null) {
      throw new NullPointerException("KeyPair was not properly initialized");
    }
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  public PublicKey publicKey() {
    return publicKey;
  }

  public PrivateKey privateKey() {
    return privateKey;
  }
}
