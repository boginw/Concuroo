package lexer;

import org.junit.Test;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.SymbolType;


import static org.junit.Assert.*;

public class LexerTest {

    @Test
    public void simpleAssignment() {
        Lexer l = new Lexer(new SymbolTable());
        String input = "a=5";
        Symbol[] s = l.lex(input);

        assertEquals(3, s.length);
        assertEquals(SymbolType.IDENT, s[0].type);
        assertEquals("a", s[0].literal);
        assertEquals(SymbolType.DECLARATION, s[1].type);
        assertEquals(SymbolType.INT_LITERAL, s[2].type);
    }
}