package factories;

import nodes.statements.IfStatement;
import parser.Parser;
import symbol.Symbol;
import symbol.SymbolTable;

import java.util.Arrays;

public class IfFactory implements StatementFactory<IfStatement> {

    @Override
    public int is(Symbol[] symbols) {
        if(symbols.length >= 5 && symbols[0].literal.equals("if") &&
                symbols[1].literal.equals("(") &&
                symbols[symbols.length - 2].literal.equals(")") &&
                symbols[symbols.length - 1].literal.equals("{")){
            return symbols.length;
        }
        return -1;
    }

    @Override
    public IfStatement makeInstance(Symbol[] symbols, SymbolTable st) {
        IfStatement f = new IfStatement();

        Symbol[] sm = Arrays.copyOfRange(symbols, 2, symbols.length - 2);
        f.setCondition(Parser.ExpressionAST(sm, st));
        f.setConsequence(null);

        return f;
    }
}
