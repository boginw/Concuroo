package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.Node;
import concuroo.nodes.TypeSpecifier;
import concuroo.nodes.expression.PrimaryExpression;

public class CastExpression implements Node {

  private TypeSpecifier specifier;
  private PrimaryExpression value;

  public TypeSpecifier getSpecifier() {
    return specifier;
  }

  public void setSpecifier(TypeSpecifier specifier) {
    this.specifier = specifier;
  }

  public PrimaryExpression getValue() {
    return value;
  }

  public void setValue(PrimaryExpression value) {
    this.value = value;
  }

  @Override
  public String getLiteral() {
    return null;
  }
}