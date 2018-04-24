package concuroo.nodes.expression.lhsExpression;

public class ReferencePrefixExpression implements LHSExpression {

  private VariableExpression identifier;
  private String literal;

  public ReferencePrefixExpression(VariableExpression identifier) {
    this.identifier = identifier;
    this.literal = "&";
  }

  @Override
  public String getLiteral() {
    return literal + identifier.getLiteral();
  }
}
