package concuroo.nodes.expression.unaryExpression;

import ConcurooParser.ConcurooParser.PostfixExpressionContext;
import ConcurooParser.ConcurooParser.UnaryExpressionContext;
import concuroo.CSTVisitor;
import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Expression;
import concuroo.nodes.Node;
import concuroo.nodes.expression.CanSetOperator;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.UnaryExpression;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * This class represents an expression of the form ++a, --a, a++, a--
 */
public class IncrementDecrementExpression implements UnaryExpression, CanSetOperator {

  private ReturnType returnReturnType;
  private LHSExpression operand;
  private String operator;
  private boolean isPrefix = false;

  /**
   * The default constructor
   */
  public IncrementDecrementExpression() {
    returnReturnType = new ReturnType();
    returnReturnType.type = Types.INT;
  }

  /**
   * Determines if this is a prefix or postfix expression. If true, the expression would like "++a",
   * and "a++" otherwise.
   *
   * @return Whether or not this is a prefix or postfix expression
   */
  public boolean isPrefix() {
    return isPrefix;
  }

  /**
   * Sets whether or not this should should be a prefix expression or not. Default is "false"
   *
   * @param prefix Whether or not this should should be a prefix expression
   */
  public void setPrefix(boolean prefix) {
    isPrefix = prefix;
  }

  @Override
  public Expression getFirstOperand() {
    return operand;
  }

  @Override
  public void setFirstOperand(Expression firstOperand) {
    // We need the first operand to be an LHSExpression, so that it *CAN* be assigned an increment
    if (firstOperand instanceof LHSExpression) {
      this.operand = (LHSExpression) firstOperand;
    } else {
      // Just throw an error, telling the user that this cannot be done.
      throw new RuntimeException(
          String.format(
              "Cannot use %s on the expression %s, due to it not being a LHS expression.",
              operator,
              firstOperand.getLiteral())
      );
    }
  }

  @Override
  public String getOperator() {
    return operator;
  }

  @Override
  public void setOperator(String operator) {
    this.operator = operator;
  }

  @Override
  public String getLiteral() {
    if (isPrefix) {
      return operator + operand.getLiteral();
    }
    return operand.getLiteral() + operator;
  }

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    if (ctx instanceof PostfixExpressionContext) {
      setFirstOperand((Expression) visitor.visit(ctx.getChild(0)));
      setOperator(ctx.getChild(1).getText());
      setPrefix(false);

      return this;
    }

    UnaryExpressionContext actx = Node.checkCtx(ctx, UnaryExpressionContext.class);

    setFirstOperand((Expression) visitor.visit(ctx.getChild(1)));
    setOperator(actx.unaryOperator().getText());
    setPrefix(true);

    return this;
  }

  @Override
  public ReturnType getReturnType() {
    return returnReturnType;
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    this.returnReturnType = returnReturnType;
  }
}
