package factories;

import symbol.Symbol;

public interface Factory<T extends Symbol> {
    int is(String input, int pos);
    T makeInstance(String literal);
}
