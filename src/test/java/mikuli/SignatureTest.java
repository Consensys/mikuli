package mikuli;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.consensys.mikuli.crypto.KeyPair;
import net.consensys.mikuli.crypto.KeyPairFactory;
import net.consensys.mikuli.crypto.SignatureAndPublicKey;
import net.consensys.mikuli.crypto.SignatureService;

public class SignatureTest {

	@Test
	public void testSimpleSignature() {
		KeyPair keyPair = KeyPairFactory.createKeyPair();

		byte[] message = "Hello".getBytes();
		SignatureAndPublicKey sigAndPubKey = SignatureService.sign(keyPair, message);

		Boolean isValid = SignatureService.verify(sigAndPubKey.getPublicKey(), sigAndPubKey.getSignature(), message);

		assertTrue(isValid);
	}

	@Test
	public void testAggregatedSignature() {

		byte[] message = "Hello".getBytes();

		List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);

		SignatureAndPublicKey sigAndPubKey = SignatureService.aggregate(sigs);

		Boolean isValid = SignatureService.verify(sigAndPubKey, message);

		assertTrue(isValid);
	}

	@Test
	public void testCorruptedMessage() {
		byte[] message = "Hello".getBytes();

		List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);

		SignatureAndPublicKey sigAndPubKey = SignatureService.aggregate(sigs);

		byte[] corruptedMessage = "Not Hello".getBytes();

		Boolean isValid = SignatureService.verify(sigAndPubKey, corruptedMessage);

		assertFalse(isValid);
	}

	@Test
	public void testCorruptedSignatute() {
		byte[] message = "Hello".getBytes();

		List<SignatureAndPublicKey> sigs = getSignaturesAndPublicKeys(message);

		KeyPair keyPair = KeyPairFactory.createKeyPair();
		byte[] notHello = "Not Hello".getBytes();

		SignatureAndPublicKey additionalSignature = SignatureService.sign(keyPair, notHello);

		sigs.add(additionalSignature);

		SignatureAndPublicKey sigAndPubKey = SignatureService.aggregate(sigs);

		Boolean isValid = SignatureService.verify(sigAndPubKey, message);

		assertFalse(isValid);
	}

	private List<SignatureAndPublicKey> getSignaturesAndPublicKeys(byte[] message) {
		KeyPair keyPair1 = KeyPairFactory.createKeyPair();
		KeyPair keyPair2 = KeyPairFactory.createKeyPair();
		KeyPair keyPair3 = KeyPairFactory.createKeyPair();

		SignatureAndPublicKey sigAndPubKey1 = SignatureService.sign(keyPair1, message);
		SignatureAndPublicKey sigAndPubKey2 = SignatureService.sign(keyPair2, message);
		SignatureAndPublicKey sigAndPubKey3 = SignatureService.sign(keyPair3, message);

		List<SignatureAndPublicKey> sigs = new ArrayList<SignatureAndPublicKey>();
		sigs.add(sigAndPubKey1);
		sigs.add(sigAndPubKey2);
		sigs.add(sigAndPubKey3);

		return sigs;
	}
}
