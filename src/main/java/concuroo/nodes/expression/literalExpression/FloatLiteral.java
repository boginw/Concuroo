package concuroo.nodes.expression.literalExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.expression.LiteralExpression;

/**
 * This class represents the domain of rational numbers
 */
public class FloatLiteral implements LiteralExpression<Double> {

  private double value;
  private ReturnType returnReturnType = new ReturnType();

  /**
   * Default constructor
   *
   * @param value The value of the float
   */
  public FloatLiteral(double value) {
    this.value = value;
    returnReturnType.type = Types.DOUBLE;
  }

  @Override
  public Double getValue() {
    return value;
  }

  @Override
  public void setValue(Double value) {
    this.value = Double.valueOf(value.toString());
  }

  @Override
  public String getLiteral() {
    return String.valueOf(this.value);
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a float literal");
  }
}