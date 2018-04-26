package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class IntLiteral implements LiteralExpression {

  private int value;

  public IntLiteral(int value) {
    this.value = value;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof Integer) {
      this.value = Integer.valueOf(value.toString());
    } else {
      throw new IllegalArgumentException("Wrong data type passed");
    }
  }

  @Override
  public String getLiteral() {
    return String.valueOf(value);
  }
}