package concuroo.nodes.expression.literalExpression;

import ConcurooParser.ConcurooParser.PrimaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LiteralExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the domain of integer numbers
 */
public class IntLiteral implements LiteralExpression<Integer> {

  private ReturnType returnType = new ReturnType();
  private int value;

  public IntLiteral() {
    returnType.type = Types.INT;
  }

  /**
   * The default constructor
   *
   * @param value The integer value
   */
  public IntLiteral(int value) {
    this.value = value;
    returnType.type = Types.INT;
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
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PrimaryExpressionContext actx = Node.checkCtx(ctx, PrimaryExpressionContext.class);
    setValue(Integer.valueOf(actx.Number().getSymbol().getText()));

    return this;
  }

  @Override
  public ReturnType getReturnType() {
    return returnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of an integer literal");
  }
}