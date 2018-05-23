package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Node;
import concuroo.nodes.Expression;

/**
 * This class represents the make expressions, which are of the form `make(chan int)` or something
 * similar
 */
public class MakeExpression implements Node, HasSpecifiers, Expression {

  private ReturnType returnReturnType;
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

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

}
