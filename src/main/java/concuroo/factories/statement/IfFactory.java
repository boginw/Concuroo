package concuroo.factories.statement;

import concuroo.language.Utilities;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.operators.groups.Curly;
import concuroo.nodes.expressions.operators.groups.Parenthesis;
import concuroo.nodes.statements.IfStatement;
import concuroo.symbol.SymbolTable;

import java.util.Arrays;

public class IfFactory implements StatementFactory<IfStatement> {

    @Override
    public int is(String input) {
        return input.startsWith("if") ? 2 : -1;
    }

    @Override
    public boolean is(Node node) {
        return node instanceof IfStatement;
    }

    @Override
    public Node makeNode(String literal) {
        return new IfStatement();
    }

    @Override
    public int is(Node[] tokens) {
        if(tokens.length < 6)
        if(!(tokens[0] instanceof IfStatement)){
            return -1;
        } else if(!(tokens[1] instanceof Parenthesis) || !((Parenthesis)tokens[1]).isStart()){
            return -1;
        }

        int par = Utilities.findClosingToken(tokens, 1, Parenthesis.class);

        if(par == -1){
            return -1;
        }

        if(!(tokens[par] instanceof Curly) || !((Curly)tokens[par]).isStart()){
            // TODO: syntax error;
            return -1;
        }

        Node[] condition = Arrays.copyOfRange(tokens, 2, par);

        int cur = Utilities.findClosingToken(tokens, 1, Curly.class);

        if(cur == -1){
            return -1;
        }

        Node[] consequence = Arrays.copyOfRange(tokens, 2, par);
        return -1;
    }

    @Override
    public IfStatement makeInstance(Node[] symbols, SymbolTable st) {
        return new IfStatement();
    }
}
