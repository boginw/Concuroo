package concuroo.factories.expression.parselets;

import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.binary.BinaryOperator;
import concuroo.nodes.expressions.operators.indecisive.Indecisive;
import concuroo.nodes.expressions.operators.unary.UnaryOperator;
import concuroo.parser.Parser;

public class Parselets {

  public static <T extends UnaryOperator> Expression parse(T expr, Parser parser, Expression pre,
      Node in, Expression post) {
    if (pre != null) {
      expr.setOperand(pre);
    } else {
      expr.setOperand(parser.parseExpression(expr.getPrecedence()));
    }

    return expr;
  }

  public static <T extends BinaryOperator> Expression parse(T expr, Parser parser, Expression pre,
      Node in, Expression post) {
    expr.setOperand(pre);

    if (post != null) {
      expr.setSecondOperand(post);
    } else {
      expr.setSecondOperand(parser.parseExpression(expr.getPrecedence()));
    }

    return expr;
  }

  public static <T extends Indecisive> Expression parse(T expr, Parser parser, Expression pre,
      Node in, Expression post) {
    expr.setOperand(pre);

    if (post != null) {
      expr.setSecondOperand(post);
    } else {
      expr.setSecondOperand(parser.parseExpression(expr.getPrecedence()));
    }

    return expr;
  }

}
