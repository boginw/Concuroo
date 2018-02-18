package concuroo.factories.statement;

import concuroo.factories.Factory;
import concuroo.nodes.Node;
import concuroo.nodes.statements.Statement;

import java.util.List;

public interface StatementFactory<T extends Statement> extends Factory{
    T makeInstance(Node[] symbols, List<Node> st);
}
