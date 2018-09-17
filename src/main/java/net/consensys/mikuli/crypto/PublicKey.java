package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.ECP2;

public final class PublicKey {

	private final ECP2 point;

	PublicKey(ECP2 point) {
		this.point = point;
	}

	ECP2 getECP2point() {
		return point;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long xa = point.getX().getA().norm();
		long ya = point.getY().getA().norm();
		long xb = point.getX().getB().norm();
		long yb = point.getY().getB().norm();
		result = prime * result + (int) (xa ^ (xa >>> 32));
		result = prime * result + (int) (ya ^ (ya >>> 32));
		result = prime * result + (int) (xb ^ (xb >>> 32));
		result = prime * result + (int) (yb ^ (yb >>> 32));
		return result;
	}
}
