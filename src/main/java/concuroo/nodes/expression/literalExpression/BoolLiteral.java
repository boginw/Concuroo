package concuroo.nodes.expression.literalExpression;

import ConcurooParser.ConcurooParser.PrimaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.types.ReturnType;
import concuroo.types.Types;
import concuroo.Visitor;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LiteralExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the domain of booleans (true, false)
 */
public class BoolLiteral implements LiteralExpression<Boolean> {

  private boolean value;

  public BoolLiteral() {

  }

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
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PrimaryExpressionContext actx = Node.checkCtx(ctx, PrimaryExpressionContext.class);
    setValue(actx.boolLiteral().getChild(0).toString().equals("true"));

    return this;
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(this);
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
