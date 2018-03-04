package concuroo.nodes.expressions.operators.indecisive;

import concuroo.nodes.expressions.operators.binary.BinaryOperator;
import concuroo.nodes.expressions.operators.unary.UnaryOperator;

public interface Indecisive extends UnaryOperator, BinaryOperator {
  int getUnaryPrecedence();
}
