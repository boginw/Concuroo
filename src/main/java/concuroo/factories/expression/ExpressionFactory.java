package concuroo.factories.expression;

import concuroo.factories.Factory;
import concuroo.nodes.expressions.Expression;

/**
 * Defines how Expressions should be created by the Lexer
 * @param <T> Expression T
 */
public interface ExpressionFactory<T extends Expression> extends Factory<T> {
    /**
     * Gets the Regex pattern to detect the Expression T
     * @return Regex Pattern
     */
    String getPattern();
}
