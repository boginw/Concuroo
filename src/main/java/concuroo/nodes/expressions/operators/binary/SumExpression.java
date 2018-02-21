package concuroo.nodes.expressions.operators.binary;

import concuroo.language.Associativity;
import concuroo.nodes.expressions.Expression;

public class SumExpression implements BinaryOperator {

  private final String literal;
  private Expression left;
  private Expression right;

  public SumExpression(String literal) {
    this.literal = literal;
  }

  @Override
  public Expression getLeft() {
    return left;
  }

  @Override
  public void setLeft(Expression e) {
    left = e;
  }

  @Override
  public Expression getRight() {
    return right;
  }

  @Override
  public void setRight(Expression e) {
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
  public String getLiteral() {
    return literal;
  }

  @Override
  public int getVal() {
    return left.getVal() + right.getVal();
  }
}
