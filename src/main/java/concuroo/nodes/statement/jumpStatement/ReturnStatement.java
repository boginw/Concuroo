package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.Expression;
import concuroo.nodes.statement.JumpStatement;

/**
 * This class represents an statement of the form return and return exp
 */
public class ReturnStatement implements JumpStatement {
  private Expression returnValue;

  // TODO: interface me
  public Expression getReturnValue() {
    return returnValue;
  }

  // TODO: interface me
  public void setReturnValue(Expression returnValue) {
    this.returnValue = returnValue;
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder("return");
    if(returnValue != null){
      sb.append(" ");
      sb.append(returnValue.getLiteral());
    }
    sb.append(";");
    return sb.toString();
  }
}
