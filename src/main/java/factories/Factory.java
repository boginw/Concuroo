package factories;

import nodes.Node;
import symbol.TokenType;

/**
 * This interface defines two critical part of the lexer
 * and helps the AST later on.
 * @param <T> Type of Node
 */
public interface Factory<T extends Node> {
    /**
     * A method that tests if a given input has Node T
     * at the start the string.
     * @param input String to check
     * @return -1 for no match, length of match otherwise
     */
    int is(String input);

    /**
     * Creates a Node T from string
     * This is used by the lexer
     * @param literal The literal string found
     * @return A Node T from input
     */
    T makeNode(String literal);

    TokenType getType();
}
