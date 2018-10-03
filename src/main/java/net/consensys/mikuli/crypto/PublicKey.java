package net.consensys.mikuli.crypto;

import net.consensys.mikuli.crypto.group.G2Point;

public final class PublicKey {

	private final G2Point point;

	PublicKey(G2Point point) {
		if (point == null) {
			throw new NullPointerException("PublicKey was not properly initialized");
		}
		this.point = point;
	}
	
	public PublicKey combine(PublicKey pk) {
		return new PublicKey(point.add(pk.point));
	}
	
	/**
	 * Public key serialization
	 * 
	 * @param publicKey The Public key, not null
	 * @return byte array representation of the public key
	 */
	public byte[] encode() {
		return point.toBytes();
	}
	
	public static PublicKey decode(byte[] bytes) {
		G2Point point = G2Point.fromBytes(bytes);
		return new PublicKey(point);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicKey other = (PublicKey) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}

	public G2Point g2Point() {
		return point;
	}
}
