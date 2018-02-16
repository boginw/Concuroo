package nodes.expressions.operators;

import nodes.expressions.Expression;
import symbol.Associativity;

public interface Operator extends Expression {
    int getPrecedence();
    Associativity getAssociativity();
}
