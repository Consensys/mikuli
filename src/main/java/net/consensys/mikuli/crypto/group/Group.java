package net.consensys.mikuli.crypto.group;

public interface Group<G> {

  G add(G g);

  G mul(Scalar scalar);
}
