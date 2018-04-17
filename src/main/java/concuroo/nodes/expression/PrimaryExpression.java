package concuroo.nodes.expression;

import concuroo.nodes.Node;

public class PrimaryExpression implements Node {

  private Node value;

  public Node getValue() {
    return value;
  }

  public void setValue(Node value) {
    this.value = value;
  }

  @Override
  public String getLiteral() {
    return value.getLiteral();
  }
}
