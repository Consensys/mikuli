package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.RAND;
import org.apache.milagro.amcl.BLS381.BIG;
import org.apache.milagro.amcl.BLS381.ECP;
import org.apache.milagro.amcl.BLS381.ECP2;
import org.apache.milagro.amcl.BLS381.ROM;
import net.consensys.mikuli.crypto.group.G2Point;
import net.consensys.mikuli.crypto.group.Scalar;

public final class KeyPairFactory {

  static public KeyPair createKeyPair() {
    G2Point g2Generator = SystemParameters.g2Generator;
    RAND rng = new RAND();

    Scalar secret = new Scalar(BIG.randomnum(SystemParameters.curveOrder, rng));

    PrivateKey privateKey = new PrivateKey(secret);
    G2Point g2Point = g2Generator.mul(secret);
    PublicKey publicKey = new PublicKey(g2Point);
    return new KeyPair(privateKey, publicKey);
  }
}
