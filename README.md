# mikuli

Aggregation schema for ETH2.0

**BLS Multi-Signatures With Public-Key Aggregation** as described in:   
https://crypto.stanford.edu/~dabo/pubs/papers/BLSmultisig.html


mikuli provides no protection against the rogue public key attack and relies on milagro crypto library for pairings on elliptic curves http://docs.milagro.io/en/. 
   
    
This is still work in progress and was not reviewed by security expert. 


Long term development goals:
1. Replacing milagro dependency with custom pairing library 
2. Security audit

Curve: **BLS-12 381**

**installation:** 
============= 

Building: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**gradle clean build**  
Testing: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**gradle test**  
Benchmarking:  **gradle benchmark**  

**Group operation benchmarks:**


Benchmark                                                  | Mode | Cnt|   Score  | Error | Units
-----------------------------------------------------------|------|----|----------|-------|--------
G1Benchmarks.pointAdditionInG1                             | avgt | 40 | 0.005 ±  | 0.001 | ms/op
G1Benchmarks.pointMultiplicationInG1                       | avgt | 40 | 1.514 ±  | 0.025 | ms/op
G2Benchmarks.pointAdditionInG2                             | avgt | 20 | 0.017 ±  | 0.001 | ms/op
G2Benchmarks.pointMultiplicationInG2                       | avgt | 20 | 4.287 ±  | 0.086 | ms/op
GTBenchmarks.pointMultiplicationInGT                       | avgt | 20 | 0.053 ±  | 0.009 | ms/op
PairingBenchmarks.pairing                                  | avgt | 60 | 13.316 ± | 0.164 | ms/op
SerializationBenchmarks.pointDeSerializationNoCompression  | avgt | 20 | 0.004 ±  | 0.001 | ms/op
SerializationBenchmarks.pointDeSerializationWithCompression| avgt | 20 | 0.189 ±  | 0.005 | ms/op


**example:** 
============= 

```java
    KeyPair keyPair = KeyPairFactory.createKeyPair();
    byte[] message = "Hello".getBytes();
    SignatureAndPublicKey sigAndPubKey = BLS12381.sign(keyPair, message);

    Boolean isValid = BLS12381.verify(sigAndPubKey.publicKey(), sigAndPubKey.signature(), message);
    assertTrue(isValid);
```
