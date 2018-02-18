package concuroo.factories.statement;

import concuroo.language.Utilities;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.statements.BlockStatement;
import concuroo.symbol.SymbolTable;

public class BlockFactory implements StatementFactory<BlockStatement> {
    @Override
    public int is(Node[] symbols) {
        if(symbols[0] instanceof Curly && symbols[0].getLiteral().equals("{")){
            return Utilities.findClosingToken(symbols, 1, Curly.class);
        }
        return -1;
    }

    @Override
    public BlockStatement makeInstance(Node[] symbols, SymbolTable st) {
        BlockStatement stat = new BlockStatement();
        int j = Utilities.findClosingToken(symbols, 1, Curly.class);
        return stat;
    }

    @Override
    public int is(String input) {
        char c = input.charAt(0);
        return c == '{' || c == '}' ? 1 : -1;
    }

    @Override
    public boolean is(Node node) {
        return node instanceof BlockStatement;
    }

    @Override
    public Node makeNode(String literal) {
        return new BlockStatement();
    }
}
