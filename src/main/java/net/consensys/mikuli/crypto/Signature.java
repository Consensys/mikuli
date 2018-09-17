package net.consensys.mikuli.crypto;

import org.apache.milagro.amcl.BLS381.ECP;

public final class Signature {
	private final ECP point;

	Signature(ECP signature) {
		this.point = signature;
	}

	ECP getEcpPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "Signature [ecpPoint=" + point + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Signature other = (Signature) obj;
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
		long x = point.getX().norm();
		long y = point.getY().norm();
		result = prime * result + (int) (x ^ (x >>> 32));
		result = prime * result + (int) (y ^ (y >>> 32));
		return result;
	}

}
