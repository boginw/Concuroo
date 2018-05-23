package concuroo.nodes.expression.literalExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.expression.LiteralExpression;

/**
 * This class represents the domain of integer numbers
 */
public class IntLiteral implements LiteralExpression<Integer> {

  private ReturnType returnReturnType = new ReturnType();
  private int value;

  /**
   * The default constructor
   *
   * @param value The integer value
   */
  public IntLiteral(int value) {
    this.value = value;
    returnReturnType.type = Types.INT;
  }

  @Override
  public Integer getValue() {
    return value;
  }

  @Override
  public void setValue(Integer value) {
    this.value = Integer.valueOf(value.toString());
  }

  @Override
  public String getLiteral() {
    return String.valueOf(value);
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of an integer literal");
  }
}