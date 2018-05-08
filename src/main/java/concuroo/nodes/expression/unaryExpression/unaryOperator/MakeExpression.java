package concuroo.nodes.expression.unaryExpression.unaryOperator;

import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Node;

public class MakeExpression implements Node, HasSpecifiers {

  private DeclarationSpecifierList specifiers;

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return this.specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  @Override
  public String getLiteral() {
    return "make(" + specifiers.getLiteral() + ")";
  }

}
