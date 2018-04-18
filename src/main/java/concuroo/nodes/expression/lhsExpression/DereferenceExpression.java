package concuroo.nodes.expression.lhsExpression;

public class DereferenceExpression implements LHSExpression {

  private VariableExpression identifier;
  private String literal;

  public DereferenceExpression(VariableExpression identifier) {
    this.identifier = identifier;
    literal = "*";
  }


  @Override
  public String getLiteral() {
    return literal + identifier.getLiteral();
  }
}
