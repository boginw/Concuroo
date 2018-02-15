package factories;

import nodes.IdentifierNode;
import nodes.statements.AssignmentStatement;
import parser.Parser;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.SymbolType;

import java.util.Arrays;

public class AssignmentFactory implements
        StatementFactory<AssignmentStatement>, Factory<AssignmentStatement> {

    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '=' ? pos + 1 : -1;
    }

    @Override
    public AssignmentStatement makeInstance(String literal) {
        if(is(literal, 0) == -1) {
            return null;
        }

        return new AssignmentStatement(literal);
    }

    @Override
    public int is(Symbol[] symbols) {
        return (symbols.length > 2 && symbols[0].type == SymbolType.IDENT &&
                symbols[1].type == SymbolType.DECLARATION) ? 1 : -1;
    }

    @Override
    public AssignmentStatement makeInstance(Symbol[] symbols, SymbolTable st) {
        st.insert(symbols[0]);

        AssignmentStatement stat = new AssignmentStatement("");
        stat.setIdent((IdentifierNode) symbols[0]);

        Symbol[] sm = Arrays.copyOfRange(symbols,2,symbols.length);
        stat.setValue(Parser.ExpressionAST(sm, st));
        return stat;
    }
}
