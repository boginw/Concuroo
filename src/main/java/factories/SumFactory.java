package factories;

import nodes.expressions.SumExpression;

public class SumFactory implements Factory<SumExpression> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return (c == '+') ? (pos + 1) : -1;
    }

    @Override
    public SumExpression makeInstance(String literal) {
        return new SumExpression();
    }
}
