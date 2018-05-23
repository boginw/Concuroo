package concuroo.nodes;

import java.util.ArrayList;

/**
 * This class represents a program. A program is just a list of declarations. Therefore, we just
 * extend the ArrayList class
 */
public class Program extends ArrayList<Declaration> implements Node {

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder();
    for (Declaration node : this) {
      sb.append(node.getLiteral());
    }
    return sb.toString();
  }
}
