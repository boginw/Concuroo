package parser;


import lexer.Lexer;
import nodes.Node;
import nodes.expressions.Expression;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.SymbolType;

import java.util.Stack;

public class Parser {
    public static Node AST(String input){
        Lexer l = new Lexer(new SymbolTable());
        Symbol[] symbols = l.lex(input);

        // Shutting-yard algorithm
        // https://en.wikipedia.org/wiki/Shunting-yard_algorithm#The_algorithm_in_detail
        Stack<Symbol> operators = new Stack<>();
        Stack<Node> outputs = new Stack<>();

        for(Symbol t : symbols){
            if(t.type == SymbolType.CONTROL && t.literal.equals("(")){
                operators.push(t);
            } else if((t.type.getValue() & SymbolType.OPERATOR.getValue()) > 0) {
                if(!operators.empty()){
                    Symbol op = operators.peek();

                    while(!operators.empty() && op.precedence <= t.precedence && !op.literal.equals("(")){
                        Symbol operator = operators.pop();
                        expressionFromOperator(operator, outputs);
                        op = operators.peek();
                    }
                }
                operators.push(t);
            } else if(t.type == SymbolType.CONTROL && t.literal.equals(")")){
                while(!operators.empty() && !operators.peek().literal.equals("(")){
                    Symbol operator = operators.pop();
                    expressionFromOperator(operator, outputs);
                }
                operators.pop();
            } else {
                outputs.push((Node) t);
            }
        }

        while (!operators.empty()){
            Symbol operator = operators.pop();
            expressionFromOperator(operator, outputs);
        }

        return outputs.pop();
    }

    private static void expressionFromOperator(Symbol operator, Stack<Node> outputs){
        Expression exp;
        if(operator instanceof Expression){
            exp = ((Expression) operator);
        } else {
            // TODO: handle error
            return;
        }

        Node e2 = outputs.pop();
        Node e1 = outputs.pop();
        exp.setLeft(e1);
        exp.setRight(e2);
        outputs.push(exp);
    }
}
