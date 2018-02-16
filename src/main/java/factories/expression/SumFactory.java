package factories.expression;

import nodes.expressions.operators.binary.SumExpression;
import language.TokenType;

public class SumFactory implements ExpressionFactory<SumExpression> {
    @Override
    public String getPattern() {
        return "^\\+";
    }

    @Override
    public int is(String input) {
        return input.charAt(0) == '+' ? 1 : -1;
    }

    @Override
    public SumExpression makeNode(String literal) {
        return new SumExpression(literal);
    }

    @Override
    public TokenType getType() {
        return TokenType.BINARY;
    }
}
