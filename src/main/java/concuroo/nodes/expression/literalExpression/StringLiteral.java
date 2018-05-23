package concuroo.nodes.expression.literalExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.expression.LiteralExpression;

/**
 * This class represents the literal strings in a program
 */
public class StringLiteral implements LiteralExpression<String> {

  private ReturnType returnReturnType;
  private String value;

  /**
   * The default constructor
   *
   * @param value The string value
   */
  public StringLiteral(String value) {
    this.value = value;
    returnReturnType = new ReturnType();
    returnReturnType.type = Types.CHAR;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String getLiteral() {
    return this.value;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a string literal");
  }
}