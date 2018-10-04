package mikuli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import net.consensys.mikuli.crypto.BLS12381;
import net.consensys.mikuli.crypto.KeyPair;
import net.consensys.mikuli.crypto.KeyPairFactory;
import net.consensys.mikuli.crypto.PublicKey;
import net.consensys.mikuli.crypto.Signature;
import net.consensys.mikuli.crypto.SignatureAndPublicKey;

public class SignatureTest {

  @Test
  public void testSimpleSignature() {
    KeyPair keyPair = KeyPairFactory.createKeyPair();
    byte[] message = "Hello".getBytes();
    SignatureAndPublicKey sigAndPubKey = BLS12381.sign(keyPair, message);

    Boolean isValid = BLS12381.verify(sigAndPubKey.publicKey(), sigAndPubKey.signature(), message);
    assertTrue(isValid);
  }

  @Test
  public void testAggregatedSignature() {
    byte[] message = "Hello".getBytes();
    List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);
    SignatureAndPublicKey sigAndPubKey = BLS12381.aggregate(sigs);

    Boolean isValid = BLS12381.verify(sigAndPubKey, message);
    assertTrue(isValid);
  }

  @Test
  public void testCorruptedMessage() {
    byte[] message = "Hello".getBytes();
    List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);
    SignatureAndPublicKey sigAndPubKey = BLS12381.aggregate(sigs);
    byte[] corruptedMessage = "Not Hello".getBytes();

    Boolean isValid = BLS12381.verify(sigAndPubKey, corruptedMessage);
    assertFalse(isValid);
  }

  @Test
  public void testCorruptedSignature() {
    byte[] message = "Hello".getBytes();
    List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);
    KeyPair keyPair = KeyPairFactory.createKeyPair();
    byte[] notHello = "Not Hello".getBytes();

    SignatureAndPublicKey additionalSignature = BLS12381.sign(keyPair, notHello);
    sigs.add(additionalSignature);

    SignatureAndPublicKey sigAndPubKey = BLS12381.aggregate(sigs);

    Boolean isValid = BLS12381.verify(sigAndPubKey, message);
    assertFalse(isValid);
  }

  @Test
  public void testSerialization() {
    KeyPair keyPair = KeyPairFactory.createKeyPair();
    byte[] message = "Hello".getBytes();
    Signature signature = BLS12381.sign(keyPair, message).signature();

    byte[] sigTobytes = signature.encode();
    Signature sigFromBytes = Signature.decode(sigTobytes);

    assertEquals(signature, sigFromBytes);
    assertEquals(signature.hashCode(), sigFromBytes.hashCode());

    PublicKey pubKey = keyPair.publicKey();
    byte[] pubKeyTobytes = pubKey.encode();
    PublicKey pubKeyFromBytes = PublicKey.decode(pubKeyTobytes);

    assertEquals(pubKey, pubKeyFromBytes);
    assertEquals(pubKey.hashCode(), pubKeyFromBytes.hashCode());
  }

  private List<SignatureAndPublicKey> getSignaturesAndPublicKeys(byte[] message) {
    KeyPair keyPair1 = KeyPairFactory.createKeyPair();
    KeyPair keyPair2 = KeyPairFactory.createKeyPair();
    KeyPair keyPair3 = KeyPairFactory.createKeyPair();

    SignatureAndPublicKey sigAndPubKey1 = BLS12381.sign(keyPair1, message);
    SignatureAndPublicKey sigAndPubKey2 = BLS12381.sign(keyPair2, message);
    SignatureAndPublicKey sigAndPubKey3 = BLS12381.sign(keyPair3, message);

    List<SignatureAndPublicKey> sigs = new ArrayList<SignatureAndPublicKey>();
    sigs.add(sigAndPubKey1);
    sigs.add(sigAndPubKey2);
    sigs.add(sigAndPubKey3);

    return sigs;
  }
}
