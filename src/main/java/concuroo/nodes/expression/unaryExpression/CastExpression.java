package concuroo.nodes.expression.unaryExpression;

import concuroo.nodes.TypeSpecifier;
import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.Expression;
import concuroo.nodes.expression.PrimaryExpression;

public class CastExpression implements PrefixExpression, CanSetOperator {

  private TypeSpecifier specifier;
  private PrimaryExpression firstOperand;

  public TypeSpecifier getSpecifier() {
    return specifier;
  }

  public void setSpecifier(TypeSpecifier specifier) {
    this.specifier = specifier;
  }


  @Override
  public String getLiteral() {
    return "(" + specifier.getLiteral() + ") " + firstOperand.getLiteral() + ";";
  }

  @Override
  public Expression getFirstOperand() {
    return (Expression) firstOperand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    this.firstOperand = (PrimaryExpression) firstOperand;
  }

  private String operator;

  @Override
  public String getOperator() {
    return operator;
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }
}