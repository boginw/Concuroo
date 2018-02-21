package concuroo.nodes.expressions.operators;

import concuroo.language.Associativity;
import concuroo.nodes.expressions.Expression;


public interface Operator extends Expression {

  /**
   * Gets the operator precedence
   *
   * @return Precedence
   */
  int getPrecedence();

  /**
   * Gets the Associativity of the operator
   *
   * @return Associativity
   */
  Associativity getAssociativity();
}
