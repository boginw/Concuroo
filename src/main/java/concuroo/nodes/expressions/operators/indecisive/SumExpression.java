package concuroo.nodes.expressions.operators.indecisive;

import concuroo.factories.expression.ExpressionFactory;
import concuroo.factories.expression.SumFactory;
import concuroo.language.Associativity;
import concuroo.nodes.expressions.Expression;

public class SumExpression implements Indecisive {

  private Expression left;
  private Expression right;

  @Override
  public Expression getOperand() {
    return left;
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
  public int getUnaryPrecedence() {
    return 2;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.LeftToRight;
  }

  @Override
  public String getLiteral() {
    return "+";
  }

  @Override
  public int getVal() {
    return left.getVal() + right.getVal();
  }

  @Override
  public ExpressionFactory getFactory() {
    return new SumFactory();
  }
}
