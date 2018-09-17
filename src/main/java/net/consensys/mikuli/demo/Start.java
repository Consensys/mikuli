package net.consensys.mikuli.demo;

import net.consensys.mikuli.crypto.KeyPair;
import net.consensys.mikuli.crypto.KeyPairFactory;

public class Start {

	public static void main(String[] args) {

		KeyPair keyPair = KeyPairFactory.createKeyPair();

		System.out.println("mikuli demo");
	}
}
