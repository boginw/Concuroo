package concuroo.nodes.expression;

abstract public class binaryExpression extends primaryExpression {
  private primaryExpression left;
  private primaryExpression right;

  public primaryExpression getLeft() {
    return left;
  }

  public void setLeft(primaryExpression left) {
    this.left = left;
  }

  public primaryExpression getRight() {
    return right;
  }

  public void setRight(primaryExpression right) {
    this.right = right;
  }

  @Override
  public String getLiteral() {
    return left.getLiteral() + " " + right.getLiteral();
  }
}
