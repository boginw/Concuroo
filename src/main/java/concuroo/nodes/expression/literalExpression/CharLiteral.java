package concuroo.nodes.expression.literalExpression;

import concuroo.nodes.expression.LiteralExpression;

public class CharLiteral implements LiteralExpression {

  private String value;

  public CharLiteral(String value) {
    this.value = value;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public void setValue(Object value) {
    if (value instanceof String) {
      this.value = (String) value;
    } else {
      throw new IllegalArgumentException("Wrong data type passed");
    }
  }

  @Override
  public String getLiteral() {
    return "\'" + String.valueOf(this.value) + "\'";
  }
}
