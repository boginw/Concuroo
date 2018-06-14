package concuroo.nodes.expression.literalExpression;

import ConcurooParser.ConcurooParser.PrimaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LiteralExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the domain of rational numbers
 */
public class FloatLiteral implements LiteralExpression<Double> {

  private double value;

  public FloatLiteral(){}

  /**
   * Default constructor
   *
   * @param value The value of the float
   */
  public FloatLiteral(double value) {
    this.value = value;
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
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PrimaryExpressionContext actx = Node.checkCtx(ctx, PrimaryExpressionContext.class);
    setValue(Double.valueOf(actx.DoubleLiteral().getSymbol().getText()));

    return this;
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType() {{
      type = Types.DOUBLE;
    }};
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a float literal");
  }
}