package concuroo.factories.statement;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;

public interface StatementFactory<T extends Statement> extends Factory{
    int is(Node[] symbols);
    T makeInstance(Node[] symbols, SymbolTable st);
}
