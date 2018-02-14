package factories;

import nodes.expressions.ModExpression;

public class ModFactory implements Factory<ModExpression> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '%'  ? pos + 1 : -1;
    }

    @Override
    public ModExpression makeInstance(String literal) {
        return new ModExpression();
    }
}
