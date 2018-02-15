package factories;

import nodes.expressions.DifferenceExpression;

public class DifferenceFactory implements Factory<DifferenceExpression> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '-'  ? pos + 1 : -1;
    }

    @Override
    public DifferenceExpression makeInstance(String literal) {
        return new DifferenceExpression();
    }
}
