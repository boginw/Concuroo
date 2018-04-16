package concuroo;

import ConcurooParser.ConcurooBaseVisitor;
import concuroo.nodes.Node;

public class ASTVisitor extends ConcurooBaseVisitor<Node> {

  @Override
  public Node aggregateResult(Node aggregate, Node nextResult) {
    if (aggregate == null) {
      return nextResult;
    }

    if (nextResult == null) {
      return aggregate;
    }

    return aggregate;
  }
}
