package concuroo.nodes.statement;

import concuroo.nodes.Statement;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.List;

public class CompoundStatement implements Statement {

  private List<Statement> list = new ArrayList<>();
  private SymbolTable scope = new SymbolTable();

  public void addStatement(Statement stat) {
    list.add(stat);
  }

  public Statement getStatement(int i) {
    return list.get(i);
  }

  public int size() {
    return list.size();
  }

  public SymbolTable getScope() {
    return scope;
  }

  public void setScope(SymbolTable scope) {
    this.scope = scope;
  }

  @Override
  public String getLiteral() {
    StringBuilder sb = new StringBuilder("{\n");

    for (Statement stat: list) {
      sb.append(stat.getLiteral()).append("\n");
    }

    sb.append("}");

    return sb.toString();
  }
}
