package factories;

import nodes.statements.Statement;
import symbol.Symbol;
import symbol.SymbolTable;

public interface StatementFactory<T extends Statement> {
    int is(Symbol[] symbols);
    T makeInstance(Symbol[] symbols, SymbolTable st);
}
