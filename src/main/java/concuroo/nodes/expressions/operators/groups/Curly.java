package concuroo.nodes.expressions.operators.groups;

import concuroo.factories.expression.CurlyFactory;
import concuroo.factories.expression.ExpressionFactory;
import concuroo.language.Associativity;

public abstract class Curly implements Group {
  private String literal;

  @Override
  public int getPrecedence() {
    return 1;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.LeftToRight;
  }

  @Override
  public String getLiteral() {
    return literal;
  }

  @Override
  public void setLiteral(String literal) {
    this.literal = literal;
  }

  @Override
  public int getVal() {
    return 0;
  }

  @Override
  public ExpressionFactory getFactory() {
    return new CurlyFactory();
  }

}
