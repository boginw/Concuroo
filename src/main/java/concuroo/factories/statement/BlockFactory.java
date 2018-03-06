package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.BlockStatement;
import concuroo.parser.Parser;

public class BlockFactory implements StatementFactory<BlockStatement> {

  @Override
  public BlockStatement parse(Parser parser, Node token) {
    BlockStatement stat = (BlockStatement) token;

    while (!parser.match("}")) {
      stat.addStatement(parser.parseStatement());
    }

    return stat;
  }

  @Override
  public int is(String input) {
    char c = input.charAt(0);
    return c == '{' || c == '}' ? 1 : -1;
  }

  @Override
  public boolean is(Node node) {
    return node instanceof BlockStatement;
  }

  @Override
  public Node makeNode(String literal) {
    return new BlockStatement(literal);
  }
}
