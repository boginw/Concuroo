package concuroo.nodes.expression.lhsExpression;

public class VariableExpression implements LHSExpression {

  private String literal;

  public VariableExpression(String literal) {
    this.literal = literal;
  }

  @Override
  public String getLiteral() {
    return literal;
  }
}
