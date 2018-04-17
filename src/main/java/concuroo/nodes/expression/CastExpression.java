package concuroo.nodes.expression;

import concuroo.nodes.Node;
import concuroo.nodes.typeSpecifier;

public class CastExpression implements Node {

  private typeSpecifier specifier;
  private PrimaryExpression value;

  public typeSpecifier getSpecifier() {
    return specifier;
  }

  public void setSpecifier(typeSpecifier specifier) {
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