package nodes.statements;

import nodes.Node;
import symbol.Symbol;
import symbol.SymbolType;

public abstract class Statement extends Symbol implements Node {
    public Statement(String literal, int precedence, SymbolType type) {
        super(literal, precedence, type);
    }
}
