package concuroo.nodes.expressions.operators.binary;

import concuroo.factories.expression.ExpressionFactory;
import concuroo.factories.expression.ProductFactory;
import concuroo.language.Associativity;
import concuroo.nodes.expressions.Expression;

public class ProductExpression implements BinaryOperator {

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
  public Expression getSecondOperand() {
    return right;
  }

  @Override
  public void setSecondOperand(Expression e) {
    right = e;
  }

  @Override
  public int getPrecedence() {
    return 3;
  }

  @Override
  public Associativity getAssociativity() {
    return Associativity.LeftToRight;
  }

  @Override
  public ExpressionFactory getFactory() {
    return new ProductFactory();
  }

  @Override
  public String getLiteral() {
    return "*";
  }

  @Override
  public int getVal() {
    return 0;
  }
}
