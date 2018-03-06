package concuroo.nodes.expressions.atoms;

import concuroo.factories.expression.ExpressionFactory;

public class IdentifierNode implements Atom {

  private final String literal;
  private int value = 0;

  public IdentifierNode(String literal) {
    this.literal = literal;
  }

  public void setValue(int val) {
    value = val;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public int getVal() {
    return value;
  }

  @Override
  public ExpressionFactory getFactory() {
    return null;
  }

}