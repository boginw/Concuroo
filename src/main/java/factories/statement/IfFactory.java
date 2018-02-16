package factories.statement;

import nodes.Node;
import nodes.statements.IfStatement;
import symbol.SymbolTable;
import language.TokenType;

public class IfFactory implements StatementFactory<IfStatement> {

    @Override
    public int is(String input) {
        return input.startsWith("if") ? 2 : -1;
    }

    @Override
    public Node makeNode(String literal) {
        return new IfStatement();
    }

    @Override
    public TokenType getType() {
        return TokenType.STATEMENT;
    }

    @Override
    public int is(Node[] symbols) {
        return 0;
    }

    @Override
    public IfStatement makeInstance(Node[] symbols, SymbolTable st) {
        return null;
    }
}
