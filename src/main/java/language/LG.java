package language;

import factories.*;
import nodes.Node;
import nodes.TokenNode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LG {
    public enum LanguageType {
        EXPR,
        STAT
    }

    private HashMap<String, Object[]> tokens;
    private List<Pair<Object[][], Factory<?>>> statements;

    public void registerToken(String key, Factory<?> factory){
        tokens.put(key, new Object[]{factory});
    }

    public void registerToken(String key, String pattern, TokenType type){
        tokens.put(key, new Object[]{pattern, type});
    }

    public void registerStatement(Factory<?> factory, Object[][] objects){
        statements.add(new ImmutablePair<>(objects, factory));
    }

    public Node lookupToken(String input){
        String found;
        for (Map.Entry<String, Object[]> t: tokens.entrySet()) {
            Object[] def = t.getValue();
            if(def.length == 2){
                String pattern = (String) def[0];
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(input);
                if(m.find()){
                    found = m.group();
                    return new TokenNode(t.getKey(), (TokenType) def[1], found);
                }
            } else if(def.length == 1) {
                Factory<?> f = (Factory<?>) def[0];
                int newPos = f.is(input);
                if(newPos != -1){
                    found = input.substring(0, newPos);
                    return f.makeNode(found);
                }
            }
        }

        // TODO: throw
        return null;
    }

    public Node lookupStatement(Node[] tokens){
        return null;
    }



    /*// Control
    EOF     = "\0"
    ILLEGAL = "ILLEGAL"
    IDENT   = "IDENT"
    COMMA   = "

    // Comparison
    EQUALS    = "=="
    NOT_EQUAL = "!="
    LESS      = "<"
    GREATER   = ">"
    LE        = "<="
    GE        = ">="

    // Boolean
    NOT = "!"
    AND = "&&"
    OR  = "||"

    // Pointer access
    REF     = "*"
    ADDR_OF = "&"

    // Bitwise
    BIT_AND    = "&"
    BIT_SHL    = "<<"
    BIT_SHR    = ">>"
    BIT_XOR    = "^"
    BIT_OR     = "|"
    BIT_NEGATE = "~"

    // Compound
    BIT_AND_ASSIGN = "&="
    BIT_OR_ASSIGN  = "|="
    TIMES_ASSIGN   = "*="
    PLUS_ASSIGN    = "+="
    MINUS_ASSIGN   = "-="
    DIVIDE_ASSIGN  = "/="
    INCREMENT      = "++"
    DECREMENT      = "--"

    // Assign
    ASSIGN = "="

    // Types
    INT      = "INT"
    CHAR     = "CHAR"
    BYTE     = "BYTE"
    FLOAT    = "FLOAT"
    BOOL     = "BOOL"
    DOUBLE   = "DOUBLE"
    LONG     = "LONG"
    SHORT    = "SHORT"
    STRING   = "STRING"
    UNSIGNED = "UNSIGNED"
    VOID     = "VOID"
    WORD     = "WORD"

    // Qualifiers
    CONST    = "CONST"
    STATIC   = "STATIC"
    VOLATILE = "VOLATILE"
    SCOPE    = "SCOPE"

    // Control-structure
    IF       = "IF"
    FOR      = "FOR"
    WHILE    = "WHILE"
    DO       = "DO"
    BREAK    = "BREAK"
    CONTINUE = "CONTINUE"
    ELSE     = "ELSE"
    ELSE_IF  = "ELSE IF"
    GOTO     = "GOTO"
    RETURN   = "RETURN"
    SWITCH   = "SWITCH"
    CASE     = "CASE"
    */
}
