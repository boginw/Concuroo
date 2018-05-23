package concuroo.nodes.expression.literalExpression;

import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.expression.LiteralExpression;

/**
 * This class represents the domain of chars ('a', 'b' '\n')
 */
public class CharLiteral implements LiteralExpression<String> {

  private ReturnType returnReturnType = new ReturnType();
  private String value;

  /**
   * Default constructor
   *
   * @param value The character literal
   */
  public CharLiteral(String value) {
    this.value = value;
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
    return "\'" + String.valueOf(this.value) + "\'";
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a character literal");
  }
}
