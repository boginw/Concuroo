package concuroo.nodes.expression.unaryExpression;

import concuroo.ReturnType;
import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.Expression;
import concuroo.nodes.expression.UnaryExpression;

/**
 * This class represents an expression of the form (int) 2.4, that is, casting an operand to a diff-
 * erent type
 */
public class CastExpression implements UnaryExpression, HasSpecifiers {
  private ReturnType returnReturnType;
  private Expression firstOperand;
  private DeclarationSpecifierList specifiers;

  @Override
  public Expression getFirstOperand() {
    return firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = firstOperand;
  }

  @Override
  public String getOperator() {
    return specifiers.getLiteral();
  }

  @Override
  public String getLiteral() {
    return "(" + specifiers.getLiteral() + ") " + getFirstOperand().getLiteral();
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}