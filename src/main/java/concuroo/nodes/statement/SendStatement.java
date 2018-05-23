package concuroo.nodes.statement;


import concuroo.ReturnType;
import concuroo.Types;
import concuroo.nodes.Statement;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;

/**
 * This class represents a statement of the form c <- 1, that is, a send statement. In order to have
 * a more uniform interface, this class extends Binary Expression, even though it's a statement. We
 * insure that it evaluates to void. The CFG, will block this statement, of ever being an
 * expression.
 */
public class SendStatement extends BinaryExpression implements Statement {

  @Override
  public String getLiteral() {
    return getFirstOperand().getLiteral() + " <- " + getSecondOperand().getLiteral();
  }

  @Override
  public String getOperator() {
    return "<-";
  }

  @Override
  public void setReturnType(ReturnType returnReturnType) {
    throw new RuntimeException("Cannot set return type of send statement. It's a statement");
  }

  @Override
  public ReturnType getReturnType() {
    return new ReturnType() {{
      type = Types.VOID;
    }};
  }
}
