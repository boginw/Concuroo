package concuroo.nodes.statement;

import concuroo.nodes.HasScope;
import concuroo.nodes.Statement;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;

/**
 * This class represents a block statement, that is, a statement of the form { ... }. This class
 * extends ArrayList, in order to make it easy to add statements to it.
 */
public class CompoundStatement extends ArrayList<Statement> implements Statement, HasScope {

  private SymbolTable scope = new SymbolTable();

  /**
   * Adds a statement to the top, instead of to the back
   *
   * @param stat Statement to add
   */
  public void addToTop(Statement stat) {
    add(0, stat);
  }

  @Override
  public SymbolTable getScope() {
    return scope;
  }

  @Override
  public void setScope(SymbolTable scope) {
    this.scope = scope;
  }

  @Override
  public String getLiteral() {
    StringBuilder indent = new StringBuilder();

    SymbolTable scope = this.scope.getParent();
    while ((scope = scope.getParent()) != null) {
      indent.append('\t');
    }

    StringBuilder sb = new StringBuilder("{\n");

    for (Statement stat : this) {
      sb.append(indent).append("\t").append(stat.getLiteral()).append("\n");
    }

    sb.append(indent).append("}" + '\n');

    return sb.toString();
  }
}
