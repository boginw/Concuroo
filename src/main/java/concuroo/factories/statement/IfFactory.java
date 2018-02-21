package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.statements.IfStatement;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.List;

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
  public IfStatement makeInstance(Node[] symbols, List<Node> arguments,
      SymbolTable symbolTable) {

    IfStatement stat = new IfStatement();
    if (arguments.size() > 0) {
      stat.setCondition(arguments.get(0));
    }
    if (arguments.size() > 1) {
      stat.setConsequence((Statement) arguments.get(1));
    }
    if (arguments.size() > 2) {
      stat.setAlternative((Statement) arguments.get(2));
    }

    return stat;
  }
}
