package factories;

import nodes.expressions.DivideExpression;

public class DivideFactory implements Factory<DivideExpression> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '/'  ? pos + 1 : -1;
    }

    @Override
    public DivideExpression makeInstance(String literal) {
        return new DivideExpression();
    }
}
