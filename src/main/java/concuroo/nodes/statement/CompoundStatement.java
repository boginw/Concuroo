package concuroo.nodes.statement;

import ConcurooParser.ConcurooParser.CompoundStatementContext;
import ConcurooParser.ConcurooParser.StatementListContext;
import concuroo.CSTVisitor;
import concuroo.nodes.HasScope;
import concuroo.nodes.Node;
import concuroo.nodes.Statement;
import concuroo.symbol.SymbolTable;
import java.util.ArrayList;
import java.util.Stack;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

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

  @Override
  public Node parse(ParserRuleContext ctx, CSTVisitor visitor) {
    Node.checkCtx(ctx, CompoundStatementContext.class);

    // scope in
    visitor.scopeIn(getScope());

    // Compound statement contains statements
    if (ctx.children.size() == 3) {
      Stack<ParseTree> parseTreeStack = new Stack<>();
      ParseTree child = ctx.getChild(1);

      // Go down the rabbit hole
      while (child instanceof StatementListContext) {
        StatementListContext castedChild = (StatementListContext) child;

        // If there are more siblings
        if (castedChild.children.size() == 2) {
          parseTreeStack.push(castedChild.getChild(1));
        }

        // Go down
        child = castedChild.getChild(0);
      }

      // Add the last statement (or first, if we never went down the rabbit hole)
      parseTreeStack.push(child);

      // empty the stack
      while (!parseTreeStack.empty()) {
        add((Statement) visitor.visit(parseTreeStack.pop()));
      }
    }

    // scope out
    visitor.scopeOut();

    return this;
  }
}
