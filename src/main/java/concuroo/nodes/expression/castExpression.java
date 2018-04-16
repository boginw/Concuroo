package concuroo.nodes.expression;

import concuroo.nodes.Node;
import concuroo.nodes.typeSpecifier;

public class castExpression implements Node {

  private typeSpecifier specifier;
  private primaryExpression value;

  public typeSpecifier getSpecifier() {
    return specifier;
  }

  public void setSpecifier(typeSpecifier specifier) {
    this.specifier = specifier;
  }

  public primaryExpression getValue() {
    return value;
  }

  public void setValue(primaryExpression value) {
    this.value = value;
  }

  @Override
  public String getLiteral() {
    return null;
  }
}