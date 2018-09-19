package mikuli;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.consensys.mikuli.crypto.KeyPair;
import net.consensys.mikuli.crypto.KeyPairFactory;
import net.consensys.mikuli.crypto.PublicKey;
import net.consensys.mikuli.crypto.Sig;
import net.consensys.mikuli.crypto.Sig.Signature;
import net.consensys.mikuli.crypto.Sig.SignatureAndPublicKey;

public class SignatureTest {

	@Test
	public void testSimpleSignature() {
		KeyPair keyPair = KeyPairFactory.createKeyPair();

		String message = "Hello";
		SignatureAndPublicKey sigAndPubKey = Sig.sign(keyPair, message.getBytes());

		Boolean isValid = Sig.verify(sigAndPubKey.getPublicKey(), sigAndPubKey.getSignature(), message.getBytes());

		assertTrue(isValid);
	}

	@Test
	public void testAggregatedSignature() {
		KeyPair keyPair1 = KeyPairFactory.createKeyPair();
		KeyPair keyPair2 = KeyPairFactory.createKeyPair();
		KeyPair keyPair3 = KeyPairFactory.createKeyPair();

		byte[] message = "Hello".getBytes();

		SignatureAndPublicKey sigAndPubKey1 = Sig.sign(keyPair1, message);
		SignatureAndPublicKey sigAndPubKey2 = Sig.sign(keyPair2, message);
		SignatureAndPublicKey sigAndPubKey3 = Sig.sign(keyPair3, message);

		List<SignatureAndPublicKey> sigs = new ArrayList<SignatureAndPublicKey>();
		sigs.add(sigAndPubKey1);
		sigs.add(sigAndPubKey2);
		sigs.add(sigAndPubKey3);

		SignatureAndPublicKey sigAndPubKey = Sig.aggregate(sigs);

		PublicKey aggPubKey = sigAndPubKey.getPublicKey();
		Signature aggSig = sigAndPubKey.getSignature();

		Boolean isValid = Sig.verify(aggPubKey, aggSig, message);

		assertTrue(isValid);
		
		
	}
	
	
}
