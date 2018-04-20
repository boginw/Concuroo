package concuroo.nodes.statement.jumpStatement;

import concuroo.nodes.expression.Expression;
import concuroo.nodes.statement.JumpStatement;

public class ReturnStatement implements JumpStatement {
  private Expression returnValue;

  public Expression getReturnValue() {
    return returnValue;
  }

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
