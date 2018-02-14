package symbol;

public class EOF extends Symbol {
    public EOF() {
        super("\0", 0, SymbolType.EOF);
    }
}
