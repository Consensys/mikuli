package net.consensys.mikuli.crypto;

import java.util.List;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.MPIN;
import net.consensys.mikuli.crypto.group.AtePairing;
import net.consensys.mikuli.crypto.group.G1Point;
import net.consensys.mikuli.crypto.group.G2Point;
import net.consensys.mikuli.crypto.group.GTPoint;

public final class BLS12381 {

  /**
   * Generates a SignatureAndPublicKey.
   *
   * @param keyPair The public and private key pair, not null
   * @param message The message to sign, not null
   * @return The SignatureAndPublicKey, not null
   */
  public static SignatureAndPublicKey sign(KeyPair keyPair, byte[] message) {
    G1Point hashInGroup1 = hashFunction(message);
    /*
     * The signature is hash point in G1 multiplied by the private key.
     */
    G1Point sig = keyPair.privateKey().sign(hashInGroup1);
    return new SignatureAndPublicKey(new Signature(sig), keyPair.publicKey());
  }

  /**
   * Verifies the given BLS signature against the message bytes using the public key.
   * 
   * @param publicKey The public key, not null
   * @param signature The signature, not null
   * @param message The message data to verify, not null
   * 
   * @return True if the verification is successful.
   */
  public static boolean verify(PublicKey pubKey, Signature sig, byte[] message) {
    G2Point g2Generator = SystemParameters.g2Generator;

    G1Point hashInGroup1 = hashFunction(message);
    GTPoint e1 = AtePairing.pair(hashInGroup1, pubKey.g2Point());
    GTPoint e2 = AtePairing.pair(sig.g1Point(), g2Generator);

    return e1.equals(e2);
  }

  /**
   * Verifies the given BLS signature against the message bytes using the public key.
   * 
   * @param sigAndPubKey The signature and public key, not null
   * @param message The message data to verify, not null
   * 
   * @return True if the verification is successful, not null
   */
  public static boolean verify(SignatureAndPublicKey sigAndPubKey, byte[] message) {
    return verify(sigAndPubKey.publicKey(), sigAndPubKey.signature(), message);
  }

  /**
   * Aggregates list of Signature and PublicKey pairs
   * 
   * @param sigAndPubKeyList The list of Signatures and corresponding Public keys to aggregate, not
   *        null
   * @return SignatureAndPublicKey, not null
   * @throws IllegalArgumentException if parameter list is empty
   */
  public static SignatureAndPublicKey aggregate(List<SignatureAndPublicKey> sigAndPubKeyList) {
    listNotEmpty(sigAndPubKeyList);
    return sigAndPubKeyList.stream().reduce((a, b) -> a.combine(b)).get();
  }

  /**
   * Aggregates list of PublicKey pairs
   * 
   * @param publicKeyList The list of public keys to aggregate, not null
   * @return PublicKey The public key, not null
   * @throws IllegalArgumentException if parameter list is empty
   */
  public static PublicKey aggregatePublicKey(List<PublicKey> publicKeyList) {
    listNotEmpty(publicKeyList);
    return publicKeyList.stream().reduce((a, b) -> a.combine(b)).get();
  }

  /**
   * Aggregates list of Signature pairs
   * 
   * @param signatureList The list of signatures to aggregate, not null
   * @throws IllegalArgumentException if parameter list is empty
   * @return Signature, not null
   */
  public static Signature aggregateSignatures(List<Signature> signatureList) {
    listNotEmpty(signatureList);
    return signatureList.stream().reduce((a, b) -> a.combine(b)).get();
  }

  private static void listNotEmpty(List<?> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("Parameter list is empty");
    }
  }

  private static G1Point hashFunction(byte[] message) {
    byte[] hashByte = MPIN.HASH_ID(ECP.SHA256, message, BIG.MODBYTES);
    return new G1Point(ECP.mapit(hashByte));
  }
}
