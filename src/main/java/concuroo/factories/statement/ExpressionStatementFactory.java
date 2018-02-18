package concuroo.factories.statement;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.statements.ExpressionStatement;
import concuroo.parser.Parser;
import concuroo.symbol.SymbolTable;

import java.util.Arrays;

public class ExpressionStatementFactory implements StatementFactory<ExpressionStatement>{
    @Override
    public int is(Node[] symbols) {
        if(!(symbols[0] instanceof Expression)){
            return -1;
        }

        int i = 1;
        while(i < symbols.length && symbols[i] instanceof Expression){
            i++;
        }
        return i;
    }

    @Override
    public ExpressionStatement makeInstance(Node[] symbols, SymbolTable st) {
        ExpressionStatement e = new ExpressionStatement();
        int i = 0;
        while(i < symbols.length && symbols[i] instanceof Expression){
            i++;
        }
        e.setExpr(Parser.ExpressionAST(Arrays.copyOfRange(symbols, 0, i), new SymbolTable()));
        return e;
    }

    @Override
    public int is(String input) {
        return -1;
    }

    @Override
    public boolean is(Node node) {
        return node instanceof ExpressionStatement;
    }

    @Override
    public Node makeNode(String literal) {
        return new ExpressionStatement();
    }
}
