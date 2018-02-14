package symbol;

public enum SymbolType {
    CONTROL,
    OPERATOR,
    IDENT,
    INT_LITERAL,
    STRING_LITERAL,
    FLOAT_LITERAL,
    EOF,
    TYPE,
    QUALIFIER;

    public long getValue(){
        return 1 << this.ordinal();
    }
}
