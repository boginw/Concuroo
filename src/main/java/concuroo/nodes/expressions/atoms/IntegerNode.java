package concuroo.nodes.expressions.atoms;

import concuroo.factories.expression.ExpressionFactory;
import concuroo.factories.expression.IntFactory;

public class IntegerNode implements Atom {

  private final String literal;

  public IntegerNode(String literal) {
    this.literal = literal;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public int getVal() {
    return Integer.parseInt(literal);
  }

  @Override
  public ExpressionFactory getFactory() {
    return new IntFactory();
  }

}
