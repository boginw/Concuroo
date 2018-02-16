package factories.statement;

import factories.Factory;
import nodes.Node;
import nodes.statements.Statement;
import symbol.SymbolTable;

public interface StatementFactory<T extends Statement> extends Factory{
    int is(Node[] symbols);
    T makeInstance(Node[] symbols, SymbolTable st);
}
