package factories.expression;

import nodes.expressions.operators.groups.Group;
import symbol.TokenType;

import java.util.function.Supplier;

public class GroupFactory<T extends Group> implements ExpressionFactory<T> {
    private final char aChar;
    private Supplier<T> supplier;

    public GroupFactory(char start, Supplier<T> supplier){
        this.aChar = start;
        this.supplier = supplier;
    }

    @Override
    public String getPattern() {
        return String.format("^%s", aChar);
    }

    @Override
    public int is(String input) {
        char c = input.charAt(0);
        return (c == aChar) ? 1 : -1;
    }

    @Override
    public T makeNode(String literal) {
        T group = supplier.get();
        return group;
    }

    @Override
    public TokenType getType() {
        return null;
    }
}
