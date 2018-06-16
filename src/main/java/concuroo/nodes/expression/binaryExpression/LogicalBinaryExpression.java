package concuroo.nodes.expression.binaryExpression;

import concuroo.types.ReturnType;
import concuroo.types.Types;

/**
 * This class represents an expression of the form EXPRESSION (>=, >, <=, <, &&, ||) EXPRESSION
 */
public abstract class LogicalBinaryExpression extends BinaryExpression {

  private ReturnType returnReturnType = new ReturnType();

  @Override
  public ReturnType getReturnType() {
    returnReturnType.type = Types.BOOL;
    return returnReturnType;
  }
}
