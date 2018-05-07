package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.DeclarationSpecifierList;
import concuroo.nodes.HasSpecifiers;
import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;

public class CastExpression implements PrefixExpression, HasSpecifiers {

  private PrimaryExpression firstOperand;
  private DeclarationSpecifierList specifiers;

  @Override
  public Expression getFirstOperand() {
    return (Expression) firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (PrimaryExpression) firstOperand;
  }

  @Override
  public String getOperator() {
    return specifiers.getLiteral();
  }

  @Override
  public String getLiteral() {
    return "(" + specifiers.getLiteral() + ") " + getFirstOperand().getLiteral() + ";";
  }

  @Override
  public DeclarationSpecifierList getSpecifiers() {
    return specifiers;
  }

  @Override
  public void setSpecifiers(DeclarationSpecifierList specifiers) {
    this.specifiers = specifiers;
  }
}