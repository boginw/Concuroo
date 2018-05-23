package concuroo.nodes.expression.literalExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.expression.LiteralExpression;

/**
 * This class represents the domain of booleans (true, false)
 */
public class BoolLiteral implements LiteralExpression<Boolean> {

  private boolean value;

  /**
   * The default constructor
   *
   * @param value The boolean value
   */
  public BoolLiteral(boolean value) {
    this.value = value;
  }

  @Override
  public Boolean getValue() {
    return value;
  }

  @Override
  public void setValue(Boolean value) {
    this.value = value;
  }

  @Override
  public String getLiteral() {
    return String.valueOf(value);
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType() {{
      type = Types.BOOL;
    }};
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set returnReturnType of a boolean literal");
  }
}
