package concuroo.nodes.expressions.operators.indecisive;

import concuroo.factories.expression.DifferenceFactory;
import concuroo.factories.expression.ExpressionFactory;
import concuroo.language.Associativity;
import concuroo.nodes.expressions.Expression;

public class DifferenceOperator implements Indecisive {

  private Expression left;
  private Expression right;

  @Override
  public Expression getOperand() {
    return this.left;
  }

  @Override
  public void setOperand(Expression e) {
    left = e;
  }

  @Override
  public boolean isPrefix() {
    return true;
  }

  @Override
  public Expression getSecondOperand() {
    return right;
  }

  @Override
  public void setSecondOperand(Expression e) {
    right = e;
  }

  @Override
  public int getPrecedence() {
    return 4;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.RightToLeft;
  }

  @Override
  public String getLiteral() {
    return "-";
  }

  @Override
  public int getVal() {
    return -left.getVal();
  }

  @Override
  public ExpressionFactory getFactory() {
    return new DifferenceFactory();
  }

}
