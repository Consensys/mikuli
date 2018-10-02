# mikuli

**BLS Multi-Signatures With Public-Key Aggregation** as described in:   
https://crypto.stanford.edu/~dabo/pubs/papers/BLSmultisig.html

This implementation relies on milagro crypto library for pairings on elliptic curves. 
http://docs.milagro.io/en/

Curve: **BLS-12 381**

To run tests and benchmarks: **mvn clean install**

**Group operation benchmarks:**

Benchmark                              | Mode |  Cnt |  Score  | Error    | Units
---------------------------------------|------|------|---------|----------|----------------------------
G1Benchmarks.pointAdditionInECP        | avgt | 20   | 0.005 ± | 0.001    |  ms/op
G1Benchmarks.pointMultiplicationInECP  | avgt | 20   | 1.426 ± | 0.034    | ms/op
G2Benchmarks.pointAdditionInECP2       | avgt | 20   | 0.016 ± | 0.001    | ms/op
G2Benchmarks.pointMultiplicationInECP2 | avgt | 20   | 3.990 ± | 0.096    | ms/op
GTBenchmarks.pointMultiplicationInGT   | avgt | 20   | 0.042 ± | 0.001    | ms/op
PairingBenchmarks.pairing              | avgt | 20   | 12.318 ±|  0.337   | ms/op


**example:** 

```java
KeyPair keyPair = KeyPairFactory.createKeyPair();
byte[] message = "Hello".getBytes();
SignatureAndPublicKey sigAndPubKey = BLS12381.sign(keyPair, message);
	
Boolean isValid = BLS12381.verify(sigAndPubKey.getPublicKey(), sigAndPubKey.getSignature(), message);
```

***This libraryd provide no security guarantees and shouldn't be used in production.***
