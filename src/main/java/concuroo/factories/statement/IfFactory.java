package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.statements.IfStatement;
import concuroo.parser.Parser;

public class IfFactory implements StatementFactory<IfStatement> {

  @Override
  public int is(String input) {
    return input.startsWith("if") ? 2 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof IfStatement;
  }

  @Override
  public Node makeNode(String literal) {
    return new IfStatement();
  }

  @Override
  public IfStatement parse(Parser parser, Node token) {
    IfStatement stat = (IfStatement) token;

    if (!parser.match(Parenthesis.class)) {
      throw new RuntimeException("Wtf?");
    }
    stat.setCondition(parser.parseExpression());

    if (!parser.match(Parenthesis.class)) {
      throw new RuntimeException("Wtf?");
    }

    stat.setConsequence(parser.parseStatement());

    if (parser.match("else")) {
      stat.setAlternative(parser.parseStatement());
    }

    return stat;
  }
}
