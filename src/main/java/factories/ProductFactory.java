package factories;

import nodes.expressions.ProductExpression;

public class ProductFactory implements Factory<ProductExpression> {
    @Override
    public int is(String input, int pos) {
        char c = input.charAt(pos);
        return c == '*'  ? pos + 1 : -1;
    }

    @Override
    public ProductExpression makeInstance(String literal) {
        return new ProductExpression();
    }
}
