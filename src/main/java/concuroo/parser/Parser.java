package concuroo.parser;

import concuroo.language.LG;
import concuroo.nodes.Node;
import concuroo.nodes.expressions.Expression;
import concuroo.nodes.expressions.operators.Operator;
import concuroo.nodes.expressions.operators.binary.BinaryOperator;
import concuroo.nodes.expressions.operators.groups.Group;
import concuroo.nodes.expressions.operators.unary.UnaryOperator;
import concuroo.nodes.statements.Statement;
import concuroo.symbol.SymbolTable;
import java.util.Stack;

public class Parser {

  public static Statement AST(Node[] tokens, LG lg, SymbolTable st) {
    return StatementAST(tokens, lg, st);
  }

  public static Statement StatementAST(Node[] tokens, LG lg, SymbolTable st) {
    return (Statement) lg.lookupStatement(tokens, st);
  }

  public static Node ExpressionAST(Node[] tokens, SymbolTable st) {
    // Shutting-yard algorithm
    // https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail
    Stack<Node> operators = new Stack<>();
    Stack<Node> outputs = new Stack<>();

    for (Node t : tokens) {
      if (t instanceof Group && ((Group) t).isStart()) {
        operators.push(t);
      } else if (t instanceof BinaryOperator) {
        if (!operators.empty()) {
          BinaryOperator op = (BinaryOperator) operators.peek();

          while (!operators.empty() && op.getPrecedence() <= ((BinaryOperator) t).getPrecedence() &&
              !op.getLiteral().equals("(")) {
            Node operator = operators.pop();
            expressionFromOperator(operator, outputs);
            op = (BinaryOperator) operators.peek();
          }
        }
        operators.push(t);
      } else if (t instanceof Group && !((Group) t).isStart()) {
        while (!operators.empty() && !operators.peek().getLiteral().equals("(")) {
          Operator operator = (Operator) operators.pop();
          expressionFromOperator(operator, outputs);
        }
        operators.pop();
            /*} else if(t.type == TokenType.VARIABLE) {
                outputs.push((Node) st.lookup(t.literal));*/
      } else {
        outputs.push(t);
      }
    }

    while (!operators.empty()) {
      Node operator = operators.pop();
      expressionFromOperator(operator, outputs);
    }

    return outputs.pop();
  }

  private static void expressionFromOperator(Node operator, Stack<Node> outputs) {
    Expression exp;
    if (operator instanceof Expression) {
      exp = ((Expression) operator);
      if (operator instanceof BinaryOperator) {
        Node e2 = outputs.pop();
        Node e1 = outputs.pop();
        ((BinaryOperator) exp).setLeft((Expression) e1);
        ((BinaryOperator) exp).setRight((Expression) e2);
      } else if (operator instanceof UnaryOperator) {
        Node e1 = outputs.pop();
        ((UnaryOperator) exp).setLeft((Expression) e1);
      }
    } else {
      // TODO: handle error
      return;
    }

    outputs.push(exp);
  }
}
