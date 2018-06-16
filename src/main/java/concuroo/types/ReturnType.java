package concuroo.types;

import org.apache.commons.lang3.tuple.Pair;

public class ReturnType implements Comparable<ReturnType> {

  public Types type;
  public Pair<TypeModifier, TypeModifier> prefix = Pair.of(null, null);

  @Override
  public int compareTo(ReturnType o) {
    int i = 0;
    if (o.prefix != null && prefix != null) {
      i = prefix.compareTo(o.prefix);
    }

    if (i != 0) {
      return i;
    }

    i = type.compareTo(o.type);

    return i;
  }

  public int getPrecedenceLevel() {
    int precedenceLevel = 0;
    if (type == Types.VOID) {
      precedenceLevel += 0;
    }
    if (type == Types.BOOL) {
      precedenceLevel += 2;
    }
    if (type == Types.CHAR) {
      precedenceLevel += 4;
    }
    if (type == Types.INT) {
      precedenceLevel += 6;
    }
    if (type == Types.DOUBLE) {
      precedenceLevel += 8;
    }

    if (prefix == null) {
      precedenceLevel += 0;
    } else {
      precedenceLevel += 1;
    }

    return precedenceLevel;
  }

  @Override
  public String toString() {
    String string = "";
    if (prefix.getLeft() != null) {
      string += prefix.getLeft().toString() + " ";
    }
    if (prefix.getRight() != null) {
      string += prefix.getRight().toString() + " ";
    }
    if (type != null) {
      string += type.toString();
    }

    return string;
  }
}
