package symbol;

import factories.*;

public class LG {
    public static Factory<?> symbols[] = new Factory[]{
            new BracesFactory(),
            new DifferenceFactory(),
            new DivideFactory(),
            new IdentifierFactory(),
            new IntegerFactory(),
            new ModFactory(),
            new ProductFactory(),
            new SumFactory(),
            new AssignmentFactory()
    };

    public static StatementFactory<?> statements[] = new StatementFactory[]{
            new AssignmentFactory(),
            new IfFactory()
    };

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
    CASE     = "CASE"*/

}
