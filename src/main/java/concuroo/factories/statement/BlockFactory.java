package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.BlockStatement;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.List;

public class BlockFactory implements StatementFactory<BlockStatement> {

  @Override
  public BlockStatement makeInstance(Node[] symbols, List<Node> arguments,
      SymbolTable symbolTable) {
    BlockStatement stat = new BlockStatement();
    for (Node t : arguments) {
      stat.addStatement((Statement) t);
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
    return new BlockStatement();
  }
}
