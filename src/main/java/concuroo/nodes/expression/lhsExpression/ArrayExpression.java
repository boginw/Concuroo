package concuroo.nodes.expression.lhsExpression;

import concuroo.ReturnType;
import concuroo.nodes.expression.LHSExpression;
import concuroo.nodes.expression.binaryExpression.BinaryExpression;

/**
 * This class represents an expression of the form a[0]. This class represents all array indexing
 */
public class ArrayExpression extends BinaryExpression implements LHSExpression {

  private ReturnType returnReturnType;

  @Override
  public String getLiteral() {
    return this.getFirstOperand().getLiteral() + "[" + this.getSecondOperand().getLiteral() + "]";
  }

  @Override
  public String getOperator() {
    return "";
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
