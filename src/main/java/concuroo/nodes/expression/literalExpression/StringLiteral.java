package concuroo.nodes.expression.literalExpression;

import ConcurooParser.ConcurooParser.PrimaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.Visitor;
import concuroo.nodes.Node;
import concuroo.nodes.expression.LiteralExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents the literal strings in a program
 */
public class StringLiteral implements LiteralExpression<String> {

  private String value;

  public StringLiteral() {
  }

  /**
   * The default constructor
   *
   * @param value The string value
   */
  public StringLiteral(String value) {
    this.value = value;
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
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    PrimaryExpressionContext actx = Node.checkCtx(ctx, PrimaryExpressionContext.class);
    setValue(actx.StringLiteral().get(0).getText());

    return this;
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType() {{
      type = Types.CHAR;
    }};
  }

  @Override
  public void visit(Visitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of a string literal");
  }
}