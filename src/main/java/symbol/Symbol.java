package symbol;

abstract public class Symbol {
    public final int precedence;
    public String literal;
    public final SymbolType type;

    public Symbol(String literal, int precedence, SymbolType type){
        this.precedence = precedence;
        this.literal = literal;
        this.type = type;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof Symbol) && literal.equals(((Symbol)o).literal);
    }
}