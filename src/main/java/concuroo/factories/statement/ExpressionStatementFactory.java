package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.ExpressionStatement;
import concuroo.parser.Parser;

public class ExpressionStatementFactory implements StatementFactory<ExpressionStatement> {

  @Override
  public ExpressionStatement parse(Parser parser, Node token) {
    ExpressionStatement es = new ExpressionStatement();
    es.setExpr(parser.parseExpression());
    return es;
  }

  @Override
  public int is(String input) {
    return -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof ExpressionStatement;
  }

  @Override
  public Node makeNode(String literal) {
    return new ExpressionStatement();
  }
}
