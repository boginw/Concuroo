package concuroo.nodes.expression.binaryExpression;

/**
 * This class represents an expression of the form a = true
 */
public class AssignmentExpression extends BinaryExpression {

  @Override
  public String getOperator() {
    return "=";
  }
}
